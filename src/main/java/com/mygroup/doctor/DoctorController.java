package com.mygroup.doctor;

import com.mygroup.patient.Patient;
import com.mygroup.patient.PatientNotFoundException;
import com.mygroup.patient.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class DoctorController {
    @Autowired
    private DoctorService service;
    @GetMapping("/gydytojai")
    public String showDoctorList(Model model){
        List<Doctor> listDoctors = service.listAll();
        model.addAttribute("listDoctors", listDoctors);
        return "doctors";
    }

    @GetMapping("/gydytojai/naujas")
    public String showNewForm(Model model){
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("pageTitle", "Pridėti naują gydytoją");
        return "doctor_form";
    }

    @PostMapping("/gydytojai/issaugoti")
    public String saveDoctor(@Valid Doctor doctor, BindingResult bindingResult, RedirectAttributes ra){
        if (bindingResult.hasErrors()) {
            return "doctor_form";
        }
        else {
            service.save(doctor);
            ra.addFlashAttribute("message", "Gydytojas sėkmingai išsaugotas");
            return "redirect:/gydytojai";
        }
    }

    @GetMapping("/gydytojai/redaguoti/{doctorID}")
    public String showEditForm(@PathVariable("doctorID") Integer doctorID, Model model, RedirectAttributes ra){
        try {
            Doctor doctor = service.get(doctorID);
            model.addAttribute("doctor", doctor);
            model.addAttribute("pageTitle", "Redaguoti gydytoją (ID: " + doctorID + ")");
            return "doctor_form";
        } catch (DoctorNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/gydytojai";
        }
    }

    @GetMapping("/gydytojai/istrinti/{doctorID}")
    public String deleteDoctor(@PathVariable("doctorID") Integer doctorID, RedirectAttributes ra){
        try {
            service.delete(doctorID);
            ra.addFlashAttribute("message", doctorID + " ID sėkmingai ištrintas");
        } catch (DoctorNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/gydytojai";
    }

}
