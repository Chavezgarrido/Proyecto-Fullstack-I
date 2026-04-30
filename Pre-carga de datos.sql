CREATE DATABASE IF NOT EXISTS inventario_db;
USE inventario_db;

CREATE TABLE productos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255) NOT NULL,
    precio INT NOT NULL,
    stock INT NOT NULL
);

INSERT INTO productos (nombre, descripcion, precio, stock) VALUES
('Laptop ASUS ROG', 'Laptop de alta gama para gaming, 16GB RAM, RTX 3060', 1200, 15),
('Mouse Logitech MX Master 3', 'Mouse ergonómico inalámbrico con sensor de 4000 DPI', 99, 50),
('Monitor Samsung 27"', 'Monitor 4K UHD con panel IPS y 144Hz', 350, 20),
('Teclado Keychron K2', 'Teclado mecánico Bluetooth con switches Brown', 85, 30),
('Sony WH-1000XM4', 'Auriculares inalámbricos con cancelación de ruido', 299, 12),
('SSD Externo Samsung T7 1TB', 'Unidad de estado sólido portátil de alta velocidad', 110, 45),
('Silla Secretlab Titan', 'Silla de oficina ergonómica premium de cuero sintético', 450, 8),
('Logitech C920', 'Webcam Full HD 1080p para videollamadas y streaming', 70, 100),
('HP LaserJet Pro', 'Impresora láser monocromática de alta eficiencia', 180, 5),
('Cable HDMI Ultra High Speed', 'Cable de 2 metros compatible con 8K @ 60Hz', 15, 200);

CREATE DATABASE IF NOT EXISTS clientes_db;
USE clientes_db;

CREATE TABLE clientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rut VARCHAR(10) UNIQUE NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    telefono VARCHAR(20),
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO clientes (rut, nombre, email, telefono) VALUES
('12345678-9', 'Juan Pérez', 'juan.perez@email.com', '+56912345678'),
('11222333-K', 'María González', 'm.gonzalez@empresa.cl', '+56922334455'),
('15987456-2', 'Carlos Soto', 'csoto@gmail.com', '+56933445566'),
('18765432-1', 'Ana Morales', 'ana.morales@outlook.com', '+56944556677'),
('9876543-2', 'Roberto Jara', 'rjara@servicios.net', '+56955667788'),
('20123456-7', 'Lucía Fernández', 'lfernandez@freelance.com', '+56966778899'),
('14555666-4', 'Diego Portales', 'dportales@gobierno.cl', '+56977889900'),
('17888999-0', 'Elena Olavarría', 'elena.o@universidad.edu', '+56988990011'),
('13222111-5', 'Felipe Avello', 'pecesillo@comedia.cl', '+56999001122'),
('19444333-8', 'Valentina Tapia', 'v.tapia@agencia.io', '+56911223344');

CREATE DATABASE IF NOT EXISTS ventas_db;
USE ventas_db;

CREATE TABLE ventas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    producto_id BIGINT,
    cliente_rut VARCHAR(10),
    cantidad INT,
    precio_total DOUBLE,
    fecha_venta DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO ventas (producto_id, cliente_rut, cantidad, precio_total) VALUES
(1, '12345678-9', 1, 1200.0),
(2, '11222333-K', 2, 198.0),
(3, '15987456-2', 1, 350.0),
(4, '18765432-1', 3, 255.0),
(5, '9876543-2', 1, 299.0),
(6, '20123456-7', 2, 220.0),
(7, '14555666-4', 1, 450.0),
(8, '17888999-0', 5, 350.0),
(9, '13222111-5', 1, 180.0),
(10, '19444333-8', 10, 150.0);

CREATE DATABASE IF NOT EXISTS reportes_db;
USE reportes_db;
