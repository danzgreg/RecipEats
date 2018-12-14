package com.example.hyacinth.recipeats.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hyacinth.recipeats.Adapter.MyRecipeListAdapter;
import com.example.hyacinth.recipeats.Adapter.SectionRecyclerViewAdapter;
import com.example.hyacinth.recipeats.Database.DatabaseHelper;
import com.example.hyacinth.recipeats.Interface.OnRecipeListInteraction;
import com.example.hyacinth.recipeats.Model.Recipe;
import com.example.hyacinth.recipeats.Model.SectionModel;
import com.example.hyacinth.recipeats.R;
import com.example.hyacinth.recipeats.RecipeActivity;

import java.util.ArrayList;
import java.util.List;


public class RecipeResultListFragment extends Fragment {

    // TODO: Customize parameters
    private int mColumnCount = 1;

    private List<Recipe> recipeList;
    //private List<Recipe> itemArrayList = new ArrayList<>();
    private ArrayList<Integer> ingredientList = new ArrayList<>();
    private DatabaseHelper mDBHelper;

    private OnRecipeListInteraction mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeResultListFragment() {
    }

    public static RecipeResultListFragment newInstance(ArrayList<Integer> ingredientSelected){
        RecipeResultListFragment fragment = new RecipeResultListFragment();

        Bundle args = new Bundle();
        args.putIntegerArrayList("ingredientList", ingredientSelected);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((RecipeActivity)getActivity()).setActionBarTitle("Recipe Results");

        mDBHelper = new DatabaseHelper(getActivity());
        ingredientList = getArguments().getIntegerArrayList("ingredientList");
        //recipeList = mDBHelper.getResultRecipe(ingredientList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        ArrayList<SectionModel> sectionModelArrayList = new ArrayList<>();

        //for loop for sections
        /*for (int i = ingredientList.size(); i >= 1; i--) {
            List<Recipe> itemArrayList = new ArrayList<>();

            for(int x = 0; x < recipeList.size(); x++){
                if(recipeList.get(x).getIngredientCount() == i){
                    itemArrayList.add(recipeList.get(x));
                }
            }

            if(itemArrayList.size() != 0){
                //add the section and items to array list
                sectionModelArrayList.add(new SectionModel("Matched with " + i + " ingredient(s)", itemArrayList));
            }
        }*/
        sectionModelArrayList = mDBHelper.getResultRecipe(ingredientList);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new SectionRecyclerViewAdapter(context, sectionModelArrayList, ingredientList, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipeListInteraction) {
            mListener = (OnRecipeListInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((RecipeActivity)getActivity()).setActionBarTitle("Recipe Results");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
