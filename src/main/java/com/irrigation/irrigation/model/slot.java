package com.irrigation.irrigation.slot;

import com.irrigation.irrigation.land.land;
import jakarta.persistence.*;

@Entity
@Table(name = "slot")
public class slot {

   @Id
   @Column(name = "land_id")
   private String land_id;
   private double time ;
   private double water;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "land_id", referencedColumnName = "id")
    private land land ;
    public slot() {
    }

    public String getLand_id() {
        return land_id;
    }

    public void setLand_id(String land_id) {
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
}
