
package br.edu.ifsul.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Bruno
 */
public class Util {
    
    public static String getMensagemErro (Exception e) {
        while (e.getCause() != null) {
            // último níve de encapsulamento tem exceção nula
            e = (Exception) e.getCause();
        }
        String retorno = e.getMessage();
        if (retorno.contains(("foreign key"))) {
            retorno = "Registro não pode ser excluído por possuir referência em outros objetos!";
        }
        return retorno;
    }
    
    public static void mensagemInformacao (String msg) {
        FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, "");
        FacesContext.getCurrentInstance().addMessage(null, mensagem);
    }
    
    public static void mensagemErro (String msg) {
        FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, "");
        FacesContext.getCurrentInstance().addMessage(null, mensagem);
    }
    
}
