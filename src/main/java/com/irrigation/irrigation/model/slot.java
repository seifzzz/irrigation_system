package com.irrigation.irrigation.model;

import jakarta.persistence.*;


// Data Model of Slot , It has OneToOne Relation with Land Class
@Entity
@Table(name = "slot")
public class slot {

   @Id
   @Column(name = "land_id")
   private Long land_id;
   private double time ;
   private double water;

    private boolean isIrrigating;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "land_id", referencedColumnName = "id")
    private land land ;
    public slot() {
    }

    public Long getLand_id() {
        return land_id;
    }

    public void setLand_id(Long land_id) {
        this.land_id = land_id;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getWater() {
        return water;
    }

    public void setWater(double water) {
        this.water = water;
    }

    public boolean isIrrigating() {
        return isIrrigating;
    }

    public void setIrrigating(boolean irrigating) {
        isIrrigating = irrigating;
    }
}
