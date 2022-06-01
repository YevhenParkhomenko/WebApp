package com.example.webapp.controller;

import com.example.webapp.entity.CountryEntity;
import com.example.webapp.entity.ParticipantEntity;
import com.example.webapp.entity.SponsorsTSTournamentEntity;
import com.example.webapp.entity.TournamentEntity;
import com.example.webapp.model.Country;
import com.example.webapp.model.Participant;
import com.example.webapp.model.Sponsor;
import com.example.webapp.service.CountryService;
import com.example.webapp.service.GoogleChartsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class CountryController {

    @Autowired
    CountryService countryService;

    @Autowired
    private GoogleChartsService gcs;

    @RequestMapping("/country")
    public String getCountries(Model model){
        Iterable<CountryEntity> countries = countryService.getCountryRepo().findAll();
        model.addAttribute("countries", countries);
        model.addAttribute("countriesData", gcs.getParticipantCountryMap(countries));

        return "countries/country";
    }

    @RequestMapping("/country/create")
    public String getCreateCountry(Model model){
        return "/countries/create-country";
    }

    @PostMapping("/country/create")
    public String createCountry(@Valid Country country, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){
        String referer = request.getHeader("Referer");
        if (countryService.createCountryCheck(country) != null){
            redirectAttributes.addFlashAttribute("wrongData", countryService.createCountryCheck(country));
            return "redirect:" + referer;
        }
        CountryEntity countryEntity = new CountryEntity(country.getName());
        countryService.getCountryRepo().save(countryEntity);

        return "redirect:/country";
    }

    @RequestMapping("/country/edit")
    public String getEditCountry(@Valid Country country, Model model){
        model.addAttribute("id", country.getId());
        model.addAttribute("name", country.getName());

        return "/countries/edit-country";
    }

    @PostMapping("/country/edit")
    public String editCountry(@Valid Country country, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){
        String referer = request.getHeader("Referer");
        if (countryService.editCountryCheck(country) != null){
            redirectAttributes.addFlashAttribute("wrongData", countryService.editCountryCheck(country));
            return "redirect:" + referer;
        }
        CountryEntity countryEntity = countryService.getCountryRepo().findById(country.getId()).get();
        CountryEntity returnEntity = new CountryEntity(country.getName());
        BeanUtils.copyProperties(returnEntity, countryEntity, "id");
        countryService.getCountryRepo().save(countryEntity);

        return "redirect:/country";
    }

    @RequestMapping("/country/details")
    public String getCountryDetails(@Valid Country country, Model model){
        Sort sort = Sort.by(
                Sort.Order.desc("rating"));
        List<ParticipantEntity> participants =
                countryService.getParticipantRepo().findAllByCountryId(country.getId(), sort);

        model.addAttribute("name", country.getName());
        model.addAttribute("participants", participants);

        return "/countries/details-country";
    }

    @RequestMapping("/country/delete")
    public String getDeleteCountry(@Valid Country country, Model model){

        model.addAttribute("id", country.getId());
        model.addAttribute("name", country.getName());

        return "/countries/delete-country";
    }

    @Transactional
    @PostMapping("/country/delete")
    public String deleteCountry(@Valid Country country, Model model, RedirectAttributes redirectAttributes){

        CountryEntity countryEntity = countryService.getCountryRepo().findById(country.getId()).get();
        redirectAttributes.addFlashAttribute("deleted", "Країну з назвою " + countryEntity.getName() + " було видалено.");
        Iterable<ParticipantEntity> participants = countryService.getParticipantRepo().findAllByCountryId(country.getId());
        for (ParticipantEntity participant: participants) {
            countryService.getResultRepo().deleteAllByParticipant_Id(participant.getId());
        }
        countryService.getParticipantRepo().deleteAllByCountryId(country.getId());
        countryService.getCountryRepo().deleteById(country.getId());

        return "redirect:/country";
    }
}
