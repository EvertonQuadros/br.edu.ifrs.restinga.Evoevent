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
import controle.OperacaoRequisicao;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Perfil;
import modelo.Requisicao;
import modelo.Usuario;

/**
 * Classe view que interage com a pagina fadministrativa e com o controle de
 * operação de requisição para manipular requisições no sistema.
 * @author notrevequadrosc@gmail.com
 */
@Named("RequisicaoBean")
@ConversationScoped
public class RequisicaoViewBean implements Serializable {
    
    @Inject 
    OperacaoRequisicao opRequisicao;
    
    @PostConstruct
    public void init(){
        opRequisicao.buscarRequisicoes();
    }

    public OperacaoRequisicao getRequisicao() {
        return opRequisicao;
    }
    
    /**
     * Método view que realiza a ligação da view com as classes de controle de 
     * requisição e de perfil para realização dos métodos de resposta de requisição 
     * e de envio de email.
     * @param opRequisicao a instância da operação requisição com os objetos
     * que serão utilizados na manipulação da resposta desta requisição.
     * @param situacao o tipo re resposta da requisição.
     * @return retorna o endereço da própria página para que ela seja atualizada
     * após a resposta.-
     */
    public String respondeRequisicao(OperacaoRequisicao opRequisicao,String situacao){

        Usuario usuario = ((Requisicao)(opRequisicao.getCrud().getInstance())).getUsuario();
        Perfil perfil = opRequisicao.getOperacaoPerfil().GetPerfilSession();
        Requisicao requisicao = (Requisicao)opRequisicao.getCrud().getInstance();
        
        opRequisicao.respondeRequisicao(situacao,perfil);
        
            if(perfil.getTipo().equalsIgnoreCase("ADMINISTRADOR")){
                
                try {
                    
                    if(requisicao.getTitulo().contains("NOVO_PERFIL")){
                        ((OperacaoAdministrador)(opRequisicao.getOperacaoPerfil().getOperacao().getOperacao())).criarPerfil(usuario);
                    }
                    else{
                        ((OperacaoAdministrador)(opRequisicao.getOperacaoPerfil().getOperacao().getOperacao()))
                                .adicionarPromocao(
                                        requisicao.getPerfil()
                                        , requisicao.getTitulo());
                    }
                        
                } catch (IllegalAccessException ex) {
                    System.out.printf("\nERRO ACESSANDO A OPÇÃO DE ADMINISTRADOR!" + ex.getMessage());
                }
                
            }
            else{
                return opRequisicao.getOperacaoPerfil().getOperacao().logout(opRequisicao.getOperacaoPerfil().getCrud());
            }
        
        return "fadministrativa";
        
    }
    
}
