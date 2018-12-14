package com.example.hyacinth.recipeats.Model;

import java.util.ArrayList;
import java.util.List;

public class SectionModel {
    private String sectionLabel;
    private List<Recipe> itemArrayList;

    public SectionModel(String sectionLabel, List<Recipe> itemArrayList) {
        this.sectionLabel = sectionLabel;
        this.itemArrayList = itemArrayList;
    }

    public String getSectionLabel() {
        return sectionLabel;
    }

    public List<Recipe> getItemArrayList() {
        return itemArrayList;
    }
}
