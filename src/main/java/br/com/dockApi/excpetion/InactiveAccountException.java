package br.com.dockApi.excpetion;

public class InactiveAccountException extends Exception {

	private static final long serialVersionUID = -3884677988599212095L;

	public InactiveAccountException(String message) {
		super(message);
	}
}
