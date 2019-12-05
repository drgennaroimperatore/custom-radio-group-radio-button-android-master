package net.crosp.customradiobtton;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DiagnosisListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<DiagnosisListElement>> expandableListDetail;

    public DiagnosisListAdapter(Context context, List<String> expandableListTitle,
                                       HashMap<String, List<DiagnosisListElement>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public DiagnosisListElement getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition).mName;
        final String expandedListTextPerc = String.valueOf( getChild(listPosition, expandedListPosition).mPercentage)+"%";

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.disease_list_view_row, null);
        }
        TextView diseaseNameTextView = (TextView) convertView
                .findViewById(R.id.diseaseListNameTextView);
        diseaseNameTextView.setText(expandedListText);

        TextView diseasePercTextView = (TextView) convertView
                .findViewById(R.id.diseaseListPercentageTextView);
        diseasePercTextView.setText(expandedListTextPerc);

        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

    /* Context mContext;
    List<DiagnosisListElement> mDiagnosisList;
    public DiagnosisListAdapter(@NonNull Context context, ArrayList<DiagnosisListElement> objects) {




        mContext = context;
        mDiagnosisList = objects;
    }*/



    /* {

      /*  View listItem = convertView;

        if(listItem==null)
            listItem= LayoutInflater.from(mContext).inflate(R.layout.disease_list_view_row, parent,false);

        DiagnosisListElement currentDisease = mDiagnosisList.get(position);

        TextView diseaseNameTV= listItem.findViewById(R.id.diseaseListNameTextView);
        diseaseNameTV.setText(currentDisease.getName());

        TextView diseasePercTV= listItem.findViewById(R.id.diseaseListPercentageTextView);
        diseasePercTV.setText(String.valueOf(currentDisease.getPercentage() + "%"));


        return listItem;*/
    }



