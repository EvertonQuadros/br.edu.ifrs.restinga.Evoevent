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

import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import modelo.utils.DateUtil;

/**
 * Classe que valida a data de um formulário de cadastro de acordo com a data
 * mínima e máxima estipulada pelo DateUtil.
 * @author notrevequadrosc@gmail.com
 */
@FacesValidator(DataValidatorBean.VALIDATOR_ID)
public class DataValidatorBean implements Validator {

    public static final String VALIDATOR_ID = "datevalidator";

    /**
     * Método que valida a data, através de um objeto convertido em Date.
     * @param facesContext a referência do contexto facelets
     * @param component a referência do componente que está passando o valor
     * @param value o objeto com a data a ser validade.
     * @throws ValidatorException estoura caso a data não esteja nos parâmetros
     * válidos, seja uma data no futuro ou no passado obedecendo os limites 
     * mínimos e máximos.
     */
    @Override
    public void validate(FacesContext facesContext, UIComponent component, 
        Object value) throws ValidatorException {

        Date data = (Date) value;
        
        Date min = DateUtil.DateFunctions.getNovaData(
            DateUtil.UnidadeTempoEnum.ANO
            , -90);
        
        Date max = DateUtil.DateFunctions.getNovaData(
            DateUtil.UnidadeTempoEnum.ANO
            , -8);
        
        if (data.after(max) || data.before(min)) {
            throw new ValidatorException(new FacesMessage(
                    FacesMessage.SEVERITY_ERROR
                    , "A data de nascimento inserida está fora dos padrões."
                    , null));
        }

    }
    
}
