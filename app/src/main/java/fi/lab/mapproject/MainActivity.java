package fi.lab.mapproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Place> placesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        placesList = new LinkedList<>();
    }

    public void saveToDB(View view) {
        testPlaces();
    }

    private void testPlaces(){
        //TESTING place creating
        if (placesList.isEmpty()){
            placesList.add(new Place(0, "Home", 10 , 10));
            placesList.add(new Place(1, "Home1", 1 , 10));
            placesList.add(new Place(2, "Home2", 10 , 100));
            placesList.add(new Place(3, "Home3", 100 , 10));
        }

        //TESTING COMPARING
        Place place1 = placesList.get(0);
        Place place2 = placesList.get(1);
        String comparingRes = place1.compareTo(place2) == 0 ? "True" : "False";

        //RESULTS
        Toast.makeText(getApplicationContext(), String.format("%s VS %s == %s", place1, place2, comparingRes), Toast.LENGTH_SHORT).show();
    }

}