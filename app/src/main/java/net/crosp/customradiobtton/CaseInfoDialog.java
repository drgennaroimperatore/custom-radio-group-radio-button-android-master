package net.crosp.customradiobtton;


import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;

public class CaseInfoDialog extends DialogFragment implements
        MyCasesGeneralInfoTabFragment.OnFragmentInteractionListener,
        MyCasesSignsInfoTabFragment.OnFragmentInteractionListener,
        MyCasesTreatmentInfoFragment.OnFragmentInteractionListener{
    TabLayout mTabLayout;
    Button mCloseButton;
    ViewPager mViewPager;
    int mCaseID;
    FragmentManager mFm;



    public CaseInfoDialog()
    {

    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       View v = inflater.inflate(R.layout.case_details_popup,null);

        mTabLayout = v.findViewById(R.id.case_info_popup_tablayout);
        mViewPager = v.findViewById(R.id.case_info_popup_viewpager);

      //  mTabLayout.addTab(mTabLayout.newTab().setText("General Info"));
      //  mTabLayout.addTab(mTabLayout.newTab().setText("Signs Info"));
       // mTabLayout.addTab(mTabLayout.newTab().setText("Treatment Info"));




        final CasesInfoPopupFragmentAdapter casesInfoPopupFragmentAdapter =
                new CasesInfoPopupFragmentAdapter(getChildFragmentManager(), getArguments().getInt("caseId"));

       MyCasesGeneralInfoTabFragment generalInfoTabFragment = new MyCasesGeneralInfoTabFragment();
       generalInfoTabFragment.setArguments(getArguments());
        casesInfoPopupFragmentAdapter.addFrag(generalInfoTabFragment,"General Info");

        MyCasesSignsInfoTabFragment signsInfoTabFragment = new MyCasesSignsInfoTabFragment();
        signsInfoTabFragment.setArguments(getArguments());
        casesInfoPopupFragmentAdapter.addFrag(signsInfoTabFragment, "Signs Info");

        MyCasesTreatmentInfoFragment treatmentInfoFragment = new MyCasesTreatmentInfoFragment();
        treatmentInfoFragment.setArguments(getArguments());
        casesInfoPopupFragmentAdapter.addFrag(treatmentInfoFragment, "Treatment Info");


        mViewPager.setAdapter(casesInfoPopupFragmentAdapter);




        mTabLayout.setupWithViewPager(mViewPager);


        mCloseButton = v.findViewById(R.id.case_info_popup_close_button);
        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
