
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Grupo;
import java.io.Serializable;

/**
 *
 * @author Bruno
 */
public class GrupoDAO<T> extends DAOGenerico<Grupo> implements Serializable {
    
    public GrupoDAO () {
        super();
        super.setClassePersistente(Grupo.class);
    }
    
}
