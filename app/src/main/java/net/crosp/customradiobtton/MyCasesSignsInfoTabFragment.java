package net.crosp.customradiobtton;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyCasesSignsInfoTabFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyCasesSignsInfoTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyCasesSignsInfoTabFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView mLVSignsPresent, mLVSignsNotPresent;

    private OnFragmentInteractionListener mListener;

    public MyCasesSignsInfoTabFragment() {
        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyCasesSignsInfoTabFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyCasesSignsInfoTabFragment newInstance(String param1, String param2) {
        MyCasesSignsInfoTabFragment fragment = new MyCasesSignsInfoTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_cases_signs_info_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLVSignsNotPresent = (ListView) view.findViewById(R.id.not_present_signs_signsInfoTabList);
        mLVSignsPresent = (ListView) view.findViewById(R.id.present_signs_signsInfoTabList);

        //set adapters
        ArrayList<String> npSigns = new ArrayList<>();
        ArrayList<String> pSigns = new ArrayList<>();

      List<SignsForCase> signsForCase =
              CasesDB.getInstance(getContext()).
                      getmCasesDBDAO().
                      getAllSignsForCase((int) getArguments().getInt("caseId"));

      if (signsForCase.isEmpty())
          return;

      for(SignsForCase sign: signsForCase)
      {
          String signName = ADDB.getInstance(getContext()).getADDBDAO().getSignNameFromID(sign.SignID).get(0);

          if(signName.length()>=11) {
              signName = signName.subSequence(0, 11).toString();
              signName+="...";
          }

          if (sign.Presence == SignPresence.PRESENT)
              pSigns.add(signName);
          if(sign.Presence == SignPresence.NOT_PRESENT)
              npSigns.add(signName);

      }

      if(pSigns.isEmpty())
          pSigns.add("NONE GIVEN");
      if(npSigns.isEmpty())
          npSigns.add("NONE GIVEN");

        ArrayAdapter<String> presentSignsAdapter = new ArrayAdapter<>(getContext(), R.layout.case_review_present_notpresent_list_row, pSigns);
        ArrayAdapter<String> notPresentSignsAdapter = new ArrayAdapter<>(getContext(), R.layout.case_review_present_notpresent_list_row,npSigns);

        mLVSignsPresent.setAdapter(presentSignsAdapter);
        mLVSignsNotPresent.setAdapter(notPresentSignsAdapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
