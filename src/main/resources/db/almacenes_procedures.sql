-- Ejecutar únicamente después de confirmar que estos procedimientos no existen.
-- No contiene DROP/ALTER, para evitar sobrescribir procedimientos instalados.
DELIMITER $$

CREATE PROCEDURE sp_almacenes_crear(
    IN p_nombre NVARCHAR(500),
    IN p_ubicacion NVARCHAR(500)
)
BEGIN
    INSERT INTO almacenes(nombre, ubicacion)
    VALUES (p_nombre, p_ubicacion);
    SELECT LAST_INSERT_ID() AS id;
END$$

CREATE PROCEDURE sp_almacenes_listar()
BEGIN
    SELECT id, nombre, ubicacion, estado_id
    FROM almacenes
    ORDER BY id DESC;
END$$

CREATE PROCEDURE sp_almacenes_obtener(IN p_id INT)
BEGIN
    SELECT id, nombre, ubicacion, estado_id
    FROM almacenes
    WHERE id = p_id;
END$$

CREATE PROCEDURE sp_almacenes_actualizar(
    IN p_id INT,
    IN p_nombre NVARCHAR(500),
    IN p_ubicacion NVARCHAR(500)
)
BEGIN
    UPDATE almacenes
    SET nombre = p_nombre,
        ubicacion = p_ubicacion
    WHERE id = p_id;
END$$

CREATE PROCEDURE sp_almacenes_actualizarEstado(
    IN p_id INT,
    IN p_estadoId INT
)
BEGIN
    UPDATE almacenes
    SET estado_id = p_estadoId
    WHERE id = p_id;
END$$

CREATE PROCEDURE sp_almacenes_eliminar(IN p_id INT)
BEGIN
    DELETE FROM almacenes
    WHERE id = p_id;
END$$

DELIMITER ;
