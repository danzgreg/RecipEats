package com.example.hyacinth.recipeats.Adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hyacinth.recipeats.Interface.OnRecipeListInteraction;
import com.example.hyacinth.recipeats.Model.SectionModel;
import com.example.hyacinth.recipeats.R;

import java.util.ArrayList;

public class SectionRecyclerViewAdapter extends RecyclerView.Adapter<SectionRecyclerViewAdapter.SectionViewHolder> {

    private Context context;
    private ArrayList<SectionModel> sectionModelArrayList;
    private final OnRecipeListInteraction mListener;
    private final ArrayList<Integer> ingredientList;

    public SectionRecyclerViewAdapter(Context context, ArrayList<SectionModel> sectionModelArrayList, ArrayList<Integer> ingredientList, OnRecipeListInteraction listener) {
        this.context = context;
        this.sectionModelArrayList = sectionModelArrayList;
        this.ingredientList = ingredientList;
        mListener = listener;
    }

    @Override
    public SectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_custom_row_layout, parent, false);
        return new SectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SectionViewHolder holder, int position) {
        final SectionModel sectionModel = sectionModelArrayList.get(position);
        holder.sectionLabel.setText(sectionModel.getSectionLabel());

        //recycler view for items
        holder.itemRecyclerView.setHasFixedSize(true);
        holder.itemRecyclerView.setNestedScrollingEnabled(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        holder.itemRecyclerView.setLayoutManager(linearLayoutManager);

        MyRecipeListAdapter adapter = new MyRecipeListAdapter(context, sectionModel.getItemArrayList(), ingredientList, mListener);
        holder.itemRecyclerView.setAdapter(adapter);

    }

    class SectionViewHolder extends RecyclerView.ViewHolder {
        private TextView sectionLabel;
        private RecyclerView itemRecyclerView;

        public SectionViewHolder(View itemView) {
            super(itemView);
            sectionLabel = itemView.findViewById(R.id.section_label);
            itemRecyclerView = itemView.findViewById(R.id.item_recycler_view);
        }
    }

    @Override
    public int getItemCount() {
        return sectionModelArrayList.size();
    }

}
