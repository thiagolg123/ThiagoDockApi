package br.com.dockApi.account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.dockApi.person.Person;

public class AccountDTO {
	private Long accountId;
	private Person person;
	private AccountType accountType = AccountType.CURRENT_ACCOUNT;
	private BigDecimal balance;
	private BigDecimal dailyWithdrawalLimit;
	private boolean activeFlag;
	private LocalDateTime dateCreation;

	public AccountDTO(Account account) {
		this.accountId = account.getAccountId();
		this.person = account.getPerson();
		this.accountType = account.getAccountType();
		this.balance = account.getBalance();
		this.dailyWithdrawalLimit = account.getDailyWithdrawalLimit();
		this.activeFlag = account.isActiveFlag();
		this.dateCreation = account.getDateCreation();
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getDailyWithdrawalLimit() {
		return dailyWithdrawalLimit;
	}

	public void setDailyWithdrawalLimit(BigDecimal dailyWithdrawalLimit) {
		this.dailyWithdrawalLimit = dailyWithdrawalLimit;
	}

	public boolean isActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	public LocalDateTime getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(LocalDateTime dateCreation) {
		this.dateCreation = dateCreation;
	}

}
