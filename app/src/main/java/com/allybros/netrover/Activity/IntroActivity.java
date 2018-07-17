package com.allybros.netrover.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allybros.netrover.Database.RecordAction;
import com.allybros.netrover.R;
import com.allybros.netrover.Unit.BrowserUnit;
import com.allybros.netrover.Unit.PermissionsUnit;
import com.allybros.netrover.Unit.ViewUnit;
import com.allybros.netrover.View.GridItem;
import com.allybros.netrover.View.NinjaToast;
import com.allybros.netrover.View.NinjaWebView;

public class IntroActivity extends Activity {

    /**
     *      Created by Umut Can Alaçam 04.06.2018
     * **/

    ViewPager introPager;
    LinearLayout dotsLayout;
    Button btnSkip, btnNext;
    Intent browserActivity;

    SharedPreferences preferences;

    private TextView[] dots;
    private int[] layouts;

    NetRoverPagerAdapter netRoverPagerAdapter;
    static boolean isCompleted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();
        setContentView(R.layout.activity_intro);

        introPager = findViewById(R.id.intro_pager);
        dotsLayout = findViewById(R.id.layoutDots);
        btnNext = findViewById(R.id.btn_next);
        btnSkip = findViewById(R.id.btn_skip);

        layouts = new int[] {
                R.layout.intro_slide_1,
                R.layout.intro_slide_2,
                R.layout.intro_slide_3,
                R.layout.intro_slide_4};

        browserActivity = new Intent(IntroActivity.this, BrowserActivity.class);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        // adding bottom dots
        addBottomDots(0);
        changeStatusBarColor();

        NetRoverPagerAdapter adapter = new NetRoverPagerAdapter();
        introPager.setAdapter(adapter);
        introPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);
                if (position == layouts.length -1){
                    btnNext.setText(getResources().getString(R.string.gotit));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        PermissionsUnit.firstTimePermissions(IntroActivity.this);



        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.edit().putBoolean(getString(R.string.sp_first), false).apply();
                isCompleted = true;
                startActivity(browserActivity);
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = introPager.getCurrentItem() + 1;
                if (current < layouts.length) {
                    introPager.setCurrentItem(current);
                    addBottomDots(current);
                }
                else {
                    preferences.edit().putBoolean(getString(R.string.sp_first), false).apply();
                    isCompleted = true;
                    startActivity(browserActivity);
                    finish();
                }
            }
        });

    }


    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];
        dotsLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&nbsp;&#8226;&nbsp;"));
            dots[i].setTextSize(36);
            dots[i].setTextColor(getResources().getColor(R.color.white));
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(getResources().getColor(R.color.blue_400));
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }



    /**
     * View pager adapter
     */
    public class NetRoverPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;



        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}

