create table medico(
                       id bigint not null auto_increment,
                       cpf varchar(14) not null,
                       nome varchar(30) not null,
                       sobrenome varchar(30) not null,
                       registro varchar(10) not null,
                       especialidade varchar(30) not null,
                       primary key (id)
);
create table proprietario(
                             id bigint not null auto_increment,
                             cpf varchar(11) not null,
                             nome varchar(30) not null,
                             sobrenome varchar(30) not null,
                             datanascimento date,
                             endereco varchar(30) not null,
                             telefone long not null,
                             primary key (id)
);
create table paciente(
                         id bigint not null auto_increment,
                         especie varchar(30) not null,
                         raca varchar(30) not null,
                         cor varchar(15) not null,
                         datanascimento date,
                         nome varchar(30) not null,
                         proprietario_id bigint not null,
                         primary key (id),
                         foreign key (proprietario_id) references proprietario(id)
);
create table consulta(
                         id bigint not null auto_increment,
                         datahora datetime not null,
                         motivo varchar(30) not null,
                         diagnostico varchar(30) not null,
                         tratamento varchar(30) not null,
                         medico_id bigint not null,
                         paciente_id bigint not null,
                         primary key (id),
                         foreign key (medico_id) references medico(id),
                         foreign key (paciente_id) references paciente(id)
);

drop table paciente;

insert into proprietario
    value (
           null
    ,12345678901
    ,'lucas'
    ,'silva'
    ,current_date
    ,'rua zero'
    ,1199998888
    );

select * from proprietario;
select * from medico;
select * from paciente;
select * from consulta;