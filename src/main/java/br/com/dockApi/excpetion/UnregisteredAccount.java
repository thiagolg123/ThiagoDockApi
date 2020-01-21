package br.com.dockApi.excpetion;

public class UnregisteredAccount extends Exception {

	private static final long serialVersionUID = -7793419296094144678L;

	public UnregisteredAccount(String message) {
		super(message);
	}
}
