package br.com.dockApi.account;

import java.math.BigDecimal;

import br.com.dockApi.person.Person;

public class CreateAccountData {

	public static AccountForm createAccountForm() {
		AccountForm accountForm = new AccountForm();
		accountForm.setAccountId(1L);
		accountForm.setAccountType(AccountType.CURRENT_ACCOUNT);
		accountForm.setActiveFlag(true);
		accountForm.setBalance(new BigDecimal(200.00));
		accountForm.setDailyWithdrawalLimit(new BigDecimal(300.00));
		accountForm.setPersonCpf("25632541528");
		return accountForm;
	}

	public static AccountDTO createAccountDTO() {
		AccountDTO accountForm = new AccountDTO();
		accountForm.setAccountId(1L);
		accountForm.setAccountType(AccountType.CURRENT_ACCOUNT);
		accountForm.setActiveFlag(true);
		accountForm.setBalance(new BigDecimal(200.00));
		accountForm.setDailyWithdrawalLimit(new BigDecimal(300.00));
		Person person = new Person();
		person.setCpf("25632541528");
		accountForm.setPerson(person);
		return accountForm;
	}

	public static AccountForm createAccountFormWrongPerson() {
		AccountForm accountForm = new AccountForm();
		accountForm.setAccountId(1L);
		accountForm.setAccountType(AccountType.CURRENT_ACCOUNT);
		accountForm.setActiveFlag(true);
		accountForm.setBalance(new BigDecimal(200.00));
		accountForm.setDailyWithdrawalLimit(new BigDecimal(300.00));
		accountForm.setPersonCpf("11111111111");
		return accountForm;
	}
}
