package com.huyue.tdtest.tower;

import android.graphics.Bitmap;

import com.huyue.tdtest.GameSurfaceView;
import com.huyue.tdtest.tdactive.Bullet;
import com.huyue.tdtest.tdactive.Monster;

public class OneTargetBullet extends Bullet
{
    public OneTargetBullet(Bitmap bitmap, int dmg, float x, float y, float dx, float dy, float size)
    {
        super(bitmap, dmg, x, y, dx, dy, size);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void attack(Monster monster)
    {
        GameSurfaceView.animManager.addNewImpact((x + monster.x) / 2, (y + monster.y) / 2);
        monster.damage(dmg);
    }
}
