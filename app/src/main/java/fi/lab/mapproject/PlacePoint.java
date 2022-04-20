package fi.lab.mapproject;

import java.io.Serializable;

public class PlacePoint implements Comparable<PlacePoint>, Serializable {
    private int id;
    private String name;
    private float longitude;
    private float latitude;

    public PlacePoint(){
        this("Unknown", 0,0);
    }

    public PlacePoint(String name, float longitude, float latitude){
        setPlaceId(0);
        setPlaceName(name);
        setPlaceLatitude(latitude);
        setPlaceLongitude(longitude);
    }

    public int getPlaceId(){ return this.id; }
    public void setPlaceId(int id) { this.id = id; }

    public String getPlaceName(){ return this.name; }
    public void setPlaceName(String name){
        if (name.trim().length() > 0){
            this.name = name;
        }
        else{
            this.name = "Unknown";
        }
    }

    public float getPlaceLongitude(){ return this.longitude; }
    public void setPlaceLongitude(float longitude){
        this.longitude = longitude;
    }

    public float getPlaceLatitude(){ return this.latitude; }
    public void setPlaceLatitude(float latitude){
        this.latitude = latitude;
    }

    @Override
    public String toString(){
        return String.format("[%s] %s [%s, %s]", getPlaceId(), getPlaceName(), getPlaceLongitude(), getPlaceLatitude());
    }


    @Override
    public int compareTo(PlacePoint placePoint) {
        return this.toString().compareToIgnoreCase(placePoint.toString());
    }
}
