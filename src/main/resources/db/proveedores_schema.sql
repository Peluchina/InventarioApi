create table proveedores(
id int auto_increment primary key,
nombre nvarchar(500),
contacto nvarchar(500),
telefono char(9),
email varchar(500),
direccion nvarchar(500)
);
ALTER TABLE proveedores
ADD COLUMN estado_id INT NOT NULL DEFAULT 1,
ADD CONSTRAINT fk_proveedor_estado
FOREIGN KEY (estado_id) REFERENCES estado(id);