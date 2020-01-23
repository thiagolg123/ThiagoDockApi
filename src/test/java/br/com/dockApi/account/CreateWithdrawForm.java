package br.com.dockApi.account;

import java.math.BigDecimal;

public class CreateWithdrawForm {

	public static WithdrawForm createWithdrawForm() {
		WithdrawForm withdrawFrom = new WithdrawForm();
		withdrawFrom.setValueWithdraw(new BigDecimal(200));
		return withdrawFrom;
	}

}
