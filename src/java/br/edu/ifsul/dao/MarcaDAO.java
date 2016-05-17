
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Marca;
import java.io.Serializable;

/**
 *
 * @author Bruno
 */
public class MarcaDAO<T> extends DAOGenerico<Marca> implements Serializable {
    
    public MarcaDAO () {
        super();
        super.setClassePersistente(Marca.class);
        super.setOrdem("nome");
    }
    
}
