CREATE DATABASE RH;
\c rh;

drop table ANDRANA;

CREATE TABLE ANDRANA(
    id serial primary key,
    anarana varchar(50),
    chiffre double precision
);

insert into ANDRANA 
values (default,'kanefa',32.2);