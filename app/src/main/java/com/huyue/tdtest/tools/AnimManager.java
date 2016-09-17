package com.huyue.tdtest.tools;

import java.util.ArrayList;

import android.graphics.Canvas;

import com.example.com.huyue.tdtest.R;
import com.huyue.tdtest.anim.Anim;
import com.huyue.tdtest.anim.IndependentAnim;

public class AnimManager
{
    private ArrayList<IndependentAnim> anims;

    private static Anim impactCloud;
    static
    {
        impactCloud = new Anim(false);
        impactCloud.add(R.drawable.impc0, 0.07f);
        impactCloud.add(R.drawable.impc1, 0.07f);
        impactCloud.add(R.drawable.impc2, 0.07f);
        impactCloud.add(R.drawable.impc3, 0.07f);
    }
    
    private static Anim upCloud;
    static
    {
        upCloud = new Anim(false);
        upCloud.add(R.drawable.up0, 0.03f);
        upCloud.add(R.drawable.up1, 0.03f);
        upCloud.add(R.drawable.up2, 0.03f);
        upCloud.add(R.drawable.up3, 0.03f);
        upCloud.add(R.drawable.up4, 0.03f);
    }

    public void addNewImpact(float x, float y)
    {
        anims.add(new IndependentAnim(impactCloud.new AnimObject(), x, y, 60, 60));
    }

    public void addNewUp(float x, float y)
    {
        anims.add(new IndependentAnim(upCloud.new AnimObject(), x, y, 60, 60));
    }

    public AnimManager()
    {
        anims = new ArrayList<IndependentAnim>();
    }

    public void allAct()
    {
        for (int i = 0; i < anims.size(); i++)
        {
            if (anims.get(i).act())
            {
                i--;
            }
        }
    }

    public void allDraw(Canvas canvas)
    {
        for (int i = 0; i < anims.size(); i++)
        {
            IndependentAnim anim = anims.get(i);
            if (anim != null)
            {
                anim.draw(canvas);
            }
        }
    }

    public void clear()
    {
        anims.clear();
    }

    public void removeAnim(IndependentAnim anim)
    {
        anims.remove(anim);
    }
}
