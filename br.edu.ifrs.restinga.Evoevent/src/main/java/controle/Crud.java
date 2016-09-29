package controle;

import java.util.List;
import modelo.Entidade;
import modelo.Utils.MessagesUtil;
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
public final class Crud<T extends Entidade> extends Dao<T>{
    
    private T objeto;
    private final List<T> listaObjetos;
    
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
        listaObjetos = listar();
        
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
     * Método que retorna uma lista de objetos do mesmo tipo que a Entidade que
     * a está invocando.
     * @return Lista de objetos represetando todos os registros resultantes da 
     * busca.
     */
    public List<T> getLista(){
        return listaObjetos;
    }
    
    /**
     * Método que salva um registro no banco de dados através da classe Dao.
     */
    public void salvar(){
        
        salvar(objeto);
        MessagesUtil.CrudMessages.getCurrentMessage(objeto.getClass()
                , "Operacão realizada com sucesso!"
                , MessagesUtil.CrudMessages.OperacaoEnum.SALVAR
                , MessagesUtil.CrudMessages.SeveridadeEnum.INFO);
        listaObjetos.add((T)objeto);
        
        try {
            setInstance((T)objeto.getClass().newInstance());
        } 
        catch (InstantiationException | IllegalAccessException ex) {
            MessagesUtil.CrudMessages.getCurrentMessage(objeto.getClass()
                , ex.getMessage()
                , MessagesUtil.CrudMessages.OperacaoEnum.SALVAR
                , MessagesUtil.CrudMessages.SeveridadeEnum.FATAL);
        }
        
    }
    
    /**
     * Método que buscar um registro do banco de dados através do método carregar
     * da classe Dao, validando se o objeto recebido é null (não encontrado).
     */
    public void buscar() {
        
        T entidade = (T) super.carregar(objeto.getId());
         
        if(entidade == null) {
            
            int cod = objeto.getId();
            MessagesUtil.CrudMessages.getCurrentMessage(objeto.getClass()
                , "ID " + cod + " do " + objeto.getClass() + " não encontrado!"
                , MessagesUtil.CrudMessages.OperacaoEnum.CARREGAR
                , MessagesUtil.CrudMessages.SeveridadeEnum.AVISO);
            
        } 
        else{
            setInstance(entidade);
        }
        
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
        MessagesUtil.CrudMessages.getCurrentMessage(objeto.getClass()
                , "Operacão realizada com sucesso!"
                , MessagesUtil.CrudMessages.OperacaoEnum.REMOVER
                , MessagesUtil.CrudMessages.SeveridadeEnum.INFO);
        listaObjetos.remove(objeto);
            
    }
    
    @Override
    public void remover(T objeto){
        
        setInstance(objeto);
        remover();
        
    }
    
    /**
     * Método que realiza o commit da transação validando-a.
     */
    public void encerrar() {
        commit();
    }
    
}
