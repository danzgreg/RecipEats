package com.example.hyacinth.recipeats.Adapter;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hyacinth.recipeats.Fragment.HomeFragment.OnHomeFragmentInteractionListener;
import com.example.hyacinth.recipeats.Model.Recipe;
import com.example.hyacinth.recipeats.Model.RecipeIngredients;
import com.example.hyacinth.recipeats.R;
import com.example.hyacinth.recipeats.ThirdPartyModule.GlideApp;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class MyHomeRecyclerViewAdapter extends RecyclerView.Adapter<MyHomeRecyclerViewAdapter.ViewHolder> {

    private final List<Recipe> mValues;
    private final OnHomeFragmentInteractionListener mListener;

    public MyHomeRecyclerViewAdapter(List<Recipe> items, OnHomeFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Recipe recipe = mValues.get(position);
        //holder.mItem = mValues.get(position);
        String ingredients = "";
        String ingredientQuantity = "";
        holder.recipeIng = mValues.get(position).getRecipeIngredients();
        for(int i = 0; i < holder.recipeIng.size(); i++){
            ingredients += holder.recipeIng.get(i).getIngredientQuantity() + " " + holder.recipeIng.get(i).getIngredientName() + "\n";

            /*ingredientQuantity += holder.recipeIng.get(i).getIngredientQuantity() + "\n";
            ingredients += holder.recipeIng.get(i).getIngredientName() + "\n";*/
            //Log.e("ingredient quantity", holder.recipeIng.get(i).getIngredientQuantity());
            //Log.e("ingredient name", holder.recipeIng.get(i).getIngredientName());
        }
        holder.recipeName = mValues.get(position).getRecipe_name();
        holder.recipePicture = mValues.get(position).getRecipe_picture();

        GlideApp
                .with(holder.mView)
                .load(getId(holder.recipePicture, R.drawable.class))
                .into(holder.recommendedImage);
        ViewCompat.setTransitionName(holder.recommendedImage, holder.recipeName);

        holder.recipeId = mValues.get(position).getRecipe_id();
        holder.recipeStat = mValues.get(position).getRecipe_status();

        holder.recommendedName.setText(mValues.get(position).getRecipe_name());
        ViewCompat.setTransitionName(holder.recommendedName, "transName"+holder.recipeId);

        holder.recommendedRecipeIngredients.setText(ingredients);
        //holder.getRecommendedRecipeIngredientQuantity.setText(ingredientQuantity);

        if(holder.recipeStat.equals("true")){
            holder.btnFav.setLiked(true);
        }
        else{
            holder.btnFav.setLiked(false);
        }
        ViewCompat.setTransitionName(holder.btnFav, "transButton"+holder.recipeId);


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onHomeFragmentInteraction(holder.recommendedImage, holder.recommendedName, holder.btnFav, holder.recipeId, holder.recipeName);
                }
            }
        });

        holder.btnFav.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                mListener.onAddToFavoritesInteraction(holder.recipeId);
                holder.recipeStat = "true";
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                mListener.onAddToFavoritesInteraction(holder.recipeId);
                holder.recipeStat = "false";
            }
        });

        /*
        holder.btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onAddToFavoritesInteraction(holder.recipeId);
                notifyDataSetChanged();

            }
        });
        */
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView recommendedImage;
        public final TextView recommendedRecipeIngredients;
        //public final TextView getRecommendedRecipeIngredientQuantity;
        public final TextView recommendedName;
        //public final Button btnExplore;
        public final LikeButton btnFav;
        //public final TextView recommendedProcedure;

        public ArrayList<RecipeIngredients> recipeIng;
        public String recipePicture;
        public int recipeId;
        public String recipeName;
        public String recipeStat;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            recommendedImage = (ImageView) view.findViewById(R.id.recommended_recipe_image);
            recommendedName = (TextView) view.findViewById(R.id.recommended_recipe_name);
            recommendedRecipeIngredients = (TextView) view.findViewById(R.id.recommended_recipe_ingredients);
            //getRecommendedRecipeIngredientQuantity = view.findViewById(R.id.recommended_recipe_ingredient_quantity);
            //btnExplore = (Button) view.findViewById(R.id.btn_explore);
            btnFav = (LikeButton) view.findViewById(R.id.btn_add_to_favorite);
            //recommendedProcedure = (TextView) view.findViewById(R.id.recommended_recipe_procedure);
        }

        @Override
        public String toString() {
            return "ViewHolder{" +
                    "mView=" + mView +
                    ", recommendedImage=" + recommendedImage +
                    ", recommendedRecipeIngredients=" + recommendedRecipeIngredients +
                    ", recommendedName=" + recommendedName +
                    ", recipeIng=" + recipeIng +
                    ", recipePicture='" + recipePicture + '\'' +
                    ", recipeId=" + recipeId +
                    '}';
        }
    }

    public static int getId(String resourceName, Class<?> c){
        try{
            Field idField = c.getDeclaredField(resourceName);
            return idField.getInt(idField);
        }
        catch (Exception e){
            throw new RuntimeException("No resource id found on: " + resourceName);
        }
    }
}
