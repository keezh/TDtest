package com.huyue.tdtest.createtower;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.com.huyue.tdtest.R;
import com.huyue.tdtest.GameActivity;
import com.huyue.tdtest.GameSurfaceView;
import com.huyue.tdtest.constant.CONST;

public class CreateTower
{

    /*
     * ix,iy表示点击时所在的格子
     */
    private int ix;
    private int iy;
    private ArrayList<Integer> aix;
    private int select;
    private int count = 0;
    private float range;

    private static Bitmap chosenBitmap = BitmapFactory.decodeResource(GameActivity.resources,
            R.drawable.chosen);
    private static Bitmap backgroundBitmap = BitmapFactory.decodeResource(GameActivity.resources,
            R.drawable.sbg);
    private ArrayList<TowerCreatorObject> towerCreators;

    public CreateTower(int ix, int iy, float range)
    {
        // 目前设想是3个塔
        aix = new ArrayList<Integer>();
        select = -1;
        this.ix = ix;
        this.iy = iy;
        this.range = range;
        towerCreators = new ArrayList<TowerCreatorObject>();
    }

    public void add(TowerCreatorObject temp)
    {
        towerCreators.add(temp);
        count++;
        if (ix >= CONST.MAP.HEIGHT - 5)
        {
            aix.add(ix - count);
        }
        else
        {
            aix.add(ix + count);
        }
    }

    public void choose(int cix, int ciy)
    {
        select = cix;
    }

    public void draw(Canvas canvas)
    {
        Paint paint = new Paint();
        Rect src = CONST.getSrcRect(chosenBitmap);
        canvas.drawBitmap(chosenBitmap, src, CONST.getRect(ix, iy), paint);
        for (int i = 0; i < count; i++)
        {
            src = CONST.getSrcRect(backgroundBitmap);
            canvas.drawBitmap(backgroundBitmap,src, CONST.getRect(aix.get(i), iy), paint);
            towerCreators.get(i).draw(canvas, aix.get(i), iy);
            if(!GameSurfaceView.moneymanager.isEnough(towerCreators.get(i).cost()))
            {
                paint.setColor(Color.GRAY);
                paint.setAlpha(200);

                canvas.drawRect(CONST.getRect(aix.get(i), iy), paint);
            }
        }
        Rect rect;
        float range = this.range;
        for (int i = 0; i < count; i++)
        {
            if (aix.get(i) == select)
            {
                rect = CONST.getRect(aix.get(i), iy);
                paint = new Paint();

                rect.left = 0;
                rect.right = CONST.SCREEN.RIGHT;
                paint.setColor(Color.GRAY);
                paint.setAlpha(100);
                canvas.drawRect(rect, paint);
                range = towerCreators.get(i).getRange();

            }
        }

        //射程
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setAlpha(50);

        rect = CONST.getRect(ix, iy);
        int x = (rect.left + rect.right) / 2;
        int y = (rect.top + rect.bottom) / 2;
        canvas.drawCircle(x, y, range, paint);
    }

    public String getString()
    {
        int i = count;
        for (int j = 0; j < aix.size(); j++)
        {
            if(aix.get(j) == select)
            {
                i = j;
            }
        }
        if(i < count)
        {
            return towerCreators.get(i).getString();
        }
        return "";
    }

    public void newTower(int tix, int tiy)
    {
        int i = count;
        for (int j = 0; j < aix.size(); j++)
        {
            if(aix.get(j) == tix)
            {
                i = j;
            }
        }
        if (i >= count)
        {
            return;
        }
        else
        {
            Rect rect = CONST.getRect(ix, iy);
            float x = (rect.left+rect.right)/2;
            float y = (rect.top+rect.bottom)/2;
            GameSurfaceView.animManager.addNewUp(x, y);
            towerCreators.get(i).act(ix, iy);
        }
    }
}
