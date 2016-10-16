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

package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import modelo.utils.DateUtil;

/**
 * Classe que preenche os checkbox com itens de interesse para o usuário.
 * @author notrevequadrosc@gmail.com
 */
@Named("formularioCadastroBean")
@ConversationScoped
public class FormularioCadastroBean implements Serializable{
    
    private List<String> interesses;
    private List<String> instituicao;
    private List<String> profissao;
    private List<String> instrucao;
    private Date dataMin;
    private Date dataMax;
    
    /**
     * Método que inicializa os itens do formulário de cadastro quando o objeto 
     * for construído em seus respectivos componentes.
     */
    @PostConstruct
    public void init() {
    //interesse será gerado pelo banco de dados futuramente.
    interesses = new ArrayList<>(Arrays.asList("Antropologia"
                                               ,"Física"
                                               ,"Geologia"
                                               ,"História"
                                               ,"Informática"
                                               ,"Inglês"
                                               ,"Matemática"
                                               ,"Química"
                                               ,"Robótica"));

    instituicao = new ArrayList<>(Arrays.asList("Instituto Federal de Goiás"
                                                ,"Instituto Federal Goiano"
                                                ,"Instituto Federal Mato Grosso"
                                                ,"Instituto Federal Mato Grosso do Sul"
                                                ,"Instituto Federal de Alagoas"
                                                ,"Instituto Federal da Bahia"
                                                ,"Instituto Federal Baiano"
                                                ,"Instituto Federal do Ceará"
                                                ,"Instituto Federal do Maranhão"
                                                ,"Instituto Federal da Paraíba"
                                                ,"Instituto Federal de Pernambuco"
                                                ,"Instituto Federal do Sertão Pernambucano"
                                                ,"Instituto Federal do Piauí"
                                                ,"Instituto Federal do Rio Grande do Norte"
                                                ,"Instituto Federal de Sergipe"
                                                ,"Instituto Federal do Acre"
                                                ,"Instituto Federal do Amapá"
                                                ,"Instituto Federal do Amazonas"
                                                ,"Instituto Federal do Pará"
                                                ,"Instituto Federal de Rondônia"
                                                ,"Instituto Federal de Roraima"
                                                ,"Instituto Federal do Tocantins"
                                                ,"Instituto Federal do Espírito Santo"
                                                ,"Instituto Federal de Minas Gerais"
                                                ,"Instituto Federal do Norte de Minas Gerais"
                                                ,"Instituto Federal do Sudeste de Minas"
                                                ,"Instituto Federal do Sul de Minas"
                                                ,"Instituto Federal do Triângulo Mineiro"
                                                ,"Instituto Federal do Rio de Janeiro"
                                                ,"Instituto Federal Fluminense"
                                                ,"Instituto Federal de São Paulo"
                                                ,"Instituto Federal do Paraná"
                                                ,"Instituto Federal do Rio Grande do Sul"
                                                ,"Instituto Federal Farroupilha"
                                                ,"Instituto Federal Sul-rio-grandense"
                                                ,"Instituto Federal de Santa Catarina"
                                                ,"Instituto Federal Catarinense"));

    profissao = new ArrayList<>(Arrays.asList("Estudante"
                                               ,"Professor"
                                               ,"Pesquisador"
                                               ,"Outro"));
    
    
    instrucao = new ArrayList<>(Arrays.asList("Fundamental Incompleto"
                                              ,"Fundamental Completo"
                                              ,"Médio Incompleto"
                                              ,"Médio Completo"
                                              ,"Superior Incompleto"
                                              ,"Superior Completo"
                                              ,"Mestrado"
                                              ,"Doutorado"));

    dataMin = DateUtil.DateFunctions.getNovaData(
            DateUtil.UnidadeTempoEnum.ANO
            , -90);
    dataMax = DateUtil.DateFunctions.getNovaData(
            DateUtil.UnidadeTempoEnum.ANO
            , -8);
    
    }
    
    public List<String> getInteresses() {
        return interesses;
    }
    
    public List<String> getInstituicao() {
        return instituicao;
    }    

    public List<String> getProfissao(){
        return profissao;
    }
    
    public List<String> getInstrucao(){
        return instrucao;
    }

    public Date getDataMin() {
        return dataMin;
    }

    public void setDataMin(Date dataMin) {
        this.dataMin = dataMin;
    }

    public Date getDataMax() {
        return dataMax;
    }

    public void setDataMax(Date dataMax) {
        this.dataMax = dataMax;
    }
    
}

