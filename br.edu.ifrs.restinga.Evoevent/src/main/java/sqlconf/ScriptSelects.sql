use evoevent;

update usuarios set email = "hehehe@gmail.com" where id=4;

select * from eventos;
select * from perfis;
select * from usuarios;
select * from requisicoes;
select * from inscricoes;

describe perfis;
describe inscricoes;

select * from eventos,inscricoes where eventos.data_hora_termino > now() and eventos.id = inscricoes.evento_id and inscricoes.perfil_id != 2;