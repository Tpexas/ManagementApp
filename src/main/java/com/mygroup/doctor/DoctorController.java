package com.mygroup.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Controller
public class DoctorController {
    @Autowired
    private DoctorService service;
    @Autowired
    private DoctorRepository repo;
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
    public String saveDoctor(@Valid Doctor doctor, BindingResult bindingResult, RedirectAttributes ra,
                             @RequestParam("image") MultipartFile multipartFile) throws IOException, SQLIntegrityConstraintViolationException {

            if (bindingResult.hasErrors()) {
                return "doctor_form";
            }
            else if(multipartFile.isEmpty()){
                service.save(doctor);
                ra.addFlashAttribute("message", "Gydytojas sėkmingai išsaugotas");
                return "redirect:/gydytojai";
            }
            else {
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                doctor.setPicture(fileName);
                service.save(doctor);
                String uploadDir = "./doctor-photos/" + doctor.getDoctorID();

                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                try (InputStream inputStream = multipartFile.getInputStream()) {
                    Path filePath = uploadPath.resolve(fileName);
                    System.out.println(filePath.toFile().getAbsolutePath());

                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    throw new IOException("Nepavyko įkelti failo: " + fileName);
                }

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
