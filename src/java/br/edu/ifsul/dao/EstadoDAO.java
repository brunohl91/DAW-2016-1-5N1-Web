
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Estado;
import java.io.Serializable;

/**
 *
 * @author Bruno
 */
public class EstadoDAO<T> extends DAOGenerico<Estado> implements Serializable {
    
    public EstadoDAO () {
        super();
        super.setClassePersistente(Estado.class);
        super.setOrdem("nome");
    }
    
}
