package com.example.hyacinth.recipeats.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hyacinth.recipeats.Model.CookingTerms;
import com.example.hyacinth.recipeats.R;

import java.util.ArrayList;

public class CookingTechniqueFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private TextView txtCookingTechniqueName, txtCookingTechniqueDescription;

    private ArrayList<CookingTerms> cookingTechnique;
    private View view;

    //private OnFragmentInteractionListener mListener;

    public CookingTechniqueFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CookingTechniqueFragment newInstance(ArrayList<CookingTerms> cookingTechnique) {
        CookingTechniqueFragment fragment = new CookingTechniqueFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("cookingTechnique", cookingTechnique);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cookingTechnique = getArguments().getParcelableArrayList("cookingTechnique");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cooking_technique, container, false);
        txtCookingTechniqueName = view.findViewById(R.id.txt_cooking_term_header);
        txtCookingTechniqueDescription = view.findViewById(R.id.txt_cooking_term_description);

        txtCookingTechniqueName.setText(cookingTechnique.get(0).getCookingTermName());
        txtCookingTechniqueDescription.setText(cookingTechnique.get(0).getCookingTermDescription());
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    /*public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
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
    /*public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
