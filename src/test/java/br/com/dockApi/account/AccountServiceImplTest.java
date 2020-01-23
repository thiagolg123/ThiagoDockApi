package br.com.dockApi.account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.dockApi.excpetion.DepositException;
import br.com.dockApi.excpetion.UnregisteredAccount;
import br.com.dockApi.excpetion.UnregisteredPerson;
import br.com.dockApi.excpetion.WithdrawException;
import br.com.dockApi.transaction.Transaction;
import br.com.dockApi.transaction.TransactionRepository;
import br.com.dockApi.transaction.TransactionType;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class AccountServiceImplTest {

	@Autowired
	private AccountServiceImpl accountServiceImpl;

	@Autowired
	private TransactionRepository transactionRepo;

	@Test
	@Order(1)
	public void shouldBeCreateAccount() throws Exception {
		assertThat(accountServiceImpl.createAccount(CreateAccountData.createAccountForm()));
	}

	@Test
	public void shouldBeExceptionUnregisteredPerson() throws UnregisteredPerson {
		assertThrows(UnregisteredPerson.class,
				() -> accountServiceImpl.createAccount(CreateAccountData.createAccountFormWrongPerson()));
	}

	@Test
	public void shouldBeExceptionUnregisteredAccount() throws UnregisteredAccount {
		assertThrows(UnregisteredAccount.class, () -> accountServiceImpl.deposit(1212L, new BigDecimal(200.00)));
	}

	@Test
	public void testDeposit() throws Exception {
		assertThat(accountServiceImpl.deposit(1L, new BigDecimal(200.00)));
	}

	@Test
	public void shouldBeDepositExceptionLessThanOneOrNull() throws DepositException, UnregisteredPerson {
		assertThrows(DepositException.class, () -> accountServiceImpl.deposit(1L, new BigDecimal(0)));
		assertThrows(DepositException.class, () -> accountServiceImpl.deposit(1L, null));
	}

	@Test
	public void testExistsAccountByCpf() throws Exception {
		assertThat(accountServiceImpl.createAccount(CreateAccountData.createAccountForm()));
		assertTrue(accountServiceImpl.existsAccount(CreateAccountData.createAccountDTO()));
	}

	@Test
	public void testConsultBalance() throws Exception {
		assertThat(accountServiceImpl.consultBalance(1L));
	}

	@Test
	public void testVerifyAccountActive() throws Exception {
		assertThat(accountServiceImpl.verifyAccountActive(CreateAccountData.createAccountDTO()));
	}

	@Test
	public void testBlockAccount() throws Exception {
		assertThat(accountServiceImpl.blockAccount(1L));
	}

	@Test
	public void testActiveAccount() throws Exception {
		assertThat(accountServiceImpl.activeAccount(1L));
	}

	@Test
	public void testWithdraw() throws Exception {
		assertThat(accountServiceImpl.withdraw(1L, new BigDecimal(200.00)));
	}

	@Test
	public void testWithdrawMoreThanBalance() throws Exception {
		assertThrows(WithdrawException.class, () -> accountServiceImpl.withdraw(1L, new BigDecimal(300.00)));
	}

	@Test
	public void testWithdrawMoreThanDailyLimit() throws Exception {
		assertThrows(WithdrawException.class, () -> accountServiceImpl.withdraw(1L, new BigDecimal(400.00)));
	}

	@Test
	public void testStatement() throws Exception {
		Transaction transaction = new Transaction();
		transaction.setAccountId(1L);
		transaction.setTransactionDate(LocalDateTime.now());
		transaction.setTransactionId(1L);
		transaction.setTransactionType(TransactionType.DEPOSIT);
		transaction.setTransactionValue(new BigDecimal(200));

		transactionRepo.save(transaction);

		assertThat(accountServiceImpl.statement(1L));
	}

}
