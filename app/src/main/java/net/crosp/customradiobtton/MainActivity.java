package net.crosp.customradiobtton;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;

import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity implements
        DiagnoserFragment.OnFragmentInteractionListener,
        NavigationFragment.OnFragmentInteractionListener,
        MyCasesFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener,
        MyCasesGeneralInfoTabFragment.OnFragmentInteractionListener
{

    BottomNavigationView mNavigationBar;

    DiagnoserFragment mDiagnoserFragment;
    MyCasesFragment mMyCasesFragment;
    ProfileFragment mProfileFragment;

    public enum FRAGMENT_LIST { DIAGNOSER, CASES, PROFILE};
    public FRAGMENT_LIST mCurrentFragment = FRAGMENT_LIST.DIAGNOSER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

        try {
            new KMZParser(this).execute(getAssets().open("eth.kml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        mCurrentFragment = FRAGMENT_LIST.DIAGNOSER;

        DiagnoserFragment diagnoserFragment = new DiagnoserFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_fragment,diagnoserFragment)
                .addToBackStack(null).commit();



        mNavigationBar = findViewById(R.id.bottom_navigation_bar);

        mNavigationBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.navigation_diagnoser:
                        if (mCurrentFragment == FRAGMENT_LIST.DIAGNOSER)
                            return false;
                        goToDiagnoserFragment();
                       // Toast.makeText(getApplicationContext(),"Diagnoser Clicked", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.navigation_my_cases:
                        if (mCurrentFragment == FRAGMENT_LIST.CASES)
                            return false;
                        goToCasesFragment();
                        //Toast.makeText(getApplicationContext(),"My Cases Clicked", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.navigation_profile:
                        if (mCurrentFragment == FRAGMENT_LIST.PROFILE)
                            return false;
                        goToProfileFragment();
                       // Toast.makeText(getApplicationContext(),"Profile Clicked", Toast.LENGTH_LONG).show();
                        break;
                }

                return false;
            }
        });


    }
    //original code for diagnosis


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void goToDiagnoserFragment()
    {
        mCurrentFragment = FRAGMENT_LIST.DIAGNOSER;
        DiagnoserFragment diagnoserFragment = new DiagnoserFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment,diagnoserFragment)
                .addToBackStack(null)
                .commit();


    }

    public void goToCasesFragment()
    {
        mCurrentFragment = FRAGMENT_LIST.CASES;

        MyCasesFragment casesFragment = new MyCasesFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment,casesFragment)
                .addToBackStack(null).commit();



    }

    public void goToProfileFragment()
    {
        mCurrentFragment = FRAGMENT_LIST.PROFILE;

        ProfileFragment profileFragment = new ProfileFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment,profileFragment)
                .addToBackStack(null).commit();

    }
}

