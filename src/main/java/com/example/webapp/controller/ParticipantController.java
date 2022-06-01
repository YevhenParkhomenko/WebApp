package com.example.webapp.controller;

import com.example.webapp.entity.*;
import com.example.webapp.model.Country;
import com.example.webapp.model.Participant;
import com.example.webapp.model.Tournament;
import com.example.webapp.service.ParticipantService;
import com.example.webapp.service.ResultService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
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
import java.util.*;

@Controller
public class ParticipantController {
    @Autowired
    ParticipantService participantService;

    @RequestMapping("/participant")
    public String getParticipants(Model model){
        Sort sort = Sort.by(
                Sort.Order.desc("rating"));
        Iterable<ParticipantEntity> participants = participantService.getParticipantRepo().findAll(sort);

        model.addAttribute("participants", participants);

        return "participants/participant";
    }

    @RequestMapping("/participant/create")
    public String getCreateParticipant(Model model){

        Iterable<CountryEntity> countries = participantService.getCountryRepo().findAll();
        Calendar c = Calendar.getInstance();
        int maxYear = c.get(Calendar.YEAR) - 18;
        System.out.println(maxYear);

        model.addAttribute("countries", countries);
        model.addAttribute("maxYear", maxYear);

        return "/participants/create-participant";
    }

    @PostMapping("/participant/create")
    public String createParticipant(@Valid Participant participant, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){

        String referer = request.getHeader("Referer");
        if (participantService.createParticipantCheck(participant) != null){
            redirectAttributes.addFlashAttribute("wrongData", participantService.createParticipantCheck(participant));
            return "redirect:" + referer;
        }

        ParticipantEntity participantEntity = new ParticipantEntity(participant.getName(), participant.getbYear(), participant.getRating(), participant.getCountry(), participant.getLichessName());
        participantService.getParticipantRepo().save(participantEntity);

        return "redirect:/participant";
    }

    @RequestMapping("/participant/edit")
    public String getEditParticipant(@Valid Participant participant, Model model){

        Iterable<CountryEntity> countries = participantService.getCountryRepo().findAll();

        Calendar c = Calendar.getInstance();
        int maxYear = c.get(Calendar.YEAR) - 18;

        model.addAttribute("maxYear", maxYear);
        model.addAttribute("countries", countries);
        model.addAttribute("id", participant.getId());
        model.addAttribute("name", participant.getName());
        model.addAttribute("bYear", participant.getbYear());
        model.addAttribute("rating", participant.getRating());
        model.addAttribute("country", participant.getCountry());
        model.addAttribute("lichessName", participant.getLichessName());

        return "/participants/edit-participant";
    }

    @PostMapping("/participant/edit")
    public String editParticipant(@Valid Participant participant, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){
        String referer = request.getHeader("Referer");
        if (participantService.editParticipantCheck(participant) != null){
            redirectAttributes.addFlashAttribute("wrongData", participantService.editParticipantCheck(participant));
            return "redirect:" + referer;
        }
        ParticipantEntity participantEntity = participantService.getParticipantRepo().findById(participant.getId()).get();
        ParticipantEntity returnEntity = new ParticipantEntity(participant.getName(), participant.getbYear(), participant.getRating(), participant.getCountry(), participant.getLichessName());
        BeanUtils.copyProperties(returnEntity, participantEntity, "id");
        participantService.getParticipantRepo().save(participantEntity);

        return "redirect:/participant";
    }

    @RequestMapping("/participant/details")
    public String getParticipantDetails(@Valid Participant participant, Model model){

        List<ResultEntity> results =
                participantService.getResultRepo().findAllByParticipantId(participant.getId());

        List<TournamentEntity> tournaments = new ArrayList<>();
        for (ResultEntity result : results) {
            tournaments.add(result.getTournament());
        }
        Collections.sort(tournaments, (o1, o2) -> Integer.compare(o1.getYear(), o2.getYear()));
        for (TournamentEntity tournament : tournaments) {
            int count = participantService.getResultRepo().countAllByTournamentId(tournament.getId());
            tournament.setNumbOfParticipants(count);

            String tournamentSponsors = "";
            Iterable<SponsorsTSTournamentEntity> sponsors = participantService.getSponsorsTSTournamentRepo().findAllByTournamentId(tournament.getId());
            for (SponsorsTSTournamentEntity sponsorsTSTournament: sponsors) {
                tournamentSponsors += sponsorsTSTournament.getSponsor().getName() + ", ";
            }
            if(tournamentSponsors.length() >= 2){
                tournamentSponsors = StringUtils.chop(tournamentSponsors);
                tournamentSponsors = StringUtils.chop(tournamentSponsors);
                tournamentSponsors += ".";
            }
            tournament.setSponsors(tournamentSponsors);
        }
        participantService.getTournamentRepo().saveAll(tournaments);

        model.addAttribute("name", participant.getName());
        model.addAttribute("bYear", participant.getbYear());
        model.addAttribute("rating", participant.getRating());
        model.addAttribute("country", participant.getCountry().getName());
        model.addAttribute("lichessName", participant.getLichessName());
        model.addAttribute("tournaments", tournaments);

        return "/participants/details-participant";
    }

    @RequestMapping("/participant/delete")
    public String getDeleteParticipant(@Valid Participant participant, Model model){

        model.addAttribute("id", participant.getId());
        model.addAttribute("name", participant.getName());
        model.addAttribute("lichessName", participant.getLichessName());

        return "/participants/delete-participant";
    }

    @Transactional
    @PostMapping("/participant/delete")
    public String deleteParticipant(@Valid Participant participant, Model model, RedirectAttributes redirectAttributes){

        ParticipantEntity participantEntity = participantService.getParticipantRepo().findById(participant.getId()).get();
        redirectAttributes.addFlashAttribute("deleted", "Учасника з іменем " + participantEntity.getName() + " було видалено.");
        participantService.getResultRepo().deleteAllByParticipant_Id(participant.getId());
        participantService.getParticipantRepo().deleteById(participant.getId());

        return "redirect:/participant";
    }
}
