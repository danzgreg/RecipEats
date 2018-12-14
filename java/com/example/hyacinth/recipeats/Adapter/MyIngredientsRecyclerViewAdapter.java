package com.example.hyacinth.recipeats.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.hyacinth.recipeats.Fragment.IngredientsFragment.OnIngredientsFragmentInteractionListener;
import com.example.hyacinth.recipeats.Model.Ingredients;
import com.example.hyacinth.recipeats.Model.SelectedIngredients;
import com.example.hyacinth.recipeats.R;

import java.util.ArrayList;
import java.util.List;

public class MyIngredientsRecyclerViewAdapter extends RecyclerView.Adapter<MyIngredientsRecyclerViewAdapter.ViewHolder> implements Filterable{

    private List<Ingredients> mValues;
    private List<SelectedIngredients> selectedIngredients;
    private OnIngredientsFragmentInteractionListener mListener;
    private Context context;
    //copy of list containing all the items
    private List<Ingredients> mValuesFull;


    public MyIngredientsRecyclerViewAdapter(Context context, List<Ingredients> items, List<SelectedIngredients> selectedIngredients, OnIngredientsFragmentInteractionListener listener) {
        this.context = context;
        mValues = items;
        mListener = listener;
        this.selectedIngredients = new ArrayList<>(selectedIngredients);
        //initialize the copy
        mValuesFull = new ArrayList<>(items);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_ingredients, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.ingredient_id = mValues.get(position).getIngredient_id();
        holder.ingredient_name = mValues.get(position).getIngredient_name();
        holder.category_name = mValues.get(position).getCategory();

        holder.mIngredientNameView.setText(holder.ingredient_name);
        //holder.checkIngredient.setChecked(mValues.get(position).isChecked());

        if(contains(selectedIngredients, holder.ingredient_id)){
            holder.isChecked = true;
            holder.checkIngredient.setChecked(holder.isChecked);
        }
        else{
            holder.isChecked = false;
            holder.checkIngredient.setChecked(holder.isChecked);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    if(holder.isChecked){
                        for(int i = 0; i < selectedIngredients.size(); i++){
                            if(selectedIngredients.get(i).getSelectedIngredient_id() == holder.ingredient_id){
                                selectedIngredients.remove(i);
                            }
                        }
                        holder.isChecked = false;
                        holder.checkIngredient.setChecked(holder.isChecked);
                    }
                    else{
                        selectedIngredients.add(new SelectedIngredients(
                                mValues.get(position).getIngredient_id(),
                                mValues.get(position).getIngredient_name()));

                        holder.isChecked = true;
                        holder.checkIngredient.setChecked(holder.isChecked);
                    }
                    mListener.onIngredientsFragmentInteraction(selectedIngredients);
                }
            }
        });

        holder.checkIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.isChecked){
                    for(int i = 0; i < selectedIngredients.size(); i++){
                        if(selectedIngredients.get(i).getSelectedIngredient_id() == holder.ingredient_id){
                            selectedIngredients.remove(i);
                        }
                    }
                    holder.isChecked = false;
                    holder.checkIngredient.setChecked(holder.isChecked);
                }
                else{
                    selectedIngredients.add(new SelectedIngredients(
                            mValues.get(position).getIngredient_id(),
                            mValues.get(position).getIngredient_name()));

                    holder.isChecked = true;
                    holder.checkIngredient.setChecked(holder.isChecked);
                }
                mListener.onIngredientsFragmentInteraction(selectedIngredients);
            }
        });
        //setAnimation(holder.mView);
    }

    /*private void setAnimation(View viewToAnimate)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        *//*if (position > lastPosition)
        {*//*
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        viewToAnimate.startAnimation(animation);
        //lastPosition = position;
        //}
    }*/

    public boolean contains(List<SelectedIngredients> list, int id){
        for(SelectedIngredients item : list){
            if(item.getSelectedIngredient_id() == id){
                return true;
            }
        }
        return false;
    }

    public void updateList(List<SelectedIngredients> updatedIngredients){
        this.selectedIngredients = new ArrayList<>(updatedIngredients);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final View mView;
        //public final Button mIngredientNameView;
        public final CheckBox checkIngredient;
        public final TextView mIngredientNameView;

        public int ingredient_id;
        public String ingredient_name;
        public String category_name;
        public boolean isChecked;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIngredientNameView = view.findViewById(R.id.ingredient_name);
            checkIngredient = view.findViewById(R.id.check_ingredient);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Ingredients> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){
                filteredList.addAll(mValuesFull);
                //filteredList.addAll(mValues);
            }
            else{
                String filteredPattern = charSequence.toString().toLowerCase().trim();

                //for(Ingredients item : mValues){
                for(Ingredients item : mValuesFull){
                    if(item.getIngredient_name().toLowerCase().contains(filteredPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mValues.clear();
            mValues.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };
}