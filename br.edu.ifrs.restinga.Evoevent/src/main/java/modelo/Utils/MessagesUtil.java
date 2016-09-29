package modelo.Utils;

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
     * Classe estática que pertence a classe MessagesUtil e que realiza operações
     * onde a mensagem exibida corresponde a uma operação CRUD.
     */
    public static class CrudMessages{
      
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
         * Método que cria uma mensagem completa para uma situação corrente 
         * referente a uma operação de CRUD no sistema.
         * @param tipo O tipo da classe que está invocando o método.
         * @param mensagem o texto que descreve o corpo da mensagem.
         * @param operacao objeto que representa uma das quatro operações CRUD.
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
