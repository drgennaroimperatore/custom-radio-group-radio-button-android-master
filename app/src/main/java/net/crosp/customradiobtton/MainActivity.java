package net.crosp.customradiobtton;

import android.os.Bundle;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import androidx.room.Room;
import androidx.sqlite.db.SimpleSQLiteQuery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    PresetRadioGroup mSetDurationPresetRadioGroup;
    PresetRadioGroup mSetPresetRadioGroup;
    AnimalAgeSeekBar mAnimalAgeSeekBar;
    Button mNextPageButton;
    ADDB mADDB;
    final int mIRadioGroupPerPage =5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mADDB= Room.databaseBuilder(this, ADDB.class, "ADDB").allowMainThreadQueries().build();
        ADDBDAO dao = mADDB.getADDBDAO();
        /*dao.deleteAllAnimals();
        dao.deleteAllDiseases();
        dao.deleteAllSigns();
        dao.deleteAllLikelihoods();
        dao.deleteAllPriorsDiseases();
        dao.deleteAllPriorsDiseases();*/

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("query.txt")));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line


                SimpleSQLiteQuery query = new SimpleSQLiteQuery
                        (mLine);

                 dao.PopulateAnimals(query);

;            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }




        mSetDurationPresetRadioGroup = (PresetRadioGroup) findViewById(R.id.preset_time_radio_group);
        mSetPresetRadioGroup = (PresetRadioGroup)  findViewById(R.id.preset_time_radio_group_1);
        
        mSetDurationPresetRadioGroup.setOnCheckedChangeListener(new PresetRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View radioGroup, View radioButton, boolean isChecked, int checkedId) {
            }});

        mAnimalAgeSeekBar = (AnimalAgeSeekBar) findViewById(R.id.seekbarWithIntervals);


        List<String> seekbarIntervals = new ArrayList<String>();

        seekbarIntervals.add("<=6" + System.getProperty("line.separator") + "Months");
        seekbarIntervals.add("> 6 Months "
                + System.getProperty("line.separator")
                +" <=1 year");
        seekbarIntervals.add("> 1 year");

        mAnimalAgeSeekBar.setIntervals(seekbarIntervals);


        //Add signs
        LayoutInflater inflater = LayoutInflater.from(this);

        String t = dao.getAnimalNameFromID(91).get(0);

       // List<String> signs = dao.getAllSignsForAnimal(dao.getAnimalIDFromName("CATTLE").get(0));

        List<Integer> A = dao.TestAnimalsTable();
        List<String> D = dao.TestDiseasesTable();
        List<String> S = dao.TestSignsTable();
        List <Integer> L =dao.TestLikelihoodsTable();
        List<Integer> PD = dao.TestPriorsDiseasesTable();
        List<Integer> SC = dao.TestSignCoresTable();

        Log.println(1,"Test", "test" );



        mNextPageButton =(Button) findViewById(R.id.next_button);
        mNextPageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                GoToResults();

            }
        });



    }

    public void GoToResults()
    {
        Intent myIntent = new Intent(this, ResultsActivity.class);
        startActivity(myIntent);
    }
}

