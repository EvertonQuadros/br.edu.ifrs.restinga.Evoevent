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

package controle;

import java.io.Serializable;
import java.util.List;
import modelo.Entidade;
import modelo.utils.MessagesUtil;
import persistencia.Dao;

/**
 * Classe padrão de operações crud (create, read, update e delete) de todas as
 * nossas subclasses Entidade, ou seja, lida diretamente com todas as operações
 * realizadas pela classe Dao, e serve como uma conexão entre a entidade e o Dao.
 * @author Notrevequadrosc@gmail.com
 * @param <T> O tipo da classe que está instanciando a nossa Crud, todos os 
 * parâmetros T, se tornam o mesmo tipo desta classe, realizando as operações
 * de acordo com o tipo da classe passada.
 */
public final class Crud<T extends Entidade> extends Dao<T> implements Serializable{
    
    private T objeto;
    private List<T> listaObjetos;
    
    /**
     * Construtor padrão da class Crud que recebe o tipo da classe e o objeto
     * Entidade inicial (instância).
     * @param tipo Definição do tipo da classe, T da classe que a está 
     * instanciando.
     * @param entidade Objeto do tipo Entidade que será o estado inicial do 
     * atributo.
     */
    public Crud(Class<T> tipo, T entidade){
        
        super();
        setTipo(tipo);
        setInstance(entidade);
        
    }
    
    /**
     * Método que define a instância atual do objeto que estaremos manipulando
     * na classe Crud.
     * @param objeto Entidade que será manipulada (objeto).
     */
    public void setInstance(T objeto) {
        this.objeto = objeto;
    }
    
    /**
     * Método que retorna a instância (objeto) entidade que representa o objeto 
     * sendo manipulado pela nossa classe Crud e simbolizando a instância 
     * desta operação.
     * @return
     */
    public T getInstance() {
        return objeto;
    }
    
    /**
     * Método que acesso o Dao e constrói uma busca através do método do dao
     * para atribuir o resultado contendo todos os registros do mesmo objeto no
     * banco de dados
     * @param query String contendo a pesquisa a ser realizada.
     */
    public void listarObjetos(String query){
        listaObjetos = listar(query);
    }
    
    @Override
    public List<T> listar(){
        return super.listar();
    }
    
    /**
     * Método que acessa o Dao e constrói uma buscar através do método do dao
     * para atribuir o resultado a sua própria lista de objetos.
     * @param query String (texto) contendo a consulta a ser realizada.
     * @param id identificar do registro a ser consultado.
     */
    public void listar(String query, int id){
        listaObjetos = listarPorId(query,id);
    }
    
    /**
     * Método que retorna uma lista de objetos do mesmo tipo que a Entidade que
     * a está invocando.
     * @return Lista de objetos represetando todos os registros resultantes da 
     * busca.
     */
    public List<T> getLista(){
        return listaObjetos;
    }
    
    /**
     * Método que atribui um novo valor para a lista de objetos armazenada na
     * Classe crud.
     * @param listaObjetos Objeto List a ser atribuído para a lista de objetos.
     */
    public void setLista(List<T> listaObjetos){
        this.listaObjetos = listaObjetos;
    }
    
    /**
     * Método que salva um registro no banco de dados através da classe Dao.
     */
    public void salvar(){
       
        salvar(objeto);
  
        if(listaObjetos != null){
            listaObjetos.add((T)objeto);
        }

        setNewInstance();
        
    }
    
    /**
     * Método crud que invoca o método DAO atualizar para realizar a ação com
     * o objeto setado na instancia do crud.
     */
    public void atualizar(){
        
        super.atualizar(getInstance());
        setNewInstance();
        
    }
    
    /**
     * Método que buscar um registro do banco de dados através do método carregar
     * da classe Dao, validando se o objeto recebido é null (não encontrado).
     */
    public void buscar() {
        
        T entidade = (T) carregar(objeto.getId());
         
        if(entidade == null) {
            
            int cod = objeto.getId();
            MessagesUtil.CrudMessages.getCurrentMessage(
                objeto.getClass()
                , "ID " + cod + " do " + objeto.getClass() + " não encontrado!"
                , MessagesUtil.CrudMessages.OperacaoEnum.CARREGAR
                , MessagesUtil.SeveridadeEnum.AVISO);
            
        } 
        else{
            setInstance(entidade);
        }
        
    }
    

    @Override
    public T consultaUnica(String query, String[] parametros, String[] resultados){
        return super.consultaUnica(query,parametros,resultados);   
    }
    
    @Override
    public List<T> consultaLista(String query
            ,String[] parametros
            ,String[] resultados){
        return super.consultaLista(query, parametros, resultados);
    }
    
    /**
     * Método que buscar um registro do banco de dados através do id informado e
     * invoca o carregar da classe Dao, validando se o objeto recebido é null 
     * (não encontrado).
     * @param id valor do identificado do registro que será carregado do banco.
     */
    public void buscar(int id){
 
        objeto.setId(id);
        buscar();
        
    }
    
    /**
     * Método específico do crud que pega a instância do atributo objeto e 
     * remove do banco de dados.
     */
    public void remover() {
       
        super.remover(objeto);
        MessagesUtil.CrudMessages.getCurrentMessage(
                objeto.getClass()
                , "Operacão realizada com sucesso!"
                , MessagesUtil.CrudMessages.OperacaoEnum.REMOVER
                , MessagesUtil.SeveridadeEnum.INFO);
        listaObjetos.remove(objeto);
            
    }
    
    @Override
    public void remover(T objeto){
        
        setInstance(objeto);
        remover();
        
    }
    
    private void setNewInstance(){
        
        try {
            setInstance((T)objeto.getClass().newInstance());
        } 
        catch (InstantiationException | IllegalAccessException ex) {
            MessagesUtil.CrudMessages.getCurrentMessage(
                objeto.getClass()
                , ex.getMessage()
                , MessagesUtil.CrudMessages.OperacaoEnum.SALVAR
                , MessagesUtil.SeveridadeEnum.FATAL);
        }
        
    }
    
}
