package com.example.webapp.controller;

import com.example.webapp.entity.SponsorEntity;
import com.example.webapp.entity.SponsorsTSTournamentEntity;
import com.example.webapp.entity.TournamentEntity;
import com.example.webapp.model.Sponsor;
import com.example.webapp.service.SponsorService;
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
public class SponsorController {

    @Autowired
    SponsorService sponsorService;

    @RequestMapping("/sponsor")
    public String getSponsors(Model model){
        Iterable<SponsorEntity> sponsors = sponsorService.getSponsorRepo().findAll();
        model.addAttribute("sponsors", sponsors);

        return "sponsors/sponsor";
    }

    @RequestMapping("/sponsor/create")
    public String getCreateSponsor(Model model){
        return "/sponsors/create-sponsor";
    }

    @PostMapping("/sponsor/create")
    public String createSponsor(@Valid Sponsor sponsor, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){
        String referer = request.getHeader("Referer");
        if (sponsorService.createSponsorCheck(sponsor) != null){
            redirectAttributes.addFlashAttribute("wrongData", sponsorService.createSponsorCheck(sponsor));
            return "redirect:" + referer;
        }
        SponsorEntity sponsorEntity = new SponsorEntity(sponsor.getName(), sponsor.getSpecialStatus());
        sponsorService.getSponsorRepo().save(sponsorEntity);

        return "redirect:/sponsor";
    }

    @RequestMapping("/sponsor/edit")
    public String getEditSponsor(@Valid Sponsor sponsor, Model model){
        model.addAttribute("id", sponsor.getId());
        model.addAttribute("name", sponsor.getName());
        model.addAttribute("specialStatus", sponsor.getSpecialStatus());

        return "/sponsors/edit-sponsor";
    }

    @PostMapping("/sponsor/edit")
    public String editSponsor(@Valid Sponsor sponsor, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){
        String referer = request.getHeader("Referer");
        if (sponsorService.editSponsorCheck(sponsor) != null){
            redirectAttributes.addFlashAttribute("wrongData", sponsorService.editSponsorCheck(sponsor));
            return "redirect:" + referer;
        }
        SponsorEntity sponsorEntity = sponsorService.getSponsorRepo().findById(sponsor.getId()).get();
        SponsorEntity returnEntity = new SponsorEntity(sponsor.getName(), sponsor.getSpecialStatus());
        BeanUtils.copyProperties(returnEntity, sponsorEntity, "id");
        sponsorService.getSponsorRepo().save(sponsorEntity);

        return "redirect:/sponsor";
    }

    @RequestMapping("/sponsor/details")
    public String getSponsorDetails(@Valid Sponsor sponsor, Model model){
        Iterable<SponsorsTSTournamentEntity> sponsorsTSTournaments =
                sponsorService.getSponsorsTSTournamentsRepo().findAllBySponsorId(sponsor.getId());

        List<TournamentEntity> tournaments = new ArrayList<>();
        for (SponsorsTSTournamentEntity sponsorsTSTournamentEntity : sponsorsTSTournaments) {
            tournaments.add(sponsorsTSTournamentEntity.getTournament());
        }
        Collections.sort(tournaments, (o1, o2) -> o1.getYear() - o2.getYear());
        for (TournamentEntity tournament : tournaments) {
            int count = sponsorService.getResultRepo().countAllByTournamentId(tournament.getId());
            tournament.setNumbOfParticipants(count);

            String tournamentSponsors = "";
            Iterable<SponsorsTSTournamentEntity> sponsors = sponsorService.getSponsorsTSTournamentRepo().findAllByTournamentId(tournament.getId());
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
        sponsorService.getTournamentRepo().saveAll(tournaments);

        model.addAttribute("name", sponsor.getName());
        model.addAttribute("specialStatus", sponsor.getSpecialStatus());
        model.addAttribute("tournaments", tournaments);

        return "/sponsors/details-sponsor";
    }

    @RequestMapping("/sponsor/delete")
    public String getDeleteSponsor(@Valid Sponsor sponsor, Model model){

        model.addAttribute("id", sponsor.getId());
        model.addAttribute("name", sponsor.getName());

        return "/sponsors/delete-sponsor";
    }

    @Transactional
    @PostMapping("/sponsor/delete")
    public String deleteSponsor(@Valid Sponsor sponsor, Model model, RedirectAttributes redirectAttributes){

        SponsorEntity sponsorEntity = sponsorService.getSponsorRepo().findById(sponsor.getId()).get();
        redirectAttributes.addFlashAttribute("deleted", "Спонсора з назвою " + sponsorEntity.getName() + " було видалено.");
        sponsorService.getSponsorsTSTournamentsRepo().deleteAllBySponsor_Id(sponsor.getId());
        sponsorService.getSponsorRepo().deleteById(sponsor.getId());

        return "redirect:/sponsor";
    }
}
