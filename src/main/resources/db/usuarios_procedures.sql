DELIMITER $$

DROP PROCEDURE IF EXISTS sp_usuarios_crear$$
CREATE PROCEDURE sp_usuarios_crear(
    IN p_nombre NVARCHAR(500),
    IN p_apellidos NVARCHAR(500),
    IN p_dni NUMERIC,
    IN p_direccion NVARCHAR(500),
    IN p_rol_id INT,
    IN p_email VARCHAR(500),
    IN p_contrasenia NVARCHAR(500)
)
BEGIN
    INSERT INTO usuarios(
        nombre, apellidos, dni, direccion, rol_id, email, contrasenia
    ) VALUES (
        p_nombre, p_apellidos, p_dni, p_direccion,
        p_rol_id, p_email, p_contrasenia
    );
    SELECT LAST_INSERT_ID() AS id;
END$$

DROP PROCEDURE IF EXISTS sp_usuarios_listar$$
CREATE PROCEDURE sp_usuarios_listar()
BEGIN
    SELECT
        u.id, u.nombre, u.apellidos, u.dni, u.direccion,
        u.rol_id, r.nombre AS rol, u.email, u.estado_id
    FROM usuarios u
    LEFT JOIN roles r ON u.rol_id = r.id
    ORDER BY u.id DESC;
END$$

DROP PROCEDURE IF EXISTS sp_usuarios_obtener$$
CREATE PROCEDURE sp_usuarios_obtener(IN p_id INT)
BEGIN
    SELECT
        u.id, u.nombre, u.apellidos, u.dni, u.direccion,
        u.rol_id, r.nombre AS rol, u.email, u.estado_id
    FROM usuarios u
    LEFT JOIN roles r ON u.rol_id = r.id
    WHERE u.id = p_id;
END$$

DROP PROCEDURE IF EXISTS sp_usuarios_actualizar$$
CREATE PROCEDURE sp_usuarios_actualizar(
    IN p_id INT,
    IN p_nombre NVARCHAR(500),
    IN p_apellidos NVARCHAR(500),
    IN p_dni NUMERIC,
    IN p_direccion NVARCHAR(500),
    IN p_rol_id INT,
    IN p_email VARCHAR(500),
    IN p_contrasenia NVARCHAR(500)
)
BEGIN
    UPDATE usuarios
    SET nombre = p_nombre,
        apellidos = p_apellidos,
        dni = p_dni,
        direccion = p_direccion,
        rol_id = p_rol_id,
        email = p_email,
        contrasenia = p_contrasenia
    WHERE id = p_id;
END$$

DROP PROCEDURE IF EXISTS sp_usuarios_actualizarEstado$$
CREATE PROCEDURE sp_usuarios_actualizarEstado(
    IN p_id INT,
    IN p_estadoId INT
)
BEGIN
    UPDATE usuarios
    SET estado_id = p_estadoId
    WHERE id = p_id;
END$$

DROP PROCEDURE IF EXISTS sp_usuarios_eliminar$$
CREATE PROCEDURE sp_usuarios_eliminar(IN p_id INT)
BEGIN
    DELETE FROM usuarios WHERE id = p_id;
END$$

DELIMITER ;
