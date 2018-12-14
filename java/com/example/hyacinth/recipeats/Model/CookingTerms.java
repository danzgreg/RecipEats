package com.example.hyacinth.recipeats.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class CookingTerms implements Parcelable {

    public static final Creator CREATOR = new Creator(){
        public CookingTerms createFromParcel(Parcel in){
            return new CookingTerms(in);
        }
        public CookingTerms[] newArray(int size){
            return new CookingTerms[size];
        }
    };

    private int cookingTermId;
    private String cookingTermName;
    private String cookingTermDescription;
    private String cookingTermImage;

    public CookingTerms(int cookingTermId, String cookingTermName) {
        this.cookingTermId = cookingTermId;
        this.cookingTermName = cookingTermName;
    }

    public CookingTerms(int cookingTermId, String cookingTermName, String cookingTermDescription, String cookingTermImage) {
        this.cookingTermId = cookingTermId;
        this.cookingTermName = cookingTermName;
        this.cookingTermDescription = cookingTermDescription;
        this.cookingTermImage = cookingTermImage;
    }

    public int getCookingTermId() {
        return cookingTermId;
    }

    public void setCookingTermId(int cookingTermId) {
        this.cookingTermId = cookingTermId;
    }

    public String getCookingTermName() {
        return cookingTermName;
    }

    public void setCookingTermName(String cookingTermName) {
        this.cookingTermName = cookingTermName;
    }

    public String getCookingTermDescription() {
        return cookingTermDescription;
    }

    public void setCookingTermDescription(String cookingTermDescription) {
        this.cookingTermDescription = cookingTermDescription;
    }

    public String getCookingTermImage() {
        return cookingTermImage;
    }

    public void setCookingTermImage(String cookingTermImage) {
        this.cookingTermImage = cookingTermImage;
    }

    public CookingTerms(Parcel in){
        this.cookingTermId = in.readInt();
        this.cookingTermName = in.readString();
        this.cookingTermDescription = in.readString();
        this.cookingTermImage = in.readString();
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.cookingTermId);
        parcel.writeString(this.cookingTermName);
        parcel.writeString(this.cookingTermDescription);
        parcel.writeString(this.cookingTermImage);
    }
}
