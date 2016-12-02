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

import controle.Operacao.OperacaoAdministrador;
import controle.OperacaoPerfil;
import controle.OperacaoRequisicao;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import modelo.Perfil;
import modelo.Requisicao;
import modelo.Usuario;

/**
 * Classe view que interage com a página fadministrativa e o controle de requisições.
 * @author notrevequadrosc@gmail.com
 */
@Named("AdministrativaBean")
@ConversationScoped
public class AdministrativaViewBean implements Serializable {
    
    OperacaoRequisicao requisicao = new OperacaoRequisicao();
    
    @PostConstruct
    public void init(){
        requisicao.buscarRequisicoes();
    }

    public OperacaoRequisicao getRequisicao() {
        return requisicao;
    }
    
    /**
     * Método view que realiza a ligação da view com as classes de controle de 
     * requisição e de perfil para realização dos métodos de resposta de requisição 
     * e de envio de email.
     * @param requisicao
     * @param situacao o tipo re resposta da requisição.
     * @return retorna o endereço da própria página para que ela seja atualizada
     * após a resposta.-
     */
    public String respondeRequisicao(OperacaoRequisicao requisicao,String situacao){
        
        OperacaoPerfil perf = new OperacaoPerfil();
        Perfil perfil = perf.GetPerfilSession();
        
        requisicao.respondeRequisicao(situacao,perfil);
        Usuario usuario = ((Requisicao)(requisicao.getCrud().getInstance())).getUsuario();
  
        try {
            if(perf.getOperacao().getOperacao() instanceof OperacaoAdministrador){
                ((OperacaoAdministrador)(perf.getOperacao())).criarPerfil(usuario);
            }
            else{
                return perf.getOperacao().logout(perf.getCrud());
            }
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AdministrativaViewBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "fadministrativa";
        
    }
    
}
