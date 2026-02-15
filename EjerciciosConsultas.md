Práctica 2: Consultas SQL-OO y PL/SQL
Este documento contiene la resolución de ejercicios sobre Bases de Datos Objeto-Relacionales (Oracle). Se incluyen consultas SQL sencillas, consultas complejas con agrupaciones y bloques PL/SQL (Procedimientos y Funciones).

📋 Contenido
Consultas SQL Básicas

Consultas de Agrupación y Filtros

Procedimientos y Funciones PL/SQL

1. Consultas SQL Básicas
1) Obtener los clientes de Albacete ordenados por apellidos.

SQL
SELECT * FROM cliente_objtab c 
ORDER BY c.apellidos;
2) Clientes de Albacete ordenados por apellidos (formato "Apellido, Nombre") limitando a los 3 primeros.

SQL
SELECT c.apellidos || ', ' || c.nombre AS nombre_completo  
FROM cliente_objtab c  
ORDER BY c.apellidos 
FETCH FIRST 3 ROWS ONLY;
3) Clientes que han realizado algún pedido superior a 200€.
Variante 1: EXISTS

SQL
SELECT c.apellidos || ', ' || c.nombre as NOMBRE_COMPLETO 
FROM cliente_objtab c  
WHERE EXISTS(
    SELECT 1  
    FROM pedido_objtab p  
    WHERE p.precio > 200 
    AND p.cliente = REF(c)
);
Variante 2: JOIN (Unión de tablas)

SQL
SELECT DISTINCT c.nombre, c.apellidos 
FROM cliente_objtab c, pedido_objtab p 
WHERE p.precio > 200 
AND p.cliente = REF(c);
4) Suministros con precio inferior a 350€ y fecha anterior a la actual.

SQL
SELECT * FROM suministro_objtab s 
WHERE s.precio < 350 
AND s.fecha_realizacion < SYSDATE;
5) Proveedores que no suministran ningún artículo.
Variante: Subconsulta

SQL
SELECT p.Nombre_empresa 
FROM Proveedor_objtab p 
WHERE NOT EXISTS ( 
    SELECT 1 
    FROM Suministro_objtab s 
    WHERE s.Proveedor = REF(p)
);
Variante: MINUS

SQL
SELECT Nombre_empresa FROM Proveedor_objtab 
MINUS 
SELECT DEREF(s.Proveedor).Nombre_empresa FROM Suministro_objtab s;


6) Actualizar precio (+21% IVA) a suministros del proveedor 1.

SQL
UPDATE Suministro_objtab s 
SET s.Precio = s.Precio * 1.21 
WHERE s.Proveedor.Cod_proveedor = 1;
7) Datos de suministro y empleado (Nombre empieza por 'A' y precio no nulo).

SQL
SELECT s.cod_suministro, s.empleado.nombre, s.precio * 1.15 
FROM suministro_objtab s 
WHERE s.empleado.nombre LIKE 'A%' 
AND s.precio IS NOT NULL;
8) Proveedores que realizan al menos un suministro.

SQL
SELECT DISTINCT s.proveedor.cod_proveedor, s.proveedor.nombre_empresa, s.proveedor.telefono 
FROM Suministro_objtab s;
Alternativa con IN:

SQL
SELECT p.Cod_proveedor, p.Nombre_empresa, p.Telefono 
FROM Proveedor_objtab p 
WHERE p.Cod_proveedor IN ( 
    SELECT s.Proveedor.Cod_proveedor FROM Suministro_objtab s 
);
2. Consultas de Agrupación y Filtros
2) Pedidos entre 1000€ y 2000€ de clientes de Albacete (Nombre empieza por 'J').

SQL
SELECT p.cod_pedido, p.precio, p.fecha_entrega, p.fecha_realizacion 
FROM pedido_objtab p  
WHERE p.precio BETWEEN 1000 AND 2000 
AND p.cliente.nombre LIKE 'J%'  
ORDER BY p.cliente.apellidos ASC;
3) Clientes que han realizado el pedido más caro.

