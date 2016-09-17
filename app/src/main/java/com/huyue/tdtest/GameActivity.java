package com.huyue.tdtest;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

import com.huyue.tdtest.constant.CONST;

public class GameActivity extends Activity
{

    public static Resources resources;
    public static Activity thisActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        /*
         * 全屏和无标题栏以及锁定竖屏
         */
        thisActivity = this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        resources = getResources();
        /*
         * 分辨率适配设置
         */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        // tv.setText("屏幕分辨率为:"+dm.widthPixels+" * "+dm.heightPixels);
        screenSuit(dm.widthPixels, dm.heightPixels);

        setContentView(new GameSurfaceView(this));
    }

    public void screenSuit(int width, int height)
    {
        CONST.SCREEN.BOTTOM = height;
        CONST.SCREEN.RIGHT = width;
        //修改kee  屏幕的比例  9/16 720*1280  1080*1920
        CONST.MAP.SIZE = width / 9;



//        if (width / 9 > height / 16)
//        {
//            CONST.MAP.SIZE = height / 16;
//        }
//        else
//        {
//            CONST.MAP.SIZE = width / 9;
//        }
        CONST.MAP.HALFSIZE = CONST.MAP.SIZE / 2;
        CONST.NORMALSPEED = (int) (100f / 60 * CONST.MAP.SIZE);
        CONST.SCREEN.TOP = CONST.MAP.SIZE * 2;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
//         Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
