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

import controle.OperacaoPerfil;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Usuario;

/**
 * Classe view que realiza operações com os dados retornados do usuário autenticado
 * e exibe esses dados na página de perfil.
 * @author notrevequadrosc@gmail.com
 */
@Named("PerfilBean")
@ConversationScoped
public class PerfilViewBean implements Serializable{

    @Inject 
    OperacaoPerfil perfil;
    
    Usuario usuario;
    
    
    @PostConstruct
    public void init(){
       usuario = perfil.GetPerfilSession().getUsuario();
    }
    
    public Usuario getUsuario(){
        return usuario;
    }
    
    /**
     * Método auxiliar que retorna o sexo do usuário corretamente por extenso.
     * @return Objeto String contendo o sexo por extenso.
     */
    public String getSexo(){
        
        if(usuario.getSexo() == 'M' || usuario.getSexo() == 'm'){
            return "Masculino";
        }
        else if(usuario.getSexo() == 'F' || usuario.getSexo() == 'f'){
            return "Feminino";
        }
        
        return "Undefined";
    }
    
}
