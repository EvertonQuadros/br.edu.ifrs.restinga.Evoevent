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
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Perfil;
import modelo.Requisicao;
import modelo.Requisicao.SituacaoEnum;
import modelo.Requisicao.TituloEnum;
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
    
    @Inject 
    OperacaoPerfil opPerfil;
    
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
            ((Requisicao)crud.getInstance()).setTitulo(TituloEnum.NOVO_PERFIL);
            ((Requisicao)crud.getInstance()).setDescricao("O usuário "+usuario.getNome()
                                                           + " deseja cadastrar-se no evoevent.");
            ((Requisicao)crud.getInstance()).setDataVencimento();
            
            crud.salvar();
            MessagesUtil.Messages.getCurrentMessage(Requisicao.class
                    ,"criada com sucesso, aguarde o email com a resposta."
                    , MessagesUtil.Messages.OperacaoEnum.REQUISICAO
                    , MessagesUtil.SeveridadeEnum.INFO);
            
        }
        
    }
    
    /**
     * Método que cria uma requisição de promoção para um perfil.
     */
    public void requisitaPromocao(){
        
        List<Requisicao> requisicoes = crud.consultaLista("FROM Requisicao WHERE "
                + "perfil_id = :perfil_id "
                + "and respondido = false "
                + "and data_vida >= now()"
                ,new String[]{"perfil_id"}
                ,new String[]{String.valueOf(opPerfil.GetPerfilSession().getId())});

        if(requisicoes != null && !requisicoes.isEmpty()){
            
            for(Requisicao requisicao : requisicoes){
                
                if(requisicao.getTitulo().startsWith("PROMOVER")){

                    MessagesUtil.Messages.getCurrentMessage(
                    Requisicao.class
                   ,"Você possui uma requisição de promoção não respondida!"
                   ,MessagesUtil.Messages.OperacaoEnum.REQUISICAO
                   ,MessagesUtil.SeveridadeEnum.ERRO);
                    break;
                    
                }
                
            }
            
        }
        else{
            
            if(opPerfil.GetPerfilSession().getTipo().equals("GERENTE DE EVENTOS") 
                    || opPerfil.GetPerfilSession().getTipo().equals("ADMINISTRADOR")
                    || opPerfil.GetPerfilSession().getTipo().contains(
                            ((Requisicao)crud.getInstance()).getTitulo()
                                    .replaceAll("PROMOVER_", ""))){
                
                MessagesUtil.Messages.getCurrentMessage(
                        Requisicao.class
                       ,"Seu perfil possui este nível de promoção!"
                       ,MessagesUtil.Messages.OperacaoEnum.REQUISICAO
                       ,MessagesUtil.SeveridadeEnum.ERRO);
                
            }
            else{
            
                ((Requisicao)crud.getInstance()).setSituacao(SituacaoEnum.AGUARDANDO);
                ((Requisicao)crud.getInstance()).setUsuario(opPerfil.GetPerfilSession().getUsuario());
                ((Requisicao)crud.getInstance()).setPerfil(opPerfil.GetPerfilSession()); 
                ((Requisicao)crud.getInstance()).setDataVencimento();
                ((Requisicao)crud.getInstance()).setDescricao("O perfil" 
                        + opPerfil.GetPerfilSession().getLogin()
                        + " do usuário " 
                        + opPerfil.GetPerfilSession().getUsuario().getNome()
                        + " Requisita uma promoção de perfil.");

                if(((Requisicao)crud.getInstance()).getTitulo() == null) {
                    MessagesUtil.Messages.getCurrentMessage(
                            Requisicao.class
                           ,"O tipo da requisição deve ser selecionada!"
                           ,MessagesUtil.Messages.OperacaoEnum.REQUISICAO
                           ,MessagesUtil.SeveridadeEnum.ERRO);
                }
                else{

                    crud.salvar();

                    MessagesUtil.Messages.getCurrentMessage(Requisicao.class
                            ,"criada com sucesso, aguarde o email com a resposta."
                            , MessagesUtil.Messages.OperacaoEnum.REQUISICAO
                            , MessagesUtil.SeveridadeEnum.INFO);

                }
        
            }
            
        }
        
    }
    
