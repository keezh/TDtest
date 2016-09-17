package com.huyue.tdtest.tower;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.com.huyue.tdtest.R;
import com.huyue.tdtest.GameActivity;
import com.huyue.tdtest.GameSurfaceView;
import com.huyue.tdtest.anim.Anim;
import com.huyue.tdtest.constant.CONST;
import com.huyue.tdtest.tdactive.Tower;

public class AeraOfEffectTower extends Tower
{
    public static Bitmap[] bitmap = new Bitmap[]
    { BitmapFactory.decodeResource(GameActivity.resources, R.drawable.t0400),
            BitmapFactory.decodeResource(GameActivity.resources, R.drawable.t0401),
            BitmapFactory.decodeResource(GameActivity.resources, R.drawable.t0402) };
    public static Anim anim;
    static
    {
        anim= new Anim(false);
        anim.add(R.drawable.b0400, 0.5f/7);
        anim.add(R.drawable.b0401, 0.5f/7);
        anim.add(R.drawable.b0402, 0.5f/7);
        anim.add(R.drawable.b0403, 0.5f/7);
        anim.add(R.drawable.b0404, 0.5f/7);
        anim.add(R.drawable.b0405, 0.5f/7);
        anim.add(R.drawable.b0406, 0.5f/7);
        anim.add(R.drawable.b0407, 0.5f/7);
    }
    public static int[] cost = new int[]
    { 100, 260, 320 };
    public static int[] attacks = new int[]
    { 17, 30, 44 };
    public static float[] ranges = new float[]
    { 2.5f, 3f, 3.5f };
    public static float[] gaps = new float[]
    { 1.8f, 1.7f, 1.6f };

    public AeraOfEffectTower(int ix,int iy,int level)
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
    }

    @Override
    public void fireBullet(float dx, float dy)
    {
        GameSurfaceView.bulletmanager.addBullet(new AeraOfEffectBullet(anim.new AnimObject(),
                attack, x, y, range));
    }
}
