package com.example.hyacinth.recipeats.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipeIngredients implements Parcelable {
    private int ingredientId;
    private String ingredientQuantity;
    private String ingredientName;

    public static final Creator CREATOR = new Creator(){
        public RecipeIngredients createFromParcel(Parcel in){
            return new RecipeIngredients(in);
        }
        public SelectedIngredients[] newArray(int size){
            return new SelectedIngredients[size];
        }
    };

    public RecipeIngredients(int ingredientId, String ingredientQuantity, String ingredientName) {
        this.ingredientId = ingredientId;
        this.ingredientQuantity = ingredientQuantity;
        this.ingredientName = ingredientName;
    }

    public int getIngredientId(){
        return ingredientId;
    }

    public String getIngredientQuantity() {
        return ingredientQuantity;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public RecipeIngredients(Parcel in){
        this.ingredientId = in.readInt();
        this.ingredientQuantity = in.readString();
        this.ingredientName = in.readString();
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.ingredientId);
        parcel.writeString(this.ingredientQuantity);
        parcel.writeString(this.ingredientName);
    }
}
