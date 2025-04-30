
/*Creacion de tablas*/
CREATE TABLE IF NOT EXISTS Mesa (
    numero INT PRIMARY KEY,
    estado BOOLEAN DEFAULT TRUE /*Cambiar variable.*/
);
CREATE TABLE IF NOT EXISTS Categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) UNIQUE NOT NULL
);
CREATE TABLE IF NOT EXISTS Producto ( /*Cambiar a producto. */
    codigo INT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion TEXT NOT NULL,
    precio DECIMAL(8,2),
    id_categoria INT,
    FOREIGN KEY (id_categoria) REFERENCES Categoría(id)
);

CREATE TABLE IF NOT EXISTS Pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    precio_total DECIMAL(8,2),
    fecha_hora DATETIME DEFAULT CURRENT_TIMESTAMP,
    numero_mesa INT,
    completado BOOLEAN DEFAULT FALSE,
    pagado BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (numero_mesa) REFERENCES Mesa(numero)
    );

CREATE TABLE IF NOT EXISTS Pedido_plato (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT NOT NULL,
    codigo_plato INT NOT NULL,
    cantidad INT NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES Pedido(id),
    FOREIGN KEY (codigo_plato) REFERENCEs Producto(codigo)
);
CREATE TABLE IF NOT EXISTS Alergeno (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) UNIQUE NOT NULL
);
CREATE TABLE IF NOT EXISTS Producto_Alergeno (
    id_producto INT NOT NULL,
    id_alergeno INT NOT NULL,
    PRIMARY KEY (id_producto, id_alergeno), 
    FOREIGN KEY (id_producto) REFERENCES Producto(codigo),
    FOREIGN KEY (id_alergeno) REFERENCES Alergeno(id)
);

CREATE TABLE IF NOT EXISTS Estadisticas_venta (
    id_producto INT NOT NULL,
    cantidad INT,
    FOREIGN KEY (id_producto) REFERENCES Producto(id_producto)
);
/*INSERT INTO Alergeno (nombre) VALUES
('Leche'),
('Huevo'),
('Pescado'),
('Crustáceos'),
('Frutos de cáscara'),
('Cacahuete'),
('Soja'),
('Trigo'),
('Sésamo'),
('Apio'),
('Mostaza'),
('Sulfitos'),
('Altramuces'), 
('Moluscos');

*/

/*
/*De momento no*/
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
/*De momento no*/
CREATE TABLE IF NOT EXISTS Proveedor (
    codigo VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(40),
    direccion VARCHAR (100),
    cuenta_bancaria VARCHAR(50)
);
/*De momento no*/
CREATE TABLE IF NOT EXISTS Inventario (
    codigo_producto VARCHAR(20) PRIMARY KEY,
    cantidad INT NOT NULL,
    codigo_proveedor VARCHAR(20) NOT NULL,
    CONSTRAINT FOREIGN KEY (codigo_proveedor) REFERENCES Proveedor(codigo)
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
    id INT AUTO_INCREMENTE PRIMARY KEY, 
    administrador BOOLEAN DEFAULT FALSE,
    nombre_usuario VARCHAR(50) NOT NULL UNIQUE,
    contraseña VARCHAR(50) NOT NULL 
);

/*De momento vamos a hacer que solo haya un administrador*/
DELIMITER //

CREATE TRIGGER prevenir_multiples_admins
BEFORE INSERT ON usuarios
FOR EACH ROW
BEGIN
  IF NEW.es_admin = TRUE THEN
    IF (SELECT COUNT(*) FROM usuarios WHERE es_admin = TRUE) > 0 THEN
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
    
    DECLARE producto_id INT;
    SELECT id_producto INTO producto_id
    FROM Producto
    WHERE codigo = NEW.codigo_plato;

    INSERT INTO Estadisticas_venta (id_producto, cantidad)
    VALUES (producto_id, NEW.cantidad)
    ON DUPLICATE KEY UPDATE cantidad = cantidad + NEW.cantidad;

END;
//
DELIMITER ;



