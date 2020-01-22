package br.com.dockApi.account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dockApi.excpetion.DepositException;
import br.com.dockApi.excpetion.InactiveAccountException;
import br.com.dockApi.excpetion.UnregisteredAccount;
import br.com.dockApi.excpetion.UnregisteredPerson;
import br.com.dockApi.excpetion.WithdrawException;
import br.com.dockApi.person.Person;
import br.com.dockApi.person.PersonRepository;
import br.com.dockApi.transaction.Transaction;
import br.com.dockApi.transaction.TransactionRepository;

@Service
public class AccountServiceImpl implements AccountService {

	private static final int GREATER_THAN = 1;
	private static final int LESS_THAN = -1;

	@Autowired
	private AccountRepository repoAccount;

	@Autowired
	private TransactionRepository repoTransaction;

	@Autowired
	private PersonRepository repoPerson;

	@Override
	public AccountDTO createAccount(AccountForm accForm) throws UnregisteredPerson {
		String personCpf = accForm.getPersonCpf();
		Person person = repoPerson.findByCpf(personCpf);

		if (person == null) {
			throw new UnregisteredPerson("No person registered with the CPF :" + personCpf);
		}

		Account account = new Account(person, accForm.getAccountType(), accForm.getBalance(),
				accForm.getDailyWithdrawalLimit(), accForm.isActiveFlag());

		repoAccount.save(account);
		return new AccountDTO(account);
	}

	@Override
	public boolean existsAccount(AccountDTO accDto) {
		return repoAccount.existsByPersonCpf(accDto.getPerson().getCpf());
	}

	@Override
	public Account deposit(Long idAccount, BigDecimal valueDeposit)
			throws InactiveAccountException, DepositException, UnregisteredAccount {
		Account accountRefresh = findAccountIfExists(idAccount);
		AccountDTO accountDTO = new AccountDTO(accountRefresh);

		if (valueDeposit == null || valueDeposit.compareTo(BigDecimal.ONE) == LESS_THAN) {
			throw new DepositException("Deposit value less than limit");
		}

		if (verifyAccountActive(accountDTO)) {
			BigDecimal newBalance = accountRefresh.getBalance().add(valueDeposit);
			accountRefresh.setBalance(newBalance);
			repoAccount.flush();
			return accountRefresh;
		}
		throw new InactiveAccountException("Inactive account");

	}

	@Override
	public AccountDTO consultBalance(Long id) throws UnregisteredAccount, InactiveAccountException {
		Account account = findAccountIfExists(id);
		AccountDTO accountDto = new AccountDTO(account);

		if (verifyAccountActive(accountDto)) {
			return accountDto;
		}
		throw new InactiveAccountException("Inactive account");
	}

	@Override
	public boolean verifyAccountActive(AccountDTO accDto) {
		return accDto != null && accDto.isActiveFlag();
	}

	@Override
	public AccountDTO blockAccount(Long accountId) throws UnregisteredAccount {
		return blockOrActive(accountId, false);
	}

	@Override
	public AccountDTO activeAccount(Long accountId) throws UnregisteredAccount {
		return blockOrActive(accountId, true);
	}

	@Override
	public AccountDTO withdraw(Long idAccount, BigDecimal valueWithdraw)
			throws UnregisteredAccount, InactiveAccountException, WithdrawException {
		Account account = findAccountIfExists(idAccount);
		AccountDTO accountDTO = new AccountDTO(account);

		checkWithdrawalRequirements(accountDTO, valueWithdraw);

		if (verifyAccountActive(accountDTO)) {
			BigDecimal newBalace = account.getBalance().subtract(valueWithdraw);
			account.setBalance(newBalace);
			repoAccount.flush();
		}

		accountDTO.setBalance(account.getBalance());
		return accountDTO;
	}

	@Override
	public List<StatementDTO> statement(Long idAccount) {
		// Como é um teste, sem escala, deixo esse filtro aqui no codigo, demonstrar um
		// pouco mais :)
		List<Transaction> transactions = repoTransaction.findAll();
		List<StatementDTO> listStatementDTO = new ArrayList<StatementDTO>();

		Stream<Transaction> filteredTransactionList = transactions.stream()
				.filter(transaction -> transaction.getAccountId() == idAccount);

		filteredTransactionList.forEach(transaction -> {
			listStatementDTO.add(new StatementDTO(transaction));
		});

		return listStatementDTO;
	}

	/**
	 * Concentrate and verify withdraw rules.
	 * 
	 * @param accountDTO
	 * @param valueWithdraw
	 * @throws WithdrawException
	 */
	private void checkWithdrawalRequirements(AccountDTO accountDTO, BigDecimal valueWithdraw) throws WithdrawException {
		verifyDailyWithdrawalLimit(accountDTO, valueWithdraw);
		verifySufficientBalance(accountDTO, valueWithdraw);
	}

	/**
	 * Roles to verify sufficient balance
	 * 
	 * @param accountDTO
	 * @param valueWithdraw
	 * @throws WithdrawException
	 */
	private void verifySufficientBalance(AccountDTO accountDTO, BigDecimal valueWithdraw) throws WithdrawException {
		if (valueWithdraw.compareTo(accountDTO.getBalance()) == GREATER_THAN) {
			throw new WithdrawException("Withdrawal amount higher than available");
		}
	}

	/**
	 * Roles to verify daily withdrawal limit.
	 * 
	 * @param accountDTO
	 * @param valueWithdraw
	 * @throws WithdrawException
	 */
	private void verifyDailyWithdrawalLimit(AccountDTO accountDTO, BigDecimal valueWithdraw) throws WithdrawException {
		if (valueWithdraw.compareTo(accountDTO.getDailyWithdrawalLimit()) == GREATER_THAN) {
			throw new WithdrawException("Withdrawal amount higher than daily Limit");
		}
	}

	/**
	 * Set state account block or active
	 * 
	 * @param accountId
	 * @param isActive
	 * @return AccountDTO
	 * @throws UnregisteredAccount
	 */
	private AccountDTO blockOrActive(Long accountId, boolean isActive) throws UnregisteredAccount {
		Account account = findAccountIfExists(accountId);
		account.setActiveFlag(isActive);
		repoAccount.flush();
		AccountDTO accountDto = new AccountDTO(account);
		return accountDto;
	}

	/**
	 * Find account by id from repo if exists, or throw UnregisteredAccount
	 * 
	 * @param account id
	 * @return account or throw exception
	 * @throws UnregisteredAccount
	 */
	private Account findAccountIfExists(Long id) throws UnregisteredAccount {
		Optional<Account> account = repoAccount.findById(id);
		if (!account.isPresent()) {
			throw new UnregisteredAccount("Account not find");
		}
		return account.get();
	}

}
