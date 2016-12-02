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
package view;

import controle.Crud;
import controle.OperacaoEvento;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Cidade;
import modelo.Estado;
import modelo.Evento;
import modelo.utils.DateUtil;
import modelo.utils.MessagesUtil;
import org.primefaces.event.TabChangeEvent;
import persistencia.GenericConverter;

/**
 * Classe view que interage com a página evento1 do perfil de usuário e com a
 * classe de controle de eventos.
 * @author notrevequadrosc@gmail.com
 */
@Named("EventoBean")
@ConversationScoped
public class EventoViewBean implements Serializable{
    
    private final Crud crud = new Crud(Cidade.class, new Cidade());
    private final GenericConverter converter = new GenericConverter(crud);
    
    @Inject 
    private OperacaoEvento opEvento;
    
    private Evento evento;
    
    private Date dataMin;
    private List<String> areas;
    
    @PostConstruct
    public void init(){
    
        opEvento.buscarEventos();
        setLista(((Evento)opEvento.getCrud().getInstance()).getEstado());
        evento = (Evento)opEvento.getCrud().getInstance();
    
        dataMin = DateUtil.DateFunctions.getNovaData(
            DateUtil.UnidadeTempoEnum.MES
            , +1);
        
        areas = new ArrayList<>(Arrays.asList("Antropologia"
                                               ,"Física"
                                               ,"Geologia"
                                               ,"História"
                                               ,"Informática"
                                               ,"Inglês"
                                               ,"Matemática"
                                               ,"Química"
                                               ,"Robótica"));
        
    }
           
    /**
     * Método auxiliar que realiza testes dos dados informados pelo usuário e 
     * faz a ligação com a classe de controle de eventos para realizar um
     * inserção de evento no banco de dados.
     */
    public void salvar(){
        
        if(DateUtil.DateFunctions.compararDatas(
                        evento.getDataHoraInicio()
                        , evento.getDataHoraTermino())){
            
            opEvento.getCrud().setInstance(evento);
            opEvento.agendaEvento();
            
            MessagesUtil.Messages.getCurrentMessage(Evento.class
                        ,"Criado com sucesso!."
                        , MessagesUtil.Messages.OperacaoEnum.MENSAGEM
                        , MessagesUtil.SeveridadeEnum.INFO);

            evento = new Evento();
            
        }
        else{
           
            MessagesUtil.Messages.getCurrentMessage(Evento.class
                    ,"A data de término deve ser após a data de ínicio!."
                    , MessagesUtil.Messages.OperacaoEnum.MENSAGEM
                    , MessagesUtil.SeveridadeEnum.AVISO);
            
        }
        
    }
    
    public Crud getCrud() {
        return crud;
    }
  
    /**
     * Método que aciona um evento cada vez que uma aba é selecionada (alterada)
     * e faz um teste, caso o teste seja verdadeiro as variaveis que guardam
     * os objetos de Eventos é reinicializada.
     * @param event
     */
    public void onTabChange(TabChangeEvent event) {

        if (event.getTab().getId().equals("agendarEventosTab")) {
            
            evento = new Evento();
            opEvento.getCrud().setInstance(new Evento());
        
        } 
      
    }
    
    /**
     * Método auxilizar responsável pela exibição de uma lista de objetos do tipo
     * cidade para preenchimento dos campos de cidade no cadastro de novo evento.
     * @param estado
     */
    public void setLista(Estado estado){
        
        if(estado !=null){
            crud.listar("from Cidade where estado_id",estado.getId());
        }
        
    }
    
    public GenericConverter getConverter(){
        return converter;
    }
    
    public OperacaoEvento getOpEvento() {
        return opEvento;
    }
    
    public Evento getEvento(){
        return evento;
    }
    
    public Date getDataMin() {
        return dataMin;
    }

    public void setDataMin(Date dataMin) {
        this.dataMin = dataMin;
    }

    public List<String> getAreas() {
        return areas;
    }
    
}
