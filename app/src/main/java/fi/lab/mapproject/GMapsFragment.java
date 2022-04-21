package fi.lab.mapproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class GMapsFragment extends Fragment {

    private SearchView searchView;
    private MainActivity mainActivity;
    private Marker lastTappedMarker;
    DBHandler dbHandler;
    GoogleMap mMap;

    List<PlacePoint> mapPlacePoints;

    public void ClearMapMarkersAndDB(){
        if (mapPlacePoints != null && !mapPlacePoints.isEmpty()){
            //Cleaning map
            mMap.clear();

            //Cleaning points list
            mapPlacePoints.clear();

            //Cleaning DB
            dbHandler.clearPlacePoints();

            //notification
            Toast.makeText(mainActivity.getApplicationContext(), "Everything is clear now!", Toast.LENGTH_SHORT).show();
        }
    }

    public void SavePlacePointsToDB(boolean notification){
        //Cleaning db before saving
        dbHandler.clearPlacePoints();

        //Adding every PlacePoint from mapPlacePoints list to db
        for (PlacePoint pp : mapPlacePoints){
            dbHandler.addPlacePoint(pp);
        }
        //Checking if notification necessary
        if (notification){
            Toast.makeText(mainActivity.getApplicationContext(), "Everything saved correctly!", Toast.LENGTH_SHORT).show();
        }

    }

    private void RemovePlacePoint(Marker marker) {

        //finding place from the list
        PlacePoint toRemove = mapPlacePoints.stream()
                .filter(item -> item.getLatLng().equals(marker.getPosition()))
                .collect(Collectors.toList()).get(0);

        //checking if everything gone ok
        if (toRemove != null){
            //removing from the list
            mapPlacePoints.remove(toRemove);
            //removing from the map
            marker.remove();

            //notification
            Toast.makeText(mainActivity.getApplicationContext(), "Marker removed!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(mainActivity.getApplicationContext(), "Something gone wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    public void LoadPlacePointsFromDB(){
        //Getting list of all PlacePoints
        mapPlacePoints = dbHandler.getAllPlacePoints();

        //Adding markers
        if (mapPlacePoints != null && !mapPlacePoints.isEmpty()){
            for (PlacePoint pp : mapPlacePoints){
                mMap.addMarker(new MarkerOptions().position(pp.getLatLng()).title(pp.getPlaceName()));
            }
        }

        //Zooming camera to last point in the list
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mapPlacePoints.get(mapPlacePoints.size() - 1).getLatLng()));
    }


    private final OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {

            mainActivity = (MainActivity)getActivity();

            //getting DBHandler from main activity for further use
            dbHandler = mainActivity.dbHandler;

            Toast.makeText(mainActivity.getApplicationContext(), "DBHandler is set up", Toast.LENGTH_SHORT).show();

            mMap = googleMap;

            mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(@NonNull LatLng latLng) {
                    Log.i("map checker", "map is clicked");
                    Log.i("map latlngchecker", latLng.toString());

                    List<Address> addressList = null;
                    Geocoder geocoder = new Geocoder(getActivity());

                    try {
                        addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (addressList.size() > 0){
                        Address address = addressList.get(0);

                        PlacePoint placePoint = new PlacePoint(address.getAddressLine(0), latLng);
                        mapPlacePoints.add(placePoint);
                        // on below line we are adding marker to that position.
                        mMap.addMarker(new MarkerOptions().position(latLng).title(address.getAddressLine(0)));
                    }
                    else {
                        mMap.addMarker(new MarkerOptions().position(latLng).title("some marker"));
                    }
                }

//                @Override
//                public void onMapClick(@NonNull LatLng latLng) {
//
//                }
            });



            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(@NonNull Marker marker) {
                    Log.i("marker checker", "marker is clicked");
                    Log.i("marker title", marker.getTitle());

                    if (lastTappedMarker != null && lastTappedMarker.equals(marker))
                    {
                        lastTappedMarker = null;

                        RemovePlacePoint(marker);
                    }
                    else{
                        lastTappedMarker = marker;
                        Toast.makeText(mainActivity.getApplicationContext(), "Tap one more time to remove marker", Toast.LENGTH_SHORT).show();
                    }

                    return false;
                }
            });

            //just for test
            //ArrayList<PlacePoint> placePointsArrayList = new ArrayList<>();

            LoadPlacePointsFromDB();

            if (mapPlacePoints.isEmpty()){
                //Generating starting points only if there is no saved points
                generateStartingPoints();
            }

        }
    };

    private void generateStartingPoints() {
        //Useful coordinates
        //LAHTI 60.98543428468401, 25.663712747153326
        //LAB 61.00655692042178, 25.66437080484379

        mapPlacePoints = new ArrayList<>();

        PlacePoint Lahti_Sibelius1 = new PlacePoint( "Lahti_Sibelius1", new LatLng(60.9948f, 25.6520f));
        PlacePoint Lahti_Keskusta1    = new PlacePoint("Lahti_Keskusta1", new LatLng(60.9816f, 25.6601f));
        PlacePoint Lahti_Trio1 = new PlacePoint("Lahti_Trio1", new LatLng(60.9828f, 25.6619f));
        PlacePoint Lahti_Salpaus1 = new PlacePoint("Lahti_Salpaus1", new LatLng(60.818522f, 25.753588f));
        PlacePoint Lahti_LAB = new PlacePoint("LAB University of Applied Sciences", new LatLng(61.00655692042178, 25.66437080484379));

        mapPlacePoints.add(Lahti_Sibelius1);
        mapPlacePoints.add(Lahti_Keskusta1);
        mapPlacePoints.add(Lahti_Trio1);
        mapPlacePoints.add(Lahti_Salpaus1);
        mapPlacePoints.add(Lahti_LAB);

        mapPlacePoints.forEach((p->{
            try {
                Log.i("placePointsArrayList", p.toString());

                Log.i("myD", p.getLatLng().toString());
                mMap.addMarker(new MarkerOptions().position(p.getLatLng()).title(p.getPlaceName()));
            }
            catch ( Exception e) {
                Log.d("sorry", String.valueOf(e));
            }

        }));

        mMap.addMarker(new MarkerOptions().position(Lahti_LAB.getLatLng()).title(Lahti_LAB.getPlaceName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Lahti_LAB.getLatLng()));

        SavePlacePointsToDB(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_g_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        //Setting up save button event through lambda expression
        Button saveButton = getActivity().findViewById(R.id.saveButton);
        saveButton.setOnClickListener(savePoints -> SavePlacePointsToDB(true));

        //Setting up clear button event through lambda expression
        Button clearButton = getActivity().findViewById(R.id.clearButton);
        clearButton.setOnClickListener(clearPoints -> ClearMapMarkersAndDB());

        searchView = getActivity().findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // on below line we are getting the
                // location name from search view.
                String location = searchView.getQuery().toString();

                // below line is to create a list of address
                // where we will store the list of all address.
                List<Address> addressList = null;


                // checking if the entered location is null or not.
                if (location.equals("")) {
                    // on below line we are creating and initializing a geo coder.
                    Geocoder geocoder = new Geocoder(getActivity());
                    try {
                        // on below line we are getting location from the
                        // location name and adding that location to address list.
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }




                    // on below line we are getting the location
                    // from our list a first position.
                    if (addressList.size() > 0) {
                        Address address = addressList.get(0);

                        // on below line we are creating a variable for our location
                        // where we will add our locations latitude and longitude.
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                        PlacePoint placePoint = new PlacePoint(location, latLng);
                        mapPlacePoints.add(placePoint);
                        // on below line we are adding marker to that position.
                        mMap.addMarker(new MarkerOptions().position(latLng).title(location));

                        // below line is to animate camera to that position.
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //searchView.
                return false;
            }
        });
    }
}