/*Selección de la base de datos. */
USE restaurate;
/*Creacion de tablas*/
CREATE TABLE IF NOT EXISTS Mesa ( --añadir activo.
    numero INT PRIMARY KEY,
    estado BOOLEAN DEFAULT TRUE, 
    activo BOOLEAN NOT NULL DEFAULT TRUE;
);
CREATE TABLE IF NOT EXISTS Categoria (--añadir activo.
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) UNIQUE NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE;
);
CREATE TABLE IF NOT EXISTS Producto ( 
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion TEXT NOT NULL,
    precio DECIMAL(8,2),
    id_categoria INT NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY (id_categoria) REFERENCES Categoría(id)
);
CREATE TABLE IF NOT EXISTS Pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    precio_total DECIMAL(8,2),
    hora_pedido DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_pedido DATETIME DEFAULT CURRENT_TIMESTAMP,
    numero_mesa INT,
    completado BOOLEAN DEFAULT FALSE,
    pagado BOOLEAN DEFAULT FALSE,
    tipo_pago ENUM('EFECTIVO', 'TARJETA') DEFAULT 'EFECTIVO',
    FOREIGN KEY (numero_mesa) REFERENCES Mesa(numero)
    );

CREATE TABLE IF NOT EXISTS Pedido_plato (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT NOT NULL,
    codigo_plato INT NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES Pedido(id),
    FOREIGN KEY (codigo_plato) REFERENCEs Producto(codigo)
);


CREATE TABLE IF NOT EXISTS Empleado (
    dni VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(40),
    apellidos VARCHAR (100),
    direccion VARCHAR (100),
    telefono VARCHAR (20),
    salario DECIMAL(8,2),
    puesto VARCHAR (40),
    cuenta_bancaria VARCHAR(50) 
);
*/
CREATE TABLE IF NOT EXISTS Flujo_caja (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo ENUM('INGRESO', 'GASTO') NOT NULL,
    descripcion TEXT NOT NULL,
    cantidad DECIMAL(10,2) NOT NULL,
    fecha DATE NOT NULL,
    categoria VARCHAR(50) NOT NULL
);
CREATE TABLE IF NOT EXISTS Usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY, 
    administrador BOOLEAN DEFAULT FALSE,
    nombre_usuario VARCHAR(50) NOT NULL UNIQUE,
    contraseña VARCHAR(50) NOT NULL 
);

CREATE TABLE IF NOT EXISTS Historial_sesiones(
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo ENUM('INICIO_SESION', 'CIERRE_SESION') NOT NULL,
    concepto VARCHAR(300),
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    id_usuario INT NOT NULL,
    CONSTRAINT FOREIGN KEY(id_usuario) REFERENCES Usuarios(id)
);

DELIMITER //
/*Trigger para prevenir múltples admins.*/
CREATE TRIGGER prevenir_multiples_admins
BEFORE INSERT ON Usuarios
FOR EACH ROW
BEGIN
  IF NEW.administrador = TRUE THEN
    IF (SELECT COUNT(*) FROM Usuarios WHERE administrador = TRUE) > 0 THEN
      SIGNAL SQLSTATE '45000'
      SET MESSAGE_TEXT = 'Solo puede existir un administrador.';
    END IF;
  END IF;
END;
//

DELIMITER ;

/*Trigger para actualizar las estadisticas de ventas cada vez que se vende un producto*/

DELIMITER //

CREATE TRIGGER agregar_actualizar_estadisticas_venta
AFTER INSERT ON Pedido_plato
FOR EACH ROW
BEGIN
    INSERT INTO Estadisticas_venta (id_producto, cantidad)
    VALUES (NEW.codigo_plato, NEW.cantidad)
    ON DUPLICATE KEY UPDATE cantidad = cantidad + NEW.cantidad;
END;
//

DELIMITER ;

/*Creamos el procedimiento para crear un pedido*/
DELIMITER $$
CREATE PROCEDURE crear_pedido(
    IN p_cliente_id INT,
    IN p_mesa_id INT,
    IN p_fecha DATE,
    IN p_hora TIME
)
BEGIN
    INSERT INTO pedidos(cliente_id, mesa_id, fecha, hora, facturado, total)
    VALUES (p_cliente_id, p_mesa_id, p_fecha, p_hora, 0, 0);
END$$
DELIMITER ;
/*Creamos el procedimiento para añadir un producto al pedido*/
/*Mirar bien la query*/
DELIMITER $$
CREATE PROCEDURE agregar_producto_a_pedido(
    IN p_pedido_id INT,
    IN p_producto_id INT,
    IN p_cantidad INT
)
BEGIN
    DECLARE precio_unitario DECIMAL(10,2);

    SELECT precio INTO precio_unitario
    FROM productos
    WHERE id = p_producto_id;

    INSERT INTO detalle_pedido(pedido_id, producto_id, cantidad)
    VALUES (p_pedido_id, p_producto_id, p_cantidad);

    UPDATE pedidos
    SET total = total + (precio_unitario * p_cantidad)
    WHERE id = p_pedido_id;
