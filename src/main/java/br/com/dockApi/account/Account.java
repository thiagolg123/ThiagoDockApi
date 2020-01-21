package br.com.dockApi.account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import br.com.dockApi.person.Person;

@Entity
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long accountId;

	@OneToOne
	private Person person;

	@Enumerated(EnumType.STRING)
	private AccountType accountType = AccountType.CURRENT_ACCOUNT;

	private BigDecimal balance;
	private BigDecimal dailyWithdrawalLimit;
	private boolean activeFlag;
	private LocalDateTime dateCreation = LocalDateTime.now();

	public Account() {
	}

	public Account(Person person, AccountType accountType, BigDecimal balance, BigDecimal dailyWithdrawalLimit,
			boolean activeFlag) {
		this.person = person;
		this.accountType = accountType;
		this.balance = balance;
		this.dailyWithdrawalLimit = dailyWithdrawalLimit;
		this.activeFlag = activeFlag;
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

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public LocalDateTime getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(LocalDateTime dateCreation) {
		this.dateCreation = dateCreation;
	}

}
