create table productos(
id int auto_increment primary key,
sku nvarchar(500),
nombre nvarchar(500),
descripcion text,
categoria_id int,
precio_compra decimal,
precio_venta decimal,
stock_minimo int,
stock_actual int,
estado_id INT NOT NULL DEFAULT 1, 
constraint fk_producto_estado 
foreign key (estado_id) references estados_producto(id),
constraint fk_producto_categoria
foreign key(categoria_id) references categorias(id)
);