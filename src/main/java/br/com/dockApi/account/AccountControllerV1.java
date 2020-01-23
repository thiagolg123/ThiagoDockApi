package br.com.dockApi.account;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.dockApi.excpetion.DepositException;
import br.com.dockApi.excpetion.InactiveAccountException;
import br.com.dockApi.excpetion.RegisteredAccount;
import br.com.dockApi.excpetion.UnregisteredAccount;
import br.com.dockApi.excpetion.UnregisteredPerson;
import br.com.dockApi.excpetion.WithdrawException;
import br.com.dockApi.transaction.TransactionService;
import br.com.dockApi.transaction.TransactionType;

/**
 * Controller of account resources
 * 
 * @author Thiago Gonçalves
 *
 */
@RestController
@RequestMapping("/v1/account")
public class AccountControllerV1 {

	private static final String V1_ACCOUNT_PARAM_ID = "/v1/account/{id}";

	private Logger logger = LoggerFactory.getLogger(AccountControllerV1.class);

	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionService transactionService;

	@PostMapping
	public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountForm accForm, UriComponentsBuilder uriBuilder) {
		AccountDTO accDto = null;
		try {
			accDto = accountService.createAccount(accForm);
			accountService.existsAccount(accDto);
			logger.info("Registred account sucess to: {}", accDto.getPerson().getName());
		} catch (UnregisteredPerson | RegisteredAccount e) {
			logger.warn(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		URI uri = uriBuilder.path(V1_ACCOUNT_PARAM_ID).buildAndExpand(accDto.getAccountId()).toUri();
		return ResponseEntity.created(uri).body(accDto);
	}

	@PutMapping("/deposit/{id}")
	public ResponseEntity<AccountDTO> accountDeposit(@PathVariable Long id, @RequestBody DepositForm depositForm) {
		try {
			BigDecimal valueDeposit = depositForm.getValueDeposit();
			Account account = accountService.deposit(id, valueDeposit);
			AccountDTO accountDTO = new AccountDTO(account);
			transactionService.recordTransaction(accountDTO, valueDeposit, TransactionType.DEPOSIT);
			logger.info("Deposit sucess to account: {}", accountDTO.getAccountId());
			return ResponseEntity.ok(accountDTO);
		} catch (DepositException | InactiveAccountException | UnregisteredAccount e) {
			logger.warn(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping("/balance/{id}")
	public ResponseEntity<BigDecimal> accountBalance(@PathVariable Long id) {
		AccountDTO accountDTO = null;
		try {
			accountDTO = accountService.consultBalance(id);
			logger.info("Consult balance to account: {}", accountDTO.getAccountId());
		} catch (UnregisteredAccount | InactiveAccountException e) {
			logger.warn(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(accountDTO.getBalance());

	}

	@PutMapping("/withdraw/{id}")
	public ResponseEntity<AccountDTO> withdraw(@PathVariable Long id, @RequestBody WithdrawForm withdrawForm) {
		AccountDTO accountDTO = null;
		try {
			accountDTO = accountService.withdraw(id, withdrawForm.getValueWithdraw());
			BigDecimal valueWithDraw = withdrawForm.getValueWithdraw();
			transactionService.recordTransaction(accountDTO, valueWithDraw, TransactionType.WITHDRAW);
			logger.info("Withdraw sucess in account: {}", accountDTO.getAccountId());
		} catch (UnregisteredAccount | InactiveAccountException | WithdrawException e) {
			logger.warn(e.getMessage());
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
		}
		return ResponseEntity.ok(accountDTO);
	}

	@PutMapping("/block/{id}")
	public ResponseEntity<AccountDTO> blockAccount(@PathVariable Long id) {
		AccountDTO accountDTO = null;
		try {
			accountDTO = accountService.blockAccount(id);
			logger.info("Block account: {}", accountDTO.getAccountId());
		} catch (UnregisteredAccount e) {
			logger.warn(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(accountDTO);
	}

	@PutMapping("/active/{id}")
	public ResponseEntity<AccountDTO> activeAccount(@PathVariable Long id) {
		AccountDTO accountDTO = null;
		try {
			accountDTO = accountService.activeAccount(id);
			logger.info("Active account: {}", accountDTO.getAccountId());
		} catch (UnregisteredAccount e) {
			logger.warn(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(accountDTO);
	}

	@GetMapping("/statement/{id}")
	public ResponseEntity<List<StatementDTO>> statementAccount(@PathVariable Long id) {
		List<StatementDTO> listOfStatementDTO = new ArrayList<>();
		listOfStatementDTO = accountService.statement(id);
		if (listOfStatementDTO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		logger.info("Execute statement sucess to account: {}", id);
		return ResponseEntity.ok(listOfStatementDTO);
	}
}
