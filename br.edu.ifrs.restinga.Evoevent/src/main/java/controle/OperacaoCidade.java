/*
 * Copyright 2016 Convidado.
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
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Cidade;
import modelo.Estado;
import modelo.Usuario;
import persistencia.GenericConverter;

/**
 * Classe de controle que realiza operações da entidade Cidade.
 * @author Convidado
 */
@Named("Cidade")
@RequestScoped
public class OperacaoCidade implements Serializable {
    
    private final Crud crud = new Crud(Cidade.class, new Cidade());
    private final GenericConverter converter = new GenericConverter(crud);

    @Inject 
    OperacaoUsuario opUsuario;
    
    @PostConstruct
    public void init() {
        setLista(((Usuario)opUsuario.getCrud().getInstance()).getEstado());
    }
    
    /**
     * Método que recebe uma entidade e busca através do banco de dados uma lista
     * com todas as cidades do estado.
     * @param estado o objeto correspondente ao estado para realizar a consulta
     * que busca todas as cidades pelo correspondente aquele estado.
     */
    public void setLista(Estado estado){
        
        if(estado !=null){
            crud.listar("from Cidade where estado_id",estado.getId());
        }
        
    }
    
    public Crud getCrud() {
        return crud;
    }
    
    public GenericConverter getConverter(){
        return converter;
    }
    
}
