package com.example.webapp.service;

import com.example.webapp.entity.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImportService {
    private final int COUNTRY_COLUMNS = 2;
    private final int PARTICIPANT_COLUMNS = 6;
    private final int RESULT_COLUMNS = 6;
    private final int SPONSOR_COLUMNS = 3;
    private final int TOURNAMENT_COLUMNS = 5;
    private final int SPONSORSTSTOURNAMENT_COLUMNS = 3;

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

    private String checkEmpty(MultipartFile reapExcelDataFile){
        if (reapExcelDataFile.isEmpty()){
            return "Тут нічого нема!";
        }
        return null;
    }

    private String checkCells(int numberOfCells, int needed){
        if (numberOfCells != needed){
            return "Неправильна кількість колонок!";
        }
        return null;
    }

    public String importCountry(MultipartFile reapExcelDataFile) throws IOException {

        if (checkEmpty(reapExcelDataFile) != null){
            return checkEmpty(reapExcelDataFile);
        }

        List<CountryEntity> tempCountryList = new ArrayList<>();
        XSSFWorkbook excelWorkbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet excelSheet = excelWorkbook.getSheetAt(0);

        int numberOfCells = excelSheet.getRow(0).getPhysicalNumberOfCells();
        if (checkCells(numberOfCells, COUNTRY_COLUMNS) != null){
            return checkCells(numberOfCells, COUNTRY_COLUMNS);
        }

        for(int i = 1; i < excelSheet.getPhysicalNumberOfRows(); i++) {
            CountryEntity tempCountry = new CountryEntity();

            XSSFRow row = excelSheet.getRow(i);

            row.getCell(1).setCellType(CellType.STRING);
            tempCountry.setName(row.getCell(1).getStringCellValue());

            if (countryService.createCountryCheck(tempCountry) == null){
                tempCountryList.add(tempCountry);
            }
        }
        for (int i = 0; i < tempCountryList.size(); i++) {
            countryService.getCountryRepo().save(tempCountryList.get(i));
        }

        return null;
    }

    public String importSponsor(MultipartFile reapExcelDataFile) throws IOException {

        if (checkEmpty(reapExcelDataFile) != null){
            return checkEmpty(reapExcelDataFile);
        }

        List<SponsorEntity> tempSponsorList = new ArrayList<>();
        XSSFWorkbook excelWorkbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet excelSheet = excelWorkbook.getSheetAt(0);

        int numberOfCells = excelSheet.getRow(0).getPhysicalNumberOfCells();
        if (checkCells(numberOfCells, SPONSOR_COLUMNS) != null){
            return checkCells(numberOfCells, SPONSOR_COLUMNS);
        }

        for(int i = 1; i < excelSheet.getPhysicalNumberOfRows(); i++) {
            SponsorEntity tempSponsor = new SponsorEntity();

            XSSFRow row = excelSheet.getRow(i);

            row.getCell(1).setCellType(CellType.STRING);
            tempSponsor.setName(row.getCell(1).getStringCellValue());
            row.getCell(2).setCellType(CellType.STRING);
            tempSponsor.setSpecialStatus(row.getCell(2).getStringCellValue());

            if (sponsorService.createSponsorCheck(tempSponsor) == null){
                tempSponsorList.add(tempSponsor);
            }
        }
        for (int i = 0; i < tempSponsorList.size(); i++) {
            sponsorService.getSponsorRepo().save(tempSponsorList.get(i));
        }

        return null;
    }

    public String importTournament(MultipartFile reapExcelDataFile) throws IOException {

        if (checkEmpty(reapExcelDataFile) != null){
            return checkEmpty(reapExcelDataFile);
        }

        List<TournamentEntity> tempTournamentList = new ArrayList<>();
        XSSFWorkbook excelWorkbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet excelSheet = excelWorkbook.getSheetAt(0);

        int numberOfCells = excelSheet.getRow(0).getPhysicalNumberOfCells();
        if (checkCells(numberOfCells, TOURNAMENT_COLUMNS) != null){
            return checkCells(numberOfCells, TOURNAMENT_COLUMNS);
        }

        for(int i = 1; i < excelSheet.getPhysicalNumberOfRows(); i++) {
            TournamentEntity tempTournament = new TournamentEntity();

            XSSFRow row = excelSheet.getRow(i);

            tempTournament.setYear((int) row.getCell(1).getNumericCellValue());
            tempTournament.setNumbOfParticipants((int) row.getCell(2).getNumericCellValue());
            tempTournament.setPrizePool((int) row.getCell(3).getNumericCellValue());
            row.getCell(4).setCellType(CellType.STRING);
            tempTournament.setSponsors(row.getCell(4).getStringCellValue());

            if (tournamentService.createTournamentCheck(tempTournament) == null){
                tempTournamentList.add(tempTournament);
            }
        }
        for (int i = 0; i < tempTournamentList.size(); i++) {
            tournamentService.getTournamentRepo().save(tempTournamentList.get(i));
        }

        return null;
    }

    public String importParticipant(MultipartFile reapExcelDataFile) throws IOException {

        if (checkEmpty(reapExcelDataFile) != null){
            return checkEmpty(reapExcelDataFile);
        }

        List<ParticipantEntity> tempParticipantList = new ArrayList<>();
        XSSFWorkbook excelWorkbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet excelSheet = excelWorkbook.getSheetAt(0);

        int numberOfCells = excelSheet.getRow(0).getPhysicalNumberOfCells();
        if (checkCells(numberOfCells, PARTICIPANT_COLUMNS) != null){
            return checkCells(numberOfCells, PARTICIPANT_COLUMNS);
        }

        for(int i = 1; i < excelSheet.getPhysicalNumberOfRows(); i++) {
            ParticipantEntity tempParticipant = new ParticipantEntity();

            XSSFRow row = excelSheet.getRow(i);

            row.getCell(1).setCellType(CellType.STRING);
            tempParticipant.setName(row.getCell(1).getStringCellValue());
            tempParticipant.setbYear((int) row.getCell(2).getNumericCellValue());
            tempParticipant.setRating((int) row.getCell(3).getNumericCellValue());
            row.getCell(4).setCellType(CellType.STRING);
            tempParticipant.setCountry(countryService.getCountryRepo().findByName(row.getCell(4).getStringCellValue()));
            row.getCell(5).setCellType(CellType.STRING);
            tempParticipant.setLichessName(row.getCell(5).getStringCellValue());

            if (participantService.createParticipantCheck(tempParticipant) == null){
                tempParticipantList.add(tempParticipant);
            }
        }
        for (int i = 0; i < tempParticipantList.size(); i++) {
            participantService.getParticipantRepo().save(tempParticipantList.get(i));
        }

        return null;
    }

    public String importResult(MultipartFile reapExcelDataFile) throws IOException {

        if (checkEmpty(reapExcelDataFile) != null){
            return checkEmpty(reapExcelDataFile);
        }

        List<ResultEntity> tempResultList = new ArrayList<>();
        XSSFWorkbook excelWorkbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet excelSheet = excelWorkbook.getSheetAt(0);

        int numberOfCells = excelSheet.getRow(0).getPhysicalNumberOfCells();
        if (checkCells(numberOfCells, RESULT_COLUMNS) != null){
            return checkCells(numberOfCells, RESULT_COLUMNS);
        }

        for(int i = 1; i < excelSheet.getPhysicalNumberOfRows(); i++) {
            ResultEntity tempResult = new ResultEntity();

            XSSFRow row = excelSheet.getRow(i);

            tempResult.setTournament(tournamentService.getTournamentRepo().findByYear((int) row.getCell(1).getNumericCellValue()));
            row.getCell(2).setCellType(CellType.STRING);
            tempResult.setParticipant(participantService.getParticipantRepo().findByLichessName(row.getCell(2).getStringCellValue()));
            tempResult.setPlace((int) row.getCell(3).getNumericCellValue());
            tempResult.setPoints((int) row.getCell(4).getNumericCellValue());
            tempResult.setPerformance((int) row.getCell(5).getNumericCellValue());

            if (resultService.createResultCheck(tempResult) == null){
                tempResultList.add(tempResult);
            }
        }
        for (int i = 0; i < tempResultList.size(); i++) {
            resultService.getResultRepo().save(tempResultList.get(i));
        }

        return null;
    }

    public String importSponsorsTSTournament(MultipartFile reapExcelDataFile) throws IOException {

        if (checkEmpty(reapExcelDataFile) != null){
            return checkEmpty(reapExcelDataFile);
        }

        List<SponsorsTSTournamentEntity> tempSponsorsTSTournamentList = new ArrayList<>();
        XSSFWorkbook excelWorkbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet excelSheet = excelWorkbook.getSheetAt(0);

        int numberOfCells = excelSheet.getRow(0).getPhysicalNumberOfCells();
        if (checkCells(numberOfCells, SPONSORSTSTOURNAMENT_COLUMNS) != null){
            return checkCells(numberOfCells, SPONSORSTSTOURNAMENT_COLUMNS);
        }

        for(int i = 1; i < excelSheet.getPhysicalNumberOfRows(); i++) {
            SponsorsTSTournamentEntity tempSponsorsTSTournament = new SponsorsTSTournamentEntity();

            XSSFRow row = excelSheet.getRow(i);

            row.getCell(1).setCellType(CellType.STRING);
            tempSponsorsTSTournament.setSponsor(sponsorService.getSponsorRepo().findByName(row.getCell(1).getStringCellValue()));
            tempSponsorsTSTournament.setTournament(tournamentService.getTournamentRepo().findByYear((int) row.getCell(2).getNumericCellValue()));

            if (sponsorsTSTournamentService.addSponsorCheck(tempSponsorsTSTournament) == null){
                tempSponsorsTSTournamentList.add(tempSponsorsTSTournament);
            }
        }
        for (int i = 0; i < tempSponsorsTSTournamentList.size(); i++) {
            sponsorsTSTournamentService.getSponsorsTSTournamentRepo().save(tempSponsorsTSTournamentList.get(i));
        }

        return null;
    }
}
