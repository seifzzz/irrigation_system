package com.irrigation.irrigation.model;

import jakarta.persistence.*;

//Data model for land
@Entity
@Table(name = "land")
public class land {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "size")
    private int size;
    @Column(name = "crop")
    private String crop;


    public land(String name, int size, String crop) {
        this.name = name;
        this.size = size;
        this.crop = crop;
    }

    public land() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
