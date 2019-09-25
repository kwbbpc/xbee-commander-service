package com.broadway.has;

import com.broadway.has.messaging.WateringRequest;
import com.broadway.has.sensor.watering.WateringExecutor;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;


@Controller
public class SimpleController{

    @Value("$spring.application.name}")
    String appName;


    @Autowired
    private WateringExecutor wateringExecutor;


    @GetMapping("/")
    public String homePage(Model model){
        model.addAttribute("appName", appName);
        return "home";
    }

    /**
     * Takes a watering request and executes it immediately for the provided valves.  Execution order and timing is determined
     * at the hardware level.
     * @return
     */
    @PostMapping("/commands/water")
    public ResponseEntity executeWatering(
            @ApiParam(value = "Watering schedule to be executed", required = true) @Valid @RequestBody WateringRequest request){

        wateringExecutor.executeWateringRequest(request);

        return new ResponseEntity(HttpStatus.OK);

    }

    /**
     * Gets the last run commands.
     * @return
     */
    @GetMapping("/commands")
    public String getLastCommands(
            @ApiParam(value = "How large of results to return", required = true) @Valid @RequestBody int pageSize,
            @ApiParam(value = "Page number of results", required = true) @Valid @RequestBody int pageNumber){

        return "";
    }

    @GetMapping("/commands/search")
    public String searchForCommands(
            @ApiParam(value = "Name or partial name of a command to get", required = false) @Valid @RequestBody String commandName,
            @ApiParam(value = "Date range of commands to get.  Only supports two dates, do not provide any more." +
                    "  Earlier date will be used as the start date.", required = false) @Valid @RequestBody List<Date> dateRange){

        return "";
    }

}