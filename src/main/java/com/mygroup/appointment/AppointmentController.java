package com.mygroup.appointment;

import com.mygroup.doctor.Doctor;
import com.mygroup.doctor.DoctorNotFoundException;
import com.mygroup.doctor.DoctorService;
import com.mygroup.patient.Patient;
import com.mygroup.patient.PatientRepository;
import com.mygroup.patient.PatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private DoctorService doctorSevice;
    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepo;

    @GetMapping("/apsilankymai")
    public String showAppointmentList(Model model){
        List<Appointment> listAppointments = appointmentService.listAll();
        model.addAttribute("listAppointments", listAppointments);
        return "appointments";
    }

    @GetMapping("/apsilankymai/naujas")
    public String showNewAptForm(Model model){
        Iterable<Doctor> listDoctors = doctorSevice.listAll();
        Iterable<Patient> listPatients = patientService.listAll();
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("listDoctors", listDoctors);
        model.addAttribute("listPatients", listPatients);
        model.addAttribute("pageTitle", "Pridėti naują apsilankymą");
        return "appointment_form";
    }

    @GetMapping("/apsilankymai/naujas/{doctorID}")
    public String showNewUserAptForm(@PathVariable("doctorID") Integer doctorID, Model model,
                                     @AuthenticationPrincipal UserDetails currenUser) throws DoctorNotFoundException {
        Doctor doctor = doctorSevice.get(doctorID);
        Patient patient = (Patient) patientRepo.findPatientsByEmail(currenUser.getUsername());
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("patient", patient);
        model.addAttribute("doctor", doctor);
        model.addAttribute("pageTitle", "Pridėti naują apsilankymą");
        return "appointment_form";
    }

    @PostMapping("/apsilankymai/issaugoti")
    public String saveAppointment(@Valid Appointment appointment, BindingResult bindingResult, Model model, RedirectAttributes ra){
        if (bindingResult.hasErrors()) {
            List<Doctor> listDoctors = doctorSevice.listAll();
            List<Patient> listPatients = patientService.listAll();

            model.addAttribute("listDoctors", listDoctors);
            model.addAttribute("listPatients", listPatients);
            return "appointment_form";
        }
        else {
            appointmentService.save(appointment);
            ra.addFlashAttribute("message", "Apsilankymas sėkmingai išsaugotas");
            return "redirect:/apsilankymai";
        }
    }

    @GetMapping("/apsilankymai/redaguoti/{aptID}")
    public String showEditForm(@PathVariable("aptID") Integer aptID, Model model, RedirectAttributes ra){
        try {
            Appointment appointment = appointmentService.get(aptID);
            model.addAttribute("appointment", appointment);

            List<Doctor> listDoctors = doctorSevice.listAll();
            List<Patient> listPatients = patientService.listAll();

            model.addAttribute("listDoctors", listDoctors);
            model.addAttribute("listPatients", listPatients);
            model.addAttribute("pageTitle", "Redaguoti apsilankyma (ID: " + aptID + ")");
            return "appointment_form";
        } catch (AppointmentNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/apsilankymai";
        }
    }

    @GetMapping("/apsilankymai/istrinti/{aptID}")
    public String deleteAppointment(@PathVariable("aptID") Integer aptID, RedirectAttributes ra){
        try {
            appointmentService.delete(aptID);
            ra.addFlashAttribute("message", aptID + " ID sėkmingai ištrintas");
        } catch (AppointmentNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/apsilankymai";
    }

}
