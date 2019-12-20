package net.crosp.customradiobtton;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.room.Room;

import java.util.List;
import java.util.UUID;

public class RegistrationActivity extends AppCompatActivity {

    Button mRegistrationButton;
    EditText mFirstNameET, mSecondNameET;
    UserInfoDB mUserInfoDB;
    UserInfoDBDao mUserInfoDBDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mUserInfoDB = UserInfoDB.getInstance(this);
        mUserInfoDBDao = mUserInfoDB.getDao();

        boolean userIsRegistered = isRegistrationComplete();

        if(userIsRegistered)
            GoToMain();

        mFirstNameET = findViewById(R.id.registration_firstName_et);
        mSecondNameET = findViewById(R.id.registration_secondName_et);

        mRegistrationButton = findViewById(R.id.registration_submit_button);
        mRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstName = mFirstNameET.getText().toString();
                String secondName = mSecondNameET.getText().toString();

                if(firstName.equals("") || secondName.equals(""))
                {
                    Toast.makeText(getBaseContext(), "Please Complete All Fields", Toast.LENGTH_LONG).show();
                    return;
                }

                if(!isRegistrationComplete())
                    registerUser(firstName,secondName);

                GoToMain();
            }
        });

    }

    public void registerUser(String fn, String sn)
    {
        UserInfo ui = new UserInfo();
        ui.FirstName= fn;
        ui.SecondName = sn;
        ui.UUID = generateUUID();

        mUserInfoDBDao.InsertUserInfo(ui);

    }

    public void GoToMain()
    {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
        finish();
    }

    public boolean isRegistrationComplete()
    {
        List<UserInfo> ui = mUserInfoDBDao.getUserInfo();

        return ui.size()==1;
    }

    public String generateUUID ()
    {
        UUID uniqueID = UUID.randomUUID();
        return uniqueID.toString();
    }
}
