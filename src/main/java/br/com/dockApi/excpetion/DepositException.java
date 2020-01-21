package br.com.dockApi.excpetion;

public class DepositException extends Exception {

	private static final long serialVersionUID = -901988664744857053L;

	public DepositException(String message) {
		super(message);
	}
}
