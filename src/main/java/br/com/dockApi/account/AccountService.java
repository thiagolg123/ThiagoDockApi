package br.com.dockApi.account;

import java.math.BigDecimal;

import br.com.dockApi.excpetion.DepositException;
import br.com.dockApi.excpetion.InactiveAccountException;
import br.com.dockApi.excpetion.RegisteredAccount;
import br.com.dockApi.excpetion.UnregisteredAccount;
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
	 * @throws InactiveAccountException
	 */
	public Account deposit(Long idAccount, BigDecimal value) throws DepositException, InactiveAccountException;

	/**
	 * Consult balance in account by id
	 * 
	 * @param id
	 * @return AccountDTO
	 * @throws InactiveAccountException
	 */
	public AccountDTO consultBalance(Long id) throws UnregisteredAccount, InactiveAccountException;

	/**
	 * Verify if account is active
	 * 
	 * @param AccountDTO
	 * @return boolean y/n
	 * @throws DepositException
	 */
	public boolean verifyAccountActive(AccountDTO accDto);
}
