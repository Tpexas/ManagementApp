package com.mygroup.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PatientController {
    @Autowired private PatientService service;

    @GetMapping("/pacientai")
    public String showPatientList(Model model){
        List<Patient> listPatients = service.listAll();
        model.addAttribute("listPatients", listPatients);
        return "patients";
    }

    @GetMapping("/pacientai/naujas")
    public String showNewForm(Model model){
        model.addAttribute("patient", new Patient());
        model.addAttribute("pageTitle", "Pridėti naują pacientą");
        return "patient_form";
    }

    @PostMapping("/pacientai/issaugoti")
        public String savePatient(Patient patient, RedirectAttributes ra){
            service.save(patient);
            ra.addFlashAttribute("message", "Pacientas sėkmingai pridėtas");
            return "redirect:/pacientai";
    }

    @GetMapping("/pacientai/redaguoti/{patientID}")
    public String showEditForm(@PathVariable("patientID") Integer patientID, Model model, RedirectAttributes ra){
        try {
            Patient patient = service.get(patientID);
            model.addAttribute("patient", patient);
            model.addAttribute("pageTitle", "Redaguoti pacientą (ID: " + patientID + ")");
            return "patient_form";
        } catch (PatientNotFoundException e) {
            ra.addFlashAttribute("message", "Pacientas sėkmingai pridėtas");
            return "redirect:/pacientai";
        }
    }
}
