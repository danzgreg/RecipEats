package com.example.hyacinth.recipeats.Model;

import java.util.ArrayList;

public class Recipe {
    private int recipe_id;
    private String recipe_name;
    private String recipe_cooking_procedure;
    private String recipe_picture;
    private ArrayList<RecipeIngredients> recipeIngredients;
    private String recipe_status;
    private int ingredientCount;

    public Recipe(int recipe_id, String recipe_name, String recipe_picture) {
        this.recipe_id = recipe_id;
        this.recipe_name = recipe_name;
        this.recipe_picture = recipe_picture;
    }

    public Recipe(int recipe_id, String recipe_name, String recipe_picture, int ingredientCount) {
        this.recipe_id = recipe_id;
        this.recipe_name = recipe_name;
        this.recipe_picture = recipe_picture;
        this.ingredientCount = ingredientCount;
    }

    public Recipe(int recipe_id, String recipe_name, String recipe_picture, ArrayList<RecipeIngredients> recipeIngredients, String recipe_status) {
        this.recipe_id = recipe_id;
        this.recipe_name = recipe_name;
        this.recipe_picture = recipe_picture;
        this.recipeIngredients = recipeIngredients;
        this.recipe_status = recipe_status;
    }

    public Recipe(int recipe_id, String recipe_name, String recipe_cooking_procedure, String recipe_picture, ArrayList<RecipeIngredients> recipeIngredients, String recipe_status) {
        this.recipe_id = recipe_id;
        this.recipe_name = recipe_name;
        this.recipe_cooking_procedure = recipe_cooking_procedure;
        this.recipe_picture = recipe_picture;
        this.recipeIngredients = recipeIngredients;
        this.recipe_status = recipe_status;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public String getRecipe_name() {
        return recipe_name;
    }

    public String getRecipe_cooking_procedure() {
        return recipe_cooking_procedure;
    }

    public String getRecipe_picture() {
        return recipe_picture;
    }

    public ArrayList<RecipeIngredients> getRecipeIngredients() {
        return recipeIngredients;
    }

    public String getRecipe_status() {
        return recipe_status;
    }

    public int getIngredientCount() {
        return ingredientCount;
    }
}
