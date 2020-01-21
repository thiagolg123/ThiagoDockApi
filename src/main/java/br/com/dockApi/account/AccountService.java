package br.com.dockApi.account;

import java.math.BigDecimal;

import br.com.dockApi.excpetion.DepositException;
import br.com.dockApi.excpetion.RegisteredAccount;
import br.com.dockApi.excpetion.UnregisteredPerson;

public interface AccountService {

	/**
	 * Business rules for creating an account
	 * 
	 * @param accForm
	 * @return AccountDTO
	 * @throws UnregisteredPerson
	 */
	public AccountDTO createAccount(AccountForm accForm) throws UnregisteredPerson;

	/**
	 * Verify if account exists
	 * 
	 * @param accDto
	 * @return boolean
	 */
	public boolean existsAccount(AccountDTO accDto) throws RegisteredAccount;

	/**
	 * Business rules for deposit
	 * 
	 * @param id account
	 * @return an account refreshed
	 */
	public Account deposit(Long idAccount, BigDecimal balance) throws DepositException;
}
