package org.whilmarbitoco.app.model;

import org.whilmarbitoco.app.database.anotation.Column;
import org.whilmarbitoco.app.database.anotation.Primary;
import org.whilmarbitoco.app.database.anotation.Table;


@Table(name = "Bird")
public class Bird {

    @Primary
    @Column(name = "id")
    private int id;

    @Column(name = "species")
    private String species;

    @Column(name = "color")
    private String color;

    public Bird() {}

    public Bird(String species, String color) {
        this.species = species;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
