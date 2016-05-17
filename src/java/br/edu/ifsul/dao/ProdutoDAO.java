
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Produto;
import java.io.Serializable;

/**
 *
 * @author Bruno
 */
public class ProdutoDAO<T> extends DAOGenerico<Produto> implements Serializable {
    
    public ProdutoDAO () {
        super();
        super.setClassePersistente(Produto.class);
        super.setOrdem("nome");
    }
    
}
