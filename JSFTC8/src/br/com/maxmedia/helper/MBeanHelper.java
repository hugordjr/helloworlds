package br.com.maxmedia.helper;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class MBeanHelper {

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public static void redirecionarPagina(String pagina) throws IOException {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		context.redirect(context.getRequestContextPath() + pagina);
	}
	
	public static ExternalContext externalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	public static void addParametroSessao(String campo, Object valor) throws IOException {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		context.getSessionMap().put(campo, valor);
	}

	public static Object getParametroSessao(String campo) throws IOException {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		return context.getSessionMap().get(campo);
	}

	public static void addParametroRequest(String campo, String valor) throws IOException {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		context.getRequestParameterMap().put(campo, valor);
	}

	public static String getParametroRequest(String campo) throws IOException {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		return context.getRequestParameterMap().get(campo);
	}
}
