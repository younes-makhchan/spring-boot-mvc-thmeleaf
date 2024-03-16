package com.example.springbootmvcthymeleaf.web;

import com.example.springbootmvcthymeleaf.entities.Patient;
import com.example.springbootmvcthymeleaf.repositories.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class PatientController {
    private final PatientRepository patientRepository;

    @GetMapping(path="/user/index")
    public String  patients(Model model,
                            @RequestParam(name="page",defaultValue = "0")int page,
                            @RequestParam(name="size",defaultValue = "5") int size,
                            @RequestParam(name="keyword",defaultValue = "") String keyword
                            ){
        Page<Patient> pagePatients=patientRepository.findByNomContains(keyword,PageRequest.of(page,size));
        model.addAttribute("listPatients",pagePatients.getContent());
        model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);

        return  "patients"; //return the patients template
    }
    @GetMapping(path="/")
    public String  home(){
        return  "home"; //return the patients template
    }
    @GetMapping(path="/admin/formPatient")
    public String  formPatients(Model model){
        model.addAttribute("patient",new Patient());
        return  "formPatients"; //return the patients template
    }
    @GetMapping(path="/admin/edit")
    public String  editPatients(
            @RequestParam(name="page",defaultValue = "0")int page,
            @RequestParam(name="keyword",defaultValue = "") String keyword,
            @RequestParam Long id,
            Model model){
        Patient patient=patientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("not found"));
        model.addAttribute("patient",patient);
        model.addAttribute("page",page);
        model.addAttribute("keyword",keyword);
        return  "editPatients"; //return the patients template
    }
    @PostMapping(path = "/admin/save")
    public String save(Model model,@Valid Patient patient,
                       @RequestParam(name="page",defaultValue = "0")int page,
                       @RequestParam(name="keyword",defaultValue = "") String keyword
                       ){
        patientRepository.save(patient);
        return "redirect:/index?page="+page+"&keyword="+keyword;//better to change to a redirect
    }
    @PostMapping("/admin/index")
    public String delete(
            @RequestParam(name="page",defaultValue = "0")int page,
            @RequestParam(name="size",defaultValue = "5") int size,
            @RequestParam(name="keyword",defaultValue = "") String keyword,
            Long id
    ){

        patientRepository.deleteById(id);
        return  "redirect:/index";

    }
}
