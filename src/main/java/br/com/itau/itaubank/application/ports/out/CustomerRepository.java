package com.unla.ghsicilianotfi.services.implementation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.unla.ghsicilianotfi.entities.Person;
import com.unla.ghsicilianotfi.dtos.PersonDTO;
import com.unla.ghsicilianotfi.repositories.IPersonRepository;
import com.unla.ghsicilianotfi.services.IPersonService;

/**
 * Serviço de implementação para gerenciar operações relacionadas a entidades Person.
 */
@Service("personService")
public class PersonService implements IPersonService {

    private final IPersonRepository personRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Construtor da classe PersonService.
     *
     * @param personRepository Repositório para acesso aos dados de Person.
     */
    public PersonService(IPersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**
     * Recupera todas as entidades Person do repositório.
     *
     * @return Lista de todas as entidades Person.
     */
    @Override
    public List<Person> getAll() {
        return personRepository.findAll();
    }

    /**
     * Insere ou atualiza uma entidade Person no repositório.
     *
     * @param person Entidade Person a ser inserida ou atualizada.
     * @return Entidade Person inserida ou atualizada.
     */
    @Override
    public Person insertOrUpdate(Person person) {
        return personRepository.save(person);
    }

    /**
     * Remove uma entidade Person do repositório pelo seu ID.
     *
     * @param id ID da entidade Person a ser removida.
     * @return true se a entidade foi removida com sucesso, false caso contrário.
     */
    @Override
    public boolean remove(int id) {
        try {
            personRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Procura uma entidade Person pelo seu ID.
     *
     * @param id ID da entidade Person.
     * @return Optional contendo a entidade Person, se encontrada.
     * @throws Exception Se ocorrer um erro durante a busca.
     */
    @Override
    public Optional<Person> findById(int id) throws Exception {
        return personRepository.findById(id);
    }

    /**
     * Procura uma entidade Person pelo seu nome.
     *
     * @param name Nome da entidade Person.
     * @return A entidade Person encontrada.
     * @throws Exception Se não existir uma entidade Person com o nome fornecido.
     */
    @Override
    public Person findByName(String name) throws Exception {
        return personRepository.findByName(name)
                .orElseThrow(() -> new Exception("ERROR: Não existe Pessoa com o nome: " + name));
    }

    /**
     * Procura entidades Person pelo nome do grau acadêmico.
     *
     * @param degreeName Nome do grau acadêmico.
     * @return Lista de PersonDTOs correspondente às entidades encontradas.
     */
    @Override
    public List<PersonDTO> findByDegreeName(String degreeName) {
        return personRepository.findByDegreeName(degreeName)
                .stream()
                .map(person -> modelMapper.map(person, PersonDTO.class))
                .collect(Collectors.toList());
    }
}
