package com.example.webapp.controller;

import com.example.webapp.service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping("/upload")
public class ImportController {
    @Autowired
    private ImportService importService;

    @PostMapping("/country")
    public String uploadCountry(@RequestParam("file") MultipartFile reapExcelDataFile,
                              HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {

        String referer = request.getHeader("Referer");
        String check = importService.importCountry(reapExcelDataFile);
        if (check != null){
            redirectAttributes.addFlashAttribute("wrongData", check);
            return "redirect:" + referer;
        }
        return "redirect:" + referer;
    }

    @PostMapping("/sponsor")
    public String uploadSponsor(@RequestParam("file") MultipartFile reapExcelDataFile,
                              HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {

        String referer = request.getHeader("Referer");
        String check = importService.importSponsor(reapExcelDataFile);
        if (check != null){
            redirectAttributes.addFlashAttribute("wrongData", check);
            return "redirect:" + referer;
        }
        return "redirect:" + referer;
    }

    @PostMapping("/tournament")
    public String uploadTournament(@RequestParam("file") MultipartFile reapExcelDataFile,
                               HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {

        String referer = request.getHeader("Referer");
        String check = importService.importTournament(reapExcelDataFile);
        if (check != null){
            redirectAttributes.addFlashAttribute("wrongData", check);
            return "redirect:" + referer;
        }
        return "redirect:" + referer;
    }

    @PostMapping("/participant")
    public String uploadParticipant(@RequestParam("file") MultipartFile reapExcelDataFile,
                             HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {

        String referer = request.getHeader("Referer");
        String check = importService.importParticipant(reapExcelDataFile);
        if (check != null){
            redirectAttributes.addFlashAttribute("wrongData", check);
            return "redirect:" + referer;
        }

        return "redirect:" + referer;
    }

    @PostMapping("/result")
    public String uploadResult(@RequestParam("file") MultipartFile reapExcelDataFile,
                              HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {

        String referer = request.getHeader("Referer");
        String check = importService.importResult(reapExcelDataFile);
        if (check != null){
            redirectAttributes.addFlashAttribute("wrongData", check);
            return "redirect:" + referer;
        }

        return "redirect:" + referer;
    }

    @PostMapping("/sponsorTSTournament")
    public String uploadSponsorsTSTournament(@RequestParam("file") MultipartFile reapExcelDataFile,
                               HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {

        String referer = request.getHeader("Referer");
        String check = importService.importSponsorsTSTournament(reapExcelDataFile);
        if (check != null){
            redirectAttributes.addFlashAttribute("wrongData", check);
            return "redirect:" + referer;
        }

        return "redirect:" + referer;
    }
}
