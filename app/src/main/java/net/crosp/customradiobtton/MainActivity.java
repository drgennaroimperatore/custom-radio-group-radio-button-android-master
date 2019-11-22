package net.crosp.customradiobtton;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.room.Room;
import androidx.sqlite.db.SimpleSQLiteQuery;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {
    PresetRadioGroup mAnimalSpeciesSelectGroup;
    PresetValueImageButton mCattleRadioButton, mSheepRadioButton, mCamelRadioButton, mEquidRadioButton;
    AnimalAgeSeekBar mAnimalAgeSeekBar;
    Button mNextPageButton;
    ADDB mADDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mADDB= Room.databaseBuilder(this, ADDB.class, "ADDB").allowMainThreadQueries().build();
        ADDBDAO dao = mADDB.getADDBDAO();
        RelativeLayout mainActivity = findViewById(R.id.mainactivityid);

        /*dao.deleteAllAnimals();
        dao.deleteAllDiseases();
        dao.deleteAllSigns();
        dao.deleteAllLikelihoods();
        dao.deleteAllPriorsDiseases();
        dao.deleteAllPriorsDiseases();

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
*/
        mAnimalSpeciesSelectGroup = (PresetRadioGroup) findViewById(R.id.animal_select_radio_group);
        /*mCattleRadioButton = (PresetValueImageButton) findViewById(R.id.cattle_radio_button);
        mSheepRadioButton = (PresetValueImageButton) findViewById(R.id.sheep_radio_button);
        mCamelRadioButton = (PresetValueImageButton) findViewById(R.id.camel_radio_button);
        mEquidRadioButton = (PresetValueImageButton) findViewById(R.id.equid_radio_button);*/


        //Add signs
        LayoutInflater inflater = LayoutInflater.from(this);


        int signCounter=0;



        LinearLayout signsContainer = (LinearLayout) findViewById(R.id.signs_container);



        //int animalID = dao.getAnimalIDFromName(" CATTLE").get(0);

        populateSignsContainer(signsContainer, dao.getAllSignsForAnimal(91));




        ////////////////REFERENCE CODE FOR CREATING SYMPTOMS LIST
     /*   <!--<net.crosp.customradiobtton.PresetRadioGroup
        android:id="@+id/preset_time_radio_group_1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentTop="false"
        android:layout_margin="3dp"
        android:layout_marginBottom="13dp"
        android:layout_below="@id/textView_symptom_heading"
        android:orientation="horizontal"
        android:weightSum="3"
        app:presetRadioCheckedId="@+id/preset_time_value_button_6">


        <net.crosp.customradiobtton.PresetValueButton
        android:id="@+id/preset_time_value_button_3"
        style="@style/PresetLayoutButton"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        app:presetButtonUnitText="Present"
        app:presetButtonValueText="Fever" />*/

     ////////////// END OF REFERENCE CODE


        
        mAnimalSpeciesSelectGroup.setOnCheckedChangeListener(new PresetRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View radioGroup, View radioButton, boolean isChecked, int checkedId) {

              PresetValueImageButton selection = (PresetValueImageButton) radioButton;
              String value = selection.getUnit();



                if(value.equals( "Horse/Mule")) // treat all equids the same
                    value = "Donkey";

                Log.d("ANIMAL SELECTION",value);

                List<Integer> ids = dao.getAnimalIDFromName(" "+value.toUpperCase());
                List<String> signs = dao.getAllSignsForAnimal(ids.get(0));

                populateSignsContainer(signsContainer, signs);




            }});




        mAnimalAgeSeekBar = (AnimalAgeSeekBar) findViewById(R.id.seekbarWithIntervals);


        List<String> seekbarIntervals = new ArrayList<String>();

        seekbarIntervals.add("Baby");
        seekbarIntervals.add("Young");
        seekbarIntervals.add("Adult");

        mAnimalAgeSeekBar.setIntervals(seekbarIntervals);




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

    public void populateSignsContainer(LinearLayout signsContainer, List<String> signs)
    {
        int signCounter=0;

        signsContainer.removeAllViews();
        for(String sign :signs)
        {
            TextView label = new TextView(this);
            label.setText(sign);
            RadioGroup group = new RadioGroup(this);
            group.setOrientation(RadioGroup.HORIZONTAL);
            RadioButton btn1 = new RadioButton(this);
            btn1.setText("Present");
            group.addView(btn1);
            RadioButton btn2 = new RadioButton(this);
            group.addView(btn2);
            btn2.setText("Not Present");

            RadioButton btnN = new RadioButton(this);
            group.addView(btnN);
            btnN.setText("Not Observed");

            signsContainer.addView(label);

            signsContainer.addView(group);

            signCounter++;
        }
    }

    public void GoToResults()
    {
        Intent myIntent = new Intent(this, ResultsActivity.class);
        startActivity(myIntent);
    }
}