//    public void requisitaInscricao(String tipo, Evento evento){
//        
//        if(tipo == null){
//            MessagesUtil.Messages.getCurrentMessage(Requisicao.class
//                    ,"O tipo de inscrição deve ser selecionado!"
//                    , MessagesUtil.Messages.OperacaoEnum.REQUISICAO
//                    , MessagesUtil.SeveridadeEnum.AVISO);
//        }
//        else if (tipo.equals("REVISOR DE TRABALHOS") 
//                && opPerfil.GetPerfilSession().getTipo().equals("PADRAO")){
//             MessagesUtil.Messages.getCurrentMessage(Requisicao.class
//                    ,"Você não possui promoção de revisor para selecionar este"
//                            + "tipo de inscrição"
//                    , MessagesUtil.Messages.OperacaoEnum.REQUISICAO
//                    , MessagesUtil.SeveridadeEnum.AVISO);
//        } 
//        else{
//            
//            ((Requisicao)crud.getInstance()).setSituacao(SituacaoEnum.AGUARDANDO);
//            ((Requisicao)crud.getInstance()).setUsuario(opPerfil.GetPerfilSession().getUsuario());
//            ((Requisicao)crud.getInstance()).setPerfil(opPerfil.GetPerfilSession());
//            ((Requisicao)crud.getInstance()).setTitulo(TituloEnum.NOVA_INSCRICAO);
//            ((Requisicao)crud.getInstance()).setDataVencimento();
//            ((Requisicao)crud.getInstance()).setDescricao("REQUISIÇÃO DE INSCRIÇÃO: " 
//                                                + evento.getId()
//                                                + ":"
//                                                + tipo);
//            
//        }
//       
//    }
    
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
     * Método que realiza uma busca por requisições de um determinado usuário 
     * (perfil).
     * @return lista de objetos do tipo Requisição resultantes da pesquisa.
     */
    public List<Requisicao> buscarRequisicoesPerfil(){
        
        crud.setLista(crud.consultaLista("FROM Requisicao WHERE destino_id = :destino_id"
                + " and respondido = false and data_vida >= now()"
                ,new String[]{"destino_id"}
                ,new String[]{String.valueOf(opPerfil.GetPerfilSession().getId())}));
        
        return crud.getLista();
        
    }
   
    /**
     * Método que responde uma requisição preenchendo todos os campos da requisição
     * e enviando a resposta para o usuário via email.
     * @param valor O tipo de resposta, se foi recusado ou aceito.
     * @param perfil O perfil que respondeu a requisição
     */
    public void respondeRequisicao(String valor, Object perfil){
        
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
        ((Requisicao)crud.getInstance()).setDestino((Perfil)perfil);        
        
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
        
    }
    
    public OperacaoPerfil getOperacaoPerfil(){
        return opPerfil;
    }
    
    /**
     * Método auxiliar, que informa o usuário que da alteração de um atributo,
     * que será utilizado na requisição de promoção.
     */
    public void setPromocaoRevisor(){
        
        ((Requisicao)crud.getInstance()).setTitulo(TituloEnum.PROMOVER_REVISOR);
        MessagesUtil.Messages.getCurrentMessage(Requisicao.class
                    ,"Promoção Gerente de Revisor de Trabalhos Selecionado."
                    , MessagesUtil.Messages.OperacaoEnum.REQUISICAO
                    , MessagesUtil.SeveridadeEnum.INFO);
        
    }
    
    /**
     * Método auxiliar, que informa o usuário que da alteração de um atributo,
     * que será utilizado na requisição de promoção.
     */
    public void setPromocaoGerente(){
        
        ((Requisicao)crud.getInstance()).setTitulo(TituloEnum.PROMOVER_GERENTE);
        MessagesUtil.Messages.getCurrentMessage(Requisicao.class
                    ,"Promoção Gerente de Eventos Selecionado."
                    , MessagesUtil.Messages.OperacaoEnum.REQUISICAO
                    , MessagesUtil.SeveridadeEnum.INFO);
        
    }
    
}
