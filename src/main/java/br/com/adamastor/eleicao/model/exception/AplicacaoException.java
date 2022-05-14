package br.com.adamastor.eleicao.model.exception;

public class AplicacaoException extends RuntimeException {

	private static final long serialVersionUID = -5783217356980097375L;
	
	public AplicacaoException(String mensagem) {
		super(mensagem);
	}

}
