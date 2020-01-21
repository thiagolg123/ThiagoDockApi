package br.com.dockApi.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

	/**
	 * Method to check account duplicity using cpf
	 * 
	 * @param cpf
	 * @return boolean indicates whether an account exists for the person
	 */
	boolean existsByPersonCpf(String cpf);

}
