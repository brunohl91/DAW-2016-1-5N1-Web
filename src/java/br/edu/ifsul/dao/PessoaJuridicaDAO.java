
package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.PessoaJuridica;
import java.io.Serializable;

/**
 *
 * @author Bruno
 */
public class PessoaJuridicaDAO<T> extends DAOGenerico<PessoaJuridica> implements Serializable {
    
    public PessoaJuridicaDAO () {
        super();
        super.setClassePersistente(PessoaJuridica.class);
        super.setOrdem("nome");
    }
    
}
