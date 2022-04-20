package fi.lab.mapproject;

import com.google.android.gms.maps.model.LatLng;

import java.util.UUID;

public class PlacePoint {


    private String placeId = UUID.randomUUID().toString();
    private String placeName;
    private double placeLongitude;
    private double placeLatitude;

    public PlacePoint( String name, double longitude, double latitude) {
        placeId = UUID.randomUUID().toString();
        if (name.trim().length() > 0){
            placeName = name;
        }
        else{
            placeName= "Unknown";
        }
        placeLongitude = longitude;
        placeLatitude = latitude;
    }

    public String getPlaceId() {
        return this.placeId;
    }


    public String getPlaceName() {
        return this.placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public double getPlaceLongitude() {
        return this.placeLongitude;
    }

    public void setPlaceLongitude(double placeLongitude) {
        this.placeLongitude = placeLongitude;
    }

    public double getPlaceLatitude() {
        return this.placeLatitude;
    }

    public void setPlaceLatitude(double placeLatitude) {
        this.placeLatitude = placeLatitude;
    }



}




