package com.huyue.tdtest.tools;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.huyue.tdtest.GameSurfaceView;
import com.huyue.tdtest.constant.CONST;

public class MoneyManager
{
    private int money;

    public int getMoney()
    {
        return money;
    }

    public void draw(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setTextSize((int) (CONST.MAP.SIZE * 0.5));
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        canvas.drawText("" + GameSurfaceView.moneymanager.getMoney(), CONST.MAP.SIZE,
                (int) (CONST.MAP.SIZE * 0.7), paint);
    }
    
    public void setMoney(int money)
    {
        this.money = money;
    }

    public boolean isEnough(int money)
    {
        return this.money >= money;
    }

    public void add(int money)
    {
        this.money += money;
    }

    public boolean spend(int money)
    {
        if (this.money >= money)
        {
            this.money -= money;
            return true;
        }
        Log.i("money", "money is not enough.");
        return false;
    }
}
