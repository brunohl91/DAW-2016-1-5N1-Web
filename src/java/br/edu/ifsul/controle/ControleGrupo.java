/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.GrupoDAO;
import br.edu.ifsul.modelo.Grupo;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


/**
 *
 * @author Bruno
 */
@ManagedBean(name = "controleGrupo")
@SessionScoped
public class ControleGrupo implements Serializable {
    
    private GrupoDAO<Grupo> dao;
    private Grupo objeto;

    public ControleGrupo() {
        dao = new GrupoDAO<>();
    }
    
    public String listar () {
        return "/privado/grupo/listar?faces-redirect=true";
    }
    
    public String novo () {
        objeto = new Grupo();
        return "formulario";
    }
    
    public String salvar () {
        boolean persistiu;
        if (objeto.getId() == null) {
            persistiu = dao.persist(objeto);
        }
        else {
            persistiu = dao.merge(objeto);
        }
        
        if (persistiu) {
            Util.mensagemInformacao(dao.getMensagem());
            return "listar";
        }
        else {
            Util.mensagemErro(dao.getMensagem());
            return "formulario";
        }
    }
    
    public String cancelar () {
        return "listar";
    }
    
    public String editar (Integer id) {
        try {
            objeto = dao.localizar(id);
            return "formulario";
        } catch (Exception e) {
            Util.mensagemErro("Erro ao recuperar objeto: " + Util.getMensagemErro(e));
            return "listar";
        }
    }
    
    public void remover (Integer id) {
        try {
            objeto = dao.localizar(id);
            if (dao.remover(objeto)) {
                Util.mensagemInformacao(dao.getMensagem());
            }
            else {
                Util.mensagemErro(dao.getMensagem());
            }
        } catch (Exception e) {
            Util.mensagemErro("Erro ao recuperar objeto: " + Util.getMensagemErro(e));
        }
    }
    
    public GrupoDAO getDao() {
        return dao;
    }

    public void setDao(GrupoDAO dao) {
        this.dao = dao;
    }

    public Grupo getObjeto() {
        return objeto;
    }

    public void setObjeto(Grupo objeto) {
        this.objeto = objeto;
    }
    
    
    
}
