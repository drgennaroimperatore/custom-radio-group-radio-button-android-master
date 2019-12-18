package net.crosp.customradiobtton;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.room.Room;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogCaseActivity extends AppCompatActivity {

    private Spinner mChosenDiseaseSpinner, mChosenRegionSpinner;
    private HashMap<String, Float> mDiagnoses;
    private ListView mPresentSignsLView, mNotPresentSignsLView;
    private TextView mChosenSpeciesTV, mPredictedDiagnosisTV;
    private EditText mCaseDateET, mCommentsET;
    private Button mSubmitCaseButton;
    String mSelectedSpecies;


    CasesDB mCasesDB;
    CasesDBDAO mCasesDBDAO;


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

        mCaseDateET = (EditText) findViewById(R.id.case_date_editText);

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date today = Calendar.getInstance().getTime();
        try {

            mCaseDateET.setText(formatter.format(today));
        }
        catch (Exception e)
        {

        }

        mCommentsET = (EditText) findViewById(R.id.log_case_comments);
        mCommentsET.setText("No Comments");

        mSubmitCaseButton= (Button) findViewById(R.id.logCase_button);


        mSubmitCaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitCase();

            }
        });


        HashMap<String, String> selectedSigns = (HashMap<String, String>) getIntent().getSerializableExtra("signs");

        ArrayList<String> pSigns = new ArrayList<>();
        ArrayList<String> npSigns = new ArrayList<>();

        for(Map.Entry<String, String> s : selectedSigns.entrySet())
        {

            if(s.getValue().equals("Not Present"))
                npSigns.add(reduceSignName(s.getKey()));
            if(s.getValue().equals("Present"))
                pSigns.add(reduceSignName(s.getKey()));
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
       int indexOfPredictedDisease = chosenDiseaseSpinnerList.indexOf(mPredictedDiagnosisTV.getText().toString());
        mChosenDiseaseSpinner.setSelection(indexOfPredictedDisease);

        ArrayAdapter<String> regionSpinnerAdapter = new ArrayAdapter(this,R.layout.chosen_diagnosis_spinner_item, REGIONS_OF_ETHIOPIA);
        mChosenRegionSpinner.setAdapter(regionSpinnerAdapter);

        mCasesDB = CasesDB.getInstance(this);
        mCasesDBDAO = mCasesDB.getmCasesDBDAO();
    }


    public String reduceSignName(String signName)
    {
        if(signName.contains("/")) {
            StringBuilder sb = new StringBuilder(signName);
            sb.insert(signName.indexOf('/')+1,'\n');
            return sb.toString();

        }
        return signName;

    }

    public void submitCase()
    {
       ADDBDAO addbdao = ADDB.getInstance(this).getADDBDAO();

        Cases newCase = new Cases();
        newCase.AnimalID = addbdao.getAnimalIDFromName(mSelectedSpecies).get(0); // CHANGE THAT
        newCase.Comments = mCommentsET.getText().toString();
        newCase.DateCaseLogged = new Date();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            newCase.DateCaseObserved = formatter.parse(mCaseDateET.getText().toString());
        }
        catch (Exception e)
        {

        }
        newCase.Region = mChosenRegionSpinner.getSelectedItem().toString();
        newCase.DiseaseChosenByUserID= 0;
        newCase.LikelihoodDPbyApp=100.0f;
        newCase.UserUUID="Fix this!";

        mCasesDBDAO.insertCase(newCase);

        List<Cases> c = mCasesDBDAO.getAllCases();

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_NO_ANIMATION| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        setTheme(R.style.AppTheme);
        startActivity(intent);





    }
}
