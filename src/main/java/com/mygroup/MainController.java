package com.mygroup;

import com.mygroup.patient.Patient;
import com.mygroup.patient.PatientRepository;
import com.mygroup.patient.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

    @Autowired
    private PatientService patientService;

    @GetMapping("")
    public String showHomePage(){
        return "index";
    }

    @GetMapping("/registracija")
    public String showSignUpForm(Model model){
        model.addAttribute("patient", new Patient());
        return "signup_form";
    }

    @PostMapping("/registracijos_procesas")
    public String processRegistration(@Valid Patient patient, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }
        else {
            patientService.save(patient);
            return "register_success";
        }
    }
}
