package client.yalantis.com.foldingtabbarandroid;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by andrewkhristyan on 11/24/16.
 */

public class Adapter extends PagerAdapter {

    private int[] mColors;

    public Adapter(int[] colors) {
        mColors = colors;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setBackgroundColor(mColors[position]);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return mColors.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
