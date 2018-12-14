package com.example.hyacinth.recipeats.Adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hyacinth.recipeats.Fragment.CreditsFragment.OnListFragmentInteractionListener;
import com.example.hyacinth.recipeats.Model.Credit;
import com.example.hyacinth.recipeats.R;

import java.util.List;

public class MyCreditsRecyclerViewAdapter extends RecyclerView.Adapter<MyCreditsRecyclerViewAdapter.ViewHolder> {

    private final List<Credit> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyCreditsRecyclerViewAdapter(List<Credit> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_credits, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.strRecipeLink = mValues.get(position).getRecipeLink();
        holder.strPhotoLink = mValues.get(position).getPhotoLink();

        Spanned recipeHtml = Html.fromHtml("<a href='"+holder.strRecipeLink+"'>"+holder.strRecipeLink+"</a>");
        Spanned photoHtml = Html.fromHtml("<a href='"+holder.strPhotoLink+"'>"+holder.strPhotoLink+"</a>");

        holder.recipeLink.setMovementMethod(LinkMovementMethod.getInstance());
        //holder.recipePhotoLink.setMovementMethod(LinkMovementMethod.getInstance());

        holder.recipeTitle.setText(mValues.get(position).getRecipeTitle());
        holder.recipeLink.setText(recipeHtml);
        //holder.recipePhotoLink.setText(photoHtml);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView recipeTitle, recipeLink/*, recipePhotoLink*/;

        public String strRecipeLink, strPhotoLink;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            recipeTitle = view.findViewById(R.id.credit_recipe_title);
            recipeLink = view.findViewById(R.id.credit_recipe_link);
            //recipePhotoLink = view.findViewById(R.id.credit_photo_link);
        }

    }
}
