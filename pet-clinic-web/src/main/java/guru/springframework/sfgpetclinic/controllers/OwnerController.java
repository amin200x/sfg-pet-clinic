package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.server.PathParam;

@RequestMapping("/owners")
@Controller

public class OwnerController {
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @RequestMapping({"", "/", "/index", "/index.html"})
    public String listOwners(Model model) {
        model.addAttribute("owners", ownerService.findAll());
        return "owners/index";
    }

   /* @RequestMapping({"/find"})
    public String findOwners() {
        return "notimplemented";
    }*/

    @RequestMapping({"/{id}"})
    public ModelAndView showOwner(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("/owners/ownerdetail");
        modelAndView.addObject("owner", ownerService.findById(Long.valueOf(id)));
        return modelAndView;
    }
    @RequestMapping({"/find"})
    public ModelAndView findOwners() {
        ModelAndView modelAndView = new ModelAndView("/owners/findowner");
        return modelAndView;
    }
    @RequestMapping({"/findbylastname"})
    public ModelAndView findOwnerByLastName(@PathParam("lastName") String lastName) {
        System.out.println(lastName);
        ModelAndView modelAndView = new ModelAndView("/owners/ownerdetail");
        Owner foundOwner = ownerService.findByLastName(lastName);
        if (foundOwner==null) {
            foundOwner = new Owner();
            foundOwner.setLastName("Not Found!!!");
        }

        modelAndView.addObject("owner", foundOwner);
        return modelAndView;
    }
    @RequestMapping({"/{id}/delete"})
    public String deleteOwner(@PathVariable Long id) {
        ownerService.deleteById(id);
        return "redirect:/owners/index";
    }

    @RequestMapping({"/new"})
    public String newOwner(Model model) {
        model.addAttribute("owner", new Owner());
        return "owners/ownerform";
    }

    @RequestMapping({"/{id}/update"})
    public String updateOwner(@PathVariable Long id, Model model) {
       model.addAttribute("owner", ownerService.findById(id));
        return "owners/ownerform";
    }
    @RequestMapping(value = {"/", ""}, method = RequestMethod.POST)
    public String updateOrSave(@ModelAttribute("owner")Owner owner) {
        ownerService.save(owner);
        return "redirect:/owners/index";
    }


}
