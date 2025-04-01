package org.lessons.java.spring_la_mia_pizzeria_crud.controller;

import java.util.List;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;





@Controller
@RequestMapping("/pizze")
public class PizzaController {
    @Autowired
    private PizzaRepository repository;

    @GetMapping
    public String index(Model model) {
        List<Pizza> pizze = repository.findAll();
        System.out.println("Pizze trovate: " + pizze);
        model.addAttribute("pizze", pizze);
        return "pizze/index";
    }

    @GetMapping("/{id}")
public String show(@PathVariable Integer id, Model model) {
    Pizza pizza = repository.findById(id).orElse(null);
    if (pizza == null) {
        return "redirect:/pizze"; // Se non trova la pizza, torna alla lista
    }
    model.addAttribute("pizza", pizza);
    return "pizze/show"; // Mostra la vista del dettaglio
    }
    @GetMapping("/searchByName")
    public String searchByName(@RequestParam(name="name") String  name, Model model) {
        List<Pizza> pizze = repository.findByNameContaining(name);
        model.addAttribute("pizze", pizze);
        return "pizze/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza",new Pizza());
        
        return "pizze/create";
    }

    @PostMapping("/create")
    public String store( @Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingresult, Model model) {
        if(bindingresult.hasErrors()) {
            return "pizze/create";
        }
        repository.save(formPizza);
        
        return "redirect:/pizze";
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id,Model model) {
        Pizza pizza = repository.findById(id).orElse(null);
        model.addAttribute("pizza", pizza);
if (pizza == null) {
    return "redirect:/pizze";
}
        return "pizze/edit";
    }

    @PostMapping("/edit/{id}")
    public String update( @Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingresult, Model model) {
        if(bindingresult.hasErrors()) {
            return "pizze/edit";
        }
        repository.save(formPizza);
        
        return "redirect:/pizze";
    }


    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        repository.deleteById(id);
        return "redirect:/pizze";
    }
    
    
    
    

    
}
