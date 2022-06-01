package com.example.webapp.controller;

import com.example.webapp.entity.*;
import com.example.webapp.export.*;
import com.example.webapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class ExportController {
    @Autowired
    private CountryService countryService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private ResultService resultService;

    @Autowired
    private SponsorService sponsorService;

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private SponsorsTSTournamentService sponsorsTSTournamentService;

    @RequestMapping("/export/country")
    public void getCountryData(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=CoutriesData" + ".xlsx";
        response.setHeader(headerKey, headerValue);
        Iterable<CountryEntity> countryEntities = countryService.getCountryRepo().findAll();
        List<CountryEntity> countryEntityList = new ArrayList<>();
        for (CountryEntity countryEntity : countryEntities){
            countryEntityList.add(countryEntity);
        }
        CountryDataExcelExporter excelExporter = new CountryDataExcelExporter(countryEntityList);
        excelExporter.export(response);
    }

    @RequestMapping("/export/sponsor")
    public void getSponsorData(HttpServletResponse response) throws IOException{
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=SponsorData" + ".xlsx";
        response.setHeader(headerKey, headerValue);
        Iterable<SponsorEntity> sponsorEntities = sponsorService.getSponsorRepo().findAll();
        List<SponsorEntity> sponsorEntityList = new ArrayList<>();
        for (SponsorEntity sponsorEntity : sponsorEntities){
            sponsorEntityList.add(sponsorEntity);
        }
        SponsorDataExcelExporter excelExporter = new SponsorDataExcelExporter(sponsorEntityList);
        excelExporter.export(response);
    }

    @RequestMapping("/export/tournament")
    public void getTournamentData(HttpServletResponse response) throws IOException{
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=TournamentsData" + ".xlsx";
        response.setHeader(headerKey, headerValue);
        Iterable<TournamentEntity> tournamentEntities = tournamentService.getTournamentRepo().findAll();
        List<TournamentEntity> tournamentEntityList = new ArrayList<>();
        for (TournamentEntity tournamentEntity : tournamentEntities){
            tournamentEntityList.add(tournamentEntity);
        }
        TournamentDataExcelExporter excelExporter = new TournamentDataExcelExporter(tournamentEntityList);
        excelExporter.export(response);
    }

    @RequestMapping("/export/participant")
    public void getParticipantData(HttpServletResponse response) throws IOException{
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=ParticipantData" + ".xlsx";
        response.setHeader(headerKey, headerValue);
        Iterable<ParticipantEntity> participantEntities = participantService.getParticipantRepo().findAll();
        List<ParticipantEntity> participantEntityList = new ArrayList<>();
        for (ParticipantEntity participantEntity : participantEntities){
            participantEntityList.add(participantEntity);
        }
        ParticipantDataExcelExporter excelExporter = new ParticipantDataExcelExporter(participantEntityList);
        excelExporter.export(response);
    }

    @RequestMapping("/export/result")
    public void getResultData(HttpServletResponse response) throws IOException{
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=ResultData" + ".xlsx";
        response.setHeader(headerKey, headerValue);
        Iterable<ResultEntity> resultEntities = resultService.getResultRepo().findAll();
        List<ResultEntity> resultEntityList = new ArrayList<>();
        for (ResultEntity resultEntity : resultEntities){
            resultEntityList.add(resultEntity);
        }
        ResultDataExcelExporter excelExporter = new ResultDataExcelExporter(resultEntityList);
        excelExporter.export(response);
    }

    @RequestMapping("/export/sponsorTSTournament")
    public void getSponsorTSTournamentData(HttpServletResponse response) throws IOException{
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=SponsorTSTournamentData" + ".xlsx";
        response.setHeader(headerKey, headerValue);
        Iterable<SponsorsTSTournamentEntity> sponsorTSTournamentEntities = sponsorsTSTournamentService.getSponsorsTSTournamentRepo().findAll();
        List<SponsorsTSTournamentEntity> sponsorTSTournamentEntityList = new ArrayList<>();
        for (SponsorsTSTournamentEntity sponsorTSTournamentEntity : sponsorTSTournamentEntities){
            sponsorTSTournamentEntityList.add(sponsorTSTournamentEntity);
        }
        SponsorsTSTournamentDataExcelExporter excelExporter = new SponsorsTSTournamentDataExcelExporter(sponsorTSTournamentEntityList);
        excelExporter.export(response);
    }
}
