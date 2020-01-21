package br.com.dockApi.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dockApi.account.AccountDTO;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	public TransactionRepository transactionRepository;

	@Override
	public void recordTransaction(AccountDTO accountDTO, BigDecimal valueOfTransaction,
			TransactionType transactionType) {
		Transaction transaction = new Transaction();
		transaction.setTransactionDate(LocalDateTime.now());
		transaction.setAccountId(accountDTO.getAccountId());
		transaction.setTransactionValue(valueOfTransaction);
		transaction.setTransactionType(transactionType);

		transactionRepository.save(transaction);
	}

}
