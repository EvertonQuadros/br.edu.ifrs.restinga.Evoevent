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
package modelo.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Classe que realiza operações envolvendo datas com método úteis que podem
 * ser utilizadas durante toda a aplicação e em qualquer lugar.
 * @author notrevequadrosc@gmail.com
 */
public class DateUtil {
    
    /**
    * Tipo de enumeradores que representam unidades de tempo:
    * ANO (Calendar.YEAR),
    * MES (Calendar.MONTH),
    * DIA (Calendar.DAY_OF_YEAR).
    */
   public enum UnidadeTempoEnum {
       ANO(Calendar.YEAR),
       MES(Calendar.MONTH),
       DIA(Calendar.DAY_OF_YEAR);

       private final int campo;

       UnidadeTempoEnum(int campo){
           this.campo = campo;
       }

       private int getCampo(){
           return this.campo;
       }

   }
    
    /**
     * Classe estática que guarda métodos de conversoes de data, ou de números 
     * como por exemplo métodos que convertem anos em dias ou que retornam 
     * datas formatadas.
     */
    public static class DateConversors{
        
        /**
        * Retorna uma String contendo a conversão de um Date para o padrão
        * brasileiro (dd/MM/yyyy HH:mm:ss).
        * @param data O objeto Date que será convertido
        * @return objeto String contendo o resultado da conversão
        */
        public static String getDateTimePadraoBrasileiro(Date data){
            
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");    
            return fmt.format(data);
            
        }
        
        /**
        * Retorna uma String contendo a conversão de um Date para o padrão
        * brasileiro (dd/MM/yyyy).
        * @param data O objeto Date que será convertido
        * @return objeto String contendo o resultado da conversão
        */
        public static String getDatePadraoBrasileiro(Date data){
            
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");    
            return fmt.format(data);
            
        }
  
    }
    
    /**
     * Classe estática que guarda métodos de funções de data úteis como por 
     * exemplo retornar novas datas, comparações entre datas e outras manipulações.
     */
    public static class DateFunctions{

       /**
        * Método que retorna uma nova data a partir da data atual e de uma 
        * unidade de tempo recebida que pode ser ano, mes ou dia, 
        * subtraida ou adicionada a data atual. 
        * Ex: ANO -3 = reduziria 3 anos da data atual. 
        * @param tempo medida de tempo, enumerador que define o tempo da qual 
        * o valor será alterado.
        * @param valor Quantidade de tempo pelo qual a nova data será alterada.
        * Números positivos aumentam a data, negativos diminuem.
        * @return um novo Objeto Date contendo o resultado da nova data alterada.
        */
       public static Date getNovaData(UnidadeTempoEnum tempo, int valor){

            Calendar calendario = Calendar.getInstance();
            calendario.add(tempo.getCampo(), valor);
            Date data = calendario.getTime();
            
            return data;
                    
        }
        
    }
    
}
