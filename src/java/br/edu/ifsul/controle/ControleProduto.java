/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.ProdutoDAO;
import br.edu.ifsul.dao.GrupoDAO;
import br.edu.ifsul.dao.MarcaDAO;
import br.edu.ifsul.modelo.Grupo;
import br.edu.ifsul.modelo.Produto;
import br.edu.ifsul.modelo.Marca;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


/**
 *
 * @author Bruno
 */
@ManagedBean(name = "controleProduto")
@ViewScoped
public class ControleProduto implements Serializable {
    
    private ProdutoDAO<Produto> dao;
    private Produto objeto;
    private GrupoDAO<Grupo> daoGrupo;
    private MarcaDAO<Marca> daoMarca;

    public ControleProduto() {
        dao = new ProdutoDAO<>();
        daoGrupo = new GrupoDAO<>();
        daoMarca = new MarcaDAO<>();
    }
    
    public String listar () {
        return "/privado/produto/listar?faces-redirect=true";
    }
    
    public void novo () {
        objeto = new Produto();
    }
    
    public void salvar () {
        boolean persistiu;
        if (objeto.getId() == null) {
            persistiu = dao.persist(objeto);
        }
        else {
            persistiu = dao.merge(objeto);
        }
        
        if (persistiu) {
            Util.mensagemInformacao(dao.getMensagem());
        }
        else {
            Util.mensagemErro(dao.getMensagem());
        }
    }
    
    public void editar (Integer id) {
        try {
            objeto = dao.localizar(id);
        } catch (Exception e) {
            Util.mensagemErro("Erro ao recuperar objeto: " + Util.getMensagemErro(e));
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
    
    public ProdutoDAO getDao() {
        return dao;
    }

    public void setDao(ProdutoDAO dao) {
        this.dao = dao;
    }

    public Produto getObjeto() {
        return objeto;
    }

    public void setObjeto(Produto objeto) {
        this.objeto = objeto;
    }

    public GrupoDAO<Grupo> getDaoGrupo() {
        return daoGrupo;
    }

    public void setDaoGrupo(GrupoDAO<Grupo> daoGrupo) {
        this.daoGrupo = daoGrupo;
    }

    public MarcaDAO<Marca> getDaoMarca() {
        return daoMarca;
    }

    public void setDaoMarca(MarcaDAO<Marca> daoMarca) {
        this.daoMarca = daoMarca;
    }
    
    
    
}
