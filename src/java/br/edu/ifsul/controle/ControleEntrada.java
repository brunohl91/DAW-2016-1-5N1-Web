/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.EntradaDAO;
import br.edu.ifsul.dao.EstadoDAO;
import br.edu.ifsul.dao.MarcaDAO;
import br.edu.ifsul.dao.PessoaJuridicaDAO;
import br.edu.ifsul.dao.ProdutoDAO;
import br.edu.ifsul.modelo.Entrada;
import br.edu.ifsul.modelo.EntradaItens;
import br.edu.ifsul.modelo.Estado;
import br.edu.ifsul.modelo.Marca;
import br.edu.ifsul.modelo.PessoaJuridica;
import br.edu.ifsul.modelo.Produto;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import java.util.Calendar;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


/**
 *
 * @author Bruno
 */
@ManagedBean(name = "controleEntrada")
@ViewScoped
public class ControleEntrada implements Serializable {
    
    private EntradaDAO<Entrada> dao;
    private Entrada objeto;
    private ProdutoDAO<Produto> daoProduto;
    private PessoaJuridicaDAO<PessoaJuridica> daoPessoaJuridica;
    private EntradaItens item;
    private Boolean novoItem;

    public ControleEntrada() {
        dao = new EntradaDAO<>();
        daoProduto = new ProdutoDAO<>();
        daoPessoaJuridica = new PessoaJuridicaDAO<>();
    }
    
    public void novoItem () {
        item = new EntradaItens();
        novoItem = true;
    }
    
    public void alterarItem (int index) {
        item = objeto.getEntradaItens().get(index);
        novoItem = false;
    }
    
    public void salvarItem () {
        if (novoItem) {
            objeto.adicionarItem(item);
        }
        else {
            atualizaValorTotalVenda();
        }
        Util.mensagemInformacao("Operação realizada com sucesso!");
    }
    
    public void removerItem (int index) {
        objeto.removerItem(index);
        Util.mensagemInformacao("Operação realizada com sucesso!");
    }
    
    public void calculaValorTotalItem () {
        if (item.getQuantidade() != null && item.getValorUnitario() != null) {
            item.setValorTotal(item.getQuantidade() * item.getValorUnitario());
        }
    }
    
    public void atualizaValorTotalVenda () {
        Double valorTotal = 0.0;
        for (EntradaItens ei : objeto.getEntradaItens()) {
            valorTotal += ei.getValorTotal();
        }
        objeto.setValorTotal(valorTotal);
    }
    
    public String listar () {
        return "/privado/entrada/listar?faces-redirect=true";
    }
    
    public void novo () {
        objeto = new Entrada();
        objeto.setData(Calendar.getInstance());
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
    
    public EntradaDAO getDao() {
        return dao;
    }

    public void setDao(EntradaDAO dao) {
        this.dao = dao;
    }

    public Entrada getObjeto() {
        return objeto;
    }

    public void setObjeto(Entrada objeto) {
        this.objeto = objeto;
    }

    public ProdutoDAO<Produto> getDaoProduto() {
        return daoProduto;
    }

    public void setDaoProduto(ProdutoDAO<Produto> daoProduto) {
        this.daoProduto = daoProduto;
    }

    public PessoaJuridicaDAO<PessoaJuridica> getDaoPessoaJuridica() {
        return daoPessoaJuridica;
    }

    public void setDaoPessoaJuridica(PessoaJuridicaDAO<PessoaJuridica> daoPessoaJuridica) {
        this.daoPessoaJuridica = daoPessoaJuridica;
    }

    public EntradaItens getItem() {
        return item;
    }

    public void setItem(EntradaItens item) {
        this.item = item;
    }

    public Boolean getNovoItem() {
        return novoItem;
    }

    public void setNovoItem(Boolean novoItem) {
        this.novoItem = novoItem;
    }
    
}
