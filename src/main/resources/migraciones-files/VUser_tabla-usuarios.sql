
create table usuarios(

    us_id bigint not null auto_increment,
    us_nombre varchar(50) not null,
    us_apellido varchar(50) not null,
    email varchar(50) not null,
    clave varchar(300) not null,
    primary key(us_id)

);