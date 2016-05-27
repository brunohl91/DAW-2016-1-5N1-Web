
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Entrada;
import java.io.Serializable;

/**
 *
 * @author Bruno
 */
public class EntradaDAO<T> extends DAOGenerico<Entrada> implements Serializable {
    
    public EntradaDAO () {
        super();
        super.setClassePersistente(Entrada.class);
        super.setOrdem("id");
    }
    
}
