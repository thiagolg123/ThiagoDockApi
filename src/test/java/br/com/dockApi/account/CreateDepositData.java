package br.com.dockApi.account;

import java.math.BigDecimal;

public class CreateDepositData {

	public static DepositForm createDepositForm() {
		DepositForm depositForm = new DepositForm();
		depositForm.setValueDeposit(new BigDecimal(200));
		return depositForm;
	}

	public static DepositForm createDepositFormWithValNull() {
		DepositForm depositForm = new DepositForm();
		depositForm.setValueDeposit(null);
		return depositForm;
	}

}
