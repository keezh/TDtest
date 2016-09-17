package com.huyue.tdtest.tools;

import java.util.ArrayList;

import com.huyue.tdtest.tdactive.Bullet;

import android.graphics.Canvas;

public class BulletManager
{
    public ArrayList<Bullet> bullets;

    public BulletManager()
    {
        bullets = new ArrayList<Bullet>();
    }

    public void addBullet(Bullet bullet)
    {
        bullets.add(bullet);
    }

    public void allAct()
    {
        for (int i = 0; i < bullets.size(); i++)
        {
            if (bullets.get(i).act())
            {
                i--;
            }
        }
    }

    public void allDraw(Canvas canvas)
    {
        for (int i = 0; i < bullets.size(); i++)
        {
            Bullet bullet = bullets.get(i);
            if (bullet != null)
            {
                bullet.draw(canvas);
            }
        }
    }

    public void clear()
    {
        bullets.clear();
    }

    public void removeBullet(Bullet bullet)
    {
        bullets.remove(bullet);
    }
}
