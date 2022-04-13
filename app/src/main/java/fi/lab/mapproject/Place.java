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
        SetId(id);
        SetName(name);
        SetLat(latitude);
        SetLong(longitude);
    }

    public int GetId(){ return this.id; }
    public void SetId(int id) { this.id = id; }

    public String GetName(){ return this.name; }
    public void SetName(String name){
        if (name.trim().length() > 0){
            this.name = name;
        }
        else{
            this.name = "Unknown";
        }
    }

    public float GetLong(){ return this.longitude; }
    public void SetLong(float longitude){
        this.longitude = longitude;
    }

    public float GetLat(){ return this.latitude; }
    public void SetLat(float latitude){
        this.latitude = latitude;
    }

    @Override
    public int compareTo(Place place) {
        return 0;
    }
}
