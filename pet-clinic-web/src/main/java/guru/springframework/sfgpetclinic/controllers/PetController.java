package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping("/pets" )
    public String saveOrUpdatePet(@ModelAttribute Pet pet){
       Pet savedPet = petService.save(pet);
      return "redirect:/owners/"+savedPet.getOwner().getId();
    }

    @GetMapping("/owners/{ownerId}/pets/new")
    public String savePetForm(@PathVariable("ownerId") Long ownerId, Model model){
        Pet newPet = new Pet();
        newPet.setOwner(Owner.builder().id(ownerId).build());
        newPet.setPetType(PetType.builder().build());
        model.addAttribute("pet", newPet);
        return "pets/petform";
    }

    @GetMapping("/pets/{id}/update")
    public String updatePetForm(@PathVariable Long id, Model model){
        model.addAttribute("pet", petService.findById(id));
        return "pets/petform";
    }

    @GetMapping("/owners/{ownerId}/pets/{id}/delete")
    public String deletePet(@PathVariable Long ownerId, @PathVariable Long id){

        petService.deleteById(id);
        return "redirect:/owners/"+ownerId;
    }


}
