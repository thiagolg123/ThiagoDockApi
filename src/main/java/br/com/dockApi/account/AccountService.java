package br.com.dockApi.account;

import java.math.BigDecimal;
import java.util.List;

import br.com.dockApi.excpetion.DepositException;
import br.com.dockApi.excpetion.InactiveAccountException;
import br.com.dockApi.excpetion.RegisteredAccount;
import br.com.dockApi.excpetion.UnregisteredAccount;
import br.com.dockApi.excpetion.UnregisteredPerson;
import br.com.dockApi.excpetion.WithdrawException;

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
	 * @throws UnregisteredAccount
	 */
	public Account deposit(Long idAccount, BigDecimal value)
			throws DepositException, InactiveAccountException, UnregisteredAccount;

	/**
	 * Consult balance in account by id
	 * 
	 * @param id
	 * @return AccountDTO
	 * @throws InactiveAccountException
	 */
	public AccountDTO consultBalance(Long idAccount) throws UnregisteredAccount, InactiveAccountException;

	/**
	 * Verify if account is active
	 * 
	 * @param AccountDTO
	 * @return boolean y/n
	 * @throws DepositException
	 */
	public boolean verifyAccountActive(AccountDTO accDto);

	/**
	 * Business rules to block an account
	 * 
	 * @param account id
	 * @return AccountDto
	 */
	public AccountDTO blockAccount(Long idAccount) throws UnregisteredAccount;

	/**
	 * Business rules active an account
	 * 
	 * @param id
	 * @return AccountDTO
	 */
	public AccountDTO activeAccount(Long idAccount) throws UnregisteredAccount;

	/**
	 * Business rules to withdraw
	 * 
	 * @param id
	 * @return AccountDTO
	 * @throws UnregisteredAccount
	 * @throws InactiveAccountException
	 */
	public AccountDTO withdraw(Long idAccount, BigDecimal valueWithdraw)
			throws UnregisteredAccount, InactiveAccountException, WithdrawException;

	/**
	 * 
	 * Business rules to statement
	 * 
	 * @param idAccount
	 * @return List<StatementDTO> as statement
	 */
	public List<StatementDTO> statement(Long idAccount);
}
