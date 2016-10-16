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

package modelo;

/**
 * Classe abstrata que define todas as classes entidades do projeto, e os 
 * métodos padrões, de get/set e o atribute id. Todas as classes entidades devem
 * extender Entidade.
 * @author notrevequadrosc@gmail.com
 */
public abstract class Entidade {
    
    public abstract int getId();
    
    public abstract void setId(int id);
        
}