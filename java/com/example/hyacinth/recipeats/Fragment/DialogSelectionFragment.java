package com.example.hyacinth.recipeats.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hyacinth.recipeats.Adapter.DialogSelectedIngredientsAdapter;
import com.example.hyacinth.recipeats.Model.SelectedIngredients;
import com.example.hyacinth.recipeats.R;
import com.example.hyacinth.recipeats.SelectionActivity;
import com.example.hyacinth.recipeats.ThirdPartyModule.RecyclerItemTouchHelper;

import java.util.ArrayList;

public class DialogSelectionFragment extends DialogFragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{

    //private OnDialogSelectedInteractionListener mListener;
    private ArrayList<SelectedIngredients> lstIngredient;
    private ArrayList<Integer> ingredientIds = new ArrayList<>();

    private RecyclerView mRecyclerview;
    private DialogSelectedIngredientsAdapter mAdapter;
    private OnDialogSelectionFragmentInteractionListener mListener;

    private CardView txtHint;
    private ImageView helpIcon;

    public DialogSelectionFragment(){
    }

    public static DialogSelectionFragment newInstance(ArrayList<SelectedIngredients> selectedIngredients){
        DialogSelectionFragment fragment = new DialogSelectionFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("selectedIngredients", selectedIngredients);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /*txtCustomTitle = new TextView(getContext());
        txtCustomTitle.setText("Selected ingredients");
        txtCustomTitle.setGravity(Gravity.LEFT);
        txtCustomTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F);
        txtCustomTitle.setPadding(30, 30, 30, 30);*/

        View view = LayoutInflater.from(getContext()).inflate(R.layout.custom_dialog_title, null);
        helpIcon = view.findViewById(R.id.help_icon);
        txtHint = view.findViewById(R.id.hint);

        helpIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtHint.getVisibility() == View.GONE){
                    txtHint.setVisibility(View.VISIBLE);
                }
                else{
                    txtHint.setVisibility(View.GONE);
                }
            }
        });

        mListener = (OnDialogSelectionFragmentInteractionListener) getContext();
        lstIngredient = getArguments().getParcelableArrayList("selectedIngredients");
        mRecyclerview = new RecyclerView(getContext());
        LayoutInflater.from(getContext()).inflate(R.layout.fragment_selected_ingredients_list, null);
        mAdapter = new DialogSelectedIngredientsAdapter(getContext(), lstIngredient, mListener);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerview.setItemAnimator(new DefaultItemAnimator());
        mRecyclerview.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerview.setAdapter(mAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerview);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback1 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT/* | ItemTouchHelper.RIGHT | ItemTouchHelper.UP*/) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Row is swiped from recycler view
                // remove it from adapter
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        // attaching the touch helper to recycler view
        new ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(mRecyclerview);

        return new AlertDialog.Builder(getActivity())
                //.setCustomTitle(txtCustomTitle)
                .setCustomTitle(view)
                .setView(mRecyclerview)
                .setPositiveButton("Search",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                for(int x = 0; x < lstIngredient.size(); x++){
                                    ingredientIds.add(lstIngredient.get(x).getSelectedIngredient_id());
                                }
                                ((SelectionActivity)getActivity()).searchCompatibleRecipe(ingredientIds);
                            }
                        })
                .setNegativeButton("Clear",
                        new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ((SelectionActivity)getActivity()).clearList();
                        }
                })
                .create();
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof DialogSelectedIngredientsAdapter.ViewHolder) {
            // get the removed item name to display it in snack bar
            String name = lstIngredient.get(viewHolder.getAdapterPosition()).getSelectedIngredient_name();

            // backup of removed item for undo purpose
            final SelectedIngredients deletedItem = lstIngredient.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            mAdapter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            /*Snackbar snackbar = Snackbar
                    .make(new LinearLayout(getContext()), name + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    mAdapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();*/
        }
    }

    public interface OnDialogSelectionFragmentInteractionListener{
        void onDialogSelectionFragmentInteraction(ArrayList<SelectedIngredients> lstSelectedIngredients);
    }
}
