package com.huyue.tdtest.tdactive;

import android.graphics.Canvas;
import android.graphics.Rect;

public abstract class TDActiveObject
{
    /*
     * x,y用于表示对象的中心在画布上的位置
     */
    public float x;
    public float y;

    public int ix;
    public int iy;

    /*
     * draw 用于绘制图像自身
     */
    public abstract void draw(Canvas canvas);

    public float getX()
    {
        return x;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public float getY()
    {
        return y;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    public int getIx()
    {
        return ix;
    }

    public void setIx(int ix)
    {
        this.ix = ix;
    }

    public int getIy()
    {
        return iy;
    }

    public void setIy(int iy)
    {
        this.iy = iy;
    }

    /*
     * act 用于线程动作
     */

    public abstract boolean act();

    public abstract void remove();
}
