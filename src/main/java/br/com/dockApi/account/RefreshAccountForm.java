package br.com.dockApi.account;

import java.math.BigDecimal;
import java.util.Optional;

import br.com.dockApi.excpetion.DepositException;

public class RefreshAccountForm {

	private BigDecimal balance;

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Account deposit(Long idAccount, AccountRepository repoAccount) throws DepositException {
		Optional<Account> accountRefresh = repoAccount.findById(idAccount);
		Account account = accountRefresh.isPresent() ? accountRefresh.get() : null;

		if (account != null && account.isActiveFlag()) {
			account.setBalance(this.balance);
			repoAccount.flush();
			return account;
		}
		throw new DepositException("Inactive account");
	}
}
