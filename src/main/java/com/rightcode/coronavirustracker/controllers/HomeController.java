package com.rightcode.coronavirustracker.controllers;

import com.rightcode.coronavirustracker.Services.CoronaVirusDataService;
import com.rightcode.coronavirustracker.models.DeathStats;
import com.rightcode.coronavirustracker.models.LocationStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private CoronaVirusDataService coronaVirusDataService;
    @GetMapping("/")
    public String home(Model model) {
        List<LocationStats> allstats = coronaVirusDataService.getAllStats();
        List<DeathStats> deathStats = coronaVirusDataService.getDeathStatsList();
        int totalReportedCases = allstats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
        int previousReportCases = allstats.stream().mapToInt(stat -> stat.getPreviousTotalCases()).sum();
        int totalReportedDeaths = deathStats.stream().mapToInt(deathStat -> deathStat.getLatestTotalConfirmed()).sum();
        model.addAttribute("locationStats", allstats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("previousReportCases", previousReportCases);
        model.addAttribute("totalReportedDeaths", totalReportedDeaths);
        return "home";
    }
}
