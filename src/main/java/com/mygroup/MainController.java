package com.mygroup;

import com.mygroup.doctor.Doctor;
import com.mygroup.doctor.DoctorService;
import com.mygroup.patient.Patient;
import com.mygroup.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    PatientService service;

    @Autowired
    DoctorService doctorService;

    @GetMapping("")
    public String showHomePage(Model model, @Param("keyword") String keyword){
        List<Doctor> listDoctors = doctorService.listAll(keyword);
        model.addAttribute("listDoctors", listDoctors);
        model.addAttribute("keyword", keyword);
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
        else{
            service.savePatientWithDefaultRole(patient);
            return "register_success";
        }
    }
}
