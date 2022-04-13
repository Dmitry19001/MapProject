package fi.lab.mapproject;

import java.io.Serializable;

public class Place implements Comparable<Place>, Serializable {
    private int id;
    private String name;
    private float longitude;
    private float latitude;

    public Place (){
        this(0,"Unknown", 0,0);
    }

    public Place (int id, String name, float latitude, float longitude){
        setId(id);
        setName(name);
        setLat(latitude);
        setLong(longitude);
    }

    public int getId(){ return this.id; }
    public void setId(int id) { this.id = id; }

    public String getName(){ return this.name; }
    public void setName(String name){
        if (name.trim().length() > 0){
            this.name = name;
        }
        else{
            this.name = "Unknown";
        }
    }

    public float getLong(){ return this.longitude; }
    public void setLong(float longitude){
        this.longitude = longitude;
    }

    public float getLat(){ return this.latitude; }
    public void setLat(float latitude){
        this.latitude = latitude;
    }

    @Override
    public String toString(){
        return String.format("[%s] %s [%s, %s]", getId(), getName(), getLat(), getLong());
    }


    @Override
    public int compareTo(Place place) {
        return this.toString().compareToIgnoreCase(place.toString());
    }
}
