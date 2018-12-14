package com.example.hyacinth.recipeats.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.hyacinth.recipeats.Model.Category;
import com.example.hyacinth.recipeats.Model.CookingTerms;
import com.example.hyacinth.recipeats.Model.Credit;
import com.example.hyacinth.recipeats.Model.Ingredients;
import com.example.hyacinth.recipeats.Model.Recipe;
import com.example.hyacinth.recipeats.Model.RecipeIngredients;
import com.example.hyacinth.recipeats.Model.SectionModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "db_recipeats.db";
    public static final String DBLOCATION = "/data/data/com.example.hyacinth.recipeats/databases/";
    public static ArrayList<Integer> randomNumber = new ArrayList<>();
    Random rand = new Random();

    private Context mContext;
    private SQLiteDatabase mDatabase;
    public Cursor cursor, cursor2;

    public DatabaseHelper(Context context){
        super(context, DBNAME, null, 1);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDatabase(){
        String dbPath = mContext.getDatabasePath(DBNAME).getPath();
        if(mDatabase != null && mDatabase.isOpen()){
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase(){
        if(mDatabase != null){
            mDatabase.close();
        }
    }

    public void addToFavorites(int recipeId){
        openDatabase();
        Log.e("testing", ""+recipeId);
        cursor = mDatabase.rawQuery("SELECT recipe_status FROM tbl_recipe WHERE recipe_id = " + toString().valueOf(recipeId), null);
        cursor.moveToFirst();

        if(cursor.getString(0).equals("true")){
            mDatabase.execSQL("UPDATE tbl_recipe SET recipe_status = 'false' WHERE recipe_id = " + toString().valueOf(recipeId));
        }
        else{
            mDatabase.execSQL("UPDATE tbl_recipe SET recipe_status = 'true' WHERE recipe_id = " + toString().valueOf(recipeId));
        }
        Log.e("recipe_status", cursor.getString(0));
        cursor.close();
        closeDatabase();
    }

    public List<Ingredients> getListIngredient(String category){
        Ingredients ingredient;
        List<Ingredients> ingredientList = new ArrayList<>();
        openDatabase();
        if(category == null){
            cursor = mDatabase.rawQuery("SELECT * FROM tbl_ingredients ORDER BY ingredient_name ASC", null);
        }
        else{
            cursor = mDatabase.rawQuery("SELECT * FROM tbl_ingredients WHERE category = '"+ category +"' ORDER BY ingredient_name ASC", null);
        }
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            ingredient = new Ingredients(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            ingredientList.add(ingredient);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return ingredientList;
    }

    public ArrayList<SectionModel> getResultRecipe(ArrayList<Integer> selectedIngredients){
        Recipe recipe;
        List<Recipe> recipeList = new ArrayList<>();
        ArrayList<SectionModel> sectionModelArrayList = new ArrayList<>();

        String query = "SELECT tbl_recipe.recipe_id, tbl_recipe.recipe_name, tbl_recipe.recipe_picture, COUNT(*) FROM tbl_recipe INNER JOIN tbl_recipe_ingredients ON tbl_recipe.recipe_id = tbl_recipe_ingredients.recipe_id WHERE ingredient_id IN (";
        String [] stringArray = new String[selectedIngredients.size()];
        for(int i = 0; i < selectedIngredients.size(); i++){
            stringArray[i] = selectedIngredients.get(i).toString();
            if(i < selectedIngredients.size() - 1){
                query += "?,";
            }
            else{
                query += "?";
            }
        }
        query += ") GROUP BY tbl_recipe.recipe_id ORDER BY COUNT(*) DESC";

        openDatabase();
        cursor = mDatabase.rawQuery(query, stringArray);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            recipe = new Recipe(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
            recipeList.add(recipe);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();

        for (int i = selectedIngredients.size(); i >= 1; i--) {
            List<Recipe> itemArrayList = new ArrayList<>();

            for(int x = 0; x < recipeList.size(); x++){
                if(recipeList.get(x).getIngredientCount() == i){
                    itemArrayList.add(recipeList.get(x));
                }
            }

            if(itemArrayList.size() != 0){
                //add the section and items to array list
                sectionModelArrayList.add(new SectionModel("Matched with " + i + " ingredient(s)", itemArrayList));
            }
        }

        return sectionModelArrayList;
    }

    public List<Recipe> getRecommendedRecipe(){
        Recipe recipe;
        List<Recipe> recommendedRecipe = new ArrayList<>();
        int recipeCount;

        openDatabase();
        String getCount = "SELECT COUNT(*) FROM tbl_recipe";
        cursor = mDatabase.rawQuery(getCount, null);
        cursor.moveToFirst();
        recipeCount = cursor.getInt(0);

        cursor.close();

        if(randomNumber.isEmpty()){
            for(int i = 0; i < 5; i++){
                int tempRand = rand.nextInt(recipeCount)+1;
                while (randomNumber.contains(tempRand)){
                    tempRand = rand.nextInt(recipeCount)+1;
                }
                randomNumber.add(tempRand);
                String x = String.valueOf(tempRand);
                String query = "SELECT recipe_id, recipe_name, recipe_picture, recipe_status FROM tbl_recipe WHERE recipe_id = " + x;

                cursor = mDatabase.rawQuery(query, null);
                cursor.moveToFirst();

                recipe = new Recipe(cursor.getInt(0), cursor.getString(1), cursor.getString(2), getRecipeIngredients(cursor.getInt(0)), cursor.getString(3));
                recommendedRecipe.add(recipe);
            }
        }
        else{
            for(int i = 0; i < randomNumber.size(); i++){
                String x = String.valueOf(randomNumber.get(i));
                String query = "SELECT * FROM tbl_recipe WHERE recipe_id = " + x;

                cursor = mDatabase.rawQuery(query, null);
                cursor.moveToFirst();

                recipe = new Recipe(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), getRecipeIngredients(cursor.getInt(0)), cursor.getString(4));
                recommendedRecipe.add(recipe);
            }
        }

        cursor.close();
        closeDatabase();
        return recommendedRecipe;
    }

    public ArrayList<RecipeIngredients> getRecipeIngredients(int recipeId){
        RecipeIngredients recipeIng;
        ArrayList<RecipeIngredients> recipeIngList = new ArrayList<>();

        cursor2 = mDatabase.rawQuery("SELECT tbl_ingredients.ingredient_id, tbl_recipe_ingredients.ingredient_quantity, tbl_ingredients.ingredient_name FROM tbl_recipe_ingredients INNER JOIN tbl_ingredients ON tbl_recipe_ingredients.ingredient_id = tbl_ingredients.ingredient_id WHERE tbl_recipe_ingredients.recipe_id = '" + recipeId + "'", null);
        cursor2.moveToFirst();

        while(!cursor2.isAfterLast()){
            recipeIng = new RecipeIngredients(cursor2.getInt(0), cursor2.getString(1), cursor2.getString(2));
            recipeIngList.add(recipeIng);
            cursor2.moveToNext();
        }
        cursor2.close();
        return recipeIngList;
    }

    public List<Recipe> getAllRecipe(){
        Recipe recipe;
        List<Recipe> allRecipeList = new ArrayList<>();

        openDatabase();
        cursor = mDatabase.rawQuery("SELECT recipe_id, recipe_name, recipe_picture FROM tbl_recipe ORDER BY recipe_name ASC", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            recipe = new Recipe(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            allRecipeList.add(recipe);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return allRecipeList;
    }

    public List<Recipe> getFavoriteRecipe(){
        Recipe recipe;
        List<Recipe> favoriteRecipeList = new ArrayList<>();

        openDatabase();
        cursor = mDatabase.rawQuery("SELECT recipe_id, recipe_name, recipe_picture FROM tbl_recipe WHERE recipe_status = 'true' ORDER BY recipe_name ASC", null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            recipe = new Recipe(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            favoriteRecipeList.add(recipe);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return favoriteRecipeList;
    }

    public List<CookingTerms> getAllCookingTerms(){
        CookingTerms techniques;
        List<CookingTerms> cookingTermList = new ArrayList<>();

        openDatabase();
        cursor = mDatabase.rawQuery("SELECT * FROM tbl_cooking_terms ORDER BY cooking_term ASC", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            techniques = new CookingTerms(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            cookingTermList.add(techniques);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return cookingTermList;
    }

    public Recipe getRecipeDetails(int recipeId){
        Recipe recipe;
        openDatabase();
        cursor = mDatabase.rawQuery("SELECT * FROM tbl_recipe WHERE recipe_id = '" + recipeId + "'", null);
        cursor.moveToFirst();
        recipe = new Recipe(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), getRecipeIngredients(cursor.getInt(0)), cursor.getString(4));
        cursor.close();
        closeDatabase();
        return recipe;
    }

    public List<Credit> getCredits(){
        Credit credit;
        List<Credit> credits = new ArrayList<>();

        openDatabase();
        cursor = mDatabase.rawQuery("SELECT recipe_id, recipe_name, recipe_link, photo_link FROM tbl_recipe", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            credit = new Credit(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            credits.add(credit);
            cursor.moveToNext();
        }

        cursor.close();
        closeDatabase();
        return credits;
    }
}