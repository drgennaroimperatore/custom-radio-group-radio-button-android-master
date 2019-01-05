package net.crosp.customradiobtton;

import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class ResultsActivity extends AppCompatActivity {

    Button mPrevButton;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
    }

    public void GoToMain()
    {
        Intent myIntent = new Intent(this, ResultsActivity.class);
        startActivity(myIntent);
    }
}
