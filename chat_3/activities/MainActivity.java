package com.example.ahmad.chat_3.activities;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.ahmad.chat_3.MyApplication;
import com.example.ahmad.chat_3.R;
import com.example.ahmad.chat_3.adapters.MessagesAdapter;
import com.example.ahmad.chat_3.db.dao.ChatDao;
import com.example.ahmad.chat_3.fragments.chats.ChatsListFragment;
import com.example.ahmad.chat_3.fragments.users.UsersFragment;
import com.example.ahmad.chat_3.generalUtils.NotificationUtils;
import com.example.ahmad.chat_3.models.db.Chat;
import com.example.ahmad.chat_3.models.db.Message;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    // constants
    public static final int USERS_FRAGMENT_POS = 0;
    public static final int CHATS_FRAGMENT_POS = 1;

    private Fragment[] fragments;
    private int activeFragmentPosition;
    private static final String TAG = "MainActivity";

    private SectionsPagerAdapter sectionPageAdapter;

    private ViewPager viewPager;
    List<Chat> userList = new ArrayList<>();

    private SearchView searchView;
    TabLayout tabLayout;
    private CompositeDisposable compositeDisposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        RecreateActivityReceiver receiver = new RecreateActivityReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,
                new IntentFilter("recreateBroadcast"));


        // intent extras
        boolean refresh = getIntent().getBooleanExtra("refresh", false);

        // setup fragments
        fragments = new Fragment[]{
                UsersFragment.newInstance(),
                ChatsListFragment.newInstance()
        };

        sectionPageAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(sectionPageAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                invalidateOptionsMenu();
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setBackgroundColor(Color.parseColor("#ffffff"));


        String users_text = getResources().getString(R.string.Users_textView);
        String chats_text = getResources().getString(R.string.Chats_textView);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_account_circle_white_48dp);
        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#35A8E0"), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(0).setText(users_text);
        tabLayout.setTabTextColors(Color.parseColor("#000000"),Color.parseColor("#000000"));


        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#35A8E0"));
        tabLayout.setSelectedTabIndicatorHeight((int) (3 * getResources().getDisplayMetrics().density));


        tabLayout.getTabAt(1).setIcon(R.drawable.ic_supervisor_account_white_36dp);
        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#35A8E0"), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).setText(chats_text);

        int marginOffset = 60;
        View tabStrip = tabLayout.getChildAt(0);
        if (tabStrip instanceof ViewGroup) {
            ViewGroup tabStripGroup = (ViewGroup) tabStrip;
            for (int i = 0; i < ((ViewGroup) tabStrip).getChildCount(); i++) {
                View tabView = tabStripGroup.getChildAt(i);
                if (tabView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    ((ViewGroup.MarginLayoutParams) tabView.getLayoutParams()).leftMargin = marginOffset;
                    ((ViewGroup.MarginLayoutParams) tabView.getLayoutParams()).rightMargin = marginOffset;
                }
            }

            tabLayout.requestLayout();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.front_tool)));

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.search);

        searchView = (SearchView) searchMenuItem.getActionView();


        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));

        searchView.setIconifiedByDefault(false);  //to make whole toolbar CLICKABLE for search
        searchView.setSubmitButtonEnabled(false);

        searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) fragments[viewPager.getCurrentItem()]);
        searchView.setOnSearchClickListener((v) -> menu.findItem(R.id.add).setVisible(false));
        searchView.setOnCloseListener(() -> {
            menu.findItem(R.id.add).setVisible(true);
            invalidateOptionsMenu();
            return false;
        });

        switch (viewPager.getCurrentItem()) {
            case 0:
                return true;
            default:
                return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_profile) {
            Intent intent = new Intent(MainActivity.this, ProfileInfoActivity.class);
            startActivity(intent);
            finish();
        }
        if (id == R.id.action_travel) {
            Intent intent = new Intent(MainActivity.this, TravelModeActivity.class);
            startActivity(intent);
        }

        if (id == R.id.action_sign_out) {  //sign out button
            // delete prefs
            getPrefs().edit().clear().apply();

            // delete database
            Completable.fromAction(() -> {
                ((MyApplication) getApplication()).getDb().chatDao().deleteAll();
                ((MyApplication) getApplication()).getDb().messageDao().deleteAll();
            })
            .subscribeOn(Schedulers.io())
            .subscribe();

            Intent intent = new Intent(MainActivity.this, SplashScreenActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // update listener
            if (searchView != null)
                searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) fragments[position]);

            return fragments[position];
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
//            switch (position) {
//                case USERS_FRAGMENT_POS:
//                    return "Users";
//
//                case CHATS_FRAGMENT_POS:
//                    return "Chats";
//            }
            return null;
        }

    }

    @Override
    public void onBackPressed() {
        // show home
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private class RecreateActivityReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            recreate();
        }
    }
}