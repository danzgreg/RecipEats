package com.example.hyacinth.recipeats.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hyacinth.recipeats.Model.CookingTerms;
import com.example.hyacinth.recipeats.R;
import com.example.hyacinth.recipeats.ThirdPartyModule.GlideApp;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class DialogCookingTermFragment extends DialogFragment {
    private ArrayList<CookingTerms> mCookingTerm;

    private View v;
    private TextView txtCookingTerm, txtCookingTermDescription;
    private ImageView imgCookingTermImage;
    private AppCompatButton btnCookingTermOk;

    public DialogCookingTermFragment() {
    }

    public static DialogCookingTermFragment newInstance(ArrayList<CookingTerms> cookingTerm){
        DialogCookingTermFragment fragment = new DialogCookingTermFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("cookingTerm", cookingTerm);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCookingTerm = getArguments().getParcelableArrayList("cookingTerm");

        v = LayoutInflater.from(getContext()).inflate(R.layout.custom_cooking_term_dialog, null);
        imgCookingTermImage = v.findViewById(R.id.img_cooking_term);
        txtCookingTerm = v.findViewById(R.id.txt_cooking_term);
        txtCookingTermDescription = v.findViewById(R.id.txt_cooking_term_description);
        btnCookingTermOk = v.findViewById(R.id.btn_cooking_term_ok);

        GlideApp
                .with(getContext())
                .load(getId(mCookingTerm.get(0).getCookingTermImage(), R.drawable.class))
                /*.override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .dontTransform()*/
                .into(imgCookingTermImage);

        txtCookingTerm.setText(mCookingTerm.get(0).getCookingTermName());
        txtCookingTermDescription.setText(mCookingTerm.get(0).getCookingTermDescription());

        btnCookingTermOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .create();
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
}
