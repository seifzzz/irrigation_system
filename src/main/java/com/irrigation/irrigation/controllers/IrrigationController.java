package com.irrigation.irrigation.controllers;

import com.irrigation.irrigation.component.RetryQueue;
import com.irrigation.irrigation.services.*;
import com.irrigation.irrigation.model.land;
import com.irrigation.irrigation.model.slot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


//Controller of Irrigation System
@RestController
@RequestMapping("/irrigation")
public class IrrigationController {
    Logger logger ;
    private final LandService landService;
    private final SlotService slotService;
    private final IrrigationSystem irrigationSystem;

    private final SensorService sensorService;

    private final RetryQueue retryQueue;
    private final AlertingSystem alertingSystem;
    public IrrigationController(LandService landService, SlotService slotService,
                                IrrigationSystem irrigationSystem,
                                SensorService sensorService,
                                RetryQueue retryQueue ,
                                AlertingSystem alertingSystem) {
        logger =  LoggerFactory.getLogger(IrrigationController.class);
        this.landService = landService;
        this.slotService = slotService;
        this.irrigationSystem = irrigationSystem;
        this.sensorService = sensorService;

        this.retryQueue = retryQueue;
        this.alertingSystem = alertingSystem;
    }

    @GetMapping("/status")
    public IrrigationSystem getSystemStatus() {
        return irrigationSystem;
    }

    @PostMapping("/start")
    public IrrigationSystem startIrrigation() throws InterruptedException {
        if (!irrigationSystem.getsIrrigating()) {
            logger.info("Automation Irrigation System is start");

            irrigationSystem.setsIrrigating(true);

            RunningSystem();
            sensorService.setSensorAvailable(true);
        }
        return irrigationSystem;
    }

    @PostMapping("/stop")
    public IrrigationSystem stopIrrigation() {
        if (irrigationSystem.getsIrrigating()) {

            irrigationSystem.setsIrrigating(false);
            logger.info("Automation Irrigation System is stop");
        }
        return irrigationSystem;
    }
    @PostMapping("/irrigate/{plotId}")
    public ResponseEntity<String> OneRequest(@PathVariable Long plotId) throws InterruptedException {
         return irrigatePlot(plotId);
    }

    private void RunningSavedLands() throws InterruptedException{
        List<slot> slots = slotService.findAll();
        List<land> lands = landService.findAll();

        if(slots.isEmpty() || lands.isEmpty()){
            return;
        }

        int count = 1;
        logger.info("Start to irrigate lands.");

        for (slot slot : slots) {
            logger.info(count+ ": Land " + lands.get(count-1).getName());
            irrigatePlot(slot.getLand_id());
            count++;
        }
    }
    private void RunningRetryLands() throws InterruptedException {

        if(retryQueue.isEmpty()) {
            return;
        }

        logger.info("Start to irrigate lands when sensor isn't available");
        int sizeQueue = retryQueue.getQueue().size();

        for(int i = 0 ;i<sizeQueue;i++){
            Optional<land> Oland = landService.findById(retryQueue.poll());
            land land = Oland.get();

            logger.info((1+i) + ": Land " + land.getName());
            irrigatePlot(land.getId());
        }
    }
    private void RunningSystem() throws InterruptedException {

         RunningSavedLands();
         RunningRetryLands();

    }

    public ResponseEntity<String> irrigatePlot(Long plotId) throws InterruptedException {

        if(!irrigationSystem.getsIrrigating()){
            return ResponseEntity.badRequest().body("the system doesn't work");
        }

        Optional<land> optionalPlot = landService.findById(plotId);
        Optional<slot> optionalSlot = slotService.findById(plotId);


        if (optionalPlot.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        land plot = optionalPlot.get();
        slot slot = optionalSlot.get();

        if (slot.isIrrigating()) {
            logger.warn("this Land is already being irrigated");
            return ResponseEntity.badRequest().body("Land is already being irrigated.");
        }

        if (sensorService.sendIrrigationRequest() ) {

            logger.info("Irrigation is working for this land");
            java.util.concurrent.TimeUnit.SECONDS.sleep((int) Math.round(slot.getTime()));
            slot.setIrrigating(true);
            slotService.save(slot);
            logger.info("Irrigation done for this land");
            sensorService.setSensorAvailable(true);
            return ResponseEntity.ok("Irrigation done for this land " + plot.getName());
        }
        else {
            logger.warn("Sensor not available. Added land to the retryQueue. " +
                    "It will irrigate after sensor is available ");
            retryQueue.add(plotId);
            return alertingSystem.alertSensorNotAvailable();

        }
    }

}








