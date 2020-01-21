package br.com.dockApi.person;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

	Person findByCpf(String cpf);

}
