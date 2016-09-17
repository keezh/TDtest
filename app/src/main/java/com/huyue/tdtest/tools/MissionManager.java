package com.huyue.tdtest.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.example.com.huyue.tdtest.R;
import com.huyue.tdtest.GameActivity;
import com.huyue.tdtest.GameSurfaceView;
import com.huyue.tdtest.constant.CONST;
import com.huyue.tdtest.createtower.CreateAeraComaTower;
import com.huyue.tdtest.createtower.CreateAeraOfEffectTower;
import com.huyue.tdtest.createtower.CreateFreezeOneTower;
import com.huyue.tdtest.createtower.CreateOneTargetTower;
import com.huyue.tdtest.createtower.CreateThroughTower;
import com.huyue.tdtest.createtower.CreateTower;
import com.huyue.tdtest.popwindow.MockDialog;
import com.huyue.tdtest.popwindow.NullListener;
import com.huyue.tdtest.popwindow.RetryTheMission;

public class MissionManager
{
    private static String[] filename = new String[]
            { "mission1", "mission2", "mission3", "mission4", "mission5" };

    private static Bitmap[] mapBitmaps = new Bitmap[]
            { BitmapFactory.decodeResource(GameActivity.resources, R.drawable.map0),
                    BitmapFactory.decodeResource(GameActivity.resources, R.drawable.map1),
                    BitmapFactory.decodeResource(GameActivity.resources, R.drawable.map2),
                    BitmapFactory.decodeResource(GameActivity.resources, R.drawable.map3),
                    BitmapFactory.decodeResource(GameActivity.resources, R.drawable.map4),};

    private static Bitmap[] eggBitmaps = new Bitmap[]
            { BitmapFactory.decodeResource(GameActivity.resources, R.drawable.egg0),
                    BitmapFactory.decodeResource(GameActivity.resources, R.drawable.egg1),
                    BitmapFactory.decodeResource(GameActivity.resources, R.drawable.egg2),
                    BitmapFactory.decodeResource(GameActivity.resources, R.drawable.egg3),
                    BitmapFactory.decodeResource(GameActivity.resources, R.drawable.egg4),
                    BitmapFactory.decodeResource(GameActivity.resources, R.drawable.egg5),
                    BitmapFactory.decodeResource(GameActivity.resources, R.drawable.egg6),
                    BitmapFactory.decodeResource(GameActivity.resources, R.drawable.egg7),
                    BitmapFactory.decodeResource(GameActivity.resources, R.drawable.egg8),
                    BitmapFactory.decodeResource(GameActivity.resources, R.drawable.egg9),
                    BitmapFactory.decodeResource(GameActivity.resources, R.drawable.egg10), };

    private static Bitmap playBitmap = BitmapFactory.decodeResource(GameActivity.resources,
            R.drawable.button0000);
    private static Bitmap pauseBitmap = BitmapFactory.decodeResource(GameActivity.resources,
            R.drawable.button0001);

    private static Bitmap retryBitmap = BitmapFactory.decodeResource(GameActivity.resources,
            R.drawable.button0100);

    private static Bitmap upbarBitmap = BitmapFactory.decodeResource(GameActivity.resources,
            R.drawable.upbar);

    private boolean[] towerUse = new boolean[5];

    int tix;
    int tiy;

    int missionNumber;
    BufferedReader input;
    int currentWave;
    int totalWave;
    int egg;
    boolean pause;

    public MissionManager(int missionnumber)
    {
        Log.i("mission number", "" + missionnumber);
        missionNumber = missionnumber;
        GameSurfaceView.mapBitmap = mapBitmaps[missionnumber];
        reLoadMissionFile();
    }

