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

        SimpleSQLiteQuery query = new SimpleSQLiteQuery
                ("INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(91, ' CATTLE', 'M', 'BABY');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(92, ' CATTLE', 'M', 'YOUNG');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(93, ' CATTLE', 'M', 'OLD');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(94, ' CATTLE', 'F', 'BABY');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(95, ' CATTLE', 'F', 'YOUNG');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(96, ' CATTLE', 'F', 'OLD');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(97, ' SHEEP', 'M', 'BABY');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(98, ' SHEEP', 'M', 'YOUNG');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(99, ' SHEEP', 'M', 'OLD');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(100, ' SHEEP', 'F', 'BABY');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(101, ' SHEEP', 'F', 'YOUNG');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(102, ' SHEEP', 'F', 'OLD');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(103, ' GOAT', 'M', 'BABY');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(104, ' GOAT', 'M', 'YOUNG');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(105, ' GOAT', 'M', 'OLD');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(106, ' GOAT', 'F', 'BABY');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(107, ' GOAT', 'F', 'YOUNG');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(108, ' GOAT', 'F', 'OLD');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(115, ' CAMEL', 'M', 'BABY');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(116, ' CAMEL', 'M', 'YOUNG');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(117, ' CAMEL', 'M', 'OLD');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(118, ' CAMEL', 'F', 'BABY');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(119, ' CAMEL', 'F', 'YOUNG');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(120, ' CAMEL', 'F', 'OLD');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(121, ' HORSE_MULE', 'M', 'BABY');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(122, ' HORSE_MULE', 'M', 'YOUNG');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(123, ' HORSE_MULE', 'M', 'OLD');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(124, ' HORSE_MULE', 'F', 'BABY');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(125, ' HORSE_MULE', 'F', 'YOUNG');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(126, ' HORSE_MULE', 'F', 'OLD');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(127, ' DONKEY', 'M', 'BABY');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(128, ' DONKEY', 'M', 'YOUNG');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(129, ' DONKEY', 'M', 'OLD');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(130, ' DONKEY', 'F', 'BABY');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(131, ' DONKEY', 'F', 'YOUNG');\n" +
                        "INSERT INTO `Animals` (`Id`, `Name`, `Sex`, `Age`) VALUES(132, ' DONKEY', 'F', 'OLD');\n");

        dao.PopulateAnimals(query);


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

