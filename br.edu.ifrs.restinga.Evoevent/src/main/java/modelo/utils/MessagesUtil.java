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
/**
 * Author:  notrevequadrosc@gmail.com
 * Created: 09/10/2016
 */

package modelo.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.Entidade;

/**
 * Classe útil que realiza operações relativas a exibição de mensagens
 * do lado cliente de vários tipos, entre elas mensagens de CRUD.
 * @author notrevequadrosc@gmail.com
 */
public class MessagesUtil {
    
    private static FacesMessage msg = null;
    
        /**
         * Tipo de enumeradores que representam a severidade nas mensagens:
         * INFO (informações),
         * AVISO (mensagens de alerta),
         * ERRO (problemas que impedem uma ação no sistema),
         * FATAL (problema que impede o funcionamento do sistema).
         */
        public enum SeveridadeEnum {
            INFO(FacesMessage.SEVERITY_INFO),
            AVISO(FacesMessage.SEVERITY_WARN),
            ERRO(FacesMessage.SEVERITY_ERROR),
            FATAL(FacesMessage.SEVERITY_FATAL);
            
            private final FacesMessage.Severity severidade;
            
            SeveridadeEnum(FacesMessage.Severity severidade){
                this.severidade = severidade;
            }
            
            private FacesMessage.Severity getSeveridade(){
                return this.severidade;
            }
            
        }
  
    /**
     * Classe estática que pertence a classe MessagesUtil e que realiza operações
     * onde a mensagem exibida corresponde a uma operação CRUD.
     */
    public static class CrudMessages extends MessagesUtil{
      
        /**
         * Tipo de enumeradores que representam as quatro operações do CRUD:
         * SALVAR,
         * CARREGAR,
         * REMOVER,
         * ATUALIZAR.
         */
        public enum OperacaoEnum {
            SALVAR(" Cadastro: "), 
            CARREGAR(" Busca: "), 
            REMOVER(" Remoção: "), 
            ATUALIZAR(" Atualização: ");
        
            private final String valorOperacao;
            
            OperacaoEnum(String valor){
                this.valorOperacao = valor ;
            }
            
            private String getValorOperacao() {
                return this.valorOperacao;
            }
            
        }
        
        /**
         * Método que cria uma mensagem completa para uma situação corrente 
         * referente a operações no sistema.
         * @param tipo O tipo da classe que está invocando o método.
         * @param mensagem o texto que descreve o corpo da mensagem.
         * @param operacao objeto que representa a operação realizada.
         * @param severidade o enumerador que representa a severidade da mensagem:
         * INFO,
         * AVISO,
         * ERRO,
         * FATAL.
         */
        public static void getCurrentMessage(Class<? extends Entidade> tipo
                , String mensagem
                , OperacaoEnum operacao
                , SeveridadeEnum severidade){
            
            msg = new FacesMessage(
                    severidade.getSeveridade()
                    , tipo.getSimpleName()
                        + operacao.getValorOperacao() + mensagem 
                        , operacao.getValorOperacao());
            setFacesContext();

        }
  
    }
    
    public static class Messages extends MessagesUtil{
        
        /**
         * Tipo de enumeradores que representam operações do sistema:
         * ACESSO (Controle de acesso a métodos, classes ou a partes do sistema),
         * REQUISICAO (Controle de Pedidos que são realizados no sistema),
         * MENSAGEM (Comunicações feitas entre perfis ou automáticas do sistema).
         */
        public enum OperacaoEnum {
            ACESSO(" Acesso: "),
            REQUISICAO(" Requisicao: "),
            MENSAGEM(" Mensagem "); 
        
            private final String valorOperacao;
            
            OperacaoEnum(String valor){
                this.valorOperacao = valor ;
            }
            
            private String getValorOperacao() {
                return this.valorOperacao;
            }
            
        }
        
        /**
         * Método que cria uma mensagem completa para uma situação corrente 
         * referente a operações no sistema.
         * @param tipo O tipo da classe que está invocando o método.
         * @param mensagem o texto que descreve o corpo da mensagem.
         * @param operacao objeto que representa a operação realizada.
         * @param severidade o enumerador que representa a severidade da mensagem:
         * INFO,
         * AVISO,
         * ERRO,
         * FATAL.
         */
        public static void getCurrentMessage(Class<? extends Entidade> tipo
                , String mensagem
                , OperacaoEnum operacao
                , SeveridadeEnum severidade){
            
            msg = new FacesMessage(
                    severidade.getSeveridade()
                    , tipo.getSimpleName()
                        + operacao.getValorOperacao() + mensagem 
                        , operacao.getValorOperacao());
            setFacesContext();

        }
        
    }
    
    private static void setFacesContext(){
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
}