    public void reLoadMissionFile()
    {
        try
        {
            InputStreamReader tempstream = new InputStreamReader(GameActivity.resources.getAssets()
                    .open(filename[missionNumber]));
            input = new BufferedReader(tempstream);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        egg = 10;
        loadMissionMap();
        loadWave();
        pause = false;
    }

    public void loadMissionMap()
    {
        String[] line;
        GameSurfaceView.map = new int[CONST.MAP.HEIGHT][CONST.MAP.WIDTH];
        for (int i = 0; i < CONST.MAP.HEIGHT; i++)
        {

            line = readLine();

            for (int j = 0; j < CONST.MAP.WIDTH; j++)
            {
                while (line[j].length() == 0)
                    j++;
                GameSurfaceView.map[i][j] = Integer.parseInt(line[j]);
                if (GameSurfaceView.map[i][j] == CONST.MAP.TARGET)
                {
                    tix = i;
                    tiy = j;
                }
            }
        }

        // 使用的塔限制
        line = readLine();
        for (int i = 0; i < 5; i++)
        {
            if (Integer.parseInt(line[i]) == 1)
            {
                towerUse[i] = true;
            }
            else
            {
                towerUse[i] = false;
            }
        }

        GameSurfaceView.moneymanager.setMoney(Integer.parseInt(read()));
        totalWave = Integer.parseInt(read());
        currentWave = 0;
        Log.i("MissionManager", "mission " + missionNumber + " map set");
    }

    public CreateTower getCreateTower(int ix, int iy)
    {
        CreateTower ret = new CreateTower(ix, iy, 0);
        if (towerUse[0])
        {
            ret.add(new CreateOneTargetTower());
        }
        if (towerUse[1])
        {
            ret.add(new CreateFreezeOneTower());
        }
        if (towerUse[2])
        {
            ret.add(new CreateThroughTower());
        }
        if (towerUse[3])
        {
            ret.add(new CreateAeraComaTower());
        }
        if (towerUse[4])
        {
            ret.add(new CreateAeraOfEffectTower());
        }
        return ret;
    }

    private String read()
    {
        try
        {
            return input.readLine();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private String[] readLine()
    {
        try
        {
            return input.readLine().split(" ");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void loadWave()
    {
        String[] line = readLine();
        int monsterCount = Integer.parseInt(line[1]);
        if (currentWave != Integer.parseInt(line[0]))
        {
            Log.i("loadwave", "wave error");
        }
        for (int i = 0; i < monsterCount; i++)
        {
            line = readLine();
            int ix, iy, maxhp, money, picnumber;
            float speed, waittime;
            ix = Integer.parseInt(line[0]);
            iy = Integer.parseInt(line[1]);
            speed = Float.parseFloat(line[2]);
            waittime = Float.parseFloat(line[3]);
            maxhp = Integer.parseInt(line[4]);
            money = Integer.parseInt(line[5]);
            picnumber = Integer.parseInt(line[6]);
            GameSurfaceView.monstermanager.addMonster(ix, iy, speed, waittime, maxhp, money,
                    picnumber);
        }
    }

    public boolean endWave()
    {
        if (egg <= 0)
            return true;
        boolean ret = GameSurfaceView.monstermanager.isEmpty();
        if (ret)
        {
            currentWave++;
        }
        return ret;
    }

    public void reset()
    {
        // monstermanager = new MonsterManager();
        // towermanager = new TowerManager();
        // bulletmanager = new BulletManager();
        // moneymanager = new MoneyManager();
        // missionManager = new
        // MissionManager(PagerSlideActivity.missionNumber);
        // animManager = new AnimManager();
        GameSurfaceView.monstermanager.clear();
        GameSurfaceView.towermanager.clear();
        GameSurfaceView.bulletmanager.clear();
        GameSurfaceView.animManager.clear();
        reLoadMissionFile();
    }

    public boolean isWin()
    {
        if (egg > 0 && currentWave == totalWave)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void closeinput()
    {
        try
        {
            input.close();
        }
        catch (IOException e)
        {
            Log.i("missiong manager", "file close error");
            e.printStackTrace();
        }
    }

    public boolean endMission()
    {
        if (egg == 0)
        {
            closeinput();
            return true;
        }
        if (currentWave == totalWave)
        {
            closeinput();
        }
        return currentWave == totalWave;
    }

    public void draw(Canvas canvas)
    {

        Paint paint = new Paint();
        /*
         * draw upbar
         */

        Rect src = CONST.getSrcRect(upbarBitmap);
        Rect dst = new Rect(0, 0, CONST.MAP.SIZE * 9, CONST.MAP.SIZE * 7 / 6);
        canvas.drawBitmap(upbarBitmap, src, dst, paint);

        paint.setTextSize((int) (CONST.MAP.SIZE * 0.5));
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);

        canvas.drawText("" + (currentWave + 1) + "/" + totalWave, (int) (CONST.MAP.SIZE * 4),
                (int) (CONST.MAP.SIZE * 0.7), paint);
        paint.setColor(Color.CYAN);
        if (GameSurfaceView.createTower != null)
        {
            canvas.drawText(GameSurfaceView.createTower.getString(), CONST.MAP.SIZE / 4,
                    CONST.MAP.SIZE + (int) (CONST.MAP.SIZE * 0.7), paint);
        }

        /*
         * draw target;
         */
        paint = new Paint();
        src = CONST.getSrcRect(eggBitmaps[egg]);
        canvas.drawBitmap(eggBitmaps[egg], src, CONST.getRect(tix, tiy), paint);

        GameSurfaceView.moneymanager.draw(canvas);

        /*
         * 绘出暂停按钮
         */
        Bitmap tempbitmap;
        if (pause)
        {
            src = CONST.getSrcRect(pauseBitmap);
            tempbitmap = playBitmap;
        }
        else
        {
            src = CONST.getSrcRect(playBitmap);
            tempbitmap = pauseBitmap;
        }
        dst = new Rect(CONST.MAP.SIZE * 8, 0, CONST.MAP.SIZE * 9, CONST.MAP.SIZE);
        canvas.drawBitmap(tempbitmap, src, dst, paint);

        /*
         * 绘制retry按钮
         */
        src = CONST.getSrcRect(retryBitmap);
        dst = new Rect(CONST.MAP.SIZE * 6, 0, CONST.MAP.SIZE * 7, CONST.MAP.SIZE);
        canvas.drawBitmap(retryBitmap, src, dst, paint);
    }

    public void stealEgg(int num)
    {
        egg -= num;
        if (egg < 0)
            egg = 0;
    }

    public boolean isPause()
    {
        return pause;
    }

    public boolean isPauseButton(int tix, int tiy)
    {
        if (tix == -1 && tiy == 8)
            return true;
        else
            return false;
    }

    public void touchPauseButton()
    {
        pause = !pause;
    }

    public void pause()
    {
        pause = true;
    }

    public void play()
    {
        pause = false;
    }

    public boolean isRetryButton(int tix, int tiy)
    {
        if (tix == -1 && tiy == 6)
            return true;
        else
            return false;
    }

    public void touchRetryButton()
    {
        MockDialog.setText("是否确认重玩本关?");
        MockDialog.setListener(MockDialog.POSITIVE_BUTTON, new RetryTheMission());
        MockDialog.setListener(MockDialog.NEGATIVE_BUTTON, new NullListener());
        Intent intent = new Intent(GameActivity.thisActivity, MockDialog.class);
        GameActivity.thisActivity.startActivity(intent);
    }
}
