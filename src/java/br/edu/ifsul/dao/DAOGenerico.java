/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.jpa.EntityManagerUtil;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Bruno
 */
public class DAOGenerico<T> implements Serializable {
    
    private List<T> listaObjetos;
    private List<T> listaTodos;
    private EntityManager em;
    private Class classePersistente;
    private String mensagem = "";
    private String ordem = "id";
    private String filter = "";
    private Integer maximoObjetos = 3;
    private Integer posicaoAtual = 0;
    private Integer totalObjetos = 0;

    public DAOGenerico() {
        em = EntityManagerUtil.getEntityManager();
    }

    public boolean persist (T obj) {
        try {
            em.getTransaction().begin();
            em.persist(obj);
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
    
    public boolean remover (T obj) {
        try {
            em.getTransaction().begin();
            em.remove(obj);
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
    
    public boolean merge (T obj) {
        try {
            em.getTransaction().begin();
            em.merge(obj);
            em.getTransaction().commit();
            mensagem = "Objeto atualizado com sucesso!";
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive() == false) {
                em.getTransaction().begin();
            }
            em.getTransaction().rollback();
            mensagem = "Erro ao atualizado: " + Util.getMensagemErro(e);
            return false;
        }
    }
    
    public T localizar (Integer id) {
        return (T) em.find(classePersistente, id);
    }
    
    public List<T> getListaObjetos() {
        String jpql = "from " + classePersistente.getSimpleName();
        String where = "";
        filter = filter.replaceAll("[';-]", "");
        ordem = ordem.replaceAll("[';-]", "");
        if (filter.length() > 0) {
            if (ordem.equals("id")) {
                try {
                    Integer.parseInt(filter);
                    where += " where " + ordem + " = '" + filter + "'";
                } catch (Exception e) {
                }
            } else {
                where += " where upper(" + ordem + ") like '" + filter.toUpperCase() + "%'";
            }
        }
        jpql += where;
        jpql += " order by " + ordem;
        totalObjetos = em.createQuery("select id from " + classePersistente.getSimpleName() + where + " order by " + ordem).getResultList().size();
        return em.createQuery(jpql).setFirstResult(posicaoAtual).setMaxResults(maximoObjetos).getResultList();
    }
    
    public List<T> getListaTodos() {
        String jpql = "from " + classePersistente.getSimpleName() + " order by " + ordem;
        return em.createQuery(jpql).getResultList();
    }
    
    public void primeiro () {
        posicaoAtual = 0;
    }
    
    public void anterior () {
        posicaoAtual -= maximoObjetos;
        if (posicaoAtual < 0) {
            posicaoAtual = 0;
        }
    }
    
    public void proximo () {
        if (posicaoAtual + maximoObjetos < totalObjetos) {
            posicaoAtual += maximoObjetos;
        }
    }
    
    public void ultimo () {
        int resto = totalObjetos % maximoObjetos;
        if (resto > 0) {
            posicaoAtual = totalObjetos - resto;
        }
        else {
            posicaoAtual = totalObjetos - maximoObjetos;
        }
    }

    public void setListaObjetos(List<T> listaObjetos) {
        this.listaObjetos = listaObjetos;
    }
    
    public String getMensagemNavegacao () {
        int ate = posicaoAtual + maximoObjetos;
        if (ate > totalObjetos) {
            ate = totalObjetos;
        }
        return "Listagem de " + (posicaoAtual + 1) + " até " + ate + " de " + totalObjetos + " registros";
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public Class getClassePersistente() {
        return classePersistente;
    }

    public void setClassePersistente(Class classePersistente) {
        this.classePersistente = classePersistente;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Integer getMaximoObjetos() {
        return maximoObjetos;
    }

    public void setMaximoObjetos(Integer maximoObjetos) {
        this.maximoObjetos = maximoObjetos;
    }

    public Integer getPosicaoAtual() {
        return posicaoAtual;
    }

    public void setPosicaoAtual(Integer posicaoAtual) {
        this.posicaoAtual = posicaoAtual;
    }

    public Integer getTotalObjetos() {
        return totalObjetos;
    }

    public void setTotalObjetos(Integer totalObjetos) {
        this.totalObjetos = totalObjetos;
    }

    public void setListaTodos(List<T> listaTodos) {
        this.listaTodos = listaTodos;
    }
    
}
