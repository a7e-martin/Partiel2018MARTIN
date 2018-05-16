package org.diiage.martin.partiel2018martin.models;

public class Artist {
    private int id;
    private String name;

    public Artist(){

    }

    public Artist(int id, String name){
        this.setId(id);
        this.setName(name);
    }
    public Artist(String name){
        this.setName(name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
