package net.crosp.customradiobtton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MyCasesDiagnosisInfoTabFragmentListViewAdapter extends ArrayAdapter {

    List<ResultsForCase> mData;
    Context mContext;
    private int mResource;

    public MyCasesDiagnosisInfoTabFragmentListViewAdapter(@NonNull Context context,
                                                          int resource,
                                                          @NonNull List<ResultsForCase> data) {
        super(context, resource, data);
        mData= data;
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource,null);

            TextView diagnosisNameTV = convertView.findViewById(R.id.diseaseListNameTextView);

          String diseaseName = ADDB.getInstance(mContext)
                  .getADDBDAO()
                  .getDiseaseNameFromId(mData.get(position).DiseaseID)
                  .get(0);

            diagnosisNameTV.setText(diseaseName);
            TextView diagnosisPercentage = convertView.findViewById(R.id.diseaseListPercentageTextView);
            diagnosisPercentage.setText(String.valueOf(mData.get(position).PredictedLikelihoodOfDisease)+"%");

        }

        return convertView;


    }
}
