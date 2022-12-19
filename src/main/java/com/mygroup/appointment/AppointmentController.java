package com.mygroup.appointment;

import com.mygroup.appointment.Appointment;
import com.mygroup.appointment.AppointmentRepository;
import com.mygroup.doctor.Doctor;
import com.mygroup.doctor.DoctorNotFoundException;
import com.mygroup.doctor.DoctorRepository;
import com.mygroup.doctor.DoctorService;
import com.mygroup.patient.Patient;
import com.mygroup.patient.PatientRepository;
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
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private DoctorService doctorSevice;
    @Autowired
    private PatientService patientService;

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

    @PostMapping("/apsilankymai/issaugoti")
    public String saveAppointment(@Valid Appointment appointment, BindingResult bindingResult, RedirectAttributes ra){
        if (bindingResult.hasErrors()) {
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