END$$
DELIMITER ;
/*Procedimiento devuelve todos los pedidos  activos */
DELIMITER $$
CREATE PROCEDURE obtener_pedidos_por_mesa(IN p_numero_mesa INT)
BEGIN
    SELECT p.*
    FROM pedidos p
    JOIN mesas m ON p.mesa_id = m.id
    WHERE m.numero = p_numero_mesa AND p.facturado = 0;
END$$
DELIMITER ;
/*Modificar la cantidad de un producto ya añadido*/

DELIMITER $$
CREATE PROCEDURE modificar_cantidad_producto_en_pedido(
    IN p_pedido_id INT,
    IN p_producto_id INT,
    IN p_nueva_cantidad INT
)
BEGIN
    DECLARE precio_unitario DECIMAL(10,2);
    DECLARE cantidad_anterior INT;

    SELECT precio INTO precio_unitario FROM productos WHERE id = p_producto_id;
    SELECT cantidad INTO cantidad_anterior FROM detalle_pedido 
    WHERE pedido_id = p_pedido_id AND producto_id = p_producto_id;

    UPDATE detalle_pedido
    SET cantidad = p_nueva_cantidad
    WHERE pedido_id = p_pedido_id AND producto_id = p_producto_id;

    UPDATE pedidos
    SET total = total - (precio_unitario * cantidad_anterior) + (precio_unitario * p_nueva_cantidad)
    WHERE id = p_pedido_id;
END$$
DELIMITER ;

/*Procedimiento: Eliminar el producto del pedido*/
DELIMITER $$
CREATE PROCEDURE eliminar_producto_de_pedido(
    IN p_pedido_id INT,
    IN p_producto_id INT
)
BEGIN
    DECLARE precio_unitario DECIMAL(10,2);
    DECLARE cantidad INT;

    SELECT precio INTO precio_unitario FROM productos WHERE id = p_producto_id;
    SELECT cantidad INTO cantidad FROM detalle_pedido 
    WHERE pedido_id = p_pedido_id AND producto_id = p_producto_id;

    DELETE FROM detalle_pedido
    WHERE pedido_id = p_pedido_id AND producto_id = p_producto_id;

    UPDATE pedidos
    SET total = total - (precio_unitario * cantidad)
    WHERE id = p_pedido_id;
END$$
DELIMITER ;

/*Procedimiento para hacer una mesa, si id coincide con el de otra mesa, se actualiza el estado.*/
DELIMITER $$

CREATE PROCEDURE CrearOModificarMesa(IN p_numero INT, IN p_estado BOOLEAN)
BEGIN
    DECLARE mesa_activa BOOLEAN;

    -- Verifica si existe la mesa
    IF EXISTS (SELECT 1 FROM Mesa WHERE numero = p_numero) THEN
        -- Si existe, verificamos si está inactiva
        SELECT activo INTO mesa_activa FROM Mesa WHERE numero = p_numero;

        IF mesa_activa = FALSE THEN
            -- Activamos la mesa si está inactiva
            UPDATE Mesa SET activo = TRUE, estado = p_estado WHERE numero = p_numero;
        END IF;

    ELSE
        -- Si no existe, la insertamos
        INSERT INTO Mesa (numero, estado, activo) VALUES (p_numero, p_estado, TRUE);
    END IF;
END$$

DELIMITER ;


/*Índice para consultas para fechas de un pedido*/
CREATE INDEX indice_pedidos_fecha ON pedidos(fecha);

/*índice de platos. */

CREATE INDEX idx_producto_categoria ON Producto(nombre, id_categoria);

/* Para detalles de un pedido. */
CREATE INDEX idx_pedido_plato_compuesto ON Pedido_plato(id_pedido, codigo_plato);
 /*Índices para roles de usuarios. */
CREATE INDEX idx_empleado_puesto ON Empleado(puesto);

/*Vistas ventas detalladas*/
CREATE VIEW vista_ventas_detalladas AS
SELECT 
  v.id_venta,
  v.fecha,
  c.nombre AS cliente,
  p.nombre AS producto,
  dv.cantidad,
  dv.precio_unitario,
  (dv.cantidad * dv.precio_unitario) AS total
FROM ventas v
JOIN clientes c ON v.id_cliente = c.id_cliente
JOIN detalle_ventas dv ON v.id_venta = dv.id_venta
JOIN productos p ON dv.id_producto = p.id_producto;
