DELIMITER $$

CREATE PROCEDURE sp_productos_crear(
    IN p_sku NVARCHAR(500),
    IN p_nombre NVARCHAR(500),
    IN p_descripcion TEXT,
    IN p_categoria_id INT,
    IN p_precio_compra DECIMAL(10,2),
    IN p_precio_venta DECIMAL(10,2),
    IN p_stock_minimo INT,
    IN p_stock_actual INT
)
BEGIN
    INSERT INTO productos(
        sku, nombre, descripcion, categoria_id,
        precio_compra, precio_venta, stock_minimo, stock_actual,estado_id
    )
    VALUES (
        p_sku, p_nombre, p_descripcion, p_categoria_id,
        p_precio_compra, p_precio_venta, p_stock_minimo, p_stock_actual,1
    );

    SELECT LAST_INSERT_ID() AS id;
END$$

CREATE PROCEDURE sp_productos_listar()
BEGIN
    SELECT
        p.id,
        p.sku,
        p.nombre,
        p.descripcion,
        p.categoria_id,
        c.nombre AS categoria,
        p.precio_compra,
        p.precio_venta,
        p.stock_minimo,
        p.stock_actual,
        p.estado_id,
        CASE
            WHEN p.stock_actual <= p.stock_minimo THEN 'STOCK BAJO'
            ELSE 'STOCK DISPONIBLE'
        END AS estado_stock
    FROM productos p
    LEFT JOIN categorias c ON p.categoria_id = c.id
    ORDER BY p.id DESC;
END$$

CREATE PROCEDURE sp_productos_obtener(
    IN p_id INT
)
BEGIN
    SELECT
        p.id,
        p.sku,
        p.nombre,
        p.descripcion,
        p.categoria_id,
        c.nombre AS categoria,
        p.precio_compra,
        p.precio_venta,
        p.stock_minimo,
        p.stock_actual
    FROM productos p
    LEFT JOIN categorias c ON p.categoria_id = c.id
    WHERE p.id = p_id;
END$$

CREATE PROCEDURE sp_productos_actualizar(
    IN p_id INT,
    IN p_sku NVARCHAR(500),
    IN p_nombre NVARCHAR(500),
    IN p_descripcion TEXT,
    IN p_categoria_id INT,
    IN p_precio_compra DECIMAL(10,2),
    IN p_precio_venta DECIMAL(10,2),
    IN p_stock_minimo INT
)
BEGIN
    UPDATE productos
    SET sku = p_sku,
        nombre = p_nombre,
        descripcion = p_descripcion,
        categoria_id = p_categoria_id,
        precio_compra = p_precio_compra,
        precio_venta = p_precio_venta,
        stock_minimo = p_stock_minimo
    WHERE id = p_id;
END$$

CREATE PROCEDURE sp_productos_eliminar(
    IN p_id INT
)
BEGIN
    DELETE FROM productos
    WHERE id = p_id;
END$$

CREATE PROCEDURE sp_productos_actualizar_estado(
    IN p_id INT,
    IN p_estado_id INT
)
BEGIN
    UPDATE productos
    SET estado_id = p_estado_id
    WHERE id = p_id;
END$$