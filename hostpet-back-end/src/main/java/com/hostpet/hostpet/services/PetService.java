package com.hostpet.hostpet.services;

import com.hostpet.hostpet.dtos.PetsListDTO;
import com.hostpet.hostpet.entity.Cliente;
import com.hostpet.hostpet.entity.Pet;
import com.hostpet.hostpet.enums.Sexo;
import com.hostpet.hostpet.forms.PetForm;
import com.hostpet.hostpet.repository.PetRepository;
import com.hostpet.hostpet.repository.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private IClienteRepository clienteRepository;


    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public List<PetsListDTO> getPetsByUser(Long userId, String nome, Sexo sexo) {
        List<Pet> pets = petRepository.findPetsWithFilters(userId, nome, sexo);
        return pets.stream()
                .map(PetsListDTO::new)
                .toList();
    }

    public Optional<Pet> getPetById(Integer id) {
        return petRepository.findById(id);
    }

    // Método para salvar o Pet e vinculá-lo ao Cliente
    public Pet savePet(PetForm pet) {
        Optional<Cliente> validCliente = clienteRepository.findById(pet.clienteId);
        if (validCliente.isEmpty()) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }

        Pet novoPet = new Pet();
        novoPet.setNome(pet.nome);
        novoPet.setSexo(pet.sexo);
        novoPet.setRacaPet(pet.racaPet);
        novoPet.setObservacoes(pet.observacoes);
        novoPet.setDtNascimento(pet.dtNascimento);
        novoPet.setCliente(validCliente.get());

        return petRepository.save(novoPet);
    }


    // Metodo para atualizar um pet
    public Pet updatePet(Integer id, Pet petAtualizado){
        Pet petExistente = petRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pet não encontrado"));

        // Atualiza apenas os campos informados
        if (petAtualizado.getNome() != null) {
            petExistente.setNome(petAtualizado.getNome());
        }
        if (petAtualizado.getSexo() != null) {
            petExistente.setSexo(petAtualizado.getSexo());
        }

        if (petAtualizado.getRacaPet() != null) {
            petExistente.setRacaPet(petAtualizado.getRacaPet());
        }

        if (petAtualizado.getObservacoes() != null) {
            petExistente.setObservacoes(petAtualizado.getObservacoes());
        }

        return petRepository.save(petExistente);
    }

    // Método para excluir um Pet
    public void deletePet(Integer id) {
        petRepository.deleteById(id);
    }
}
