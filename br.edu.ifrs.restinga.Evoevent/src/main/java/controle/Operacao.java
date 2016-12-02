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
import java.util.UUID;
import javax.faces.context.FacesContext;
import modelo.Evento;
import modelo.Inscricao;
import modelo.Perfil;
import modelo.Usuario;
import modelo.utils.EmailUtil;
import modelo.utils.MessagesUtil;

/**
 * Classe que realiza operações relativas ao controle de usuário.
 * @author notrevequadrosc@gmail.com
 */
public class Operacao implements Serializable{
 
    private Operacao operacoes = null;
    
    /**
     * Método que realiza o login do perfil através dos dados preenchidos pelo
     * usuário de login e senha.
     * @param crud Objeto que realiza as operações no banco de dados.
     * @return objeto resultante da consulta.
     */
    public String doLogin(Crud crud) {
        
        String query = "FROM Perfil WHERE login = :login and senha = :senha";
        Perfil objeto = (Perfil)crud.consultaUnica(query
                    ,new String[]{"login","senha"}
                    ,new String[]{((Perfil)crud.getInstance()).getLogin()
                                 ,((Perfil)crud.getInstance()).getSenha()}); 

        if(objeto == null) {
            
            MessagesUtil.Messages.getCurrentMessage(
                Perfil.class
                , "As credenciais de perfil e/ou senha são inválidas!"
                , MessagesUtil.Messages.OperacaoEnum.ACESSO
                , MessagesUtil.SeveridadeEnum.AVISO);
            
            return null;
            
        } 
        else {
            
            crud.setInstance(objeto);
            setOperacao(objeto.getTipo());
            FacesContext facesContext = FacesContext.getCurrentInstance();
	    facesContext.getExternalContext().getSessionMap().put("Perfil", objeto);
            
            return "home";
            
        }

    }
    
    /**
     * Método que retorna o objeto de operações avançadas do perfil caso o mesmo
     * possua. 
     * @return Retorna o objeto das operações instânciado de acordo com a permissão
     * do perfil ou null
     * @throws IllegalAccessException Estoura exceção se houver tentativa de acesso
     * ao objeto e o mesmo for null, parte-se do princípio que o perfil não possui
     * acesso pois o mesmo não foi instanciado.
     */
    public Operacao getOperacao() throws IllegalAccessException{
        
        try {
            
            if(operacoes == null){
                throw new IllegalAccessException("PERMISSÃO DE ACESSO INSUFICIENTES!");
            }
        
            return operacoes;
            
        } 
        catch (IllegalAccessException ex) {
            
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            MessagesUtil.Messages.getCurrentMessage(
                Perfil.class
                , "ACESSO INVÁLIDO: "+ ex.getMessage()
                , MessagesUtil.Messages.OperacaoEnum.ACESSO
                , MessagesUtil.SeveridadeEnum.FATAL);
             
        }
        
        return null;
   
    }
    
    private void setOperacao(String permissao){
        
        switch (permissao) {
            case "ADMINISTRADOR":
                operacoes = new OperacaoAdministrador();
                break;
            case "GERENTE DE EVENTOS":
                operacoes = new OperacaoGerenteEventos();
                break;
            case "REVISOR DE EVENTOS":
                operacoes = new OperacaoRevisorEventos();
                break;
            default:
                operacoes = null;
                break;
        }
        
    }
    
    /**
     * Método que encerra a sessão do perfil;
     * @param crud Objeto que realiza as operações no banco de dados.
     * @return Um texto contendo o caminho da página inicial do evoevent.
     */
    public String logout(Crud crud) {
        
        crud.setInstance(new Perfil());
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        
        return "index";
        
    }
    
    /**
     * Método realiza teste para saber se um perfil está conectado.
     * @param crud Objeto que realiza as operações no banco de dados.
     * @return valor boleano (true/false) com o resultado do teste.
     */
    public boolean PerfilLogado(Crud crud) {
        return ((Perfil)crud.getInstance()).getLogin() != null;
    }

