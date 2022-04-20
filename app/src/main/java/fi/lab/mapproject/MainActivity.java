package fi.lab.mapproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<PlacePoint> placePointsList;
    DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing database
        dbHandler = new DBHandler(this);
        //Initializing list of PlacePoints
        placePointsList = new LinkedList<>();
    }

    @Override
    protected void onDestroy() {
        dbHandler.close();
        super.onDestroy();
    }

    public void saveToDB(View view) {
        testPlaces();
    }

    private void testPlaces(){
        //TESTING place point creating
        if (placePointsList.isEmpty()){
            placePointsList.add(new PlacePoint("Home", new LatLng(10, 10)));
            placePointsList.add(new PlacePoint("Home1", new LatLng(1 , 10)));
            placePointsList.add(new PlacePoint("Home2", new LatLng(10 , 100)));
            placePointsList.add(new PlacePoint("Home3", new LatLng(100 , 10)));
        }

        //TESTING COMPARING
        //PlacePoint placePoint1 = placePointsList.get(0);
        //PlacePoint placePoint2 = placePointsList.get(1);
        //String comparingRes = placePoint1.compareTo(placePoint2) == 0 ? "True" : "False";

        //RESULTS
        //Toast.makeText(getApplicationContext(), String.format("%s VS %s == %s", placePoint1, placePoint2, comparingRes), Toast.LENGTH_SHORT).show();
        if (dbHandler.getAllPlacePoints().isEmpty()){
            for (PlacePoint pp : placePointsList){
                dbHandler.addPlacePoint(pp);
            }

            Toast.makeText(getApplicationContext(), String.format("Added %s entries to db", dbHandler.getAllPlacePoints().size()), Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(getApplicationContext(), String.format("There is %s entries in db", dbHandler.getAllPlacePoints().size()), Toast.LENGTH_SHORT).show();
    }

    public void clearDB(View view) {
        if (!dbHandler.getAllPlacePoints().isEmpty()){
            dbHandler.clearPlacePoints();
            Toast.makeText(getApplicationContext(), "Database is clean now!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Database is already clean!", Toast.LENGTH_SHORT).show();
        }
    }
}