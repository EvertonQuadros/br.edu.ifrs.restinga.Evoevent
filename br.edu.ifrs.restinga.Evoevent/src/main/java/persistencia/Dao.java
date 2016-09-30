package persistencia;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import modelo.Entidade;
import modelo.utils.HibernateUtil;
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
    
    private final Session sessao;
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
    
    /**
     * Construtor padrão da classe, inicializa a sessão e a transação para
     * realizar as operações com o banco de dados.
     */
    public Dao() {
        
        sessao = HibernateUtil.getSessionFactory().getCurrentSession();
        sessao.beginTransaction();
        
    }
    
    public void setTipo(Class<T> tipo){
        this.tipo = tipo;
    }
    
    public Class<T> getTipo(){
        return tipo;
    }
    
    /**
     * Método Dao que realiza a gravação (inclusão) de um registro no banco de 
     * dados.
     * @param object Um objeto do tipo T que será gravado no banco.
     */
    public void salvar(T object) {
        sessao.save(object);
    }
    
    /**
     * Método Dao que atualiza (edita) um registro no banco de dados de acordo 
     * com o tipo do objeto passado por parâmetro.
     * @param object Um objeto do tipo T que será atualizado no banco.
     */
    public void atualizar(T object){
        sessao.update(object);
    }
    
    /**
     * Método Dao que carrega um registro do banco de dados de acordo com o id
     * passado por parâmetro.
     * @param id o número do id que se deseja carregar.
     * @return objeto do tipo T representando o registro carregado.
     */
    public T carregar(int id) {
        return (T) sessao.get(getTipo(), id);
    }
    
    /**
     * Método que remover um registro do banco de dados de acordo com o tipo do
     * objeto Entidade recebido. 
     * @param object objeto T que representa o registro a ser removido.
     */
    public void remover(T object) {
        sessao.delete(object);
    }
    
    /**
     * Método que carrega uma lista de registros do banco de dados do tipo da
     * classe que está invocando o método.
     * @return uma lista de objetos Entidade do mesmo tipo da classe que está
     * invocando este método.
     */
    public List<T> listar() {
        return sessao.createCriteria(tipo).list();
    }
    
    /**
     * Método que valida a transação no banco de dados através do commit na sessão
     */
    public void commit() {
        sessao.getTransaction().commit();
    }
    
}
