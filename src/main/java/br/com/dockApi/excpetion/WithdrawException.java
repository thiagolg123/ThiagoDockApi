package br.com.dockApi.excpetion;

public class WithdrawException extends Exception {

	private static final long serialVersionUID = 7779570333228190876L;

	public WithdrawException(String message) {
		super(message);
	}
}
