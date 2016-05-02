
package br.edu.ifsul.dao;

import br.edu.ifsul.jpa.EntityManagerUtil;
import br.edu.ifsul.modelo.Grupo;
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
public class GrupoDAO {
    
    private Grupo objetoSelecionado;
    private String mensagem = "";
    private EntityManager em;

    public GrupoDAO() {
        em = EntityManagerUtil.getEntityManager();
    }
    
    public List<Grupo> getLista () {
        // jpql => FROM NA CLASSE E NÃO NO BANCO
        return em.createQuery("from Grupo order by nome").getResultList();
    }
   
    public boolean salvar (Grupo grupo) {
        try {
            em.getTransaction().begin();
            if (grupo.getId() == null) {
                em.persist(grupo);
            }
            else {
                em.merge(grupo);
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
    
    public boolean remover (Grupo grupo) {
        try {
            em.getTransaction().begin();
            em.remove(grupo);
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
    
    public boolean validaObjeto(Grupo obj){
        Validator validador = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Grupo>> erros = validador.validate(obj);
        if (erros.size() > 0){
            mensagem = "";
            mensagem += "Objeto com erros!<br/>";
            for (ConstraintViolation<Grupo> erro : erros){
                mensagem += "Erro: "+erro.getMessage()+"<br/>";
            }
            return false;
        } else {
            return true;
        }
    }
    
    public Grupo localizar (Integer id) {
        return em.find(Grupo.class, id);
    }
    
    public Grupo getObjetoSelecionado() {
        return objetoSelecionado;
    }

    public void setObjetoSelecionado(Grupo objetoSelecionado) {
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
