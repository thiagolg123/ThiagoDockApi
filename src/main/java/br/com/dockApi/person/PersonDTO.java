package br.com.dockApi.person;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PersonDTO {
  private Long id;
  private String name;
  private String cpf;
  private LocalDateTime birthDate;

  public PersonDTO(Person person) {
    this.id = person.getIdPerson();
    this.name = person.getName();
    this.cpf = person.getCpf();
    this.birthDate = person.getBirthDate();
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getCPF() {
    return cpf;
  }

  public LocalDateTime getBirthDate() {
    return birthDate;
  }

  public static List<PersonDTO> convert(List<Person> person) {
    return person.stream().map(PersonDTO::new).collect(Collectors.toList());
  }

}
