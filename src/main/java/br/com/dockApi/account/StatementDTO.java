package br.com.dockApi.account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.dockApi.transaction.Transaction;
import br.com.dockApi.transaction.TransactionType;

public class StatementDTO {

	private LocalDateTime dateOfTransaction;
	private TransactionType typeOfTransaction;
	private BigDecimal transactionValue;

	public StatementDTO(Transaction transaction) {
		this.dateOfTransaction = transaction.getTransactionDate();
		this.transactionValue = transaction.getTransactionValue();
		this.typeOfTransaction = transaction.getTransactionType();
	}

	public LocalDateTime getDateOfTransaction() {
		return dateOfTransaction;
	}

	public void setDateOfTransaction(LocalDateTime dateOfTransaction) {
		this.dateOfTransaction = dateOfTransaction;
	}

	public TransactionType getTypeOfTransaction() {
		return typeOfTransaction;
	}

	public void setTypeOfTransaction(TransactionType typeOfTransaction) {
		this.typeOfTransaction = typeOfTransaction;
	}

	public BigDecimal getTransactionValue() {
		return transactionValue;
	}

	public void setTransactionValue(BigDecimal transactionValue) {
		this.transactionValue = transactionValue;
	}
}
