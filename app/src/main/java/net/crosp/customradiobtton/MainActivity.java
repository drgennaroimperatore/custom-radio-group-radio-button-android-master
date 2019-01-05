package net.crosp.customradiobtton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    PresetRadioGroup mSetDurationPresetRadioGroup;
    PresetRadioGroup mSetPresetRadioGroup;
    AnimalAgeSeekBar mAnimalAgeSeekBar;
    Button mNextPageButton;
    final int mIRadioGroupPerPage =5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



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

