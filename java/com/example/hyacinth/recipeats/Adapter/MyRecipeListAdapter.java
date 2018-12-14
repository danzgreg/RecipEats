package com.example.hyacinth.recipeats.Adapter;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.example.hyacinth.recipeats.Interface.OnRecipeListInteraction;
import com.example.hyacinth.recipeats.Model.Recipe;
import com.example.hyacinth.recipeats.R;
import com.example.hyacinth.recipeats.ThirdPartyModule.GlideApp;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MyRecipeListAdapter extends RecyclerView.Adapter<MyRecipeListAdapter.ViewHolder> implements Filterable {

    private final List<Recipe> mValues;
    private final OnRecipeListInteraction mListener;
    private final Context context;
    private final List<Recipe> mValuesFull;
    private final ArrayList<Integer> ingredientList;
    //private int lastPosition = -1;

    public MyRecipeListAdapter(Context context, List<Recipe> items, ArrayList<Integer> ingredientList, OnRecipeListInteraction listener) {
        mValues = items;
        this.ingredientList = ingredientList;
        mListener = listener;
        this.context = context;
        mValuesFull = new ArrayList<>(items);
    }

    @Override
    public MyRecipeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_item, parent, false);
        return new MyRecipeListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyRecipeListAdapter.ViewHolder holder, final int position) {
        holder.recipeId = mValues.get(position).getRecipe_id();
        holder.recipeName = mValues.get(position).getRecipe_name();
        holder.recipePicture = mValues.get(position).getRecipe_picture();

        //holder.imgRecipeImage.setImageResource(getId(holder.recipePicture, R.drawable.class));
        GlideApp
                .with(holder.mView)
                .load(getId(holder.recipePicture, R.drawable.class))
                .apply(RequestOptions.circleCropTransform())
                .into(holder.imgRecipeImage);
        ViewCompat.setTransitionName(holder.imgRecipeImage, holder.recipeName);

        holder.txtRecipeName.setText(holder.recipeName);
        ViewCompat.setTransitionName(holder.txtRecipeName, "transName"+holder.recipeId);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onRecipeListInteraction(holder.imgRecipeImage, holder.txtRecipeName, holder.recipeId, holder.recipeName, ingredientList);
                }
            }
        });

        setAnimation(holder.mView);
    }

    private void setAnimation(View viewToAnimate)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        /*if (position > lastPosition)
        {*/
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        viewToAnimate.startAnimation(animation);
        //lastPosition = position;
        //}
    }

    public static int getId(String resourceName, Class<?> c){
        try{
            Field idField = c.getDeclaredField(resourceName);
            return idField.getInt(idField);
        }
        catch (Exception e){
            throw new RuntimeException("No resource id found");
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        //public final Button btnAllRecipe;
        public final TextView txtRecipeName;
        public final ImageView imgRecipeImage;

        public int recipeId;
        public String recipeName;
        public String recipePicture;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            imgRecipeImage = view.findViewById(R.id.rounded_recipe_image);
            txtRecipeName = view.findViewById(R.id.txt_recipe_name);
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
            List<Recipe> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){
                filteredList.addAll(mValuesFull);
            }
            else{
                String filteredPattern = charSequence.toString().toLowerCase().trim();

                for(Recipe item : mValuesFull){
                    if(item.getRecipe_name().toLowerCase().contains(filteredPattern)){
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
