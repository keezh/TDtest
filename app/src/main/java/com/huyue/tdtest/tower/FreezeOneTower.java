package com.huyue.tdtest.tower;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.com.huyue.tdtest.R;
import com.huyue.tdtest.GameActivity;
import com.huyue.tdtest.GameSurfaceView;
import com.huyue.tdtest.constant.CONST;
import com.huyue.tdtest.tdactive.Tower;

public class FreezeOneTower extends Tower
{

    float freezetime;
    float slow;

    public static Bitmap[] bitmap = new Bitmap[]
    { BitmapFactory.decodeResource(GameActivity.resources, R.drawable.t0100),
            BitmapFactory.decodeResource(GameActivity.resources, R.drawable.t0101),
            BitmapFactory.decodeResource(GameActivity.resources, R.drawable.t0102) };
    public static Bitmap[] bulletBitmap = new Bitmap[]
            { BitmapFactory.decodeResource(GameActivity.resources, R.drawable.b0100),
                    BitmapFactory.decodeResource(GameActivity.resources, R.drawable.b0101) };
    public static int[] cost = new int[]
    { 120, 220, 260 };
    public static int[] attacks = new int[]
    { 2, 4, 9 };
    public static float[] ranges = new float[]
    { 1.5f, 2f, 2.4f };
    public static float[] gaps = new float[]
    { 1.2f, 1f, 0.7f };
    public static float[] freezetimes = new float[]
    { 5f, 7f, 8f };
    public static float[] slows = new float[]
    { 0.42f, 0.36f, 0.28f };
    
    public FreezeOneTower(int ix, int iy, int level)
    {
        super(ix, iy, 10, 2.0f, 0.25f);
        setLevel(level);
    }

    public static void testdraw(Canvas canvas, int ix, int iy)
    {
        Paint paint = new Paint();
        Rect dst = CONST.getRect(ix, iy);
        Rect src = CONST.getSrcRect(bitmap[0]);
        canvas.drawBitmap(bitmap[0], src, dst, paint);
    }

    public void setLevel(int level)
    {
        GameSurfaceView.moneymanager.spend(cost[level]);
        this.level = level;
        attack = attacks[level];
        setRange(ranges[level]);
        gap = gaps[level];
        freezetime = freezetimes[level];
        slow = slows[level];
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
        GameSurfaceView.bulletmanager.addBullet(new FreezeOneBullet(bb , attack, x,
                y, dx, dy, 0.5f + 0.25f * level, freezetime, slow));
    }
}
