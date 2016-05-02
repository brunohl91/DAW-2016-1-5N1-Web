
package br.edu.ifsul.dao;

import br.edu.ifsul.jpa.EntityManagerUtil;
import br.edu.ifsul.modelo.Estado;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 *
 * @author Bruno
 */
public class EstadoDAO implements Serializable {
    
    private Estado objetoSelecionado;
    private String mensagem = "";
    private EntityManager em;

    public EstadoDAO() {
        em = EntityManagerUtil.getEntityManager();
    }
    
    public List<Estado> getLista () {
        // jpql => FROM NA CLASSE E NÃO NO BANCO
        return em.createQuery("from Estado order by nome").getResultList();
    }
    
    public boolean salvar (Estado est) {
        try {
            em.getTransaction().begin();
            if (est.getId() == null) {
                em.persist(est);
            }
            else {
                em.merge(est);
            }
            em.getTransaction().commit();
            mensagem = "Objeto persistido com sucesso!";
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive() == false) {
                em.getTransaction().begin();
            }
            em.getTransaction().rollback();
            mensagem = "Erro ao persistir: " + Util.getMensagemErro(e);
            return false;
        }
    }
    
    public boolean remover (Estado est) {
        try {
            em.getTransaction().begin();
            em.remove(est);
            em.getTransaction().commit();
            mensagem = "Objeto removido com sucesso!";
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive() == false) {
                em.getTransaction().begin();
            }
            em.getTransaction().rollback();
            mensagem = "Erro ao remover: " + Util.getMensagemErro(e);
            return false;
        }
    }
    
    public Estado localizar (Integer id) {
        return em.find(Estado.class, id);
    }
    
    public boolean validaObjeto (Estado e) {
        Validator validador = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Estado>> erros = validador.validate(e);
        if (erros.size() > 0) {
            mensagem = "Objeto com erros!<br/>";
            for (ConstraintViolation<Estado> erro : erros) {
                mensagem += "Erro: " + erro.getMessage() + "<br/>";
            }
            return false;
        }
        else {
            return true;
        }
    }
    
    public Estado getObjetoSelecionado() {
        return objetoSelecionado;
    }

    public void setObjetoSelecionado(Estado objetoSelecionado) {
        this.objetoSelecionado = objetoSelecionado;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
    
}
