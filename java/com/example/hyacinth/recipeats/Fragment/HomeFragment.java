package com.example.hyacinth.recipeats.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hyacinth.recipeats.Adapter.MyHomeRecyclerViewAdapter;
import com.example.hyacinth.recipeats.Database.DatabaseHelper;
import com.example.hyacinth.recipeats.MainActivity;
import com.example.hyacinth.recipeats.Model.Recipe;
import com.example.hyacinth.recipeats.R;
import com.like.LikeButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class HomeFragment extends Fragment {

    // TODO: Customize parameters
    private int mColumnCount = 1;
    //private static ArrayList<Integer> randomNumber;

    private OnHomeFragmentInteractionListener mListener;
    private List<Recipe> recommendedRecipe;
    //private List<RecipeIngredients> recipeIng;
    private DatabaseHelper mDBHelper;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_list, container, false);
        ((MainActivity)getActivity()).setActionBarTitle("Home");

        mDBHelper = new DatabaseHelper(getActivity());

        File database = getActivity().getDatabasePath(DatabaseHelper.DBNAME);
        if(false == database.exists()){
            mDBHelper.getReadableDatabase();

            if(copyDatabase(getActivity())){
                Log.e("Database: ", "Copy database success");
            }
            else{
                Log.e("Database: ", "Copy database error");
                //return;
            }
        }

        recommendedRecipe = mDBHelper.getRecommendedRecipe();

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyHomeRecyclerViewAdapter(recommendedRecipe, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnHomeFragmentInteractionListener) {
            mListener = (OnHomeFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private boolean copyDatabase(Context context){
        try{
            InputStream inputStream = context.getAssets().open(DatabaseHelper.DBNAME);
            String outFileName = DatabaseHelper.DBLOCATION + DatabaseHelper.DBNAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[]buff = new byte[1024];
            int length;
            while ((length = inputStream.read(buff)) > 0){
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.v("MainActivity", "DB copied");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnHomeFragmentInteractionListener {
        // TODO: Update argument type and name
        void onHomeFragmentInteraction(ImageView recommendedImage, TextView recommendedName, LikeButton btnFav, int recipeId, String recipeName);
        void onAddToFavoritesInteraction(int recipeId);
    }
}
