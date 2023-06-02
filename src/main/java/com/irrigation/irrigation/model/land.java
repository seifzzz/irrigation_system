package com.irrigation.irrigation.land;



import jakarta.persistence.*;

@Entity
@Table(name = "land")
public class land {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "size")
    private int size;
    @Column(name = "crop")
    private String crop;




   public land() {

    }

    public land(String id, String name,int size, String crop) {
        this.id = id;
        this.name = name;
        this.crop = crop;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
