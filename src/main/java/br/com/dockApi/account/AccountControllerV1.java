package br.com.dockApi.account;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.dockApi.excpetion.DepositException;
import br.com.dockApi.excpetion.RegisteredAccount;
import br.com.dockApi.excpetion.UnregisteredPerson;

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

	@Autowired
	private AccountService accountService;

	/**
	 * Create an account if there is a person
	 * 
	 * @param accForm    form from body
	 * @param uriBuilder Uri builder
	 * @return response entity according to cases
	 */
	@PostMapping
	public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountForm accForm, UriComponentsBuilder uriBuilder) {
		AccountDTO accDto = null;
		try {
			accDto = accountService.createAccount(accForm);
			accountService.existsAccount(accDto);
		} catch (UnregisteredPerson | RegisteredAccount e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		URI uri = uriBuilder.path(V1_ACCOUNT_PARAM_ID).buildAndExpand(accDto.getAccountId()).toUri();
		return ResponseEntity.created(uri).body(accDto);
	}

	/**
	 * Deposit a value in account
	 * 
	 * @param accForm
	 * @param uriBuilder
	 * @return
	 */
	@PutMapping("/deposit/{id}")
	public ResponseEntity<AccountDTO> accountDeposit(@PathVariable Long id,
			@RequestBody RefreshAccountForm refreshAccForm) {

		try {
			Account account = accountService.deposit(id, refreshAccForm.getBalance());
			return ResponseEntity.ok(new AccountDTO(account));
		} catch (DepositException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}