        /**
         * Classe interna que implementa métodos e atributos específicos do perfil
         * com privilégios de administrador.
         */
        public class OperacaoAdministrador extends OperacaoGerenteEventos 
                implements Serializable{

        /**
         * Método que cria um novo perfil no sistema, referente a um usuário
         * previamente cadastrado. O login e a senha deste usuário é gerado pelo
         * sistema.
         * @param usuario Objeto relativo ao usuário na qual o perfil pertence.
         */
        public void criarPerfil(Usuario usuario){
                
            if(usuario != null){

            Crud crud = new Crud(Perfil.class, new Perfil());
            String senha = String.valueOf(String.valueOf(
                    UUID.randomUUID())
                    .replaceAll("-", "")
                    .substring(5,17));

            ((Perfil)crud.getInstance()).setLogin(usuario.getEmail());
            ((Perfil)crud.getInstance()).setSenha(senha);
            ((Perfil)crud.getInstance()).setTipo("PADRAO");
            ((Perfil)crud.getInstance()).setUsuario(usuario);

            crud.salvar();

            EmailUtil.Email.enviaEmail(usuario.getEmail()
                ,"NO-REPLY: Suas credenciais do Evoevent"
                ,"Seja bem vindo ao Evoevent, plataforma de "
                        +"eventos acadêmicos, essas são suas credênciais"
                        +" provisórias\nLogin: "+usuario.getEmail()+"\nSenha: "
                        +senha+"\n\nEMAIL GERADO AUTOMATICAMENTE, NÃO RESPONDA.");
            }

        }
     
        /**
         * Método administrativo reference a edição das permissões de um usuário.
         * @param perfil Objeto Perfil que receberá a alteração das permissões.
         * @param promocao Objeto String referente ao tipo de promoção:
         * GERENTE DE EVENTOS
         * REVISOR DE EVENTOS
         */
        public void adicionarPromocao(Perfil perfil, String promocao){
                
                Crud crud = new Crud(Perfil.class, perfil);
                
                if(promocao.equals("PROMOVER_GERENTE")){
                    ((Perfil)crud.getInstance()).setTipo("GERENTE DE EVENTOS");
                }
                else{
                    ((Perfil)crud.getInstance()).setTipo("REVISOR DE EVENTOS");
                }
                
                crud.atualizar();
                
            }
            
        }

        /**
         * Classe interna que implementa métodos e atributos específicos do perfil
         * com privilégios de Gerente de Eventos.
         */
        public class OperacaoGerenteEventos extends OperacaoRevisorEventos 
                implements Serializable{

        /**
         * Método de classe interna do gerente de eventos responsável por 
         * cadastrar um novo evento no sistema e realiza uma inscricao do 
         * gerente como administrador do evento.
         * @param evento o evento criado a partir do preenchimento do formulário.
         */
        public void agendarNovoEvento(Evento evento){
                
                FacesContext facesContext = FacesContext.getCurrentInstance();
                Perfil perfil = (Perfil)facesContext.getExternalContext().getSessionMap().get("Perfil");
                
                Crud crud = new Crud(Evento.class, new Evento());
                crud.setInstance(evento);
                crud.salvar();
                
                crud = new Crud(Inscricao.class, new Inscricao());
                
                Inscricao inscricao = new Inscricao();
                inscricao.setDataInscricao(new Date());
                inscricao.setEvento(evento);
                inscricao.setPerfil(perfil);
                inscricao.setTipoInscricao("GERENTE DE EVENTO");
                crud.setInstance(inscricao);
                crud.salvar();
                
            }

        }

        /**
         * Classe interna que implementa métodos e atributos específicos do perfil
         * com privilégios de Revisor de Eventos.
         */
        public class OperacaoRevisorEventos extends Operacao 
                implements Serializable{

            public String testeRevisor(){
                return null;
            }

        }
    
    
}
