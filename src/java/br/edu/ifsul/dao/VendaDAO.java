
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Venda;
import java.io.Serializable;

/**
 *
 * @author Bruno
 */
public class VendaDAO<T> extends DAOGenerico<Venda> implements Serializable {
    
    public VendaDAO () {
        super();
        super.setClassePersistente(Venda.class);
        super.setOrdem("id");
    }
    
}
