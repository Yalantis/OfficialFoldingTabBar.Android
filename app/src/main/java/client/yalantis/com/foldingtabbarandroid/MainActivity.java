package client.yalantis.com.foldingtabbarandroid;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import org.jetbrains.annotations.NotNull;

import client.yalantis.com.foldingtabbar.FoldingTabBar;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private int[] mColors = {Color.BLACK, Color.BLUE, Color.RED, Color.CYAN};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager();

        FoldingTabBar tabBar = (FoldingTabBar) findViewById(R.id.folding_tab_bar);


        tabBar.setOnFoldingItemClickListener(new FoldingTabBar.OnFoldingItemSelectedListener() {
            @Override
            public boolean onFoldingItemSelected(@NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ftb_menu_nearby:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.ftb_menu_new_chat:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.ftb_menu_profile:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.ftb_menu_settings:
                        mViewPager.setCurrentItem(3);
                        break;
                }
                return false;
            }
        });
    }

    private void setupViewPager() {
        Adapter adapter = new Adapter(mColors);
        mViewPager.setAdapter(adapter);
    }

}
