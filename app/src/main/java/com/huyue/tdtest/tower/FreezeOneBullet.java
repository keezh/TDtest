package com.huyue.tdtest.tower;

import android.graphics.Bitmap;

import com.huyue.tdtest.GameSurfaceView;
import com.huyue.tdtest.tdactive.Bullet;
import com.huyue.tdtest.tdactive.Monster;

public class FreezeOneBullet extends Bullet
{
    float freezetime;
    float slow;

    public FreezeOneBullet(Bitmap bitmap, int dmg, float x, float y, float dx, float dy,
            float size, float freezetime, float slow)
    {
        super(bitmap, dmg, x, y, dx, dy, size);
        this.freezetime = freezetime;
        this.slow = slow;
        // TODO Auto-generated constructor stub
    }

    public void attack(Monster monster)
    {
        GameSurfaceView.animManager.addNewImpact((x + monster.x) / 2, (y + monster.y) / 2);
        monster.freeze(dmg, freezetime, slow);
    }
}
