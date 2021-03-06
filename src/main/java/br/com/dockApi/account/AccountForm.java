package br.com.dockApi.account;

import java.math.BigDecimal;

public class AccountForm {
	private Long accountId;
	private String personCpf;
	private AccountType accountType = AccountType.CURRENT_ACCOUNT;
	private BigDecimal balance;
	private BigDecimal dailyWithdrawalLimit;
	private boolean activeFlag;

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
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

	public String getPersonCpf() {
		return personCpf;
	}

	public void setPersonCpf(String personCpf) {
		this.personCpf = personCpf;
	}

}
