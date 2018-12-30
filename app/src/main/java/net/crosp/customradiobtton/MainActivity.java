package net.crosp.customradiobtton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    PresetRadioGroup mSetDurationPresetRadioGroup;
    PresetRadioGroup mSetPresetRadioGroup;
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
    }
}

