package com.example.webapp.controller;

import com.example.webapp.entity.ResultEntity;
import com.example.webapp.entity.SponsorEntity;
import com.example.webapp.entity.SponsorsTSTournamentEntity;
import com.example.webapp.entity.TournamentEntity;
import com.example.webapp.model.Participant;
import com.example.webapp.model.Sponsor;
import com.example.webapp.model.SponsorsTSTournament;
import com.example.webapp.model.Tournament;
import com.example.webapp.service.*;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class TournamentController {

    @Autowired
    SponsorsTSTournamentService sponsorsTSTournamentService;

    @Autowired
    TournamentService tournamentService;

    @Autowired
    ResultService resultService;

    @Autowired
    SponsorService sponsorService;

    @RequestMapping("/tournament")
    public String getTournaments(Model model){
        Sort sort = Sort.by(
                Sort.Order.asc("year"));
        Iterable<TournamentEntity> tournaments = tournamentService.getTournamentRepo().findAll(sort);

        for (TournamentEntity tournament : tournaments) {

            Long tournamentID = tournamentService.getTournamentRepo().findByYear(tournament.getYear()).getId();

            int count = resultService.getResultRepo().countAllByTournamentId(tournamentID);
            tournament.setNumbOfParticipants(count);

            String tournamentSponsors = "";
            Iterable<SponsorsTSTournamentEntity> sponsors = sponsorsTSTournamentService.getSponsorsTSTournamentRepo().findAllByTournamentId(tournament.getId());
            for (SponsorsTSTournamentEntity sponsor: sponsors) {
                tournamentSponsors += sponsor.getSponsor().getName() + ", ";
            }
            if(tournamentSponsors.length() >= 2){
                tournamentSponsors = StringUtils.chop(tournamentSponsors);
                tournamentSponsors = StringUtils.chop(tournamentSponsors);
                tournamentSponsors += ".";
            }
            tournament.setSponsors(tournamentSponsors);
            tournamentService.getTournamentRepo().save(tournament);
        }


        model.addAttribute("tournaments", tournaments);

        return "tournaments/tournament";
    }

    @RequestMapping("/tournament/create")
    public String getCreateTournament(Model model){
        return "/tournaments/create-tournament";
    }

    @PostMapping("/tournament/create")
    public String createSTournament(@Valid Tournament tournament, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){
        String referer = request.getHeader("Referer");
        if (tournamentService.createTournamentCheck(tournament) != null){
            redirectAttributes.addFlashAttribute("wrongData", tournamentService.createTournamentCheck(tournament));
            return "redirect:" + referer;
        }
        TournamentEntity tournamentEntity = new TournamentEntity(tournament.getYear(), tournament.getNumbOfParticipants(), tournament.getPrizePool(), "");
        tournamentService.getTournamentRepo().save(tournamentEntity);

        return "redirect:/tournament";
    }

    @RequestMapping("/tournament/edit")
    public String getEditTournament(@Valid Tournament tournament, Model model){
        model.addAttribute("id", tournament.getId());
        model.addAttribute("year", tournament.getYear());
        model.addAttribute("prizePool", tournament.getPrizePool());
        model.addAttribute("sponsors", tournament.getSponsors());

        return "/tournaments/edit-tournament";
    }

    @PostMapping("/tournament/edit")
    public String editTournament(@Valid Tournament tournament, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){
        String referer = request.getHeader("Referer");
        if (tournamentService.editTournamentCheck(tournament) != null){
            redirectAttributes.addFlashAttribute("wrongData", tournamentService.editTournamentCheck(tournament));
            return "redirect:" + referer;
        }
        TournamentEntity tournamentEntity = tournamentService.getTournamentRepo().findById(tournament.getId()).get();
        TournamentEntity returnEntity = new TournamentEntity(tournament.getYear(), tournament.getNumbOfParticipants(), tournament.getPrizePool(), tournament.getSponsors());
        BeanUtils.copyProperties(returnEntity, tournamentEntity, "id");
        tournamentService.getTournamentRepo().save(tournamentEntity);

        return "redirect:/tournament";
    }

    @RequestMapping("/tournament/add_sponsor")
    public String getAddSponsor(@Valid SponsorsTSTournament sponsorsTSTournament, Model model){
        model.addAttribute("id", sponsorsTSTournament.getTournament().getId());
        Iterable<SponsorEntity> sponsors = sponsorService.getSponsorRepo().findAll();
        model.addAttribute("sponsors", sponsors);
        return "/tournaments/add-sponsor";
    }

    @PostMapping("/tournament/add_sponsor")
    public String addSponsor(@Valid SponsorsTSTournament sponsorsTSTournament, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){
        String referer = request.getHeader("Referer");
        if (sponsorsTSTournamentService.addSponsorCheck(sponsorsTSTournament) != null){
            redirectAttributes.addFlashAttribute("wrongData", sponsorsTSTournamentService.addSponsorCheck(sponsorsTSTournament));
            return "redirect:" + referer;
        }
        SponsorsTSTournamentEntity sponsorsTSTournamentEntity = new SponsorsTSTournamentEntity(sponsorsTSTournament.getSponsor(), sponsorsTSTournament.getTournament());
        sponsorsTSTournamentService.getSponsorsTSTournamentRepo().save(sponsorsTSTournamentEntity);

        return "redirect:/tournament";
    }

    @RequestMapping("/tournament/remove_sponsor")
    public String getRemoveSponsor(@Valid SponsorsTSTournament sponsorsTSTournament, Model model){
        model.addAttribute("id", sponsorsTSTournament.getTournament().getId());
        Iterable<SponsorsTSTournamentEntity> sponsors = sponsorsTSTournamentService.getSponsorsTSTournamentRepo().findAllByTournamentId(sponsorsTSTournament.getTournament().getId());
        model.addAttribute("sponsors", sponsors);
        return "/tournaments/remove-sponsor";
    }

    @PostMapping("/tournament/remove_sponsor")
    public String removeSponsor(@Valid SponsorsTSTournament sponsorsTSTournament, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        SponsorsTSTournamentEntity sponsorsTSTournamentEntity = sponsorsTSTournamentService.getSponsorsTSTournamentRepo().findBySponsorIdAndTournamentId(sponsorsTSTournament.getSponsor().getId(), sponsorsTSTournament.getTournament().getId());
        redirectAttributes.addFlashAttribute("deleted", "Спонсора з назвою" + sponsorsTSTournamentEntity.getSponsor().getName() + " було вилучено з турніру " + sponsorsTSTournamentEntity.getTournament().getYear() + ".");
        sponsorsTSTournamentService.getSponsorsTSTournamentRepo().deleteById(sponsorsTSTournamentEntity.getId());

        return "redirect:/tournament";
    }

    @RequestMapping("/tournament/delete")
    public String getDeleteTournament(@Valid Tournament tournament, Model model){

        model.addAttribute("id", tournament.getId());
        model.addAttribute("year", tournament.getYear());

        return "/tournaments/delete-tournament";
    }

    @Transactional
    @PostMapping("/tournament/delete")
    public String deleteTournament(@Valid Tournament tournament, Model model, RedirectAttributes redirectAttributes){

        TournamentEntity tournamentEntity = tournamentService.getTournamentRepo().findById(tournament.getId()).get();
        redirectAttributes.addFlashAttribute("deleted", "Турнір " + tournamentEntity.getYear() + " року було видалено.");
        tournamentService.getSponsorsTSTournamentsRepo().deleteAllByTournament_Id(tournament.getId());
        tournamentService.getResultRepo().deleteAllByTournament_Id(tournament.getId());
        tournamentService.getTournamentRepo().deleteById(tournament.getId());

        return "redirect:/tournament";
    }

    @RequestMapping("/tournament/details")
    public String getTournamentDetails(@Valid Tournament tournament, Model model){

        Sort sort = Sort.by(
                Sort.Order.desc("points"),
                Sort.Order.desc("performance"));
        List<ResultEntity> results =
                tournamentService.getResultRepo().findAllByTournamentId(tournament.getId(), sort);

        int i = 1;
        for (ResultEntity result: results) {
            result.setPlace(i);
            i++;
            tournamentService.getResultRepo().save(result);
        }

        model.addAttribute("year", tournament.getYear());
        model.addAttribute("numbOfParticipants", tournament.getNumbOfParticipants());
        model.addAttribute("prizePool", tournament.getPrizePool());
        model.addAttribute("results", results);

        return "/tournaments/details-tournament";
    }
}
