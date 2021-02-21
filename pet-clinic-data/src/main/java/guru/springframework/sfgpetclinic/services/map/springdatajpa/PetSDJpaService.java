package guru.springframework.sfgpetclinic.services.map.springdatajpa;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class PetSDJpaService implements PetService {
    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;
    private final PetTypeService petTypeService;

    public PetSDJpaService(PetRepository petRepository, OwnerRepository ownerRepository, PetTypeService petTypeService) {
        this.petRepository = petRepository;
        this.ownerRepository = ownerRepository;
        this.petTypeService = petTypeService;
    }

    @Override
    public Set<Pet> findAll() {
        Set<Pet> pets = new HashSet<>();
        petRepository.findAll().forEach(pets::add);
        return pets;
    }

    @Override
    public Pet findById(Long id) {
        return petRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Pet save(Pet pet) {
        Pet savedPet = new Pet();
        if (pet.getId() == null) {
            pet.setOwner(ownerRepository.findById(pet.getOwner().getId()).get());
            ownerRepository.findById(pet.getOwner().getId()).get().getPets().add(pet);
            pet.setPetType(petTypeService.save(pet.getPetType()));
            savedPet = petRepository.save(pet);
        } else {
            Optional<Pet> petOptional = petRepository.findById(pet.getId());
            if (petOptional.isPresent()) {
                Pet updatePet = petOptional.get();
                updatePet.getPetType().setName(pet.getPetType().getName());
                updatePet.setPetType(pet.getPetType());
                updatePet.setName(pet.getName());
                updatePet.setBirthDate(pet.getBirthDate());
                savedPet = petRepository.save(updatePet);
            }
        }

        return savedPet;
    }

    @Override
    public void delete(Pet pet) {
        petRepository.delete(pet);
    }

    @Override
    public void deleteById(Long id) {
        petRepository.deleteById(id);
    }
}
