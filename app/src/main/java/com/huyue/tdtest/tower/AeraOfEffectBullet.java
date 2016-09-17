package com.huyue.tdtest.tower;

import java.util.HashSet;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.huyue.tdtest.GameSurfaceView;
import com.huyue.tdtest.anim.Anim;
import com.huyue.tdtest.constant.CONST;
import com.huyue.tdtest.tdactive.Bullet;
import com.huyue.tdtest.tdactive.Monster;

public class AeraOfEffectBullet extends Bullet
{

    private Anim.AnimObject animObject;
    private float range;
    private HashSet<Monster> dmged;
    
    public AeraOfEffectBullet(Anim.AnimObject animObject,int dmg, float x, float y, float range)
    {
        super(null, dmg, x, y, 0, 0, 0);
        this.animObject = animObject;
        this.range = range;
        animObject.start();
        size = 0;
        dmged = new HashSet<Monster>();
        bitmap = animObject.getFirstBitmap();
    }
    
    @Override
    public void draw(Canvas canvas)
    {
        Paint paint = new Paint();
        Rect src = CONST.getSrcRect(bitmap);
        float tr = range * size;
        Rect dst = new Rect((int) (x - tr), (int) (y - tr), (int) (x + tr), (int) (y + tr));
        canvas.drawBitmap(bitmap, src, dst, paint);
    }
    
    @Override
    public boolean tryAttack()
    {
        double mindis = range * size;
        for (Monster monster : GameSurfaceView.monstermanager.getAllMonsters())
        {
            float tx = monster.x;
            float ty = monster.y;
            double dis = Math.sqrt((x - tx) * (x - tx) + (y - ty) * (y - ty));
            if (dis < CONST.MAP.HALFSIZE * 0.5 +  mindis && monster.isactive)
            {
                if(!dmged.contains(monster))
                {
                    attack(monster);
                    dmged.add(monster);
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean act()
    {
        bitmap = animObject.getBitmap();
        tryAttack();
        if(bitmap == null)
        {
            remove();
            return true;
        }
        else
        {
            size += 2f /CONST.FPS;
            if (size > 1)
                size = 1;
            return false;
        }
    }

    @Override
    public void attack(Monster monster)
    {
        monster.damage(dmg);
    }
}

