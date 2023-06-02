package com.irrigation.irrigation.services;
import org.springframework.stereotype.Service;

@Service
public class IrrigationSystem {
    private Boolean isIrrigating = false;

    public IrrigationSystem() {
    }
    public Boolean getsIrrigating() {
        return isIrrigating;
    }

    public void setsIrrigating(Boolean isIrrigating) {
        this.isIrrigating = isIrrigating;
    }
}

