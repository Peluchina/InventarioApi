-- La tabla estado ya existe; almacenes.estado_id referencia estado.id.
CREATE TABLE almacenes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre NVARCHAR(500),
    ubicacion NVARCHAR(500)
);

ALTER TABLE almacenes
    ADD COLUMN estado_id INT NOT NULL DEFAULT 1,
    ADD CONSTRAINT fk_almacenes_estado
        FOREIGN KEY (estado_id) REFERENCES estado(id);
