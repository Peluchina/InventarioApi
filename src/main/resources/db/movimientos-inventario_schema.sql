create table movimientos_inventario(
id int auto_increment primary key,
producto_id int,
tipo_movimiento enum('entrada','salida','ajuste','transferencia'),
cantidad int,
stock_anterior int,
stock_nuevo int,
referencia nvarchar(500),
usuario_id int,
fecha time,
created_at datetime,
update_at datetime,
constraint fk_movimientos_productos 
foreign key (producto_id) references productos(id),
constraint fk_movimientos_usuarios 
foreign key (usuario_id) references usuarios(id)
)