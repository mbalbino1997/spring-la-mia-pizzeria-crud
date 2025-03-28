package org.lessons.java.spring_la_mia_pizzeria_crud.controller;

import java.util.List;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



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
    

    
}
