package br.com.maxmedia.helper;

import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class MessageHelper {

	public void sendMessageInfo(String msg) {
        this.sendMessage(msg, FacesMessage.SEVERITY_INFO);
    }

	public void sendMessageError(String msg) {
        this.sendMessage(msg, FacesMessage.SEVERITY_ERROR);
    }

	public void sendMessageWarn(String msg) {
        this.sendMessage(msg, FacesMessage.SEVERITY_WARN);
    }

    protected void sendMessage(String msg, Severity severity) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(severity, msg, null);
        facesContext.addMessage(null, message);
    }

	public void sendMessageError(List<String> erros) {
		for (Iterator iterator = erros.iterator(); iterator.hasNext();) {
			String erro = (String) iterator.next();
			this.sendMessageError(erro);
		}
	}
}
