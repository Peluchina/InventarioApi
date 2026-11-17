DELIMITER $$
CREATE PROCEDURE sp_categorias_crear(
    IN p_nombre NVARCHAR(500),
    IN p_descripcion NVARCHAR(500)
)
BEGIN
    INSERT INTO categorias(nombre, descripcion)
    VALUES (p_nombre, p_descripcion);

    SELECT LAST_INSERT_ID() AS id;
END$$

CREATE PROCEDURE sp_categorias_listar()
BEGIN
    SELECT id, nombre, descripcion
    FROM categorias
    ORDER BY id DESC;
END$$

CREATE PROCEDURE sp_categorias_obtener(
    IN p_id INT
)
BEGIN
    SELECT id, nombre, descripcion
    FROM categorias
    WHERE id = p_id;
END$$

CREATE PROCEDURE sp_categorias_actualizar(
    IN p_id INT,
    IN p_nombre NVARCHAR(500),
    IN p_descripcion NVARCHAR(500)
)
BEGIN
    UPDATE categorias
    SET nombre = p_nombre,
        descripcion = p_descripcion
    WHERE id = p_id;
END$$

CREATE PROCEDURE sp_categorias_eliminar(
    IN p_id INT
)
BEGIN
    DELETE FROM categorias
    WHERE id = p_id;
END$$
DELIMITER ;