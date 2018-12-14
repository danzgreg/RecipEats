package com.example.hyacinth.recipeats.Adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.hyacinth.recipeats.Model.RecipeIngredients;
import com.example.hyacinth.recipeats.R;

import java.util.ArrayList;

public class RecipeIngredientsRecyclerViewAdapter extends RecyclerView.Adapter<RecipeIngredientsRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<RecipeIngredients> mValues;
    private final ArrayList<Integer> selectedIng;

    public RecipeIngredientsRecyclerViewAdapter(ArrayList<RecipeIngredients> items, ArrayList<Integer> selectedIngredients) {
        mValues = items;
        selectedIng = selectedIngredients;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_ingredients_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(selectedIng != null){
            if(contains(selectedIng, mValues.get(position).getIngredientId())){
                //holder.ingredientCheckbox.setChecked(true);
                holder.txtIngredientName.setTextColor(Color.parseColor("#FF6600"));
            }
        }

        //holder.txtIngredientQuantity.setText(mValues.get(position).getIngredientQuantity());
        holder.txtIngredientName.setText(mValues.get(position).getIngredientQuantity()+" "+mValues.get(position).getIngredientName());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        //public final CheckBox ingredientCheckbox;
        public final TextView/* txtIngredientQuantity,*/ txtIngredientName;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            //ingredientCheckbox = view.findViewById(R.id.recipe_ingredient_checkbox);
            //txtIngredientQuantity = view.findViewById(R.id.txt_ingredient_quantity);
            txtIngredientName = view.findViewById(R.id.txt_ingredient_name);
        }

    }

    public boolean contains(ArrayList<Integer> list, int ingredientId){
        for(Integer item : list){
            if(item == ingredientId){
                return true;
            }
        }
        return false;
    }
}
