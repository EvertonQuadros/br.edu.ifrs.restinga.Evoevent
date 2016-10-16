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
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import modelo.Perfil;
import modelo.utils.MessagesUtil;

/**
 * Classe de controle que realiza operações de um perfil e suas atribuções de 
 * acordo com o tipo de perfil da instância do atributo operacao;
 * @author notrevequadrosc@gmail.com
 */
@ManagedBean(name="Perfil")
@SessionScoped
public class OperacaoPerfil implements Serializable{
    
    private final Crud crud = new Crud(Perfil.class, new Perfil());
    private Operacao operacao;
    
    /**
     * Método que inicializa na construção do objeto o tipo da instância do 
     * controle de perfis (Padrão,Revisor,Gerente ou Administrador);
     */
    @PostConstruct
    public void init(){
        
        operacao = new Operacao();
        
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        
        String message = (String) externalContext.getSessionMap().remove("message");

        if (message != null) {
            MessagesUtil.Messages.getCurrentMessage(
                    Perfil.class
                   ,message
                   ,MessagesUtil.Messages.OperacaoEnum.ACESSO
                   ,MessagesUtil.SeveridadeEnum.ERRO);
        }
        
    }
    
    public Crud getCrud() {
        return crud;
    }
    
    public Operacao getOperacao(){
        return operacao;
    }

}
