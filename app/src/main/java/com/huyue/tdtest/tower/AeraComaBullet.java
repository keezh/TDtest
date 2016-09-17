package com.huyue.tdtest.tower;

import android.graphics.Bitmap;

import com.example.com.huyue.tdtest.R;
import com.huyue.tdtest.GameSurfaceView;
import com.huyue.tdtest.anim.Anim;
import com.huyue.tdtest.anim.Anim.AnimObject;
import com.huyue.tdtest.tdactive.Bullet;
import com.huyue.tdtest.tdactive.Monster;

public class AeraComaBullet extends Bullet
{
    public static Anim anim;
    static
    {
        anim = new Anim(false);
        anim.add(R.drawable.b0302, 0.5f / 7);
        anim.add(R.drawable.b0303, 0.5f / 7);
        anim.add(R.drawable.b0304, 0.5f / 7);
        anim.add(R.drawable.b0305, 0.5f / 7);
        anim.add(R.drawable.b0306, 0.5f / 7);
        anim.add(R.drawable.b0307, 0.5f / 7);
        anim.add(R.drawable.b0308, 0.5f / 7);
    }
    
    float comaRange;
    float comaTime;

    public AeraComaBullet(Bitmap bitmap, int dmg, float x, float y, float dx, float dy, float size,
            float comaRange, float comaTime)
    {
        super(bitmap, dmg, x, y, dx, dy, size);
        this.comaRange = comaRange;
        this.comaTime = comaTime;
    }

    @Override
    public void attack(Monster monster)
    {
        GameSurfaceView.bulletmanager.addBullet(new AeraComaSpBullet(anim.new AnimObject(), dmg, x,
                y, comaRange, comaTime));
    }
}
