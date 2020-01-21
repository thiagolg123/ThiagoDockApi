package br.com.dockApi.excpetion;

public class UnregisteredPerson extends Exception {

	private static final long serialVersionUID = -2653867375975735471L;

	public UnregisteredPerson(String message) {
		super(message);
	}
}
