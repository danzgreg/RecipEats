package com.example.hyacinth.recipeats.Interface;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.hyacinth.recipeats.Model.RecipeIngredients;

import java.util.ArrayList;

public interface OnRecipeListInteraction {
    void onRecipeListInteraction(ImageView recipeImage, TextView txtRecipeName,
                                            int recipeId, String recipeName, ArrayList<Integer> ingredientList);
}
