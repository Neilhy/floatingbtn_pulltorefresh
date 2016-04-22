package com.example.neilhy.floatingbuttontest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.neilhy.floatingbutton_library.FloatingActionButton;
import com.example.neilhy.floatingbutton_library.FloatingActionMenu;
import com.example.neilhy.floatingbuttontest.PictureWork.HandlePicture;
import com.example.neilhy.floatingbuttontest.PictureWork.TakePicture;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int TAKE_PHOTO=1;

    private FloatingActionMenu menuButton;
    private ListView listViewTest;
    private FloatingActionButton fab_add;
    private FloatingActionButton fab_camera;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerlayout;
    private NavigationView navigationView;
    private int mPreviousVisibleItem;

    private Handler mUiHandler=new Handler();

    private File imageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_menu_button);

        initMenuButton();//初始化floatingbutton的工作
        initListView();
//        takePhoto();
    }

    private void initMenuButton(){
        menuButton= (FloatingActionMenu) findViewById(R.id.menu_button);
        fab_add = (FloatingActionButton) findViewById(R.id.fab_add);
        fab_camera = (FloatingActionButton) findViewById(R.id.fab_camera);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                mDrawerlayout.closeDrawer(GravityCompat.START);

                switch (item.getItemId()) {
                    case R.id.home:

                        break;
                    case R.id.menus:

                        break;
                    case R.id.progress:

                        break;
                }


                return true;
            }
        });

        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerlayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerlayout.addDrawerListener(toggle);
        toggle.syncState();

        fab_add.setOnClickListener(this);
        fab_camera.setOnClickListener(this);

        menuButton.setClosedOnTouchOutside(true);//点击外部可以关闭选项
        menuButton.hideMenuButton(false);

        mUiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                menuButton.showMenuButton(true);
            }
        },500);//让这个按钮500毫秒之后显示出来

        TakePicture.createCustomAnimation(menuButton);//设置点击按钮后的动画，星星变叉


    }

    private void initListView(){
        listViewTest= (ListView) findViewById(R.id.listview_test);
        Locale[] availableLocales = Locale.getAvailableLocales();
        List<String> locales = new ArrayList<>();
        for (Locale locale : availableLocales) {
            locales.add(locale.getDisplayName());
        }

        listViewTest.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,
                android.R.id.text1, locales));

        listViewTest.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem > mPreviousVisibleItem) {
                    menuButton.hideMenuButton(true);
                } else if (firstVisibleItem < mPreviousVisibleItem) {
                    menuButton.showMenuButton(true);
                }
                mPreviousVisibleItem = firstVisibleItem;
            }
        });

    }

    private void takePhoto(){
        imageFile=HandlePicture.createFileForPhoto();//创建图片路径
        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
        startActivityForResult(intent,TAKE_PHOTO);//启动相机程序
    }



    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager=getFragmentManager();
                switch (v.getId()) {
                    case R.id.fab_add:
                        Toast.makeText(MainActivity.this,"click add",Toast.LENGTH_SHORT).show();
//                        fragmentManager.beginTransaction().replace(R.id.Frameforfragment,new Ptr_Sun_frag()).commit();
                        Intent intent = new Intent(MainActivity.this, Edit_marks_aty.class);
                        startActivity(intent);
                        break;
                    case R.id.fab_camera:
                        Toast.makeText(MainActivity.this,"click camera",Toast.LENGTH_SHORT).show();
//                        fragmentManager.beginTransaction().replace(R.id.Frameforfragment,new Ptr_String_frag()).commit();
                        takePhoto();
                        break;
                }
        menuButton.close(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Edit_marks_aty.actionStart(MainActivity.this,imageFile.getAbsolutePath());
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerlayout != null && mDrawerlayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerlayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
