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

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import modelo.Evento;
import modelo.Inscricao;
import modelo.Perfil;

/**
 * Classe que realiza operações referentes a inscrições, que sao responsáveis
 * por relacionar um usuário (perfil) a um determinado evento.
 * @author notrevequadrosc@gmail.com
 */
@Named("Inscricao")
@SessionScoped
public class OperacaoInscricao implements Serializable{
    
    private final Crud crud = new Crud(Inscricao.class, new Inscricao());
    
    public Crud getCrud() {
        return crud;
    }
    
    /**
     * Método que busca inscrições de um determinado usuário (perfil).
     * @param perfil Objeto do Perfil relativo ao usuário que será incluido na 
     * busca através do id (identificador) do usuário.
     * @return uma lista de objetos do tipo Inscrição referentes a essa busca.
     */
    public List<Inscricao> buscarInscricoes(Perfil perfil){
        
        crud.setLista(crud.consultaLista("FROM Inscricao WHERE perfil_id = :perfil_id "
                ,new String[]{"perfil_id"}
                ,new String[]{String.valueOf(perfil.getId())}));
        
        return crud.getLista();
        
    }
    
    /**
     * Método que realiza a busca de inscrições e filtra apenas inscrições de
     * eventos que ainda não terminaram.
     * @param perfil Objeto relativo ao usuário (perfil) que será utilizado na
     * busca.
     * @return lista de objetos do tipo Inscrição referentes a essa busca.
     */
    public List<Inscricao> buscarInscricoesFiltro(Perfil perfil){
        
        crud.setLista(buscarInscricoes(perfil));
         
        for(int i = 0; i < crud.getLista().size() ; i++){
            
            Evento evento = ((Inscricao)crud.getLista().get(i)).getEvento();
            
            if(!evento.getDataHoraTermino().after(new Date())){
                
                crud.getLista().remove(i);
                --i;
                
            }
            
        }
        
        return crud.getLista();
        
    }
    
}
