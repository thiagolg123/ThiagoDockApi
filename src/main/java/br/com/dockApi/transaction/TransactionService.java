package br.com.dockApi.transaction;

import java.math.BigDecimal;

import br.com.dockApi.account.AccountDTO;

public interface TransactionService {

	void recordTransaction(AccountDTO accountDTO, BigDecimal valueOfTransaction, TransactionType transactionType);

}
