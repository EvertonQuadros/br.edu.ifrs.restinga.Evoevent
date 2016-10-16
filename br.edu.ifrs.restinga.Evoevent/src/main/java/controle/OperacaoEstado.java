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
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import modelo.Estado;
import persistencia.GenericConverter;

/**
 * Classe de controle que realiza operações da entidade Estado
 * @author notrevequadrosc@gmail.com
 */
@Named("Estado")
@RequestScoped
public class OperacaoEstado implements Serializable{
    
    private final Crud crud = new Crud(Estado.class, new Estado());
    private final GenericConverter converter = new GenericConverter(crud);
    
    /**
     * Método que inicializa na construção do objeto uma lista contendo todos os
     * estados cadastrados no banco de dados. 
     */
    @PostConstruct
    public void init() {
        crud.setLista(crud.listar());
    }
    
    public Crud getCrud() {
        return crud;
    }
    
    public GenericConverter getConverter(){
        return converter;
    }
    
}
