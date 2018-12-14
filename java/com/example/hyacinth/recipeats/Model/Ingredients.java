package com.example.hyacinth.recipeats.Model;

public class Ingredients {
    private int ingredient_id;
    private String ingredient_name;
    private String category;

    public Ingredients(int ingredient_id, String ingredient_name, String category) {
        this.ingredient_id = ingredient_id;
        this.ingredient_name = ingredient_name;
        this.category = category;
    }

    public int getIngredient_id() {
        return ingredient_id;
    }

    public void setIngredient_id(int ingredient_id) {
        this.ingredient_id = ingredient_id;
    }

    public String getIngredient_name() {
        return ingredient_name;
    }

    public void setIngredient_name(String ingredient_name) {
        this.ingredient_name = ingredient_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
