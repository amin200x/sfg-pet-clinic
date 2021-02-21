package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class VisitController {
    private final VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @GetMapping("/pets/{petId}/visits/new")
    public String saveVisitForm(@PathVariable("petId") Long petId, Model model){
        Pet pet = new Pet();
        pet.setId(petId);
        model.addAttribute("visit", Visit.builder().pet(pet).build());
        return "pets/visitform";
    }

    @GetMapping("/visits/{id}/update")
    public String updateVisitForm(@PathVariable Long id, Model model){
        model.addAttribute("visit", visitService.findById(id));
        return "pets/visitform";
    }

    @PostMapping("/visits" )
    public String saveOrUpdateVisit(@ModelAttribute Visit visit){
        Visit savedVisit = visitService.save(visit);
        return "redirect:/owners/"+savedVisit.getPet().getOwner().getId();
    }
    @GetMapping("/visits/{id}/delete")
    public String deleteVisit(@PathVariable Long id){
       Visit visit = visitService.findById(id);
       long ownerId = visit.getPet().getOwner().getId();
       visitService.delete(visit);
        return "redirect:/owners/"+ownerId;
    }

}
