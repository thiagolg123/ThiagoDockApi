package br.com.dockApi.excpetion;

public class RegisteredAccount extends Exception {

	private static final long serialVersionUID = 2759105909430789107L;

	public RegisteredAccount(String message) {
		super(message);
	}
}
