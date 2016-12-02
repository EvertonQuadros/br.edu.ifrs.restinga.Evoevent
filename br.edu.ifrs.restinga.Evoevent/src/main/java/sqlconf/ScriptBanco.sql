/* 
 * Copyright 2016 notrevequadrosc@gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * Author:  notrevequadrosc@gmail.com
 * Created: 09/10/2016
 */

/**
 * Author:  notrevequadrosc@gmail.com
 * Created: 29/09/2016
 * ATENÇÃO O SCRIPT DE INSERÇÕES DEVE SER EXECUTADO NA ORDEM PARA O 
 * FUNCIONAMENTO DO BANCO DE DADOS E DA APLICAÇÃO.
 */

/**
Criação do banco de dados;
*/
drop database if exists evoevent;
create database if not exists evoevent;

/**
Criação das tabelas;
*/
use evoevent;

create table estados(
    id int not null auto_increment, 
    sigla varchar(3) not null, 
    nome_estado varchar(100) not null, 
        primary key(id))engine = innodb;

create index estado_sigla on estados(sigla);

create table cidades(
    id int not null auto_increment, 
    nome_cidade varchar(100) not null, 
    estado_id int not null, 
        foreign key(estado_id) references estados(id), 
        primary key(id))engine = innodb;

create table usuarios(
id int not null auto_increment, 
nome varchar(100) not null, 
datanasc date not null, 
rg varchar(15) not null unique, 
cpf varchar(15) not null unique, 
curriculo varchar(100), 
sexo char(1) not null, 
profissao varchar(50) not null,
email varchar(100) not null unique,
instituicao varchar(50) not null,
estado_id int not null,
cidade_id int not null,
instrucao varchar(50) not null,
interesse mediumtext not null,
descricao mediumtext,
codigo varchar(40) not null,
emailconfirmado boolean default false not null,
datacadastro timestamp DEFAULT CURRENT_TIMESTAMP,
    foreign key(estado_id) references estados(id),
    foreign key(cidade_id) references cidades(id),
    primary key(id))engine=innodb;

create table perfis(
id int not null auto_increment, 
foto longtext,
ultimo_acesso timestamp,
login varchar(100) not null unique, 
senha varchar(50) not null,
acessos int not null,
tipo enum("PADRAO","REVISOR DE EVENTOS","GERENTE DE EVENTOS","ADMINISTRADOR"),
usuario_id int not null,
    foreign key(usuario_id) references usuarios(id),
    primary key(id))engine=innodb;

create table requisicoes(
id int not null auto_increment,
titulo varchar(50),
usuario_id int,
perfil_id int,
destino_id int,
situacao enum("AGUARDANDO","RECUSADO","ACEITO","CANCELADO","EXPIRADO") DEFAULT "AGUARDANDO",
respondido boolean default false not null,
resposta mediumtext,
descricao mediumtext not null,
data_vida Date not null,
data_resposta Date,
    foreign key(usuario_id) references usuarios(id),
    foreign key(perfil_id) references perfis(id),
    foreign key(destino_id) references perfis(id),
    primary key(id))engine=innodb;

create table eventos(
id int not null auto_increment,
nome_evento varchar(100) not null,
estado_id int not null,
cidade_id int not null,
local_evento varchar(100) not null,
data_hora_inicio DateTime not null,
data_hora_termino DateTime not null,
area mediumtext not null,
descricao mediumtext not null,
    foreign key(estado_id) references estados(id),
    foreign key(cidade_id) references cidades(id),
    primary key(id))engine=innodb;

create table inscricoes(
id int not null auto_increment,
evento_id int not null,
perfil_id int not null,
data_inscricao Date not null,
tipo_inscricao enum("ALUNO","PROFESSOR","ORIENTADOR","COLABORADOR","PALESTRANTE","REVISOR DE TRABALHOS","GERENTE DE EVENTO"),
    foreign key(evento_id) references eventos(id),
    foreign key(perfil_id) references perfis(id),
    primary key(id))engine=innodb;
