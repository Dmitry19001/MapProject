package fi.lab.mapproject;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class PlacePoint implements Comparable<PlacePoint>, Serializable {
    private int id;
    private String name;
    private LatLng latLng;

    public PlacePoint(){
        this("Unknown", new LatLng(0,0));
    }

    public PlacePoint(String name, LatLng latLng){
        setPlaceId(0);
        setPlaceName(name);
        setLatLng(latLng);
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

    public LatLng getLatLng(){ return this.latLng; }
    public void setLatLng(LatLng latLng){ this.latLng = latLng; }

    public double getPlaceLongitude(){ return this.latLng.longitude; }
    public void setPlaceLongitude(double longitude){
        this.latLng = new LatLng(longitude, this.latLng.latitude);
    }

    public double getPlaceLatitude(){ return this.latLng.latitude; }
    public void setPlaceLatitude(float latitude){
        this.latLng = new LatLng(this.latLng.longitude, latitude);
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
