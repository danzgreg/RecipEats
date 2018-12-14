package com.example.hyacinth.recipeats;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hyacinth.recipeats.Database.DatabaseHelper;
import com.example.hyacinth.recipeats.Fragment.RecipeFragment;
import com.example.hyacinth.recipeats.Fragment.RecipeResultListFragment;
import com.example.hyacinth.recipeats.Interface.OnRecipeListInteraction;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity implements OnRecipeListInteraction,
        RecipeFragment.OnRecipeFragmentInteractionListener{

    private ArrayList<Integer> ingredientList;
    private String FRAGMENT_TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        ingredientList = getIntent().getExtras().getIntegerArrayList("ingredientList");
        for(int i = 0; i < ingredientList.size(); i++){
            Log.e("ingredientList: ", "" + ingredientList.get(i).toString());
        }

        RecipeResultListFragment fragment = new RecipeResultListFragment().newInstance(ingredientList);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        FRAGMENT_TAG = "IngredientList";
        ft.replace(R.id.flRecipe, fragment);
        ft.commit();
    }

    @Override
    public void onRecipeListInteraction(ImageView recipeImageView, TextView txtRecipeName, int recipeId, String recipeName, ArrayList<Integer> ingredientList) {
        Fragment previousFragment = getSupportFragmentManager().findFragmentById(R.id.flRecipe);
        RecipeFragment fragment = new RecipeFragment().newInstance(recipeId, ingredientList);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        //previous fragment enter transition
        Fade enterPreviousFade = new Fade();
        enterPreviousFade.setDuration(100);
        previousFragment.setEnterTransition(enterPreviousFade);

        //previous fragment exit transition
        Fade exitFade = new Fade();
        exitFade.setDuration(100);
        previousFragment.setExitTransition(exitFade);

        //new fragment enter transition
        Fade enterFade = new Fade();
        enterFade.setStartDelay(100);
        enterFade.setDuration(100);
        fragment.setEnterTransition(enterFade);

        //new fragment exit transition
        Fade exitNewFade = new Fade();
        exitNewFade.setDuration(100);
        fragment.setExitTransition(exitNewFade);

        ft.addSharedElement(recipeImageView, recipeName);
        ft.addSharedElement(txtRecipeName, "transName"+recipeId);
        FRAGMENT_TAG = "Recipe";
        ft.replace(R.id.flRecipe, fragment, FRAGMENT_TAG);
        ft.addToBackStack(FRAGMENT_TAG);
        ft.commit();
    }

    public void onRecipeFragmentInteraction(int recipeId){
        new DatabaseHelper(this).addToFavorites(recipeId);
    }

    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }
}
