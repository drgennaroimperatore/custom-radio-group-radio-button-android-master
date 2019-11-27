package net.crosp.customradiobtton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DiagnosisListAdapter extends ArrayAdapter<DiagnosisListElement> {
    Context mContext;
    List<DiagnosisListElement> mDiagnosisList;
    public DiagnosisListAdapter(@NonNull Context context, ArrayList<DiagnosisListElement> objects) {


        super(context, 0, objects);

        mContext = context;
        mDiagnosisList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;

        if(listItem==null)
            listItem= LayoutInflater.from(mContext).inflate(R.layout.disease_list_view_row, parent,false);

        DiagnosisListElement currentDisease = mDiagnosisList.get(position);

        TextView diseaseNameTV= listItem.findViewById(R.id.diseaseListNameTextView);
        diseaseNameTV.setText(currentDisease.getName());

        TextView diseasePercTV= listItem.findViewById(R.id.diseaseListPercentageTextView);
        diseasePercTV.setText(String.valueOf(currentDisease.getPercentage() + "%"));




        return listItem;
    }
}
