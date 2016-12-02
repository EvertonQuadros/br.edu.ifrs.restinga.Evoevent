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
package controle;

import controle.Operacao.OperacaoGerenteEventos;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Evento;
import modelo.Inscricao;

/**
 * Classe responsável por operações envolvendo eventos, esta classe realiza 
 * operações no banco de dados e se comunica com a view para exibição de elementos
 * na tela.
 * @author notrevequadrosc@gmail.com
 */
@Named("Evento")
@SessionScoped
public class OperacaoEvento implements Serializable{
 
    private final Crud crud = new Crud(Evento.class, new Evento());
    
    @Inject 
    OperacaoPerfil opPerfil;
    
    @Inject
    OperacaoInscricao opInscricao;
    
    public Crud getCrud() {
        return crud;
    }
    
    /**
     * Método auxiliar que acessa um método operacional dentro do perfil de usuário
     * responsável por agendamento de eventos.
     */
    public void agendaEvento(){
        
        try {
            ((OperacaoGerenteEventos)opPerfil.getOperacao().getOperacao()).agendarNovoEvento((Evento)crud.getInstance());
        } 
        catch (IllegalAccessException ex) {
            System.out.printf("Erro: "+ex.getMessage());
        }
        
    }
    
      /**
     * Método que realiza uma consulta no banco de dados e procura apenas
     * Eventos que ainda não terminaram.
     * @return lista de objetos resultantes da consulta ou null se nada for 
     * encontrado.
     */
    public List<Evento> buscarEventos(){
        
        crud.setLista(crud.consultaLista("FROM Evento WHERE "
                + "data_hora_inicio > now() "
                + "order by data_hora_inicio"
                ,new String[]{}
                ,new String[]{}));
        
        return crud.getLista();
        
    }
    
    /**
     * Método que realiza busca de eventos exclusivo de um determinado usuário
     * (perfil).
     * @return lista de eventos relativo aquele perfil.
     */
    public List<Evento> buscarEventosExclusivo(){

        opInscricao.getCrud().setLista(opInscricao.getCrud().consultaLista("from Inscricao where "
                + "perfil_id = :perfil_id"
                ,new String[]{"perfil_id"}
                ,new String[]{String.valueOf(opPerfil.GetPerfilSession().getId())}));
        
        List<Evento> listaObjetos = buscarEventos();
        
        for(Inscricao inscricao : (List<Inscricao>)opInscricao.getCrud().getLista()){
            
            for(int i = 0; i < listaObjetos.size() ; i++){
                
                if(inscricao.getEvento().getId() == listaObjetos.get(i).getId()){
                    
                    listaObjetos.remove(listaObjetos.get(i));
                    --i;
                    
                }
                
            }
        
        }
        
        if(!listaObjetos.isEmpty()){
            crud.setLista(listaObjetos);
        }
  
        return crud.getLista();
        
    }
    
}
