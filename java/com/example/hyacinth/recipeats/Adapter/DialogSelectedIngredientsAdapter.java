package com.example.hyacinth.recipeats.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hyacinth.recipeats.Fragment.DialogSelectionFragment.OnDialogSelectionFragmentInteractionListener;
import com.example.hyacinth.recipeats.Model.SelectedIngredients;
import com.example.hyacinth.recipeats.R;

import java.util.ArrayList;

public class DialogSelectedIngredientsAdapter extends RecyclerView.Adapter<DialogSelectedIngredientsAdapter.ViewHolder> {
    private ArrayList<SelectedIngredients> mValues;
    private OnDialogSelectionFragmentInteractionListener mListener;
    private Context context;

    public DialogSelectedIngredientsAdapter(Context context, ArrayList<SelectedIngredients> items, OnDialogSelectionFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        this.context = context;
        //mValuesFull = new ArrayList<>(items);
    }

    @Override
    public DialogSelectedIngredientsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_selected_ingredients, parent, false);
        return new DialogSelectedIngredientsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DialogSelectedIngredientsAdapter.ViewHolder holder, final int position) {
        final SelectedIngredients item = mValues.get(position);
        holder.txtIngredient.setText(mValues.get(position).getSelectedIngredient_name());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView txtIngredient;
        public RelativeLayout viewBackground, viewForeground;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            txtIngredient = view.findViewById(R.id.tv_selected_ingredient);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
        }
    }

    public void removeItem(int position) {
        mValues.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        mListener.onDialogSelectionFragmentInteraction(mValues);
        notifyItemRemoved(position);
    }

    public void restoreItem(SelectedIngredients item, int position) {
        mValues.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
}