SQL
SELECT DISTINCT c.nombre, c.apellidos 
FROM cliente_objtab c, pedido_objtab p 
WHERE REF(c) = p.cliente 
AND p.precio = (SELECT MAX(precio) FROM pedido_objtab);
4) Conteo de clientes (Apellido empieza por 'M') que realizan pedidos.

SQL
SELECT COUNT(DISTINCT c.nombre) as cliente, c.nombre, c.apellidos 
FROM cliente_objtab c, pedido_objtab p 
WHERE REF(c) = p.cliente 
AND c.apellidos LIKE 'M%' 
GROUP BY c.nombre, c.apellidos;
5) Clientes que realizan al menos un pedido y su apellido comienza por M.

SQL
SELECT DISTINCT c.nombre, c.apellidos 
FROM cliente_objtab c, pedido_objtab p  
WHERE REF(c) = p.cliente  
AND c.apellidos LIKE 'M%';
6) Conteo de pedidos con precio mayor al mínimo de suministros.

SQL
SELECT COUNT(p.cod_pedido) AS cont_precio, p.fecha_entrega, c.email  
FROM pedido_objtab p, cliente_objtab c 
WHERE REF(c) = p.cliente  
AND p.precio >= (SELECT MIN(s.precio) FROM suministro_objtab s) -- Asumido corrección lógica
GROUP BY p.fecha_entrega, c.email;
7) Empleados (Femenino) que encarguen al menos dos suministros.

SQL
SELECT e.cod_empleado, e.nombre  
FROM empleado_objtab e, suministro_objtab s 
WHERE REF(e) = s.empleado 
AND e.sexo = 'Femenino' 
GROUP BY e.cod_empleado, e.nombre 
HAVING COUNT(*) >= 2;
8) Suministros de proveedores no de Almansa con precio mayor al mínimo de pedidos.

SQL
SELECT s.* FROM suministro_objtab s, proveedor_objtab p 
WHERE REF(p) = s.proveedor 
AND p.poblacion != 'Almansa' 
AND s.precio > (SELECT MIN(p.precio) FROM pedido_objtab p);
9) Igual que la anterior, pero mostrando datos específicos con DEREF.

SQL
SELECT s.cod_suministro, s.fecha_entrega, s.fecha_realizacion, s.empleado.cod_empleado 
FROM suministro_objtab s 
WHERE s.empleado.poblacion != 'Almansa' 
AND s.precio > (SELECT MIN(p.precio) FROM pedido_objtab p);
3. Procedimientos y Funciones PL/SQL
1. Procedimiento: Pedido Máximo de Cliente

SQL
CREATE OR REPLACE PROCEDURE Pedido_Maximo_Cliente (v_nombre cliente_objtab.nombre%type) IS 
    v_cod_cliente cliente_objtab.cod_cliente%type; 
    v_fecha_realizacion pedido_objtab.fecha_realizacion%type; 
    v_precio pedido_objtab.precio%type; 
BEGIN 
    SELECT c.cod_cliente, p.fecha_realizacion, p.precio  
    INTO v_cod_cliente, v_fecha_realizacion, v_precio  
    FROM pedido_objtab p, cliente_objtab c 
    WHERE REF(c) = p.cliente 
    AND p.precio = (
        SELECT DISTINCT MAX(p1.precio) 
        FROM pedido_objtab p1, cliente_objtab c1 
        WHERE REF(c1) = p1.cliente 
        AND c1.nombre = v_nombre
    ) 
    AND ROWNUM = 1; 
    
    DBMS_OUTPUT.PUT_LINE('Codigo cliente: ' || v_cod_cliente || ', Fecha: ' || v_fecha_realizacion || ', Precio: ' || v_precio); 
END;
2. Procedimiento: Imprimir Proveedor 1

SQL
CREATE OR REPLACE PROCEDURE imprimir_proveedor AS 
    v_nombreProveedor proveedor_objtab.nombre_empresa%type; 
BEGIN 
    SELECT p.nombre_empresa 
    INTO v_nombreProveedor 
    FROM proveedor_objtab p 
    WHERE p.cod_proveedor = 1; 

    DBMS_OUTPUT.PUT_LINE('El nombre del proveedor 1 es: ' || v_nombreProveedor); 
END;
3. Procedimiento: Imprimir Cliente 1

