package com.huyue.tdtest.tools;

import java.util.ArrayList;

import com.example.com.huyue.tdtest.R;
import com.huyue.tdtest.anim.Anim;
import com.huyue.tdtest.tdactive.Monster;

import android.graphics.Canvas;

public final class MonsterManager
{
    private ArrayList<Monster> monsters;
    private static ArrayList<Anim> monsterAnims;
    static 
    {
        monsterAnims = new ArrayList<Anim>();
        Anim tempanim = new Anim(true);
        tempanim.add(R.drawable.m0000, 0.2f);
        tempanim.add(R.drawable.m0001, 0.2f);
        monsterAnims.add(tempanim);
        tempanim = new Anim(true);
        tempanim.add(R.drawable.m0100, 0.2f);
        tempanim.add(R.drawable.m0101, 0.2f);
        monsterAnims.add(tempanim);
        tempanim = new Anim(true);
        tempanim.add(R.drawable.m0200, 0.2f);
        tempanim.add(R.drawable.m0201, 0.2f);
        monsterAnims.add(tempanim);
        tempanim = new Anim(true);
        tempanim.add(R.drawable.m0300, 0.2f);
        tempanim.add(R.drawable.m0301, 0.2f);
        monsterAnims.add(tempanim);
        tempanim = new Anim(true);
        tempanim.add(R.drawable.m0400, 0.2f);
        tempanim.add(R.drawable.m0401, 0.2f);
        monsterAnims.add(tempanim);
        tempanim = new Anim(true);
        tempanim.add(R.drawable.m0500, 0.2f);
        tempanim.add(R.drawable.m0501, 0.2f);
        monsterAnims.add(tempanim);
        tempanim = new Anim(true);
        tempanim.add(R.drawable.m0600, 0.2f);
        tempanim.add(R.drawable.m0601, 0.2f);
        monsterAnims.add(tempanim);
        tempanim = new Anim(true);
        tempanim.add(R.drawable.m0700, 0.2f);
        tempanim.add(R.drawable.m0701, 0.2f);
        monsterAnims.add(tempanim);
        tempanim = new Anim(true);
        tempanim.add(R.drawable.m0800, 0.2f);
        tempanim.add(R.drawable.m0801, 0.2f);
        monsterAnims.add(tempanim);
        tempanim = new Anim(true);
        tempanim.add(R.drawable.m0900, 0.2f);
        tempanim.add(R.drawable.m0901, 0.2f);
        monsterAnims.add(tempanim);
        tempanim = new Anim(true);
        tempanim.add(R.drawable.m1000, 0.2f);
        tempanim.add(R.drawable.m1001, 0.2f);
        monsterAnims.add(tempanim);
        tempanim = new Anim(true);
        tempanim.add(R.drawable.m1100, 0.2f);
        tempanim.add(R.drawable.m1101, 0.2f);
        monsterAnims.add(tempanim);
        tempanim = new Anim(true);
        tempanim.add(R.drawable.m1200, 0.2f);
        tempanim.add(R.drawable.m1201, 0.2f);
        monsterAnims.add(tempanim);
        tempanim = new Anim(true);
        tempanim.add(R.drawable.m1300, 0.2f);
        tempanim.add(R.drawable.m1301, 0.2f);
        monsterAnims.add(tempanim);
        tempanim = new Anim(true);
        tempanim.add(R.drawable.m1400, 0.2f);
        tempanim.add(R.drawable.m1401, 0.2f);
        monsterAnims.add(tempanim);
        tempanim = new Anim(true);
        tempanim.add(R.drawable.m1500, 0.2f);
        tempanim.add(R.drawable.m1501, 0.2f);
        monsterAnims.add(tempanim);
        tempanim = new Anim(true);
        tempanim.add(R.drawable.m1600, 0.2f);
        tempanim.add(R.drawable.m1601, 0.2f);
        monsterAnims.add(tempanim);
//            monsteranim = new Anim(true);
//            monsteranim.add(R.drawable.m0101, 0.2f);
//            monsteranim.add(R.drawable.m0102, 0.2f);
    }
    
    public MonsterManager()
    {
        monsters = new ArrayList<Monster>();
    }

    

    public ArrayList<Monster> getAllMonsters()
    {
        return monsters;
    }

    public void addMonster(int ix, int iy, float speed, float waittime, int maxhp,int money,int picnumber)
    {
        monsters.add(new Monster(ix, iy, speed, waittime, maxhp, money,monsterAnims.get(picnumber).new AnimObject()));
    }

    public void allAct()
    {
        for (int i = 0; i < monsters.size(); i++)
        {
            if (monsters.get(i).act())
            {
                i--;
            }
        }
    }

    public void allDraw(Canvas canvas)
    {
        for (int i = 0; i < monsters.size(); i++)
        {
            Monster monster = monsters.get(i);
            if (monster != null)
            {
                monster.draw(canvas);
            }
        }
    }

    public void clear()
    {
        monsters.clear();
    }
    
    public boolean isEmpty()
    {
        return monsters.isEmpty();
    }

    public void removeMonster(Monster monster)
    {
        monsters.remove(monster);
    }
}