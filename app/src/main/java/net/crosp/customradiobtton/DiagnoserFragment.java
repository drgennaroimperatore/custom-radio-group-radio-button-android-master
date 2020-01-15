package net.crosp.customradiobtton;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DiagnoserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DiagnoserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiagnoserFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    PresetRadioGroup mAnimalSpeciesSelectGroup;
    List<RadioGroup> mSignRadioGroups = new ArrayList<RadioGroup>();
    List<Signs> mSignsForAnimal = new ArrayList<>();
    // AnimalAgeSeekBar mAnimalAgeSeekBar;
    Button diagnoseButton;
    int mCurrentAnimalID;
    ADDB mADDB;
    UserInfoDB mUserInfoDB;
    UserInfoDBDao mUserInfoDao;




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DiagnoserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiagnoserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiagnoserFragment newInstance(String param1, String param2) {
        DiagnoserFragment fragment = new DiagnoserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_diagnoser, container, false);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mADDB= ADDB.getInstance(getContext());
        ADDBDAO dao = mADDB.getADDBDAO();
        // RelativeLayout mainActivity = findViewById(R.id.mainactivityid);

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
        mAnimalSpeciesSelectGroup = (PresetRadioGroup) getView().findViewById(R.id.animal_select_radio_group);
        /*mCattleRadioButton = (PresetValueImageButton) findViewById(R.id.cattle_radio_button);
        mSheepRadioButton = (PresetValueImageButton) findViewById(R.id.sheep_radio_button);
        mCamelRadioButton = (PresetValueImageButton) findViewById(R.id.camel_radio_button);
        mEquidRadioButton = (PresetValueImageButton) findViewById(R.id.equid_radio_button);*/


        //Add signs
        LayoutInflater inflater = LayoutInflater.from(getContext());


        int signCounter=0;



        LinearLayout signsContainer = (LinearLayout) getView().findViewById(R.id.signs_container);



        //int animalID = dao.getAnimalIDFromName(" CATTLE").get(0);



        mCurrentAnimalID =91;
        mSignsForAnimal =dao.getAllSignsForAnimal(mCurrentAnimalID);
        populateSignsContainer(signsContainer, mSignsForAnimal);




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



                if(value.equals( "Horse/Mule"))
                    value = "Horse_Mule";

                Log.d("ANIMAL SELECTION",value);

                List<Integer> ids = dao.getAnimalIDFromName(" "+value.toUpperCase());
                mCurrentAnimalID=ids.get(0);
                List<Signs> signs =dao.getAllSignsForAnimal(mCurrentAnimalID);

                populateSignsContainer(signsContainer, signs);




            }});





      /*  mAnimalAgeSeekBar = (AnimalAgeSeekBar) findViewById(R.id.seekbarWithIntervals);


        List<String> seekbarIntervals = new ArrayList<String>();

        seekbarIntervals.add("Baby");
        seekbarIntervals.add("Young");
        seekbarIntervals.add("Adult");

        mAnimalAgeSeekBar.setIntervals(seekbarIntervals);*/




        String t = dao.getAnimalNameFromID(91).get(0);

        // List<String> signs = dao.getAllSignsForAnimal(dao.getAnimalIDFromName("CATTLE").get(0));

       /* List<Integer> A = dao.TestAnimalsTable();
        List<String> D = dao.TestDiseasesTable();
        List<String> S = dao.TestSignsTable();
        List <Integer> L =dao.TestLikelihoodsTable();
        List<Integer> PD = dao.TestPriorsDiseasesTable();
        List<Integer> SC = dao.TestSignCoresTable();*/

        Log.println(1,"Test", "test" );



        diagnoseButton =(Button) getView().findViewById(R.id.diagnose_button);
        diagnoseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                getSelectedSigns();

                GoToResults(diagnoseAnimal(dao,getSelectedSigns(),mCurrentAnimalID,dao.getAllDiseases()));


            }
        });




    }

    public List<String> getNamesFromSigns(List<Signs> signs)
    {
        List<String> names = new ArrayList<>();
        for(Signs sign: signs)
        {
            names.add(sign.Name);
        }
        return names;
    }

    public HashMap<String, Float> diagnoseAnimal(ADDBDAO dao, HashMap<Signs, String> selectedSigns, int animalID, List<Diseases> diseases)
    {
        HashMap<String, Float> diagnosis = new HashMap<>();

        for(Diseases d:diseases)

        {

            if (!checkIfDiseasesAffectsAnimal(dao, animalID, d.Id))
                continue;

            float chainProbability = 1.0f;
            for (Map.Entry selectedSign: selectedSigns.entrySet())
            {
                String signPresence = (String)selectedSign.getValue();
                int signID = ((Signs)selectedSign.getKey()).Id;

                float likelihoodValue = 1.0f; // sign is not observed

                if(signPresence.equals("Present"))
                {

                    try
                    {

                        String stringl =  dao.getLikelihoodValue(animalID, signID, d.Id).get(0).Value;
                        Float floatl =Float.parseFloat( dao.getLikelihoodValue(animalID, signID, d.Id).get(0).Value);
                        likelihoodValue = Float.parseFloat( dao.getLikelihoodValue(animalID, signID, d.Id).get(0).Value)/100.0f;


                    } catch (IndexOutOfBoundsException iob)
                    {
                        Log.d("Diagnosis error ", "SignID "+signID+"DiseaseID "+d.Id+ "AnimalID "+animalID );
                    }
                }

                if (signPresence.equals("Not Present"))
                {
                    try
                    {

                        String stringl =  dao.getLikelihoodValue(animalID, signID, d.Id).get(0).Value;
                        Float floatl =Float.parseFloat( dao.getLikelihoodValue(animalID, signID, d.Id).get(0).Value);
                        likelihoodValue = 1.0f - Float.parseFloat( dao.getLikelihoodValue(animalID, signID, d.Id).get(0).Value)/100.0f;


                    } catch (IndexOutOfBoundsException iob)
                    {
                        Log.d("Diagnosis error ", "SignID "+signID+"DiseaseID "+d.Id+ "AnimalID "+animalID );
                    }
                }
                chainProbability*=likelihoodValue;


            }


            float prior = Float.parseFloat(dao.getPriorForDisease(animalID, d.Id).get(0).Probability);
            float posterior = chainProbability * prior;
            diagnosis.put(d.Name, posterior*100.0f);
        }

        HashMap<String, Float> n = normaliseDiagnoses(diagnosis);
        HashMap<String, Float> s= sortDiagnoses(n);

        return s;
    }

    private boolean checkIfDiseasesAffectsAnimal(ADDBDAO dao, int animalID, int id) {
        return !dao.getPriorForDisease(animalID,id).isEmpty();
    }

    public  HashMap<String, Float> normaliseDiagnoses(HashMap <String, Float> originalList)
    {
        Float sum =0.0f;
        for(Map.Entry e : originalList.entrySet())
        {
            sum+=(Float) e.getValue();
        }

        HashMap<String, Float> normalised = new HashMap<>();

        for(Map.Entry e : originalList.entrySet())
        {
            Float norm = (Float)e.getValue()/sum;
            norm*=100.0f;
            BigDecimal bd = new BigDecimal(Float.toString(norm));
            bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
            norm= bd.floatValue();

            normalised.put((String)e.getKey(), norm );
        }

        return normalised;
    }

    public HashMap<String, Float> sortDiagnoses(HashMap <String, Float> originalList )
    {
        List<Map.Entry<String, Float> > list =
                new LinkedList<Map.Entry<String, Float> >(originalList.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Float> >() {
            public int compare(Map.Entry<String, Float> o1,
                               Map.Entry<String, Float> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Float> temp = new LinkedHashMap<String, Float>();
        for (Map.Entry<String, Float> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }


    public HashMap<Signs, String> getSelectedSigns()
    {
        HashMap <Signs,String> selectedSigns = new HashMap<>();

        int index=0;
        for(RadioGroup group: mSignRadioGroups)
        {
            RadioButton selectedRadioButton = getView().findViewById( group.getCheckedRadioButtonId());

            selectedSigns.put(mSignsForAnimal.get(index), (String)selectedRadioButton.getText());
            index++;


        }
        return selectedSigns;
    }

    public HashMap<String, String> getSelectedSignsStrings()
    {
        HashMap <String,String> selectedSigns = new HashMap<>();

        int index=0;
        for(RadioGroup group: mSignRadioGroups)
        {
            RadioButton selectedRadioButton = getView().findViewById( group.getCheckedRadioButtonId());

            selectedSigns.put(mSignsForAnimal.get(index).Name, (String)selectedRadioButton.getText());
            index++;


        }
        return selectedSigns;
    }

    public void populateSignsContainer(LinearLayout signsContainer, List<Signs> signs)
    {
        int signCounter=0;

        mSignRadioGroups = new ArrayList<>();


        signsContainer.removeAllViews();
        for(Signs sign :signs)
        {
            TextView label = new TextView(getContext());
            label.setText(sign.Name);
            RadioGroup group = new RadioGroup(getContext());
            group.setOrientation(RadioGroup.HORIZONTAL);

            RadioButton presentRadioButton = new RadioButton(getContext());
            presentRadioButton.setText("Present");
            group.addView(presentRadioButton);

            RadioButton notPresentRadioButton = new RadioButton(getContext());
            group.addView(notPresentRadioButton);
            notPresentRadioButton.setText("Not Present");

            RadioButton notObservedRadioButton = new RadioButton(getContext());
            group.addView(notObservedRadioButton);
            notObservedRadioButton.setText("Not Observed");

            notObservedRadioButton.setChecked(true);

            signsContainer.addView(label);

            signsContainer.addView(group);

            mSignRadioGroups.add(group);

            signCounter++;
        }

        mSignsForAnimal = signs;
    }

    public void GoToResults(HashMap<String, Float> d)
    {
        Intent myIntent = new Intent(getContext(), ResultsActivity.class);
        myIntent.putExtra("species", mADDB.getADDBDAO().getAnimalNameFromID(mCurrentAnimalID).get(0));
        myIntent.putExtra("signs",getSelectedSignsStrings());
        myIntent.putExtra("diagnoses",d );

        startActivity(myIntent);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
