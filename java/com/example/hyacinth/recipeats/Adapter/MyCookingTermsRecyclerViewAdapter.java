package com.example.hyacinth.recipeats.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.hyacinth.recipeats.Fragment.CookingTermsFragment.OnCookingTermListFragmentInteractionListener;
import com.example.hyacinth.recipeats.Model.CookingTerms;
import com.example.hyacinth.recipeats.R;

import java.util.ArrayList;
import java.util.List;

public class MyCookingTermsRecyclerViewAdapter extends RecyclerView.Adapter<MyCookingTermsRecyclerViewAdapter.ViewHolder> implements Filterable {

    private final List<CookingTerms> mValues;
    private final List<CookingTerms> mValuesFull;
    private final OnCookingTermListFragmentInteractionListener mListener;

    public MyCookingTermsRecyclerViewAdapter(List<CookingTerms> items, OnCookingTermListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        mValuesFull = new ArrayList<>(items);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_cookingterms, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.cookingTerms = new CookingTerms(
                mValues.get(position).getCookingTermId(),
                mValues.get(position).getCookingTermName(),
                mValues.get(position).getCookingTermDescription(),
                mValues.get(position).getCookingTermImage());

        holder.txtTechniqueName.setText(mValues.get(position).getCookingTermName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onCookingTermListFragmentInteraction(holder.cookingTerms);
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
        public final TextView txtTechniqueName;

        public CookingTerms cookingTerms;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            txtTechniqueName = view.findViewById(R.id.txtTechniqueName);
        }

    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<CookingTerms> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){
                filteredList.addAll(mValuesFull);
            }
            else{
                String filteredPattern = charSequence.toString().toLowerCase().trim();

                for(CookingTerms item : mValuesFull){
                    if(item.getCookingTermName().toLowerCase().contains(filteredPattern)){
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
