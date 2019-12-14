package net.crosp.customradiobtton;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LogCaseActivity extends AppCompatActivity {

    private Spinner mChosenDiseaseSpinner, mChosenRegionSpinner;
    private HashMap<String, Float> mDiagnoses;
    private ListView mPresentSignsLView, mNotPresentSignsLView;
    private TextView mChosenSpeciesTV, mPredictedDiagnosisTV;
    String mSelectedSpecies;


    private String[] REGIONS_OF_ETHIOPIA = new String[] {
            "Addis Ababa",
            "Afar",
            "Amhara",
            "Benishangul-Gumuz",
            "Dire Dawa",
            "Gambela",
            "Harari",
            "Oromia",
            "Somali",
            "Tigray",
            "Southern Nations Nationalities and People"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_case);

        mSelectedSpecies =getIntent().getStringExtra("species");
        mChosenSpeciesTV = findViewById(R.id.case_review_species);
        mChosenSpeciesTV.setText(mSelectedSpecies);

        mPresentSignsLView = (ListView)findViewById(R.id.present_signs_reviewList);
        mNotPresentSignsLView =(ListView) findViewById(R.id.not_present_signs_reviewList);


        HashMap<String, String> selectedSigns = (HashMap<String, String>) getIntent().getSerializableExtra("signs");

        ArrayList<String> pSigns = new ArrayList<>();
        ArrayList<String> npSigns = new ArrayList<>();

        for(Map.Entry<String, String> s : selectedSigns.entrySet())
        {
            if(s.getValue().equals("Not Present"))
                npSigns.add(s.getKey());
            if(s.getValue().equals("Present"))
                pSigns.add(s.getKey());
        }

        if (pSigns.isEmpty())
            pSigns.add("NONE GIVEN");
        if(npSigns.isEmpty())
            npSigns.add("NONE GIVEN");

        ArrayAdapter<String> presentSignsAdapter = new ArrayAdapter<>(this, R.layout.case_review_present_notpresent_list_row,pSigns);
        ArrayAdapter<String> notPresentSignsAdapter = new ArrayAdapter<>(this, R.layout.case_review_present_notpresent_list_row,npSigns);

        mPresentSignsLView.setAdapter(presentSignsAdapter);
        mNotPresentSignsLView.setAdapter(notPresentSignsAdapter);



        mDiagnoses = (HashMap<String, Float>) getIntent().getSerializableExtra("diagnoses");

        mPredictedDiagnosisTV = findViewById(R.id.case_review_most_likely_diagnosis);
        mPredictedDiagnosisTV.setText(getIntent().getStringExtra("mostLikelyDiagnosis"));

        mChosenDiseaseSpinner= findViewById(R.id.chosen_diagnosis_spinner);
        mChosenRegionSpinner = findViewById(R.id.chosen_region_spinner);

        ArrayList<String> chosenDiseaseSpinnerList = new ArrayList<>();

        for(Map.Entry <String, Float> d: mDiagnoses.entrySet())
        {
            chosenDiseaseSpinnerList.add(d.getKey());
        }

        ArrayAdapter<String> diagnosisSpinnerAdapter = new ArrayAdapter(this,R.layout.chosen_diagnosis_spinner_item,chosenDiseaseSpinnerList);
        mChosenDiseaseSpinner.setAdapter(diagnosisSpinnerAdapter);

        ArrayAdapter<String> regionSpinnerAdapter = new ArrayAdapter(this,R.layout.chosen_diagnosis_spinner_item, REGIONS_OF_ETHIOPIA);
        mChosenRegionSpinner.setAdapter(regionSpinnerAdapter);
    }


}
