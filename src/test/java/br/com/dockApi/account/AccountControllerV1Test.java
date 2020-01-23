package br.com.dockApi.account;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.dockApi.transaction.TransactionRepository;

@SpringBootTest
public class AccountControllerV1Test {

	@Autowired
	private AccountControllerV1 accountController;

	@Autowired
	private TransactionRepository transactionRepo;

	@Test
	public void testCreateAccount() throws Exception {
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
		ResponseEntity<AccountDTO> responseEntity = this.accountController
				.createAccount(CreateAccountData.createAccountForm(), uriBuilder);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
	}

	@Test
	public void testCreateAccountWithoutPerson() throws Exception {
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
		ResponseEntity<AccountDTO> responseEntity = this.accountController
				.createAccount(CreateAccountData.createAccountFormWrongPerson(), uriBuilder);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void testAccountDeposit() throws Exception {
		ResponseEntity<AccountDTO> responseEntity = this.accountController.accountDeposit(1L,
				CreateDepositData.createDepositForm());

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void testAccountDepositWithValNullAndLessThanOne() throws Exception {
		ResponseEntity<AccountDTO> responseEntity = this.accountController.accountDeposit(1L,
				CreateDepositData.createDepositFormWithValNull());
		assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	public void testAccountDepositUnknowAccount() throws Exception {
		ResponseEntity<AccountDTO> responseEntity = this.accountController.accountDeposit(456465L,
				CreateDepositData.createDepositForm());

		assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	public void testAccountBalance() throws Exception {
		ResponseEntity<BigDecimal> responseEntity = this.accountController.accountBalance(1L);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void testUnregisteredAccountBalance() throws Exception {
		ResponseEntity<BigDecimal> responseEntity = this.accountController.accountBalance(456465L);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	public void testWithdraw() throws Exception {
		ResponseEntity<AccountDTO> responseEntity = this.accountController.withdraw(1L,
				CreateWithdrawForm.createWithdrawForm());

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void testUnregisteredAccountWithdraw() throws Exception {
		ResponseEntity<AccountDTO> responseEntity = this.accountController.withdraw(456465L,
				CreateWithdrawForm.createWithdrawForm());

		assertEquals(responseEntity.getStatusCode(), HttpStatus.PRECONDITION_FAILED);
	}

	@Test
	public void testBlockAccount() throws Exception {
		ResponseEntity<AccountDTO> responseEntity = this.accountController.blockAccount(1L);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void testUnregisteredAccountBlockAccount() throws Exception {
		ResponseEntity<AccountDTO> responseEntity = this.accountController.blockAccount(456465L);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	public void testActiveAccount() throws Exception {
		ResponseEntity<AccountDTO> responseEntity = this.accountController.activeAccount(1L);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void testUnregisteredAccountActiveAccount() throws Exception {
		ResponseEntity<AccountDTO> responseEntity = this.accountController.activeAccount(456465L);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	public void testStatementAccount() throws Exception {
		ResponseEntity<List<StatementDTO>> responseEntity = this.accountController.statementAccount(1L);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void testStatementAccountWithNoTransaction() throws Exception {
		transactionRepo.deleteAll(); // super real, db in memory for only tests :S :/
		ResponseEntity<List<StatementDTO>> responseEntity = this.accountController.statementAccount(1L);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
	}

}
