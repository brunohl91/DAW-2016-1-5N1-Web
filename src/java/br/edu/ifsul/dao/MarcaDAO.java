
package br.edu.ifsul.dao;

import br.edu.ifsul.jpa.EntityManagerUtil;
import br.edu.ifsul.modelo.Marca;
import br.edu.ifsul.util.Util;
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
public class MarcaDAO {
    
    private Marca objetoSelecionado;
    private String mensagem = "";
    private EntityManager em;

    public MarcaDAO() {
        em = EntityManagerUtil.getEntityManager();
    }
    
    public List<Marca> getLista () {
        // jpql => FROM NA CLASSE E NÃO NO BANCO
        return em.createQuery("from Marca order by nome").getResultList();
    }
   
    public boolean salvar (Marca marca) {
        try {
            em.getTransaction().begin();
            if (marca.getId() == null) {
                em.persist(marca);
            }
            else {
                em.merge(marca);
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
    
    public boolean remover (Marca marca) {
        try {
            em.getTransaction().begin();
            em.remove(marca);
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
    
    public boolean validaObjeto(Marca obj){
        Validator validador = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Marca>> erros = validador.validate(obj);
        if (erros.size() > 0){
            mensagem = "";
            mensagem += "Objeto com erros!<br/>";
            for (ConstraintViolation<Marca> erro : erros){
                mensagem += "Erro: "+erro.getMessage()+"<br/>";
            }
            return false;
        } else {
            return true;
        }
    }
    
    public Marca localizar (Integer id) {
        return em.find(Marca.class, id);
    }
    
    public Marca getObjetoSelecionado() {
        return objetoSelecionado;
    }

    public void setObjetoSelecionado(Marca objetoSelecionado) {
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
