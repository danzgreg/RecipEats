package com.example.hyacinth.recipeats.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.example.hyacinth.recipeats.Adapter.MyIngredientsRecyclerViewAdapter;
import com.example.hyacinth.recipeats.Database.DatabaseHelper;
import com.example.hyacinth.recipeats.Model.Ingredients;
import com.example.hyacinth.recipeats.Model.SelectedIngredients;
import com.example.hyacinth.recipeats.R;
import com.example.hyacinth.recipeats.SelectionActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class IngredientsFragment extends Fragment{

    // TODO: Customize parameters
    private int mColumnCount = 1;
    //private String category;

    private OnIngredientsFragmentInteractionListener mListener;
    private List<Ingredients> lstIngredient;
    private static List<SelectedIngredients> selectedIngredients = new ArrayList<>();
    private DatabaseHelper mDBHelper;
    private String selectedCategory;
    private MyIngredientsRecyclerViewAdapter adapter;
    private View view;

    public IngredientsFragment(/*String category*/) {
        //this.category = category;
    }

    /*
    public static IngredientsFragment newInstance(String categorySelected){
        IngredientsFragment fragment = new IngredientsFragment();

        Bundle args = new Bundle();
        args.putString("categorySelected", categorySelected);
        fragment.setArguments(args);
        return fragment;
    } */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true); //this line is a trial code

        //selectedCategory = getArguments().getString("categorySelected");
        ((SelectionActivity)getActivity()).setActionBarTitle("Ingredients");

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
        lstIngredient = mDBHelper.getListIngredient(null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.filter_none:
                this.selectedCategory = null;
                updateData();
                return true;
            case R.id.filter_bread:
                this.selectedCategory = "Bread";
                updateData();
                return true;
            case R.id.filter_chips:
                this.selectedCategory = "Chips";
                updateData();
                return true;
            case R.id.filter_condiments:
                this.selectedCategory = "Condiments";
                updateData();
                return true;
            case R.id.filter_confections:
                this.selectedCategory = "Confections";
                updateData();
                return true;
            case R.id.filter_eggs:
                this.selectedCategory = "Eggs";
                updateData();
                return true;
            case R.id.filter_fish:
                this.selectedCategory = "Fish";
                updateData();
                return true;
            case R.id.filter_fruits:
                this.selectedCategory = "Fruits";
                updateData();
                return true;
            case R.id.filter_grain:
                this.selectedCategory = "Grain";
                updateData();
                return true;
            case R.id.filter_herbs:
                this.selectedCategory = "Herbs";
                updateData();
                return true;
            case R.id.filter_legumes:
                this.selectedCategory = "Legumes";
                updateData();
                return true;
            case R.id.filter_liquid:
                this.selectedCategory = "Liquid";
                updateData();
                return true;
            case R.id.filter_meat:
                this.selectedCategory = "Meat";
                updateData();
                return true;
            case R.id.filter_noodles:
                this.selectedCategory = "Noodles";
                updateData();
                return true;
            case R.id.filter_pasta:
                this.selectedCategory = "Pasta";
                updateData();
                return true;
            case R.id.filter_powder:
                this.selectedCategory = "Powder";
                updateData();
                return true;
            case R.id.filter_seafoods:
                this.selectedCategory = "Seafoods";
                updateData();
                return true;
            case R.id.filter_vegetables:
                this.selectedCategory = "Vegetables";
                updateData();
                return true;
            case R.id.filter_wrapper:
                this.selectedCategory = "Wrapper";
                updateData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.ingredient_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.search_ingredient);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ingredients_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
            adapter = new MyIngredientsRecyclerViewAdapter(context, lstIngredient, this.selectedIngredients, mListener);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnIngredientsFragmentInteractionListener) {
            mListener = (OnIngredientsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnIngredientsFragmentInteractionListener");
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

    public void clearList(){
        selectedIngredients.clear();
        adapter.updateList(selectedIngredients);
        adapter.notifyDataSetChanged();
    }

    public void updateList(boolean removeItem, List<SelectedIngredients> selectedList){
        if(removeItem){
            selectedIngredients = selectedList;
            adapter.updateList(selectedIngredients);
            adapter.notifyDataSetChanged();
        }
        else{
            selectedIngredients = selectedList;
        }
    }

    public void updateData(){
        //mDBHelper.updateIngredientStatus(ingredient_id, isChecked);
        List<Ingredients> tempIngredient = mDBHelper.getListIngredient(selectedCategory);
        lstIngredient.clear();
        //Ingredients tempIng = new Ingredients(ingredient_id, ingredient_name, selectedCategory, isChecked);
        //lstIngredient.set(position, tempIng);
        lstIngredient.addAll(tempIngredient);
        adapter.notifyDataSetChanged();
    }

    public void makeToast(){
        Toast.makeText(getContext(), "Select at least one ingredient", Toast.LENGTH_SHORT).show();
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
    public interface OnIngredientsFragmentInteractionListener {
        // TODO: Update argument type and name
        void onIngredientsFragmentInteraction(List<SelectedIngredients> selectedList);
    }
}
