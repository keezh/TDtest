package com.huyue.tdtest.tower;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.example.com.huyue.tdtest.R;
import com.huyue.tdtest.GameActivity;
import com.huyue.tdtest.GameSurfaceView;
import com.huyue.tdtest.constant.CONST;
import com.huyue.tdtest.tdactive.Tower;

public class OneTargetTower extends Tower
{
    // 表示塔的等级，目前是{0 1 2} 3个级别

    public static Bitmap[] bitmap = new Bitmap[]
            { BitmapFactory.decodeResource(GameActivity.resources, R.drawable.t0000),
                    BitmapFactory.decodeResource(GameActivity.resources, R.drawable.t0001),
                    BitmapFactory.decodeResource(GameActivity.resources, R.drawable.t0002) };
    public static Bitmap[] bulletBitmap = new Bitmap[]
            { BitmapFactory.decodeResource(GameActivity.resources, R.drawable.b0000),
                    BitmapFactory.decodeResource(GameActivity.resources, R.drawable.b0001) };
    public static int[] cost = new int[]
            { 100, 180, 260 };
    public static int[] attacks = new int[]
            { 10, 11, 14 };
    public static float[] ranges = new float[]
            { 2f, 2.5f, 3f };
    public static float[] gaps = new float[]
            { 0.5f, 0.33f, 0.28f };

    public OneTargetTower(int ix, int iy, int level)
    {
        super(ix, iy, 10, 2.0f, 0.25f);
        // TODO 3种级别
        setLevel(level);
    }

    @Override
    public void setLevel(int level)
    {
        GameSurfaceView.moneymanager.spend(cost[level]);
        this.level = level;
        attack = attacks[level];
        setRange(ranges[level]);
        gap = gaps[level];
    }

    public static void testdraw(Canvas canvas, int ix, int iy)
    {
        Paint paint = new Paint();
        Rect dst = CONST.getRect(ix, iy);
        Rect src = CONST.getSrcRect(bitmap[0]);
        canvas.drawBitmap(bitmap[0], src, dst, paint);
    }

    @Override
    public Bitmap getBitmap()
    {
        return bitmap[level];
    }

    @Override
    public int getCost()
    {
        int ret = 0;
        for (int i = 0; i <= level; i++)
        {
            ret += cost[i];
        }
        Log.i("getCost", ""+ret);
        ret *= 0.8;
        return ret;
    }

    @Override
    public Bitmap getBitmapByLevel(int level)
    {
        return bitmap[level];
    }

    @Override
    public int getCostByLevel(int level)
    {
        return cost[level];
    }

    @Override
    public float getRangeByLevel(int level)
    {
        return getDisplayRange(ranges[level]);
    }

    @Override
    public void fireBullet(float dx, float dy)
    {
        Bitmap bb = (dx<0?bulletBitmap[0]:bulletBitmap[1]);
        GameSurfaceView.bulletmanager.addBullet(new OneTargetBullet(bb, attack, x,
                y, dx, dy,  0.5f + 0.25f * level));
    }

}
