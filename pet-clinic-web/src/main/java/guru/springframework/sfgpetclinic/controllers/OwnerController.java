package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping({"/find"})
    public String findOwners() {
        return "notimplemented";
    }

    @RequestMapping({"/{id}"})
    public ModelAndView showOwner(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView("/owners/ownerdetail");
        modelAndView.addObject("owner", ownerService.findById(Long.valueOf(id)));
        return modelAndView;
    }

    @RequestMapping({"/{id}/delete"})
    public String deleteOwner(@PathVariable Long id) {
        ownerService.deleteById(id);
        return "redirect:/index";
    }


    @RequestMapping({"/{id}/update"})
    public String updateOwner(@PathVariable Long id, Model model) {
       model.addAttribute("owner", ownerService.findById(id));
        return "owners/ownerform";
    }
}
