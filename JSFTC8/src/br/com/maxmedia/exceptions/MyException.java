package br.com.maxmedia.exceptions;

public class MyException extends Exception {

	private static final long serialVersionUID = -7337840813260827718L;

	private String motivo;
	
	public MyException(String mensagem) {
		this.setMotivo(mensagem);
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}


}
