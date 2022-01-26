package com.example.inventoryapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    AnimatedBottomBar animatedBottomBar;
    FragmentManager fragmentManager;
//    FrameLayout frameLayout;
//    LinearLayout layout_tab;
//    TabLayout tab;
//    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        animatedBottomBar = findViewById(R.id.bottomnav);
//        layout_tab = (LinearLayout) findViewById(R.id.layout_tab);
//        frameLayout = (FrameLayout) findViewById(R.id.fragment_container);
//        tab = (TabLayout) findViewById(R.id.tab_layout);
//        viewPager = (ViewPager) findViewById(R.id.viewpager);
//        Tab_adapter tab_adapter = new Tab_adapter(getSupportFragmentManager(), tab.getTabCount());
//        viewPager.setAdapter(tab_adapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
//        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

        if (savedInstanceState == null){
            animatedBottomBar.selectTabById(R.id.home, true);
            fragmentManager = getSupportFragmentManager();
            HomeFragment homeFragment = new HomeFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, homeFragment)
                    .commit();
        }

        animatedBottomBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NonNull AnimatedBottomBar.Tab newTab) {

                Fragment fragment = null;
                switch (newTab.getId()){
                    case R.id.home:
//                        layout_tab.setVisibility(View.GONE);
//                        frameLayout.setVisibility(View.VISIBLE);
                        fragment = new HomeFragment();
                        break;

                    case R.id.items:
//                        layout_tab.setVisibility(View.GONE);
//                        frameLayout.setVisibility(View.VISIBLE);
                        fragment = new ItemsFragment();
                        break;

                    case R.id.in:
//                        layout_tab.setVisibility(View.VISIBLE);
//                        frameLayout.setVisibility(View.GONE);
                        fragment = new HistoryStockInFragment();
                        break;

                    case R.id.out:
//                        layout_tab.setVisibility(View.VISIBLE);
//                        frameLayout.setVisibility(View.GONE);
                        fragment = new HistoryStockOutFragment();
                        break;

                    case R.id.settings:
//                        layout_tab.setVisibility(View.GONE);
//                        frameLayout.setVisibility(View.VISIBLE);
                        fragment = new SettingsFragment();
                        break;
                }
                if (fragment !=null){
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
                            .commit();
                }else {
                    Log.e(TAG, "Error in creating fragment");
                }
            }

            @Override
            public void onTabReselected(int i, @NonNull AnimatedBottomBar.Tab tab) {

            }
        });

    }

//class Tab_adapter extends FragmentStatePagerAdapter {
//
//    int jumlahTab;
//    public Tab_adapter(@NonNull FragmentManager fm, int jmltab) {
//        super(fm);
//        this.jumlahTab=jmltab;
//    }
//
//    @NonNull
//    @Override
//    public Fragment getItem(int position) {
//        switch (position)
//        {
//            case 0:
//                StockIn stockIn = new StockIn();
//                return stockIn;
//            case 1:
//                StockOut stockOut = new StockOut();
//                return stockOut;
//        }
//        return null;
//    }
//
//    @Override
//    public int getCount() {
//        return jumlahTab;
//    }
//}

}