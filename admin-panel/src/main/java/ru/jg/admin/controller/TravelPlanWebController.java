package ru.jg.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.jg.admin.dto.TravelPlanResponseDto;
import ru.jg.admin.service.TravelPlanClientService;

@Slf4j
@Controller
@RequestMapping("/travel-plans")
public class TravelPlanWebController {

    private TravelPlanClientService travelPlanClientService;

    public TravelPlanWebController(TravelPlanClientService travelPlanClientService) {
        this.travelPlanClientService = travelPlanClientService;
    }

    @GetMapping
    public String showForm(Model model ) {
        model.addAttribute("travelPlan", new TravelPlanResponseDto());
        return "travel-plan-form";
    }

    @PostMapping
    public String getTravelPlan(@RequestParam("id") Long id, Model model) {
        TravelPlanResponseDto response = travelPlanClientService.getTravelPlanById(id);
        model.addAttribute("travelPlan", response);
        return "travel-plan-result";
    }
}