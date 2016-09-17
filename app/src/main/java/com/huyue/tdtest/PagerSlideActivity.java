package com.huyue.tdtest;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.example.com.huyue.tdtest.R;

public class PagerSlideActivity extends Activity
{

    private ViewPager choiceViewPager;
    // private PagerTitleStrip choicePagerTitleStrip;

    ImageButton Ibtn1;
    ImageButton Ibtn2;
    ImageButton Ibtn3;
    ImageButton Ibtn4;
    ImageButton Ibtn5;

    public static int missionNumber;

    public void view5sp(View v)
    {
        Intent intent = new Intent(PagerSlideActivity.this, GameActivity.class);
        startActivity(intent);
        missionNumber = 4;
        Log.i("click", "5 button");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        /*
         * 全屏和无标题栏以及锁定竖屏
         */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.viewpage);

        choiceViewPager = (ViewPager) findViewById(R.id.viewpager);

        LayoutInflater choiceSlipe = LayoutInflater.from(this);
        final View view1 = choiceSlipe.inflate(R.layout.view1, null);
        final View view2 = choiceSlipe.inflate(R.layout.view2, null);
        final View view3 = choiceSlipe.inflate(R.layout.view3, null);
        final View view4 = choiceSlipe.inflate(R.layout.view4, null);
        final View view5 = choiceSlipe.inflate(R.layout.view5, null);
        final ArrayList<View> views = new ArrayList<View>();

        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);
        views.add(view5);

        PagerAdapter choicePagerAdapter = new PagerAdapter()
        {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1)
            {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            @Override
            public int getCount()
            {
                // TODO Auto-generated method stub
                return views.size();
            }

            @Override
            public void destroyItem(View container, int position, Object object)
            {
                ((ViewPager) container).removeView(views.get(position));

            }

            @Override
            public Object instantiateItem(View container, int position)
            {

                // 实现页面间的跳转

                Ibtn1 = (ImageButton) container.findViewById(R.id.view1button);
                if (Ibtn1 != null)
                {
                    Ibtn1.setOnClickListener(new OnClickListener()
                    {

                        @Override
                        public void onClick(View arg0)
                        {
                            // TODO Auto-generated method stub
                            Intent intent = new Intent(PagerSlideActivity.this, GameActivity.class);
                            startActivity(intent);
                            missionNumber = 0;
                            Log.i("click", "1 button");
                        }

                    });
                    Log.i("1", "1");
                }
                // case 1:
                Ibtn2 = (ImageButton) container.findViewById(R.id.view2button);
                if (Ibtn2 != null)
                {
                    Ibtn2.setOnClickListener(new OnClickListener()
                    {

                        @Override
                        public void onClick(View arg0)
                        {
                            Intent intent = new Intent(PagerSlideActivity.this, GameActivity.class);
                            startActivity(intent);
                            missionNumber = 1;
                            Log.i("click", "2 button");
                        }

                    });
                    Log.i("2", "2");
                }
                // case 2:
                Ibtn3 = (ImageButton) container.findViewById(R.id.view3button);
                if (Ibtn3 != null)
                {
                    Ibtn3.setOnClickListener(new OnClickListener()
                    {

                        @Override
                        public void onClick(View arg0)
                        {
                            Intent intent = new Intent(PagerSlideActivity.this, GameActivity.class);
                            startActivity(intent);
                            Log.i("click", "3 button");
                            missionNumber = 2;
                        }

                    });
                    Log.i("3", "3");
                }

                Ibtn4 = (ImageButton) container.findViewById(R.id.view4button);
                if (Ibtn4 != null)
                {
                    Ibtn4.setOnClickListener(new OnClickListener()
                    {

                        @Override
                        public void onClick(View arg0)
                        {
                            Intent intent = new Intent(PagerSlideActivity.this, GameActivity.class);
                            startActivity(intent);
                            Log.i("click", "4 button");
                            missionNumber = 3;
                        }

                    });
                    Log.i("4", "4");
                }

                Ibtn5 = (ImageButton) container.findViewById(R.id.view5button);
                if (Ibtn5 != null)
                {
                    Ibtn5.setOnClickListener(new OnClickListener()
                    {

                        @Override
                        public void onClick(View arg0)
                        {
                            Intent intent = new Intent(PagerSlideActivity.this, GameActivity.class);
                            startActivity(intent);
                            Log.i("click", "5 button");
                            missionNumber = 4;
                        }

                    });
                    Log.i("5", "5");
                }
                ((ViewPager) container).addView(views.get(position));
                return views.get(position);
            }

        };
        choiceViewPager.setAdapter(choicePagerAdapter);
    }
}
