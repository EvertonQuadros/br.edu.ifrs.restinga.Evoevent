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

package persistencia;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import modelo.Entidade;
import modelo.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * É a classe principal que realiza todas as operações Dao em nosso projeto 
 * Utiliza o parâmetro T (type) genérico para receber o tipo de classe e se
 * adaptar a esse tipo para realizar as operações corretamente, por isso 
 * utilizamos apenas um Dao para todo o projeto, Não realizar alterações nesta
 * classe sem analisar os impactos no projeto.
 * @author notrevequadrosc@gmail.com
 * @param <T> tipo da classe que obrigatóriamente deve ser subclasse de Entidade.
 */
public abstract class Dao<T extends Entidade> {
    
    private Session sessao;
    private Class<T> tipo;
    
    /**
     * Método que retorna o tipo de classe na qual a nossa classe 
     * foi instanciada.
     * @return Tipo da Classe.
     */
    private Class<T> getT(){
        
        ParameterizedType parameterizedType = (ParameterizedType) getClass()
                .getGenericSuperclass();

        return (Class<T>) parameterizedType.getActualTypeArguments()[0]; 
        
    }
      
    protected void setTipo(Class<T> tipo){
        this.tipo = tipo;
    }
    
    protected Class<T> getTipo(){
        return tipo;
    }
    
    /**
     * Método Dao que realiza a gravação (inclusão) de um registro no banco de 
     * dados.
     * @param object Um objeto do tipo T que será gravado no banco.
     */
    protected void salvar(T object) {
        
        sessao = HibernateUtil.getSessionFactory().getCurrentSession();
        sessao.save(object);
        
    }
    
    /**
     * Método Dao que atualiza (edita) um registro no banco de dados de acordo 
     * com o tipo do objeto passado por parâmetro.
     * @param object Um objeto do tipo T que será atualizado no banco.
     */
    protected void atualizar(T object){
        
        sessao = HibernateUtil.getSessionFactory().getCurrentSession();
        sessao.merge(object);
        
    }
    
    /**
     * Método Dao que carrega um registro do banco de dados de acordo com o id
     * passado por parâmetro.
     * @param id o número do id que se deseja carregar.
     * @return objeto do tipo T representando o registro carregado.
     */
    protected T carregar(int id) {
        
        sessao = HibernateUtil.getSessionFactory().getCurrentSession();
        return (T) sessao.get(getTipo(), id);
        
    }
    
    /**
     * Método que remover um registro do banco de dados de acordo com o tipo do
     * objeto Entidade recebido. 
     * @param object objeto T que representa o registro a ser removido.
     */
    protected void remover(T object) {
        
        sessao = HibernateUtil.getSessionFactory().getCurrentSession();
        sessao.delete(object);
        
    }
    
    /**
     * Método que constrói uma consulta personalizada de uma tabela no banco
     * de dados.
     * @param query String (texto) contendo a consulta que será construida no
     * método.
     * @return uma lista de objetos Entidade do mesmo tipo da classe que está
     * invocando este método.
     */
    protected List<T> listar(String query){
        
        sessao = HibernateUtil.getSessionFactory().getCurrentSession();
        Query cons = sessao.createQuery(query);
        return cons.list();
        
    }
    
    /**
     * Método que carrega uma lista de registros do banco de dados do tipo da
     * classe que está invocando o método.
     * @return uma lista de objetos Entidade do mesmo tipo da classe que está
     * invocando este método.
     */
    protected List<T> listar() {
        
        sessao = HibernateUtil.getSessionFactory().getCurrentSession();
        return sessao.createCriteria(tipo).list();
        
    }
    
    /**
     * Método que cria uma consultar do tipo listagem (múltiplos resultados) 
     * através de uma String contendo a query por parâmetro e o id a ser 
     * pesquisado.
     * @param query String (texto) contendo a consulta que será construida no
     * método.
     * @param id identificador do registro a ser pesquisado.
     * @return uma lista contendo todos os resultados obtidos da consulta.
     */
    protected List<T> listarPorId(String query, int id){
        
        sessao = HibernateUtil.getSessionFactory().getCurrentSession();
        Query cons = sessao.createQuery(query.concat(" = :param"));
        cons.setInteger("param", id);
        return cons.list();
        
    }
    
    /**
     * Método que realiza uma consulta simples de parâmetros duplos e retorna um
     * resultado único do mesmo tipo da classe que o invocou.
     * @param query a consulta a ser realizada.
     * @param param Os parâmetros de filtro da consulta
     * @param resultados Os valores a serem consultados em cada filtro
     * @return objeto de retorno da consulta ou null se nada for encontrado.
     */
    protected T consultaUnica(String query, String[] param, String[] resultados){
        
        sessao = HibernateUtil.getSessionFactory().getCurrentSession();
        Query consulta = sessao.createQuery(query);
        
        for(int i = 0; i < param.length && i < resultados.length;i++){
            consulta.setString(param[i], resultados[i]);
        }
   
        return (T) consulta.uniqueResult();
        
    }
    
    /**
     * Método que realiza uma consulta de parâmetros duplos e retorna uma lista
     * do objetos do mesmo tipo da classe que o invocou.
     * @param query a consulta a ser realizada.
     * @param param Os parâmetros de filtro da consulta
     * @param resultados Os valores a serem consultados em cada filtro
     * @return uma lista de objetos da consulta ou null se nada for encontrado.
     */
    protected List<T> consultaLista(String query, String[] param, String[] resultados){
        
        sessao = HibernateUtil.getSessionFactory().getCurrentSession();
        Query consulta = sessao.createQuery(query);
        
        for(int i = 0; i < param.length && i < resultados.length;i++){
            consulta.setString(param[i], resultados[i]);
        }
        
        return (List<T>) consulta.list();
        
    }
    
    
//    /**
//     * Método que realiza uma consulta de parâmetros indeterminadores e retorna
//     * um objeto do resultado da consulta do mesmo tipo da classe que o invocou.
//     * @param parametros - Um map contendo todos os parâmetros e valores a serem
//     * adicionados a busca.
//     * @param consulta - Um objeto String contendo a parte inicial da busca.
//     * @return Retorna o objeto da busca, ou null se nada for encontrado.
//     */
//    protected T consultaUnica(Map<String,String> parametros, String consulta){
//        
//        StringBuilder query = new StringBuilder(consulta);
//        String values = "";
//        if(parametros != null){
//            
//            int numParametros = parametros.size()-1;
//            
//            for (Map.Entry<String, String> entrada : parametros.entrySet()) {
//                
//                String param = entrada.getKey();
//                if(param != null && !param.isEmpty()){
//                    
//                    String valor = entrada.getValue();
//                  
//                    if(valor != null && !valor.isEmpty()){
//                        
//                        values = values.concat(valor);
//                        query.append(param)
//                             .append(" = :param")
//                             .append(numParametros);   
//                        
//                        if(numParametros - 1 > 0){
//                            
//                            query.append(" and ");
//                            values = values.concat(",");
//                            numParametros--;
//                            
//                        }
//                        
//                    }
//                    
//                }
//   
//            }
//            
//            sessao = HibernateUtil.getSessionFactory().getCurrentSession();
//            Query cons = sessao.createQuery(query.toString());
//            numParametros = parametros.size()-1;
//            String[] valores = values.split(",");
//            
//            int y = 0;
//            
//            for(int i = numParametros; i <= 0; i--){
//                
//                cons.setString("param"+numParametros, valores[y]);
//                y++;
//                
//            }
//            
//            return (T) cons.uniqueResult();
//            
//        }
//        else{
//            return null;
//        }
//        
//    }
    
}