SQL
CREATE OR REPLACE NONEDITIONABLE PROCEDURE imprimir2 AS 
    v_nombre cliente_objtab.nombre%TYPE; 
BEGIN 
    SELECT c.nombre 
    INTO v_nombre 
    FROM cliente_objtab c 
    WHERE c.cod_cliente = 1; 

    DBMS_OUTPUT.PUT_LINE('El nombre del cliente 1 es: ' || v_nombre); 
END;
4. Función: Insertar Empleado (Hardcoded)

SQL
CREATE OR REPLACE NONEDITIONABLE FUNCTION insertarEmpleado RETURN varchar2 AS 
BEGIN 
    INSERT INTO empleado_objtab(cod_empleado, nombre, apellidos, dni) VALUES( 
        99, 'Pedro', 'Lopez', '4554231G'
    ); 
    COMMIT; 
    RETURN 'Empleado insertado correctamente.'; 
END;
5. Función: Total Facturas por Población

SQL
CREATE OR REPLACE NONEDITIONABLE FUNCTION Total_Facturas_Poblacion (v_poblacion cliente_objtab.poblacion%type) RETURN INT IS 
    v_totalFacturas factura_objtab.precio_total%type; 
BEGIN 
    SELECT SUM(f.precio_total) INTO v_totalFacturas 
    FROM factura_objtab f, cliente_objtab c 
    WHERE REF(c) = f.cliente 
    AND c.poblacion = v_poblacion; 

    RETURN NVL(v_totalFacturas, 0); 
END;
6. Función: Media Facturas Particulares

SQL
CREATE OR REPLACE FUNCTION Media_Facturas_Particulares RETURN factura_objtab.PRECIO_TOTAL%TYPE IS 
    v_mediaFacturas factura_objtab.PRECIO_TOTAL%TYPE; 
BEGIN 
    SELECT SUM(f.precio_total) 
    INTO v_mediaFacturas  
    FROM factura_objtab f, cliente_objtab c 
    WHERE REF(c) = f.cliente 
    AND c.cod_tarjeta IS NOT NULL; 
    
    RETURN v_mediaFacturas; 
END;
7. Procedimiento: Buscar Pedido y Factura por CP

SQL
CREATE OR REPLACE PROCEDURE Buscar_Pedido_Por_CP (v_codigo_postal cliente_objtab.cp%TYPE) IS 
    v_codigoPedido pedido_objtab.cod_pedido%type; 
    v_codigoFactura factura_objtab.cod_factura%type; 
BEGIN 
    SELECT p.cod_pedido, f.cod_factura 
    INTO v_codigoPedido, v_codigoFactura 
    FROM pedido_objtab p, cliente_objtab c, factura_objtab f 
    WHERE REF(c) = p.cliente 
    AND REF(c) = f.cliente 
    AND c.cp = v_codigo_postal; 
    
    DBMS_OUTPUT.PUT_LINE('ID Pedido: ' || v_codigoPedido || ', ID Factura: ' || v_codigoFactura); 
END;
8. Función: Calcular IVA Total de un Pedido (Usando TABLE)

SQL
CREATE OR REPLACE FUNCTION Calcular_IVA_Total_Pedido (v_cod_pedido pedido_objtab.cod_pedido%TYPE) RETURN NUMBER IS 
    v_ivaTotal NUMBER; 
BEGIN 
    SELECT SUM((l.precio * l.cantidad) * (l.iva / 100)) 
    INTO v_ivaTotal 
    FROM pedido_objtab p, TABLE(p.linea) l 
    WHERE p.cod_pedido = v_cod_pedido; 

    RETURN v_ivaTotal; 
END;
10. Función: Descuento

SQL
CREATE OR REPLACE FUNCTION descuento (v_codigo suministro_objtab.cod_suministro%TYPE)  
RETURN suministro_objtab.precio%TYPE IS 
    v_precio_descontado suministro_objtab.precio%TYPE; 
BEGIN 
    SELECT (s.precio * 0.3 / 10) 
    INTO v_precio_descontado 
    FROM suministro_objtab s 
    WHERE s.cod_suministro = v_codigo; 
    
    RETURN v_precio_descontado; 
END;