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
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import modelo.Usuario;
import modelo.utils.DateUtil;
import modelo.utils.EmailUtil;
import modelo.utils.MessagesUtil;
import modelo.utils.MessagesUtil.Messages.OperacaoEnum;
import modelo.utils.MessagesUtil.SeveridadeEnum;

/**
 * Classe que realiza operações entre o formulário de cadastro e a entidade 
 * de cadastro
 * @author notrevequadrosc@gmail.com
 */
@Named("Usuario")
@SessionScoped
public class OperacaoUsuario implements Serializable{
    
    private final Crud crud = new Crud(Usuario.class, new Usuario());
    
    public Crud getCrud() {
        return crud;
    }
    
    /**
     * Método que salva o usuário no banco de dados e envia um email de confirmação
     * para o endereço de email informado pelo usuário, pois apenas assim a 
     * requisição pode ser efetivada.
     */
    public void salvar(){
        
        String email = ((Usuario)crud.getInstance()).getEmail();
        String acesso = "localhost:8080/br.edu.ifrs.restinga.Evoevent/faces/resources/pages/emailpagina.xhtml";
        //alterar esse link no futuro.
        
        ((Usuario)crud.getInstance()).setCodigo(UUID.randomUUID().toString());
        
        EmailUtil.Email.enviaEmail(
                email
                ,"NO-REPLY-Confirmação de Cadastro Evoevent "
                    + DateUtil.DateConversors.getDatePadraoBrasileiro(new Date()) 
                ,"Para efetivar seu cadastro você deve confirmar seu email em:\n" 
                    +acesso
                    +"\ne inserir o código: "
                    + ((Usuario)crud.getInstance()).getCodigo()
                        +"\n\nEMAIL GERADO AUTOMATICAMENTE, NÃO RESPONDA.");
        
        crud.salvar();
        enviaMensagem(" Um email foi de confirmação foi enviado para "+email+".", 2, 3);

    }
    
    /**
     * Método que realiza a consulta para saber se o código é válido e retorna
     * um objeto do tipo usuário dono do código caso seja válido e o email não
     * tenha sido confirmado.
     * @return objeto do usuário resultante da consulta ou null se não houver 
     * 
     */
    public Usuario confirmarEmail(){
 
        Usuario usuario = (Usuario)crud.consultaUnica("FROM Usuario WHERE codigo = :codigo"
                ,new String[]{"codigo"}
                ,new String[]{((Usuario)crud.getInstance()).getCodigo()});
        
        if(usuario != null){
            
            if(usuario.isEmailConfirmado()){
                enviaMensagem("Não é necessário reconfirmar o email, pois este email consta como confirmado.", 2, 0);
            }
            else{
                
                usuario.setEmailConfirmado(true);
                crud.setInstance(usuario);
                crud.atualizar();
                
                enviaMensagem("Email: "+ usuario.getEmail()+" confirmado com sucesso!", 2, 3);
                return usuario;
                
            }
            
        }
        else{
            enviaMensagem(" O código inserido é inválido!.", 2, 1); 
        }

        return null;
        
    }
    
    private void enviaMensagem(String mensagem, int tipo, int sev){
       
        OperacaoEnum[] op = {
                OperacaoEnum.ACESSO
                ,OperacaoEnum.MENSAGEM
                ,OperacaoEnum.REQUISICAO};
        
        SeveridadeEnum[] severidade = {
                SeveridadeEnum.AVISO
                ,SeveridadeEnum.ERRO
                ,SeveridadeEnum.FATAL
                ,SeveridadeEnum.INFO};
        
        if(tipo < 0 || tipo > op.length){
            tipo = (int)(Math.random() * op.length);
        }
        
        if(sev < 0 || sev > severidade.length){
            sev = (int)(Math.random() * op.length);
        }
        
        MessagesUtil.Messages.getCurrentMessage(
            crud.getInstance().getClass()
            , mensagem
            , op[tipo]
            , severidade[sev]);
        
    }
    
}
