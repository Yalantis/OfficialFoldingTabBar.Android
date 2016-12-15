package client.yalantis.com.foldingtabbarandroid;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import org.jetbrains.annotations.NotNull;

import client.yalantis.com.foldingtabbar.FoldingTabBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FoldingTabBar tabBar = (FoldingTabBar) findViewById(R.id.folding_tab_bar);
        changeFragment(new ProfileFragment());

        tabBar.setOnFoldingItemClickListener(new FoldingTabBar.OnFoldingItemSelectedListener() {
            @Override
            public boolean onFoldingItemSelected(@NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ftb_menu_nearby:
                        break;
                    case R.id.ftb_menu_new_chat:
                        changeFragment(new ChatsFragment());
                        break;
                    case R.id.ftb_menu_profile:
                        changeFragment(new ProfileFragment());
                        break;
                    case R.id.ftb_menu_settings:
                        break;
                }
                return false;
            }
        });
    }

    private void changeFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

}
