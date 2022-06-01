package com.example.webapp.controller;

import com.example.webapp.entity.ParticipantEntity;
import com.example.webapp.entity.ResultEntity;
import com.example.webapp.entity.TournamentEntity;
import com.example.webapp.model.Result;
import com.example.webapp.model.Tournament;
import com.example.webapp.service.ResultService;
import com.example.webapp.service.TournamentService;
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

@Controller
public class ResultController {
    @Autowired
    ResultService resultService;

    @RequestMapping("/result")
    public String getResults(Model model){
        Sort sort = Sort.by(
                Sort.Order.asc("tournament.year"),
                Sort.Order.desc("points"),
                Sort.Order.desc("performance"));
        Iterable<ResultEntity> results = resultService.getResultRepo().findAll(sort);
        int year = 0;
        int i = 1;
        for (ResultEntity result: results) {
            if (year != result.getTournament().getYear()){
                year = result.getTournament().getYear();
                i = 1;
            }
            result.setPlace(i);
            i++;
        }
        resultService.getResultRepo().saveAll(results);

        model.addAttribute("results", results);

        return "results/result";
    }

    @RequestMapping("/result/create")
    public String getCreateResult(Model model){
        Iterable<TournamentEntity> tournaments = resultService.getTournamentRepo().findAll();
        Iterable<ParticipantEntity> participants = resultService.getParticipantRepo().findAll();

        model.addAttribute("tournaments", tournaments);
        model.addAttribute("participants", participants);

        return "/results/create-result";
    }

    @PostMapping("/result/create")
    public String createResult(@Valid Result result, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){
        String referer = request.getHeader("Referer");
        if (resultService.createResultCheck(result) != null){
            redirectAttributes.addFlashAttribute("wrongData", resultService.createResultCheck(result));
            return "redirect:" + referer;
        }
        ResultEntity resultEntity = new ResultEntity(result.getTournament(), result.getParticipant(), result.getPlace(), result.getPoints(), result.getPerformance());
        resultService.getResultRepo().save(resultEntity);

        return "redirect:/result";
    }

    @RequestMapping("/result/edit")
    public String getEditResult(@Valid Result result, Model model){
        Iterable<TournamentEntity> tournaments = resultService.getTournamentRepo().findAll();
        Iterable<ParticipantEntity> participants = resultService.getParticipantRepo().findAll();

        model.addAttribute("id", result.getId());
        model.addAttribute("tournaments", tournaments);
        model.addAttribute("participants", participants);
        model.addAttribute("points", result.getPoints());
        model.addAttribute("performance", result.getPerformance());

        return "/results/edit-result";
    }

    @PostMapping("/result/edit")
    public String editResult(@Valid Result result, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){
        String referer = request.getHeader("Referer");
        if (resultService.editResultCheck(result) != null){
            redirectAttributes.addFlashAttribute("wrongData", resultService.editResultCheck(result));
            return "redirect:" + referer;
        }
        ResultEntity resultEntity = resultService.getResultRepo().findById(result.getId()).get();
        ResultEntity returnEntity = new ResultEntity(result.getTournament(), result.getParticipant(), result.getPlace(), result.getPoints(), result.getPerformance());
        BeanUtils.copyProperties(returnEntity, resultEntity, "id");
        resultService.getResultRepo().save(resultEntity);

        return "redirect:/result";
    }

    @RequestMapping("/result/delete")
    public String getDeleteResult(@Valid Result result, Model model){

        model.addAttribute("id", result.getId());
        model.addAttribute("name", result.getParticipant().getName());
        model.addAttribute("points", result.getPoints());
        model.addAttribute("place", result.getPlace());
        model.addAttribute("year", result.getTournament().getYear());

        return "/results/delete-result";
    }

    @Transactional
    @PostMapping("/result/delete")
    public String deleteResult(@Valid Result result, Model model, RedirectAttributes redirectAttributes){

        ResultEntity resultEntity = resultService.getResultRepo().findById(result.getId()).get();
        redirectAttributes.addFlashAttribute("deleted", "Результат гравця " + resultEntity.getParticipant().getName() + " у турнірі " + resultEntity.getTournament().getYear() + " року було видалено.");
        resultService.getResultRepo().deleteById(result.getId());

        return "redirect:/result";
    }
}
