package com.example.hyacinth.recipeats.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.hyacinth.recipeats.R;
import com.example.hyacinth.recipeats.ThirdPartyModule.GlideApp;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    //arrays for the pages
    public int[] imagestobeshown = {
            R.drawable.help_home_screen,
            R.drawable.help_add_to_favorites,
            R.drawable.help_search_recipe,
            R.drawable.help_selecting_ingredients,
            R.drawable.help_category_selection,
            R.drawable.help_selected_ingredient_screen,
            R.drawable.help_removing_ingredient,
            R.drawable.help_recipe_result_screen
    };

    @Override
    public int getCount() {
        return imagestobeshown.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView tutImage = view.findViewById(R.id.img_tuts);

        //tutImage.setImageResource(imagestobeshown[position]);

        GlideApp
                .with(view)
                .load(imagestobeshown[position])
                .into(tutImage);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
