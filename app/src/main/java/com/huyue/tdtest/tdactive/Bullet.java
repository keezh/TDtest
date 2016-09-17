package com.huyue.tdtest.tdactive;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.huyue.tdtest.GameSurfaceView;
import com.huyue.tdtest.constant.CONST;

public abstract class Bullet extends TDActiveObject
{
    public int dmg;
    public float dx;
    public float dy;
    public Bitmap bitmap;
    public float size;
    
    public abstract void attack(Monster monster);
    
    public Bullet(Bitmap bitmap, int dmg, float x, float y, float dx, float dy, float size)
    {
        this.dmg = dmg;
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.bitmap = bitmap;
        this.size = size;
    }

    public Rect getDst()
    {
        int hs = (int) (CONST.MAP.HALFSIZE * size);
        Rect ret = new Rect((int) x - hs, (int) y - hs, (int) x + hs, (int) y + hs);
        return ret;
    }

    @Override
    public void draw(Canvas canvas)
    {
        Paint paint = new Paint();
        Rect src = CONST.getSrcRect(bitmap);
        canvas.drawBitmap(bitmap, src, getDst(), paint);
    }

    public boolean tryAttack()
    {
        Monster one = null;
        double mindis = 2000;
        for (Monster monster : GameSurfaceView.monstermanager.getAllMonsters())
        {
            float tx = monster.x;
            float ty = monster.y;
            double dis = Math.sqrt((x - tx) * (x - tx) + (y - ty) * (y - ty));
            if (dis < CONST.MAP.HALFSIZE * 1.4 && dis < mindis && monster.isactive)
            {
                mindis = dis;
                one = monster;
            }
        }

        if (one == null)
        {
            return false;
        }
        else
        {
            attack(one);
            remove();
            return true;
        }
    }
    
    @Override
    public boolean act()
    {
        x += dx;
        y += dy;

        if (x < 0 || x > CONST.SCREEN.RIGHT || y < 0 || y > CONST.SCREEN.BOTTOM)
        {
            remove();
            return true;
        }
        return tryAttack();
    }

    @Override
    public boolean equals(Object o)
    {
        return o == this;
    }

    @Override
    public void remove()
    {
        GameSurfaceView.bulletmanager.removeBullet(this);
    }
}
