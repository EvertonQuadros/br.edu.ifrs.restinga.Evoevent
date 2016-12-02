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

import controle.OperacaoInscricao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Evento;
import modelo.Inscricao;
import modelo.Perfil;
import modelo.utils.MessagesUtil;

/**
 * Classe view que interage com a tela perfil1 evento1 e com a classe de controle
 * responsável por manipular inscrições.
 * @author notrevequadrosc@gmail.com
 */
@Named("InscricaoBean")
@ConversationScoped
public class InscricaoViewBean implements Serializable{
    
    @Inject 
    private OperacaoInscricao opInscricao;
    
    private Inscricao inscricao;
    private List<String> tiposInscricao;
    
    @PostConstruct
    public void init(){
        
        inscricao = (Inscricao) opInscricao.getCrud().getInstance();
        
        tiposInscricao = new ArrayList<>(Arrays.asList("ALUNO"
                                                        ,"PROFESSOR"
                                                        ,"ORIENTADOR"
                                                        ,"COLABORADOR"
                                                        ,"PALESTRANTE"
                                                        ,"REVISOR DE TRABALHOS"));
        
    }

    public OperacaoInscricao getOpInscricao() {
        return opInscricao;
    }

    public Inscricao getInscricao() {
        return inscricao;
    }

    public List<String> getTiposInscricao() {
        return tiposInscricao;
    }
    
    /**
     * Método auxiliar que realiza testes nos dados informados pelo usuário e
     * realiza o preenchimento de um objeto do tipo Inscrição e posterior acesso
     * ao controle de Inscrições para inserir o objeto no banco de dados.
     * @param evento objeto evento que será incluido na inscrição.
     * @param perfil objeto Perfil do usuário que será incluído na inscrição.
     * @return Objeto String relativo a chamda da página através do método action.
     */
    public String salvar(Evento evento, Perfil perfil){
        
        if(inscricao.getTipoInscricao() != null){
            
            if(perfil.getTipo().equals("PADRAO") 
                && inscricao.getTipoInscricao().equals("REVISOR DE TRABALHOS")){
                
                MessagesUtil.Messages.getCurrentMessage(
                            Inscricao.class
                           ,"Você deve possuir promoção de Revisor para realizar esta"
                                   + " inscrição!"
                           ,MessagesUtil.Messages.OperacaoEnum.MENSAGEM
                           ,MessagesUtil.SeveridadeEnum.AVISO);
            }
            else{
                
                inscricao.setEvento(evento);
                inscricao.setDataInscricao(new Date());
                inscricao.setPerfil(perfil);

                opInscricao.getCrud().setInstance(inscricao);
                opInscricao.getCrud().salvar();

                MessagesUtil.Messages.getCurrentMessage(
                            Inscricao.class
                           ,"Inscrição realizada com sucesso!"
                           ,MessagesUtil.Messages.OperacaoEnum.MENSAGEM
                           ,MessagesUtil.SeveridadeEnum.INFO);

                inscricao = new Inscricao();

                return "home.xhtml";
                
            }
            
        }
        else{
            MessagesUtil.Messages.getCurrentMessage(
                        Inscricao.class
                       ,"Você deve selecionar o tipo de inscrição!"
                       ,MessagesUtil.Messages.OperacaoEnum.MENSAGEM
                       ,MessagesUtil.SeveridadeEnum.AVISO);
        }
        
        return null;
        
    }
    
}
