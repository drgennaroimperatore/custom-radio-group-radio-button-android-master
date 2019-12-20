package net.crosp.customradiobtton;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

public class CaseInfoDialog extends Dialog {
    TabLayout mTabLayout;
    Button mCloseButton;
    public CaseInfoDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.case_details_popup);
        mTabLayout = findViewById(R.id.case_info_popup_tablayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("General Info"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Signs Info"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Treatment Info"));

        mCloseButton = findViewById(R.id.case_info_popup_close_button);
        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}
