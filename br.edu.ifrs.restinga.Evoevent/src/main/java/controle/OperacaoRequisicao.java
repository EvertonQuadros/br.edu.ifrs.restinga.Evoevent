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
import modelo.Perfil;
import modelo.Requisicao;
import modelo.Requisicao.SituacaoEnum;
import modelo.Usuario;
import modelo.utils.DateUtil;
import modelo.utils.EmailUtil;
import modelo.utils.MessagesUtil;

/**
 * Classe que realiza operações do tipo requisição como por exemplo listar 
 * requisições, responder requisições e remover requisições expiradas.
 * @author notrevequadrosc@gmail.com
 */
@Named("Requisicao")
@SessionScoped
public class OperacaoRequisicao implements Serializable{
    
    private final Crud crud = new Crud(Requisicao.class, new Requisicao());
    
    public Crud getCrud() {
        return crud;
    }
    
    /**
     * Método que cria uma requisição após o email do usuário.
     * @param usuario o objeto do usuário que realizou a confirmação através
     * do código.
     */
    public void requisitaCadastro(Usuario usuario){
     
        if(usuario != null){
            
            ((Requisicao)crud.getInstance()).setSituacao(SituacaoEnum.AGUARDANDO);
            ((Requisicao)crud.getInstance()).setUsuario(usuario);
            ((Requisicao)crud.getInstance()).setDescricao("Requisição de novo perfil");
            ((Requisicao)crud.getInstance()).setDataVencimento();
            
            crud.salvar();
            MessagesUtil.Messages.getCurrentMessage(Requisicao.class
                    ,"criada com sucesso, aguarde o email com a resposta."
                    , MessagesUtil.Messages.OperacaoEnum.REQUISICAO
                    , MessagesUtil.SeveridadeEnum.INFO);
            
        }
        
    }
    
    /**
     * Método que realiza uma consulta no banco de dados e procura apenas
     * requisições que não tem um destinatário (sem destinatário consideramos
     * que a requisição pertence ao sistema), que não foram respondidas e que 
     * a data de vencimento é igual ou maior a data atual.
     * @return lista de objetos resultantes da consulta ou null se nada for 
     * encontrado.
     */
    public List<Requisicao> buscarRequisicoes(){
        
        crud.setLista(crud.consultaLista("FROM Requisicao WHERE destino_id is null "
                + "and respondido = false and data_vida >= now()"
                ,new String[]{}
                ,new String[]{}));
        
        return crud.getLista();
        
    }
   
    /**
     * Método que responde uma requisição preenchendo todos os campos da requisição
     * e enviando a resposta para o usuário via email.
     * @param valor O tipo de resposta, se foi recusado ou aceito.
     * @param perfil O perfil que respondeu a requisição
     * @return Uma String contendo o redirecionamento para a página de requisições.
     */
    public String respondeRequisicao(String valor, Perfil perfil){
        
        SituacaoEnum sit;
        
        if(valor.equals("ACEITO")){
            sit = SituacaoEnum.ACEITO;
        }
        else{
            sit = SituacaoEnum.RECUSADO;
        }
        
        ((Requisicao)crud.getInstance()).setSituacao(sit);
        ((Requisicao)crud.getInstance()).setRespondido();
        ((Requisicao)crud.getInstance()).setDataResposta(new Date());
        ((Requisicao)crud.getInstance()).setDestino(perfil);        
        
        EmailUtil.Email.enviaEmail((
            (Requisicao)crud.getInstance()).getUsuario().getEmail()
            , "NO-REPLY: A SUA REQUISIÇÃO FOI RESPONDIDA"
            , "A sua requisição ("+ ((Requisicao)crud.getInstance()).getDescricao()
                +"):\nRespondido por "+ ((Requisicao)crud.getInstance()).getDestino().getLogin()
                    + "\nData: "+ DateUtil.DateConversors.getDatePadraoBrasileiro(
                            ((Requisicao)crud.getInstance()).getDataResposta())
                    +"\nSituacao: "+ ((Requisicao)crud.getInstance()).getSituacao()
                    +"\nResposta: "+ ((Requisicao)crud.getInstance()).getResposta()
                    +"\n\nEMAIL GERADO AUTOMATICAMENTE, NÃO RESPONDA.");
        
        crud.atualizar();
        
        return "administradorpage1";
        
    }
    
}
