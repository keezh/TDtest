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

public class AeraComaTower extends Tower
{
    float comaRange;
    float comaTime;
    
    public static Bitmap[] bitmap = new Bitmap[]
    { BitmapFactory.decodeResource(GameActivity.resources, R.drawable.t0300),
            BitmapFactory.decodeResource(GameActivity.resources, R.drawable.t0301),
            BitmapFactory.decodeResource(GameActivity.resources, R.drawable.t0302) };
    public static Bitmap[] bulletBitmap = new Bitmap[]
            { BitmapFactory.decodeResource(GameActivity.resources, R.drawable.b0300),
                    BitmapFactory.decodeResource(GameActivity.resources, R.drawable.b0301) };
    public static int[] cost = new int[]
            { 160, 220, 320 };
    public static int[] attacks = new int[]
    { 10, 14, 26 };
    public static float[] ranges = new float[]
    { 2f, 2.5f, 3f };
    public static float[] gaps = new float[]
    { 3f, 3f, 3f };
    public static float[] comaRanges = new float[]
    { 2f, 2f, 2f };
    public static float[] comaTimes = new float[]
    { 1f, 1f, 1f };
    
    public AeraComaTower(int ix,int iy,int level)
    {
        super(ix, iy, 10, 2.0f, 0.25f);
        setLevel(level);
    }

    public static void testdraw(Canvas canvas, int ix, int iy)
    {
        Paint paint = new Paint();
        Rect src = CONST.getSrcRect(bitmap[0]);
        Rect dst = CONST.getRect(ix, iy);
        canvas.drawBitmap(bitmap[0], src, dst, paint);
    }
    
    @Override
    public Bitmap getBitmap()
    {
        return bitmap[level];
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
    public void setLevel(int level)
    {
        GameSurfaceView.moneymanager.spend(cost[level]);
        this.level = level;
        attack = attacks[level];
        setRange(ranges[level]);
        gap = gaps[level];
        comaRange = getDisplayRange(comaRanges[level]);
        comaTime = comaTimes[level];
    }

    @Override
    public void fireBullet(float dx, float dy)
    {
        Bitmap bb = (dx<0?bulletBitmap[0]:bulletBitmap[1]);
        GameSurfaceView.bulletmanager.addBullet(new AeraComaBullet(bb, attack, x, y, dx, dy,
                0.5f + 0.25f * level, comaRange, comaTime));
    }
}
