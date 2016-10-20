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

import java.io.Serializable;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * Classe que realiza operações de visão nas telas do projeto, mais especificamente
 * na barra de acesso da página.
 * @author notrevequadrosc@gmail.com
 */
@Named("PagesBean")
@ConversationScoped
public class PagesViewBean implements Serializable{
    
    String uri = FacesContext.getCurrentInstance().getViewRoot().getViewId();
    
    String titulo = "Evoevent 0.1.0.0 Alpha";
    
    /**
     * Método que verifica se o nome da página atual termina com index, pois 
     * significa que estamos na página inicial.
     * @return retorna um valor boleano para o teste.
     */
    public boolean isIndex(){
        return uri.endsWith("index.xhtml");
    }
    
    /**
     * Método que constrói o título rodapé da página.
     * @return Objeto String contendo o valor do titulo atual + rodapé.
     */
    public String inicializaRodape(){
        return titulo.concat("----- Eventos Acadêmicos  -  IFRS 2016/2");
    }
    
    /**
     * Método que constrói o título da página.
     * @return Objeto String contendo o valor do título atual.
     */
    public String inicializaTitulo(){
        return titulo;
    }
    
    /**
     * Método que realiza a verificação do botão para saber se estamos na página
     * e adicionar efeito ao botão relativo a essa página (efeito de botão fosco).
     * @param id identificação do botão, não necessariamente o nome dele mas a
     * função ou a que página redireciona no nosso sistema.
     * @return retorna uma String contendo o tipo de estilo a ser aplicado:
     * buttonHead, estilo padrão botão normal;
     * nuttonHeadSelected, estilo de botão pressionado, o mesmo fica cinza.
     */
    public String buttonSwitch(String id){
        
        String comparador = uri.substring(uri.lastIndexOf("/")+1, uri.lastIndexOf("."));
        if(comparador.equalsIgnoreCase(id)){
            return "buttonHeadSelected";
        }
        else{
            return "buttonHead";
        }
        
    }
    
}
