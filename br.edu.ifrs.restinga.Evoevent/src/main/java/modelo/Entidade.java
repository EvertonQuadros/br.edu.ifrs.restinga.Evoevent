package modelo;

/**
 * Classe abstrata que define todas as classes entidades do projeto, e os 
 * métodos padrões, de get/set e o atribute id. Todas as classes entidades devem
 * extender Entidade.
 * @author notrevequadrosc@gmail.com
 */
public abstract class Entidade {
    
    private int id;
    
    public abstract int getId();
    
    public abstract void setId(int id);
    
}