package com.example.hyacinth.recipeats.Model;

public class Credit {
    private int recipeId;
    private String recipeTitle, recipeLink, photoLink;

    public Credit(int recipeId, String recipeTitle, String recipeLink, String photoLink) {
        this.recipeId = recipeId;
        this.recipeTitle = recipeTitle;
        this.recipeLink = recipeLink;
        this.photoLink = photoLink;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public String getRecipeLink() {
        return recipeLink;
    }

    public String getPhotoLink() {
        return photoLink;
    }
}
