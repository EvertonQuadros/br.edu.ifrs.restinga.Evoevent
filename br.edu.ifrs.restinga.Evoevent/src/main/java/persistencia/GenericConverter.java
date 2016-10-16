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
package persistencia;

/**
 * Classe conversora genérica para todos os tipos de objetos que forem recebidos
 * através dos nossos comboBox.
 * @author notrevequadrosc@gmail.com
 */

import controle.Crud;
import java.util.Objects;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;
import modelo.Entidade;

@FacesConverter(value = "Converter")
public class GenericConverter<T extends Entidade> implements javax.faces.convert.Converter {

    private final Crud crud;
    private final Class<T> tipo;
    
    /**
     * Construtor padrão recebe o crud, e através dele pode pegar o tipo da 
     * classe que será trabalhada nos métodos.
     * @param crud O objeto crud da nossa classe de controle
     */
    public GenericConverter(Crud crud){
        
        this.crud = crud;
        tipo = crud.getTipo();
    
    }
    
    /**
     * Método que recebe um objeto String e através do id recebido 
     * busca no banco de dados o id referente para transformar esse objeto em
     * nosso objeto T.
     * @param fc Contexto dos facelets
     * @param uic Identificador do componente
     * @param string Objeto String que será transformado no objeto T 
     * correspondente
     * @return Object contendo a instância da String transformada em objeto T
     */
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
 
        if (string != null && !string.isEmpty()) {
            
            if(string.contains("Selecione")){
                string = "0";
            }
           
            return crud.carregar(Integer.parseInt(string));
            
        }
        
        return null;
        
    }

    /**
     * Método que converte o objeto e retorna o id do objeto transformado em 
     * String.
     * @param fc Contexto dos facelets
     * @param uic Identificador do componente
     * @param o o objeto recebido pelo método para conversão
     * @return retorna uma String correspondente do id do objeto
     */
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o != null && tipo.isInstance(o)) {
            return String.valueOf(((T) o).getId());
        }

        return null;
    }
    
    @Override
    public boolean equals(Object obj) {
        
    if (!(tipo.isAssignableFrom(obj.getClass()))) {
        return false;
    }
    
    T objeto = (T) obj;

    return (this.crud.getInstance().getId() == objeto.getId());

    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.crud.getInstance());
        return hash;
    }
    
}
