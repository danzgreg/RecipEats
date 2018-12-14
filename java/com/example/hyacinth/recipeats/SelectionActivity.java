package com.example.hyacinth.recipeats;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.hyacinth.recipeats.Fragment.DialogSelectionFragment;
import com.example.hyacinth.recipeats.Fragment.IngredientsFragment;
import com.example.hyacinth.recipeats.Model.SelectedIngredients;

import java.util.ArrayList;
import java.util.List;


public class SelectionActivity extends AppCompatActivity implements
        IngredientsFragment.OnIngredientsFragmentInteractionListener,
        DialogSelectionFragment.OnDialogSelectionFragmentInteractionListener{

    private static ArrayList<SelectedIngredients> selectedIngredients = new ArrayList<>();
    private static String FRAGMENT_TAG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        IngredientsFragment fragment = new IngredientsFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        /*Fade enterFade = new Fade();
        enterFade.setDuration(100);
        fragment.setEnterTransition(enterFade);*/

        FRAGMENT_TAG = "IngredientFragmentTag";
        ft.replace(R.id.flSelection, fragment, FRAGMENT_TAG);
        ft.commit();

        FloatingActionButton fab = findViewById(R.id.fab_selected);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedIngredients.isEmpty()){
                    IngredientsFragment fragment = (IngredientsFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
                    fragment.makeToast();
                }
                else{
                    DialogFragment mDialog = new DialogSelectionFragment().newInstance(selectedIngredients);
                    mDialog.show(getSupportFragmentManager(), "selectedIngredients");
                }
            }
        });
    }

    /*
    public void onCategoryFragmentInteraction(String position){
        Log.e("Error", position);
        this.pos = position;

        IngredientsFragment fragment = new IngredientsFragment().newInstance(position);
        //ingFrag.newInstance(position);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        this.IngredientsFragmentTag = "IngredientFragmentTag";
        ft.replace(R.id.flSelection, fragment, this.IngredientsFragmentTag);
        ft.addToBackStack(null);
        ft.commit();
    } */

    public void onIngredientsFragmentInteraction(List<SelectedIngredients> selectedList){
        selectedIngredients = new ArrayList<>(selectedList);

        IngredientsFragment fragment = (IngredientsFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        //fragment.updateData(ingredient_id, isChecked);
        fragment.updateList(false, selectedList);
    }

    public void clearList(){
        selectedIngredients.clear();
        IngredientsFragment fragment = (IngredientsFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        fragment.clearList();
    }

    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    public boolean contains(ArrayList<SelectedIngredients> list, int id){
        for(SelectedIngredients item : list){
            if(item.getSelectedIngredient_id() == id){
                return true;
            }

        }
        return false;
    }

    public void searchCompatibleRecipe(ArrayList<Integer> selectedIngredients){
        for (int i = 0; i < selectedIngredients.size(); i++){
            Log.e("searchCompatibleRecipe", "id: " + selectedIngredients.get(i).toString());
        }
        Intent i = new Intent(SelectionActivity.this, RecipeActivity.class);
        i.putIntegerArrayListExtra("ingredientList", selectedIngredients);
        startActivity(i);
    }

    public void onDialogSelectionFragmentInteraction(ArrayList<SelectedIngredients> selectedList){
        //selectedIngredients.clear();
        selectedIngredients = selectedList;
        IngredientsFragment fragment = (IngredientsFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        fragment.updateList(true, selectedList);
    }
}
