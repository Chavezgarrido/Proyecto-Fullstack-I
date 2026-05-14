--Registros de Clients (método POST en url localhost:8080/api/clients)
{
        "rut": "12345678-9",
        "nombre": "Juan Pérez",
        "email": "juan.perez@example.com",
        "telefono": "+56912345678"
}
{
        "rut": "15987654-K",
        "nombre": "María José Concha",
        "email": "m.concha@empresa.cl",
        "telefono": "987654321"
}
{
        "rut": "20444555-0",
        "nombre": "Carlos Soto",
        "email": "csoto@freelance.io",
        "telefono": "+56223334444"
}

--Registros de Products (método POST en url localhost:8080/api/products)
{
        "sku": "PROD-OFF-001",
        "nombre": "Escritorio Ergonómico",
        "descripcion": "Escritorio ajustable en altura con superficie de madera de roble.",
        "precio": 250000,
        "stock": 11
}
{
        "sku": "TECH-LAP-042",
        "nombre": "Laptop Pro 14\"",
        "descripcion": "Procesador de última generación, 16GB RAM, 512GB SSD.",
        "precio": 1200000,
        "stock": 8
}
{
        "sku": "PERI-MOU-009",
        "nombre": "Mouse Inalámbrico",
        "descripcion": "Mouse óptico con conexión Bluetooth y batería recargable.",
        "precio": 35000,
        "stock": 48
}

--Registros de Sellers (metodo POST en url localhost:8080/api/sellers)
{
        "rut": "184445556",
        "nombre": "Roberto",
        "apellido": "Sanhueza",
        "email": "roberto.s@empresa-erp.cl",
        "porcentajeComision": 2.5,
        "sucursal": "Santiago Centro",
        "activo": true
}
{
    "rut": "16777888K",
    "nombre": "Elena",
    "apellido": "Morales",
    "email": "e.morales@empresa-erp.cl",
    "porcentajeComision": 3.0,
    "sucursal": "Las Condes",
    "activo": true
}
{
    "rut": "123334445",
    "nombre": "Fernando",
    "apellido": "Lorca",
    "email": "f.lorca@empresa-erp.cl",
    "porcentajeComision": 1.8,
    "sucursal": "Concepción",
    "activo": true
}

--Registros de Orders (Metodo POST en localhost:8080/api/orders)
{
    "clienteRut": "12345678-9",
    "vendedorRut": "184445556",
    "items": [
        {
            "id": 1,
            "productoSku": "PROD-OFF-001",
            "cantidad": 2,
            "precioUnitario": 250000.0,
            "subtotal": 500000.0
        },
        {
            "id": 2,
            "productoSku": "PERI-MOU-009",
            "cantidad": 1,
            "precioUnitario": 35000.0,
            "subtotal": 35000.0
        }
    ],
    "total": 535000.0,
    "fechaPedido": "2026-05-13T19:50:06.208557"
}

{
    "clienteRut": "12345678-9",
    "vendedorRut": "184445556",
    "items": [
        {
            "id": 3,
            "productoSku": "PROD-OFF-001",
            "cantidad": 2,
            "precioUnitario": 250000.0,
            "subtotal": 500000.0
        },
        {
            "id": 4,
            "productoSku": "PERI-MOU-009",
            "cantidad": 1,
            "precioUnitario": 35000.0,
            "subtotal": 35000.0
        }
    ],
    "total": 535000.0,
    "fechaPedido": "2026-05-13T19:50:40.971743"
}
