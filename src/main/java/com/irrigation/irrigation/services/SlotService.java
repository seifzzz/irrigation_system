package com.irrigation.irrigation.services;

import com.irrigation.irrigation.model.land;
import com.irrigation.irrigation.repositories.SlotRepository;
import com.irrigation.irrigation.model.slot;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SlotService {
    private final SlotRepository slotRepository;

    public SlotService(SlotRepository slotRepository) {
        this.slotRepository = slotRepository;
    }
    public List<slot> findAll() {
        List<slot> slots = new ArrayList<>();
        slotRepository.findAll().forEach(slots::add);

        return slots;
    }
    public slot save(slot slotToUpdate) {
        return slotRepository.save(slotToUpdate);
    }
    public Optional<slot> findById(Long id) {
        return slotRepository.findById(id);
    }

    public void delete(Long id) {
        slotRepository.deleteById(id);
    }

    public double CalculateTime(int size , int time){

        return  (size/50.0) * time ;
    }
    public double CalculateWater(int size , int amount){

        return  (size/50.0) * amount ;
    }

    public slot createSlot(land land ){

        slot slot = new slot();
        slot.setLand_id(land.getId());
        slot.setIrrigating(false);
        if(Objects.equals(land.getCrop(), "fruits")){

            slot.setTime(CalculateTime(land.getSize(),15));
            slot.setWater(CalculateWater(land.getSize(),10));

        }
        else if(Objects.equals(land.getCrop(), "vegetables")){
            slot.setTime(CalculateTime(land.getSize(),10));
            slot.setWater(CalculateWater(land.getSize(),5));
        }
        else if(Objects.equals(land.getCrop(), "rice")){
            slot.setTime(CalculateTime(land.getSize(),5));
            slot.setWater(CalculateWater(land.getSize(),3));
        }
        else{
            slot.setTime(5);
            slot.setWater(3);
        }
         return slot;

    }





}
