package com.example.hyacinth.recipeats;

import android.content.Intent;
//import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hyacinth.recipeats.Database.DatabaseHelper;
import com.example.hyacinth.recipeats.Fragment.AllRecipeFragment;
import com.example.hyacinth.recipeats.Fragment.CookingTermsFragment;
import com.example.hyacinth.recipeats.Fragment.CreditsFragment;
import com.example.hyacinth.recipeats.Fragment.DialogCookingTermFragment;
import com.example.hyacinth.recipeats.Fragment.FavoritesFragment;
import com.example.hyacinth.recipeats.Fragment.HomeFragment;
import com.example.hyacinth.recipeats.Fragment.RecipeFragment;
import com.example.hyacinth.recipeats.Interface.OnRecipeListInteraction;
import com.example.hyacinth.recipeats.Model.CookingTerms;
import com.like.LikeButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        HomeFragment.OnHomeFragmentInteractionListener,
        RecipeFragment.OnRecipeFragmentInteractionListener,
        OnRecipeListInteraction,
        CookingTermsFragment.OnCookingTermListFragmentInteractionListener{

    private Fragment mFragment;
    private String FRAGMENT_TAG;
    //private int currentMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SelectionActivity.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        /*SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        boolean firstStart = preferences.getBoolean("firstStart", true);

        if(firstStart){
            Intent i = new Intent(MainActivity.this, GetStarted.class);
            startActivity(i);
        }*/

        /*SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();*/

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        mFragment = new HomeFragment();
        FRAGMENT_TAG = "Home";
        ft.replace(R.id.flMain, mFragment, FRAGMENT_TAG);
        ft.commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    } */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        Fragment previousFragment = getSupportFragmentManager().findFragmentById(R.id.flMain);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        int id = item.getItemId();

        /*if(id == currentMenuItem){
            drawer.closeDrawer(GravityCompat.START);
            return false;
        }*/

        if (id == R.id.nav_home) {
            mFragment = new HomeFragment();
            FRAGMENT_TAG = "Home";
        } else if (id == R.id.nav_fav) {
            mFragment = new FavoritesFragment();
            FRAGMENT_TAG = "Favorite";
        } else if (id == R.id.nav_cookterms) {
            mFragment = new CookingTermsFragment();
            FRAGMENT_TAG = "CookTerms";
        } else if (id == R.id.nav_allrecipe) {
            mFragment = new AllRecipeFragment();
            FRAGMENT_TAG = "AllRecipe";
        } else if (id == R.id.nav_credits) {
            mFragment = new CreditsFragment();
            FRAGMENT_TAG = "Credits";
        }else if (id == R.id.nav_help) {
            Intent i = new Intent(MainActivity.this, GetStarted.class);
            startActivity(i);
            drawer.closeDrawer(GravityCompat.START);
            return true;
            /*mFragment = new HelpFragment();
            FRAGMENT_TAG = "Help";*/
        }

        //currentMenuItem = id;

        drawer.closeDrawer(GravityCompat.START);

        //previous fragment enter transition
        Fade enterPreviousFade = new Fade();
        enterPreviousFade.setDuration(300);
        previousFragment.setEnterTransition(enterPreviousFade);

        //previous fragment exit transition
        Fade exitFade = new Fade();
        exitFade.setDuration(300);
        previousFragment.setExitTransition(exitFade);

        //new fragment enter transition
        Fade enterFade = new Fade();
        enterFade.setStartDelay(300);
        enterFade.setDuration(300);
        mFragment.setEnterTransition(enterFade);

        //new fragment exit transition
        Fade exitNewFade = new Fade();
        exitNewFade.setDuration(300);
        mFragment.setExitTransition(exitNewFade);

        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        ft.replace(R.id.flMain, mFragment, FRAGMENT_TAG);
        ft.addToBackStack(FRAGMENT_TAG);
        ft.commit();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //click listener of the home fragment
    public void onHomeFragmentInteraction(ImageView recommendedImage, TextView recommendedName, LikeButton btnFav, int recipeId, String recipeName){
        Fragment previousFragment = getSupportFragmentManager().findFragmentById(R.id.flMain);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        RecipeFragment newFragment = new RecipeFragment().newInstance(recipeId);

        //previous fragment enter transition
        Fade enterPreviousFade = new Fade();
        enterPreviousFade.setDuration(100);
        previousFragment.setEnterTransition(enterPreviousFade);

        //previous fragment exit transition
        Fade exitFade = new Fade();
        exitFade.setDuration(100);
        previousFragment.setExitTransition(exitFade);

        //new fragment enter transition
        Fade enterFade = new Fade();
        enterFade.setStartDelay(100);
        enterFade.setDuration(100);
        newFragment.setEnterTransition(enterFade);

        //new fragment exit transition
        Fade exitNewFade = new Fade();
        exitNewFade.setDuration(100);
        newFragment.setExitTransition(exitNewFade);

        ft.addSharedElement(recommendedImage, recipeName);
        ft.addSharedElement(recommendedName, "transName"+recipeId);
        ft.addSharedElement(btnFav, "transButton"+recipeId);
        FRAGMENT_TAG = "Recipe";
        ft.replace(R.id.flMain, newFragment, FRAGMENT_TAG);
        ft.addToBackStack(FRAGMENT_TAG);
        ft.commit();
        //performTransition(recipeId, recipeName, recipeProc, recipeImage, recipeIng, recipeStat);
    }

    public void onAddToFavoritesInteraction(int recipeId){
        new DatabaseHelper(this).addToFavorites(recipeId);
    }

    public void onRecipeFragmentInteraction(int recipeId){
        new DatabaseHelper(this).addToFavorites(recipeId);
    }

    @Override
    public void onCookingTermListFragmentInteraction(CookingTerms cookingTechnique) {
        ArrayList<CookingTerms> cookingTerm = new ArrayList<>();
        cookingTerm.add(cookingTechnique);

        /*Fragment previousFragment = getSupportFragmentManager().findFragmentById(R.id.flMain);
        CookingTechniqueFragment fragment = new CookingTechniqueFragment().newInstance(cookingTech);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        FRAGMENT_TAG = "CookingTechnique";
        ft.replace(R.id.flMain, fragment, FRAGMENT_TAG);
        ft.addToBackStack(FRAGMENT_TAG);
        ft.commit();*/

        DialogFragment mDialog = new DialogCookingTermFragment().newInstance(cookingTerm);
        mDialog.show(getSupportFragmentManager(), "cookingTermTag");
    }

    @Override
    public void onRecipeListInteraction(ImageView recipeImageView, TextView txtRecipeName, int recipeId, String recipeName, ArrayList<Integer> ingredientList) {
        Fragment previousFragment = getSupportFragmentManager().findFragmentById(R.id.flMain);
        RecipeFragment fragment = new RecipeFragment().newInstance(recipeId);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        //previous fragment enter transition
        Fade enterPreviousFade = new Fade();
        enterPreviousFade.setDuration(100);
        previousFragment.setEnterTransition(enterPreviousFade);

        //previous fragment exit transition
        Fade exitFade = new Fade();
        exitFade.setDuration(100);
        previousFragment.setExitTransition(exitFade);

        //new fragment enter transition
        Fade enterFade = new Fade();
        enterFade.setStartDelay(100);
        enterFade.setDuration(100);
        fragment.setEnterTransition(enterFade);

        //new fragment exit transition
        Fade exitNewFade = new Fade();
        exitNewFade.setDuration(100);
        fragment.setExitTransition(exitNewFade);

        ft.addSharedElement(recipeImageView, recipeName);
        ft.addSharedElement(txtRecipeName, "transName"+recipeId);
        FRAGMENT_TAG = "Recipe";
        ft.replace(R.id.flMain, fragment, FRAGMENT_TAG);
        ft.addToBackStack(FRAGMENT_TAG);
        ft.commit();
    }
}
