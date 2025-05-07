
/*Datos para categorías. */
INSERT INTO Categoria (nombre) VALUES
('Entrantes'),
('Platos principales'),
('Postres'),
('Bebidas'),
('Vinos'),
('Cervezas'),
('Ensaladas'),
('Copas Vino'),
('Varios'),
('Platos niño'),
('Centors');
/*Datos para prodcutos*/
INSERT INTO Producto (nombre, descripcion, precio, id_categoria) VALUES
-- Entrantes (id_categoria = 1)
('Patatas Bravas', 'Patatas fritas con salsa brava casera', 4.50, 1),
('Croquetas de Jamón', 'Croquetas cremosas de jamón ibérico', 5.00, 1),

-- Platos principales (id_categoria = 2)
('Entrecot de Ternera', 'Entrecot a la parrilla con guarnición', 14.50, 2),
('Paella Mixta', 'Paella con marisco y pollo', 13.00, 2),

-- Postres (id_categoria = 3)
('Tarta de Queso', 'Tarta de queso casera con mermelada', 4.20, 3),
('Helado de Vainilla', 'Bola de helado artesanal de vainilla', 3.00, 3),

-- Bebidas (id_categoria = 4)
('Coca-Cola', 'Refresco de cola 33cl', 2.00, 4),
('Agua Mineral', 'Botella de agua 50cl', 1.50, 4),

-- Vinos (id_categoria = 5)
('Rioja Crianza', 'Vino tinto D.O. Rioja Crianza', 12.00, 5),
('Albariño', 'Vino blanco D.O. Rías Baixas', 11.00, 5);


INSERT INTO Producto (nombre, descripcion, precio, id_categoria) VALUES
-- Entrantes (id_categoria = 1)
('Patatas Bravas', 'Patatas fritas con salsa brava casera', 4.50, 1),
('Croquetas de Jamón', 'Croquetas cremosas de jamón ibérico', 5.00, 1),

-- Platos principales (id_categoria = 2)
('Entrecot de Ternera', 'Entrecot a la parrilla con guarnición', 14.50, 2),
('Paella Mixta', 'Paella con marisco y pollo', 13.00, 2),

-- Postres (id_categoria = 3)
('Tarta de Queso', 'Tarta de queso casera con mermelada', 4.20, 3),
('Helado de Vainilla', 'Bola de helado artesanal de vainilla', 3.00, 3),

-- Bebidas (id_categoria = 4)
('Coca-Cola', 'Refresco de cola 33cl', 2.00, 4),
('Agua Mineral', 'Botella de agua 50cl', 1.50, 4),

-- Vinos (id_categoria = 5)
('Rioja Crianza', 'Vino tinto D.O. Rioja Crianza', 12.00, 5),
('Albariño', 'Vino blanco D.O. Rías Baixas', 11.00, 5),

-- Cervezas (id_categoria = 6)
('Estrella Galicia', 'Cerveza rubia 33cl', 2.50, 6),
('Mahou Cinco Estrellas', 'Cerveza clásica 33cl', 2.40, 6),

-- Ensaladas (id_categoria = 7)
('Ensalada César', 'Lechuga, pollo, picatostes, salsa César', 6.50, 7),

-- Copas Vino (id_categoria = 8)
('Copa Rioja', 'Copa de vino tinto D.O. Rioja', 2.80, 8),

-- Centros (id_categoria = 9)
('Tabla de Jamón', 'Jamón ibérico de bellota con pan y tomate', 15.00, 9),

-- Varios (id_categoria = 10)
('Pan', 'Ración de pan', 1.00, 10),

-- Platos niño (id_categoria = 11)
('Escalope con patatas', 'Escalope de pollo con patatas fritas', 7.00, 11);


/*Datos empleado*/
INSERT INTO Empleado (dni, nombre, apellidos, direccion, telefono, salario, puesto, cuenta_bancaria) VALUES
('12345678A', 'Lucía', 'Martínez López', 'Calle Mayor 12, Madrid', '600123456', 1800.00, 'Camarera', 'ES7620770024003102575766'),
('23456789B', 'Carlos', 'Pérez Gómez', 'Av. Andalucía 34, Sevilla', '611234567', 2200.00, 'Cocinero', 'ES9121000418450200051332'),
('34567890C', 'María', 'Ruiz Sánchez', 'Calle Real 21, Valencia', '622345678', 2000.00, 'Encargada', 'ES3421001234567890123456'),
('45678901D', 'Javier', 'Fernández Torres', 'Calle Luna 5, Zaragoza', '633456789', 1600.00, 'Ayudante de cocina', 'ES3023100001180000012345'),
('56789012E', 'Laura', 'García Díaz', 'Calle Sol 8, Málaga', '644567890', 1700.00, 'Camarera', 'ES9021002456789012345678'),
('67890123F', 'Pedro', 'Morales Ruiz', 'Av. del Mar 77, Cádiz', '655678901', 1900.00, 'Camarero', 'ES8720385785941234567890'),
('78901234G', 'Ana', 'López Ortega', 'Paseo del Prado 15, Madrid', '666789012', 2100.00, 'Chef principal', 'ES4521007845123456789012'),
('89012345H', 'David', 'Serrano Núñez', 'Calle Ronda 23, Granada', '677890123', 1500.00, 'Repartidor', 'ES7321005678123456789012'),
('90123456I', 'Carmen', 'Torres Rivas', 'Camino Viejo 45, Bilbao', '688901234', 1850.00, 'Responsable de caja', 'ES3521009834567890123456'),
('01234567J', 'Alberto', 'Santos Pardo', 'Calle Nueva 3, Salamanca', '699012345', 1600.00, 'Limpiador', 'ES4621005678123456789011');

/*Datos usuario. */

INSERT INTO Usuarios (administrador, nombre_usuario, contraseña) VALUES
(FALSE, 'lucia_m', 'lucia2024'),
(FALSE, 'carlos_p', 'passcarlos'),
(FALSE, 'maria_ruiz', 'mr1987'),
(FALSE, 'javierft', 'javi2024'),
(FALSE, 'laura_gg', 'laura2024');
