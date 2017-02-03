package gr.myapp.app;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import android.content.Intent;
import android.widget.*;
import android.widget.ExpandableListAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainActivity extends MyActivity {

    DrawerLayout drawer;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    SlidingMenu slidingMenu;

    ListView listViewRight;
    CustomListViewAdapterRight adapterRight;
    ArrayList<RightMenuRowItem> rowItemsRight;
    gr.myapp.app.ExpandableListAdapter adapterLeft;
    ExpandableListView listView;
    private Boolean pressedExit = false;
    ProgressBar menuProgress;
    OneFragment frag1;
    TwoFragment frag2;
    private int lastExpandedPosition = -1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slidingMenu = new SlidingMenu(this);

        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);

        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
        slidingMenu.setShadowDrawable(R.drawable.shadow);
        int deviceSize = getResources().getInteger(R.integer.isTablet);
        if (deviceSize == 3 ) {
            slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        }else if (deviceSize == 1){
            slidingMenu.setBehindOffsetRes(R.dimen.tablet_slidingmenu_offset);
        }else{
            slidingMenu.setBehindOffsetRes(R.dimen.medium_slidingmenu_offset);
        }
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.setMenu(R.layout.menu);
        slidingMenu.setSecondaryMenu(R.layout.right_menu);
        slidingMenu.setSecondaryShadowDrawable(R.drawable.shadow_right);

        handleChangelang();

        viewPager = (CustomViewPager) findViewById(R.id.viewpager);
        viewPager.setPagingEnabled(false);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        /* toolbar with tabs initialize end*/




        /* sliding menu triggered by buttons procedure */
        final ImageButton merchantsButton = (ImageButton) findViewById(R.id.action_merchants);
        merchantsButton.setOnTouchListener(new ViewListeners(this, 0, slidingMenu).new TouchMenuBar());
        final ImageButton profileButton = (ImageButton) findViewById(R.id.action_profile);
        profileButton.setOnTouchListener(new ViewListeners(this, 1, slidingMenu).new TouchMenuBar());

         /* sliding menu triggered by buttons procedure end*/

         listView = (ExpandableListView) findViewById(R.id.left_menu_listview);
        listView.setDividerHeight(2);
        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    listView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
        adapterLeft = SingletonMenus.getInstance(this.getApplicationContext()).getLeftMenu();

        if(adapterLeft == null){

            menuProgress = (ProgressBar) findViewById(R.id.menuProgress);
            if(menuProgress != null){

                menuProgress.setVisibility(View.VISIBLE);
            }

        }
        listView.setAdapter(adapterLeft);

        listViewRight = (ListView) findViewById(R.id.right_menu_listview);
        adapterRight = SingletonMenus.getInstance(this.getApplicationContext()).getRightMenu();
        listViewRight.setAdapter(adapterRight);



        EventBus.getDefault().register(this);


        adapterRight.notifyDataSetChanged();

        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {


                switch(tab.getPosition()){

                    case 0:
                        frag1.filterQuery = null;
                        frag1.fillCardView(0, null, true);
                        break;


                    case 1:
                        frag2.filterQuery = null;
                        frag2.fillCardView(0, null, true);
                        break;

                    default:

                        break;

                }
            }
        });


    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        if(frag2!=null) {
            frag2.GPSGranded();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapterLeft == null){
            SingletonMenus.getInstance(this.getApplicationContext()).getLeftMenu();
        }

    }

    @Override
    public void onBackPressed() {
        if (pressedExit) {
            finish();
        } else {
            Toast.makeText(this, "Press again to Exit App.",
                    Toast.LENGTH_SHORT).show();
            pressedExit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pressedExit = false;
                }
            }, 3 * 1000);

        }

    }



    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
         frag1 = new OneFragment();
         frag2 = new TwoFragment();
        adapter.addFragment(frag1, getString(R.string.news_tab));
        adapter.addFragment(frag2, getString(R.string.deals_tab));
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Subscribe
    public void onLoginEvent(LoginEvent event){



        rowItemsRight.clear();

        rowItemsRight.add(new RightMenuRowItem("Send Money", ContextCompat.getDrawable(this, R.drawable.contact)));

        adapterRight.notifyDataSetChanged();



        TextView text1 = (TextView) findViewById(R.id.nameProfile);
        text1.setText(event.fullName);
        if(slidingMenu.isMenuShowing()){
            slidingMenu.toggle();

        }
        slidingMenu.showSecondaryMenu();



    }


    @Subscribe

    public void onLeftMenuFilledEvent(SingletonMenus.FilledLeftListEvent event){


        if(menuProgress != null){

            menuProgress.setVisibility(View.GONE);

        }

        adapterLeft = event.leftMenu;
        listView.setAdapter(adapterLeft);

        adapterLeft.notifyDataSetChanged();

    }

    @Subscribe

    public void onLeftMenuRefreshEvent(refreshLeftMenu event){

        SingletonMenus.getInstance(MainActivity.this.getApplicationContext()).getLeftMenu();


    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }


    public void handleChangelang(){

        Intent data = getIntent();

        if(data.getExtras() !=  null) {

            Boolean resultData = data.getExtras().getBoolean("changedLang");

            if (resultData){

                SingletonMenus.getInstance(this).reset();

                slidingMenu.showSecondaryMenu();

            }
        }

    }




}
