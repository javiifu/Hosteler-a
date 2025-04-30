
/*Creacion de tablas*/
CREATE TABLE IF NOT EXISTS Mesa (
    numero INT PRIMARY KEY,
    estado BOOLEAN DEFAULT TRUE /*Cambiar variable.*/
);
/*Crear tabla Catergoría, producto tabla aux de categoría */
CREATE TABLE IF NOT EXISTS Producto ( /*Cambiar a producto. */
    codigo INT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion TEXT NOT NULL,
    precio DECIMAL(8,2)
);

CREATE TABLE IF NOT EXISTS Pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    precio_total DECIMAL(8,2),
    hora_pedido TIME,
    completado BOOLEAN DEFAULT FALSE
    );

CREATE TABLE IF NOT EXISTS Pedido_plato (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT NOT NULL,
    codigo_plato INT NOT NULL,
    cantidad INT NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES Pedido(id),
    FOREIGN KEY (codigo_plato) REFERENCEs Producto(codigo)
);

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
    
)






