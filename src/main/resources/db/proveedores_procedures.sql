DROP PROCEDURE IF EXISTS sp_proveedores_obtener;
DELIMITER $$
CREATE PROCEDURE sp_proveedores_crear(
    IN p_nombre NVARCHAR(500),
    IN p_contacto NVARCHAR(500),
    IN p_telefono CHAR(9),
    IN p_email VARCHAR(500),
    IN p_direccion NVARCHAR(500)
)
BEGIN
    INSERT INTO proveedores(
        nombre, contacto, telefono, email, direccion,estado_id
    )
    VALUES (
        p_nombre, p_contacto, p_telefono, p_email, p_direccion,1
    );

    SELECT LAST_INSERT_ID() AS id;
END$$
DELIMITER $$
CREATE PROCEDURE sp_proveedores_listar()
BEGIN
    SELECT id, nombre, contacto, telefono, email, direccion,estado_id
    FROM proveedores
    ORDER BY id DESC;
END$$
DELIMITER $$
CREATE PROCEDURE sp_proveedores_obtener(
    IN p_id INT
)
BEGIN
    SELECT id, nombre, contacto, telefono, email, direccion,estado_id
    FROM proveedores
    WHERE id = p_id;
END$$
DELIMITER $$
CREATE PROCEDURE sp_proveedores_actualizar(
    IN p_id INT,
    IN p_nombre NVARCHAR(500),
    IN p_contacto NVARCHAR(500),
    IN p_telefono CHAR(9),
    IN p_email VARCHAR(500),
    IN p_direccion NVARCHAR(500)
)
BEGIN
    UPDATE proveedores
    SET nombre = p_nombre,
        contacto = p_contacto,
        telefono = p_telefono,
        email = p_email,
        direccion = p_direccion
    WHERE id = p_id;
END$$
DELIMITER $$
CREATE PROCEDURE sp_proveedores_actualizarEstado(
    IN p_id INT,
    IN p_estado_id INT
)
BEGIN
    UPDATE proveedores
    SET estado_id = p_estado_id
    WHERE id = p_id;
END$$
DELIMITER $$
CREATE PROCEDURE sp_proveedores_eliminar(
    IN p_id INT
)
BEGIN
    DELETE FROM proveedores
    WHERE id = p_id;
END$$
DELIMITER ;