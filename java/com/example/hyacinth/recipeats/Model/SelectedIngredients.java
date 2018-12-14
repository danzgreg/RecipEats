package com.example.hyacinth.recipeats.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class SelectedIngredients implements Parcelable{

    public static final Creator CREATOR = new Creator(){
        public SelectedIngredients createFromParcel(Parcel in){
            return new SelectedIngredients(in);
        }
        public SelectedIngredients[] newArray(int size){
            return new SelectedIngredients[size];
        }
    };

    private int selectedIngredient_id;
    private String selectedIngredient_name;

    public SelectedIngredients(int selectedIngredient_id, String selectedIngredient_name) {
        this.selectedIngredient_id = selectedIngredient_id;
        this.selectedIngredient_name = selectedIngredient_name;
    }

    public int getSelectedIngredient_id() {
        return selectedIngredient_id;
    }

    public void setSelectedIngredient_id(int selectedIngredient_id) {
        this.selectedIngredient_id = selectedIngredient_id;
    }

    public String getSelectedIngredient_name() {
        return selectedIngredient_name;
    }

    public void setSelectedIngredient_name(String selectedIngredient_name) {
        this.selectedIngredient_name = selectedIngredient_name;
    }

    public SelectedIngredients(Parcel in){
        this.selectedIngredient_id = in.readInt();
        this.selectedIngredient_name = in.readString();
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.selectedIngredient_id);
        parcel.writeString(this.selectedIngredient_name);
    }
}
