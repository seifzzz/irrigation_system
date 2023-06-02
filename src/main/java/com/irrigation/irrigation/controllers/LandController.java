package com.irrigation.irrigation.controllers;

import com.irrigation.irrigation.model.land;
import com.irrigation.irrigation.request.UpdateLand;
import com.irrigation.irrigation.services.LandService;
import com.irrigation.irrigation.services.SlotService;
import com.irrigation.irrigation.model.slot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


//Land Controller to create,update or delete lands
@RestController
@RequestMapping("/plots")
public class LandController {
    Logger logger  ;
    public LandService landService;
    public SlotService slotService;

    public LandController(LandService landService,SlotService slotService) {
        logger =  LoggerFactory.getLogger(IrrigationController.class);
        this.landService = landService;
        this.slotService = slotService;
    }
    @GetMapping
    public ResponseEntity <List<land>> FindAll() {
        List<land> lands = landService.findAll();
        List<slot> slots = slotService.findAll();

        return new ResponseEntity<>(lands,  HttpStatus.OK);
    }
    @GetMapping("/slots")
    public ResponseEntity <List<slot>> FindAllSlots() {

        List<slot> slots = slotService.findAll();

        return new ResponseEntity<>(slots,  HttpStatus.OK);
    }


    @PostMapping
    public  ResponseEntity<land> createLand(@RequestBody land land) {

        land created = landService.create(land);
        slot creatdSlot = slotService.createSlot(created);
        slotService.save(creatdSlot);
        logger.info("New Land is Created and added to System");
        return new ResponseEntity<>(land, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<land> oneLand(@PathVariable Long id) {
        Optional<land> optional = landService.findById(id);

        if (optional.isPresent()) {
            logger.info("Land is found");
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);
        }
        logger.warn("Land isn't found");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<land> updateLand(@PathVariable Long id, @RequestBody UpdateLand input) {
        Optional<land> optionalLand = landService.findById(id);


        if (optionalLand.isEmpty() ) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        land updateLand = optionalLand.get();

        updateLand.setName(input.name());
        updateLand.setSize(input.size());
        updateLand.setCrop(input.crop());

        land landUpdated = landService.update(updateLand);
        slotService.save(slotService.createSlot(landUpdated));
        logger.info("Land is updated");
        return new ResponseEntity<>(landUpdated, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLand(@PathVariable Long id) {
        landService.delete(id);
        logger.info("Land is deleted");
        return ResponseEntity.noContent().build();
    }



}
