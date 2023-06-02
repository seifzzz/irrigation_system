package com.irrigation.irrigation.model;

import jakarta.persistence.*;

public class IrrigationSystem {
    private Boolean isIrrigating;

    public IrrigationSystem() {

    }


    public Boolean getsIrrigating() {
        return isIrrigating;
    }

    public void setsIrrigating(Boolean isIrrigating) {
        this.isIrrigating = isIrrigating;
    }
}

