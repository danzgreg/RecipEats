package com.example.hyacinth.recipeats.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.target.Target;
import com.example.hyacinth.recipeats.Adapter.RecipeIngredientsRecyclerViewAdapter;
import com.example.hyacinth.recipeats.Database.DatabaseHelper;
import com.example.hyacinth.recipeats.Model.Recipe;
import com.example.hyacinth.recipeats.Model.RecipeIngredients;
import com.example.hyacinth.recipeats.Model.SelectedIngredients;
import com.example.hyacinth.recipeats.R;
import com.example.hyacinth.recipeats.ThirdPartyModule.GlideApp;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class RecipeFragment extends Fragment {

    private OnRecipeFragmentInteractionListener mListener;
    private DatabaseHelper mDBHelper;
    private Recipe recipe;
    private RecipeIngredientsRecyclerViewAdapter adapter;

    private int recipeId;
    private String recipeName;
    private String recipeProc;
    private String recipeImage;
    private ArrayList<RecipeIngredients> recipeIng;
    private ArrayList<Integer> selectedIng;
    private String recipeStat;
    private String imageTransName;
    //private String[] tags = {"cooking", "terms"};

    private View view;

    public ImageView imgRecipe;
    public TextView tvIngredients;
    public TextView tvName;
    public TextView tvProc;
    public LikeButton btnFav;

    public RecipeFragment() {
        // Required empty public constructor
    }

    public static RecipeFragment newInstance(int recipeId){
        RecipeFragment fragment = new RecipeFragment();

        Bundle args = new Bundle();
        args.putInt("recipeId", recipeId);
        fragment.setArguments(args);
        return fragment;
    }

    public static RecipeFragment newInstance(int recipeId, ArrayList<Integer> selectedIng){
        RecipeFragment fragment = new RecipeFragment();

        Bundle args = new Bundle();
        args.putInt("recipeId", recipeId);
        args.putIntegerArrayList("selectedIngredients", selectedIng);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recipe, container, false);
        mDBHelper = new DatabaseHelper(getActivity());
        recipe = mDBHelper.getRecipeDetails(recipeId);

        recipeName = recipe.getRecipe_name();
        recipeProc = recipe.getRecipe_cooking_procedure();
        recipeImage = recipe.getRecipe_picture();
        recipeStat = recipe.getRecipe_status();
        recipeIng = recipe.getRecipeIngredients();

        setActionBarTitle(recipeName);

        /*String recipeIngredients = "";
        imgRecipe = view.findViewById(R.id.recipe_image);


        tvName = view.findViewById(R.id.tv_recipeName);
        tvName.setTransitionName("transName"+recipeId);

        btnFav = (LikeButton) view.findViewById(R.id.btn_add_to_fav);
        btnFav.setTransitionName("transButton"+recipeId);

        GlideApp
                .with(getContext())
                .load(getId(recipeImage, R.drawable.class))
                .into(imgRecipe);
        imgRecipe.setTransitionName("transImage"+recipeId);

        tvName.setText(recipeName);

        for(int i = 0; i < recipeIng.size(); i++){
            recipeIngredients += recipeIng.get(i).getIngredientQuantity()
                    + " "
                    + recipeIng.get(i).getIngredientName()
                    + "\n";
        }

        tvIngredients = view.findViewById(R.id.tv_ingredients);
        tvIngredients.setText(recipeIngredients);

        tvProc = view.findViewById(R.id.tv_recipeProc);
        tvProc.setText(recipeProc);

        if(recipeStat.equals("true")){
            btnFav.setLiked(true);
        }
        else{
            btnFav.setLiked(false);
        }

        btnFav.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                onLikeButtonPressed(recipeId);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                onLikeButtonPressed(recipeId);
            }
        });*/

        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.ingredient_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        adapter = new RecipeIngredientsRecyclerViewAdapter(recipeIng, selectedIng);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String recipeIngredients = "";
        imgRecipe = view.findViewById(R.id.recipe_image);

        imgRecipe.setTransitionName(recipeName);
        GlideApp
                .with(getContext())
                .load(getId(recipeImage, R.drawable.class))
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .dontTransform()
                .into(imgRecipe);

        tvName = view.findViewById(R.id.tv_recipeName);
        tvName.setTransitionName("transName"+recipeId);

        btnFav = view.findViewById(R.id.btn_add_to_fav);
        btnFav.setTransitionName("transButton"+recipeId);

        tvName.setText(recipeName);

        for(int i = 0; i < recipeIng.size(); i++){
            recipeIngredients += recipeIng.get(i).getIngredientQuantity()
                    + " "
                    + recipeIng.get(i).getIngredientName()
                    + "\n";
        }

        /*tvIngredients = view.findViewById(R.id.tv_ingredients);
        tvIngredients.setText(recipeIngredients);*/

        tvProc = view.findViewById(R.id.tv_recipeProc);
        tvProc.setText(recipeProc);

        if(recipeStat.equals("true")){
            btnFav.setLiked(true);
        }
        else{
            btnFav.setLiked(false);
        }

        btnFav.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                onLikeButtonPressed(recipeId);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                onLikeButtonPressed(recipeId);
            }
        });
    }

    /*private void makeTagLinks(final String text, final TextView tv) {

        if (text == null || tv == null) {
            return;
        }
        final SpannableString ss = new SpannableString(text);
        final List items = Arrays.asList(text.split(" "));
        int start, end, counter = 0;

        for(int i = 0; i < items.size(); i++){
            final String item;
            item = items.get(i).toString();

            for(int j = 0; j < tags.length; j++){
                if(item.equals(tags[j])){
                    start = text.toLowerCase().indexOf(tags[j], counter);
                    end = start + item.length();
                    counter = end;
                    if(start < end){
                        ss.setSpan(new MyClickableSpan(item), start, end, 0);
                    }
                }
            }
        }
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        tv.setText(ss, TextView.BufferType.SPANNABLE);
    }*/

    /*private class MyClickableSpan extends ClickableSpan {
        private final String mText;
        private MyClickableSpan(final String text) {
            mText = text;
        }
        @Override
        public void onClick(final View widget) {
            //mListener.onTagClicked(mText);
        }
    }*/

    // TODO: Rename method, update argument and hook method into UI event
    public void onLikeButtonPressed(int recipeId) {
        if (mListener != null) {
            mListener.onRecipeFragmentInteraction(recipeId);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipeFragmentInteractionListener) {
            mListener = (OnRecipeFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

        recipeId = getArguments().getInt("recipeId");
        selectedIng = getArguments().getIntegerArrayList("selectedIngredients");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    public void setActionBarTitle(String title){
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(title);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnRecipeFragmentInteractionListener {
        // TODO: Update argument type and name
        void onRecipeFragmentInteraction(int recipeId);
    }
}
