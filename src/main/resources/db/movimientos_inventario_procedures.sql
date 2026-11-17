DELIMITER $$

CREATE PROCEDURE sp_movimientos_registrar(
    IN p_producto_id INT,
    IN p_tipo_movimiento VARCHAR(20),
    IN p_cantidad INT,
    IN p_referencia NVARCHAR(500),
    IN p_usuario_id INT
)
BEGIN
    DECLARE v_stock_anterior INT DEFAULT NULL;
    DECLARE v_stock_nuevo INT DEFAULT NULL;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    START TRANSACTION;

    IF p_cantidad < 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'La cantidad no puede ser negativa';
    END IF;

    IF p_tipo_movimiento NOT IN ('entrada', 'salida', 'ajuste') THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT =
                'Tipo de movimiento no valido';
    END IF;

    IF p_tipo_movimiento <> 'ajuste' AND p_cantidad = 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT =
                'La cantidad debe ser mayor que cero';
    END IF;

    SELECT stock_actual
    INTO v_stock_anterior
    FROM productos
    WHERE id = p_producto_id
    FOR UPDATE;

    IF v_stock_anterior IS NULL THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT =
                'El producto no existe o su stock no esta definido';
    END IF;

    IF p_tipo_movimiento = 'entrada' THEN
        SET v_stock_nuevo = v_stock_anterior + p_cantidad;

    ELSEIF p_tipo_movimiento = 'salida' THEN
        IF p_cantidad > v_stock_anterior THEN
            SIGNAL SQLSTATE '45000'
                SET MESSAGE_TEXT =
                    'Stock insuficiente para realizar la salida';
        END IF;

        SET v_stock_nuevo = v_stock_anterior - p_cantidad;

    ELSEIF p_tipo_movimiento = 'ajuste' THEN
        SET v_stock_nuevo = p_cantidad;
    END IF;

    UPDATE productos
    SET stock_actual = v_stock_nuevo
    WHERE id = p_producto_id;

    INSERT INTO movimientos_inventario(
        producto_id,
        tipo_movimiento,
        cantidad,
        stock_anterior,
        stock_nuevo,
        referencia,
        usuario_id,
        fecha,
        created_at,
        update_at
    )
    VALUES (
        p_producto_id,
        p_tipo_movimiento,
        p_cantidad,
        v_stock_anterior,
        v_stock_nuevo,
        p_referencia,
        p_usuario_id,
        CURTIME(),
        NOW(),
        NOW()
    );

    COMMIT;

    SELECT
        LAST_INSERT_ID() AS movimiento_id,
        p_producto_id AS producto_id,
        p_tipo_movimiento AS tipo_movimiento,
        v_stock_anterior AS stock_anterior,
        v_stock_nuevo AS stock_nuevo,
        'Movimiento registrado correctamente' AS mensaje;
END$$

DELIMITER ;
 
 DELIMITER $$

CREATE PROCEDURE sp_movimientos_listar()
BEGIN
    SELECT
        m.id,
        m.producto_id,
        p.nombre AS producto,
        p.sku,
        m.tipo_movimiento,
        m.cantidad,
        m.stock_anterior,
        m.stock_nuevo,
        m.referencia,
        m.usuario_id,
        CONCAT_WS(' ', u.nombre, u.apellidos) AS usuario,
        m.fecha,
        m.created_at,
        m.update_at
    FROM movimientos_inventario m
    INNER JOIN productos p ON m.producto_id = p.id
    INNER JOIN usuarios u ON m.usuario_id = u.id
    ORDER BY m.id DESC;
END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_movimientos_obtener(
    IN p_id INT
)
BEGIN
    SELECT
        m.id,
        m.producto_id,
        p.nombre AS producto,
        p.sku,
        m.tipo_movimiento,
        m.cantidad,
        m.stock_anterior,
        m.stock_nuevo,
        m.referencia,
        m.usuario_id,
        CONCAT_WS(' ', u.nombre, u.apellidos) AS usuario,
        m.fecha,
        m.created_at,
        m.update_at
    FROM movimientos_inventario m
    INNER JOIN productos p ON m.producto_id = p.id
    INNER JOIN usuarios u ON m.usuario_id = u.id
    WHERE m.id = p_id;
END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_movimientos_por_producto(
    IN p_producto_id INT
)
BEGIN
    SELECT
        m.id,
        m.producto_id,
        p.nombre AS producto,
        m.tipo_movimiento,
        m.cantidad,
        m.stock_anterior,
        m.stock_nuevo,
        m.referencia,
        CONCAT_WS(' ', u.nombre, u.apellidos) AS usuario,
        m.fecha,
        m.created_at
    FROM movimientos_inventario m
    INNER JOIN productos p ON m.producto_id = p.id
    INNER JOIN usuarios u ON m.usuario_id = u.id
    WHERE m.producto_id = p_producto_id
    ORDER BY m.id DESC;
END$$

DELIMITER ;