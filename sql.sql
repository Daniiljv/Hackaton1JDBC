create table users
(
    id       serial primary key,
    login    varchar(20) unique,
    password varchar(30),
    rate     int check (rate >= 1 and rate <= 10),
    status   varchar check (status = 'User' or status = 'Seller')
);

create table products
(
    id            serial primary key,
    name          varchar,
    category      varchar(30),
    seller_id     Integer references users (id),
    url_photo     varchar,
    fabric        varchar(30),
    size          varchar(25),
    description   varchar(300),
    likesCount    integer default 0,
    dislikesCount integer default 0
);

create table evaluate_product
(
    product_id Integer references products (id),
    user_id    Integer references users (id),
    evaluate   int check ( evaluate >= 1 and evaluate <= 10 )
);



create table comments
(
    product_id    Integer references products (id),
    user_id       Integer references users (id),
    comment       varchar(1000),
    likesCount    integer default 0,
    dislikesCount integer default 0
);

select u.login,
       p.id,
       p.category,
       p.url_photo,
       p.fabric,
       p.size,
       p.likesCount,
       p.dislikesCount
from products p
         join users u on p.seller_id = u.id;


