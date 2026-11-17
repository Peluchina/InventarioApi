create table usuarios(
id int auto_increment primary key,
nombre nvarchar(500),
apellidos nvarchar(500),
dni numeric,
direccion nvarchar(500),
rol_id int,
email varchar(500),
contrasenia nvarchar(500),
constraint fk_usuarios_roles
foreign key (rol_id) references roles(id)
);
ALTER TABLE usuarios
ADD COLUMN estado_id INT NOT NULL DEFAULT 1,
ADD CONSTRAINT fk_usuarios_estado
FOREIGN KEY (estado_id) REFERENCES estado(id);