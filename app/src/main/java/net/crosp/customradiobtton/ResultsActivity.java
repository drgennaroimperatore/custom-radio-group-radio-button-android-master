package net.crosp.customradiobtton;

import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.support.v7.app.AppCompatActivity;

public class ResultsActivity extends AppCompatActivity {

    Button mPrevButton;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        mPrevButton =(Button) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                GoToMain();

            }
        });
    }

    public void GoToMain()
    {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
    }
}
