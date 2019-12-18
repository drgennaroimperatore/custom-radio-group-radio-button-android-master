package net.crosp.customradiobtton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CasesListAdapter extends ArrayAdapter<Cases> {
    int mResource;
    Context mContext;
    public CasesListAdapter(@NonNull Context context,  int resource, ArrayList<Cases> list) {
        super(context, resource,list);
        mResource= resource;
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ADDB addb = ADDB.getInstance(mContext);
        ADDBDAO addbdao = addb.getADDBDAO();


        View v = convertView;
        if (v==null)
        {
            LayoutInflater inflater= LayoutInflater.from(mContext);
            v= inflater.inflate(mResource,null);

        }

        Cases c = (Cases) getItem(position);
        TextView caseNumberTv = v.findViewById(R.id.case_list_case_nr_tv);
        caseNumberTv.setText(String.valueOf(c.ID));

        TextView speciesTV = v.findViewById(R.id.case_list_animal_name_tv);
        speciesTV.setText(addbdao.getAnimalNameFromID(c.AnimalID).get(0));

        TextView dateCaseObservedTV = v.findViewById(R.id.case_list_date_observed_tv);
        TextView dateCaseLoggedTV =v.findViewById(R.id.case_list_date_logged_tv);

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        try {
            dateCaseObservedTV.setText( formatter.format(c.DateCaseObserved));
            dateCaseLoggedTV.setText(formatter.format(c.DateCaseLogged));
        }
        catch (Exception e)
        {

        }

     /*   TextView userChosenDiseaseTV = v.findViewById(R.id.case_list_user_disease_tv);
        String diseaseChosenByUser =addbdao.getDiseaseNameFromId(c.DiseaseChosenByUserID).get(0);
        userChosenDiseaseTV.setText(diseaseChosenByUser);*/





        return v;
    }

    @Override
    public int getPosition(@Nullable Cases item) {
        return super.getPosition(item);
    }

    @Nullable
    @Override
    public Cases getItem(int position) {
        return super.getItem(position);
    }
}