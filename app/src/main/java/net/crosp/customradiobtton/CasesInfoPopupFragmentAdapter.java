package net.crosp.customradiobtton;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class CasesInfoPopupFragmentAdapter extends FragmentPagerAdapter {

    private int mCaseId;
    ArrayList<Fragment> mFragmentList = new ArrayList<>();
    ArrayList<String> mFragmentTitleList = new ArrayList<>();
    public CasesInfoPopupFragmentAdapter(FragmentManager fm, int caseID) {
        super(fm);
        mCaseId = caseID;

    }

    @Override
    public Fragment getItem(int i)
    {

       return mFragmentList.get(i);

    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {

        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

}
