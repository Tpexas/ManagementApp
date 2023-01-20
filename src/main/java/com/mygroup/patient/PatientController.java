package com.mygroup.patient;

import com.mygroup.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PatientController {
    @Autowired private PatientService service;
    @Autowired private PatientRepository repo;
    @GetMapping("/pacientai/{pageNum}")
    public String showPatientListByPage(Model model,
                                        @PathVariable(name = "pageNum") int pageNum,
                                        @Param("sortField") String sortField,
                                        @Param("sortDir") String sortDir){
        Page<Patient> page = service.listPage(pageNum, sortField, sortDir);
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();

        List<Patient> listPatients = page.getContent();

        double averageAge = repo.avg();
        int minAge = repo.min();
        int maxAge = repo.max();
        model.addAttribute("average", averageAge);
        model.addAttribute("min", minAge);
        model.addAttribute("max", maxAge);

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listPatients", listPatients);
        return "patients";
    }

    @RequestMapping("/pacientai")
    public String showPatientList(Model model){
        double average = repo.avg();
        model.addAttribute("average", average);
        return showPatientListByPage(model, 1, "name", "asc");
    }

    @GetMapping("/pacientai/naujas")
    public String showNewForm(Model model){
        List<Role> listRoles = service.getRoles();
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("patient", new Patient());
        model.addAttribute("pageTitle", "Pridėti naują pacientą");
        return "patient_form";
    }

    @PutMapping("/pacientai/issaugoti")
    public String savePatient(@Valid Patient patient, BindingResult bindingResult, Model model, RedirectAttributes ra){
        if (bindingResult.hasErrors()) {
            List<Role> listRoles = service.getRoles();
            model.addAttribute("listRoles", listRoles);
            return "patient_form";
        }
        else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodedPassword = encoder.encode(patient.getPassword());
            patient.setPassword(encodedPassword);
            service.save(patient);
            ra.addFlashAttribute("message", "Pacientas sėkmingai išsaugotas");
            return "redirect:/pacientai";
        }
    }

    @GetMapping("/pacientai/redaguoti/{patientID}")
    public ModelAndView showEditForm(@PathVariable("patientID") Integer patientID, Model model, RedirectAttributes ra) {
        if(repo.findById(patientID).isPresent()){
            Patient patient = repo.findById(patientID).get();
            model.addAttribute("patient", patient);
            List<Role> listRoles = service.getRoles();
            model.addAttribute("listRoles", listRoles);
            model.addAttribute("pageTitle", "Redaguoti pacientą (ID: " + patient.getPatientID() + ")");
            return new ModelAndView("patient_form", "patient", patient);
        }
        else {
            ra.addFlashAttribute("message", "Pacientas nerastas");
            return new ModelAndView("redirect:/pacientai");
        }
    }

    @PutMapping("/redaguoti")
    public ModelAndView editPatient(@ModelAttribute Patient patient){
        return new ModelAndView("patient_form");
    }

    @GetMapping("/pacientai/istrinti/{patientID}")
    public ModelAndView viewDeletePatient(@PathVariable("patientID") Integer patientID, RedirectAttributes ra) {
        if(repo.findById(patientID).isPresent()){
            Patient patient = repo.findById(patientID).get();
            return new ModelAndView("delete_patient", "patient", patient);
        }
        else {
            ra.addFlashAttribute("message", "Pacientas nerastas");
            return new ModelAndView("redirect:/pacientai");
        }
    }

    @DeleteMapping("/istrinti")
    public ModelAndView deletePatient(@ModelAttribute Patient patient, RedirectAttributes ra){
        try {
            service.delete(patient.getPatientID());
            ra.addFlashAttribute("message", patient.getPatientID() + " ID sėkmingai ištrintas");
        } catch (PatientNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return new ModelAndView("redirect:/pacientai");
    }
}
