create table medico(
                       id bigint not null auto_increment,
                       cpf varchar(14) not null,
                       nome varchar(30) not null,
                       sobrenome varchar(30) not null,
                       registro varchar(10) not null,
                       especialidade varchar(30) not null,
                       primary key (id)
);

select * from medico;