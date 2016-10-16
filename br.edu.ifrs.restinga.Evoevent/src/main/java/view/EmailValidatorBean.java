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

import controle.Crud;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import modelo.Usuario;

/**
 * Classe que valida o email e verifica se já existe um usuário cadastrado com
 * este email.
 * @author notrevequadrosc@gmail.com
 */
@FacesValidator(EmailValidatorBean.VALIDATOR_ID)
public class EmailValidatorBean implements Validator {

    public static final String VALIDATOR_ID = "emailvalidator";

    /**
     * Método que o email verificando se existe no sistema.
     * @param facesContext a referência do contexto facelets
     * @param component a referência do componente que está passando o valor
     * @param value o objeto com o email a ser validade.
     * @throws ValidatorException estoura caso um usuário com este email seja 
     * encontrado no sistema.
     */
    @Override
    public void validate(FacesContext facesContext, UIComponent component, 
        Object value) throws ValidatorException {

        Crud crud = new Crud(Usuario.class, new Usuario());
        
        String email = (String) value;
        
        Usuario usuario = (Usuario) crud.consultaUnica(
                "From Usuario where email = :email"
                , new String[]{"email"}
                , new String[]{email.toLowerCase()});
        
        if (usuario != null) {
            throw new ValidatorException(new FacesMessage(
                    FacesMessage.SEVERITY_ERROR
                    , "Existe um cadastro com este email no sistema."
                    , null));
        }

    }
    
}
