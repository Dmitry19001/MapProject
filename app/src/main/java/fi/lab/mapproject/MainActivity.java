package fi.lab.mapproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentContainerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//    List<PlacePoint> placePointsList;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing database
        dbHandler = new DBHandler(this);
        //Initializing list of PlacePoints
//        placePointsList = new LinkedList<>();
    }

    @Override
    protected void onDestroy() {
        dbHandler.close();
        super.onDestroy();
    }

//    public void saveToDB(View view) {
//        PlacePoint pp = new PlacePoint("Home", new LatLng(10, 10));
//
//        if (gMapsFragment == null){
//            Toast.makeText(getApplicationContext(), "gMapsFragment is null", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            Toast.makeText(getApplicationContext(), gMapsFragment.toString(), Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public void clearDB(View view) {
//        if (!dbHandler.getAllPlacePoints().isEmpty()){
//            dbHandler.clearPlacePoints();
//            gMapsFragment
//            gMapsFragment.ClearMapMarkersAndDB();
//            Toast.makeText(getApplicationContext(),  String.format("Database is clean now! gmaps_len: %s", gMapsFragment.mapPlacePoints.size()), Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            Toast.makeText(getApplicationContext(), "Database is already clean!", Toast.LENGTH_SHORT).show();
//        }
//    }
}