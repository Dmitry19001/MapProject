package fi.lab.mapproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

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

public class GMapsFragment extends Fragment {

    private SearchView searchView;
    GoogleMap mMap;


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
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
           // LatLng latLng;

            //public void onMapClick (LatLng latLng){
                //MarkerOptions marker = new MarkerOptions()
               //         .position(latLng);
             //   this.googleMap.addMarker(marker);
           // }

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(@NonNull LatLng latLng) {
                    Log.i("map checker", "map is clicked");
                    Log.i("map latlngchecker", latLng.toString());
                    mMap.addMarker(new MarkerOptions().position(latLng).title("some market"));
                }
            });



            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(@NonNull Marker marker) {
                    Log.i("marker checker", "marker is clicked");
                    Log.i("marker title", marker.getTitle());
                    return false;
                }
            });

            //just for test
            ArrayList<PlacePoint> placePointsArrayList = new ArrayList<>();


            PlacePoint Lahti_Sibelius1 = new PlacePoint("Lahti_Sibelius1", 60.9948f, 25.6520f);
            PlacePoint Lahti_Keskusta1 = new PlacePoint("Lahti_Keskusta1", 60.9816f, 25.6601f);
            PlacePoint Lahti_Trio1 = new PlacePoint("Lahti_Trio1", 60.9828f, 25.6619f);
            PlacePoint Lahti_Salpaus1 = new PlacePoint("Lahti_Salpaus1", 60.818522f, 25.753588f);


            placePointsArrayList.add(Lahti_Sibelius1);
            placePointsArrayList.add(Lahti_Keskusta1);
            placePointsArrayList.add(Lahti_Trio1);
            placePointsArrayList.add(Lahti_Salpaus1);


            placePointsArrayList.forEach((p -> {
                try {
                    Log.i("placePointsArrayList", p.toString());
                    LatLng d = new LatLng(p.getPlaceLongitude(), p.getPlaceLatitude());
                    Log.i("myD", d.toString());
                    mMap.addMarker(new MarkerOptions().position(d).title(p.getPlaceName()));
                } catch (Exception e) {
                    Log.d("sorry", String.valueOf(e));
                }

            }));


            //LAHTI 60.98543428468401, 25.663712747153326
            //LAB 61.00655692042178, 25.66437080484379
            LatLng lab = new LatLng(61.00655692042178, 25.66437080484379);
            mMap.addMarker(new MarkerOptions().position(lab).title("LAB University of Applied Sciences"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(lab));
        }
    };



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

        searchView = (SearchView) getActivity().findViewById(R.id.searchView);
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
                if (location != null || location.equals("")) {
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