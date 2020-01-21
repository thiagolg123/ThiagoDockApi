package br.com.dockApi.account;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dockApi.excpetion.DepositException;
import br.com.dockApi.excpetion.InactiveAccountException;
import br.com.dockApi.excpetion.UnregisteredAccount;
import br.com.dockApi.excpetion.UnregisteredPerson;
import br.com.dockApi.person.Person;
import br.com.dockApi.person.PersonRepository;

@Service
public class AccountServiceImpl implements AccountService {

	private static final int LESS_THAN = -1;

	@Autowired
	private AccountRepository repoAccount;

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
	public Account deposit(Long idAccount, BigDecimal valueDeposit) throws InactiveAccountException, DepositException {
		Optional<Account> accountRefresh = repoAccount.findById(idAccount);
		Account account = accountRefresh.isPresent() ? accountRefresh.get() : null;
		AccountDTO accountDTO = new AccountDTO(account);

		if (valueDeposit == null || valueDeposit.compareTo(BigDecimal.ONE) == LESS_THAN) {
			throw new DepositException("Deposit value less than limit");
		}

		if (verifyAccountActive(accountDTO)) {
			BigDecimal newBalance = account.getBalance().add(valueDeposit);
			account.setBalance(newBalance);
			repoAccount.flush();
			return account;
		}
		throw new InactiveAccountException("Inactive account");

	}

	@Override
	public AccountDTO consultBalance(Long id) throws UnregisteredAccount, InactiveAccountException {
		Optional<Account> account = repoAccount.findById(id);
		if (!account.isPresent()) {
			throw new UnregisteredAccount("Account not find");
		}

		AccountDTO accountDto = new AccountDTO(account.get());

		if (verifyAccountActive(accountDto)) {
			return accountDto;
		}

		throw new InactiveAccountException("Inactive account");
	}

	@Override
	public boolean verifyAccountActive(AccountDTO accDto) {
		return accDto != null && accDto.isActiveFlag();
	}

}
