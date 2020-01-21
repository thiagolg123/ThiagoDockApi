package br.com.dockApi.person;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class PersonControllerV2 {

  @Autowired
  private PersonRepository personRepo;

  @GetMapping("/people")
  public List<PersonDTO> getPeople() {

    return PersonDTO.convert(personRepo.findAll());
  }

}
