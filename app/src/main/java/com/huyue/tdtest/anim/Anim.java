package com.huyue.tdtest.anim;

import java.util.ArrayList;

import com.huyue.tdtest.GameActivity;
import com.huyue.tdtest.constant.CONST;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Anim
{
    private ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
    private ArrayList<Float> waittimes = new ArrayList<Float>();
    private ArrayList<AnimObject> anims = new ArrayList<AnimObject>();
    public boolean iscircle;

    public Anim(boolean iscircle)
    {
        this.iscircle = iscircle;
    }

    public void add(int id, float waittime)
    {
        bitmaps.add(BitmapFactory.decodeResource(GameActivity.resources, id));
        waittimes.add(waittime);
    }

    public class AnimObject
    {
        private int index;
        private float cd;

        public void start()
        {
            index = 0;
            cd = waittimes.get(index);
        }

        public Bitmap getBitmap()
        {
            if (cd > 1e-5)
            {
                cd -= 1f / CONST.FPS;
            }
            else
            {
                index++;
                if (index >= bitmaps.size())
                {
                    if (iscircle)
                    {
                        index = 0;
                    }
                    else
                    {
                        return null;
                    }
                }
                cd = waittimes.get(index);
            }
            return bitmaps.get(index);
        }

        public Bitmap getFirstBitmap()
        {
            return bitmaps.get(0);
        }
        
        public void remove()
        {
            anims.remove(this);
        }

        @Override
        public boolean equals(Object o)
        {
            return this == o;
        }
    }
}
