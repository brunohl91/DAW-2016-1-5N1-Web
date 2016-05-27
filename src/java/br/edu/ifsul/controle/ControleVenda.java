/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.VendaDAO;
import br.edu.ifsul.dao.PessoaFisicaDAO;
import br.edu.ifsul.dao.ProdutoDAO;
import br.edu.ifsul.dao.UsuarioDAO;
import br.edu.ifsul.modelo.Venda;
import br.edu.ifsul.modelo.VendaItens;
import br.edu.ifsul.modelo.PessoaFisica;
import br.edu.ifsul.modelo.Produto;
import br.edu.ifsul.modelo.Usuario;
import br.edu.ifsul.modelo.VendaItens;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import java.util.Calendar;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


/**
 *
 * @author Bruno
 */
@ManagedBean(name = "controleVenda")
@ViewScoped
public class ControleVenda implements Serializable {
    
    private VendaDAO<Venda> dao;
    private Venda objeto;
    private ProdutoDAO<Produto> daoProduto;
    private PessoaFisicaDAO<PessoaFisica> daoPessoaFisica;
    private UsuarioDAO<Usuario> daoUsuario;
    private VendaItens item;
    private Boolean novoItem;

    public ControleVenda() {
        dao = new VendaDAO<>();
        daoUsuario = new UsuarioDAO<>();
        daoPessoaFisica = new PessoaFisicaDAO<>();
        daoProduto = new ProdutoDAO<>();
    }
    
    public void novoItem () {
        item = new VendaItens();
        novoItem = true;
    }
    
    public void alterarItem (int index) {
        item = objeto.getItens().get(index);
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
        for (VendaItens vi : objeto.getItens()) {
            valorTotal += vi.getValorTotal();
        }
        objeto.setValorTotal(valorTotal);
    }
    
    public void buscarPrecoProduto () {
        item.setValorUnitario(item.getProduto().getPreco());
    }
    
    public void checkQuantidade () {
        Double estoqueProduto = item.getProduto().getEstoque();
        if (item.getQuantidade() > estoqueProduto) {
            item.setQuantidade(estoqueProduto);
            Util.mensagemErro("Você selecionou mais do que o estoque! Quantidade limitada em " + estoqueProduto + " produtos.");
        }
    }
    
    public String listar () {
        return "/privado/venda/listar?faces-redirect=true";
    }
    
    public void novo () {
        objeto = new Venda();
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
    
    public VendaDAO getDao() {
        return dao;
    }

    public void setDao(VendaDAO dao) {
        this.dao = dao;
    }

    public Venda getObjeto() {
        return objeto;
    }

    public void setObjeto(Venda objeto) {
        this.objeto = objeto;
    }

    public PessoaFisicaDAO<PessoaFisica> getDaoPessoaFisica() {
        return daoPessoaFisica;
    }

    public void setDaoPessoaFisica(PessoaFisicaDAO<PessoaFisica> daoPessoaFisica) {
        this.daoPessoaFisica = daoPessoaFisica;
    }

    public VendaItens getItem() {
        return item;
    }

    public void setItem(VendaItens item) {
        this.item = item;
    }

    public Boolean getNovoItem() {
        return novoItem;
    }

    public void setNovoItem(Boolean novoItem) {
        this.novoItem = novoItem;
    }

    public UsuarioDAO<Usuario> getDaoUsuario() {
        return daoUsuario;
    }

    public void setDaoUsuario(UsuarioDAO<Usuario> daoUsuario) {
        this.daoUsuario = daoUsuario;
    }

    public ProdutoDAO<Produto> getDaoProduto() {
        return daoProduto;
    }

    public void setDaoProduto(ProdutoDAO<Produto> daoProduto) {
        this.daoProduto = daoProduto;
    }
    
}
