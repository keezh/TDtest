package com.huyue.tdtest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.huyue.tdtest.constant.CONST;
import com.huyue.tdtest.createtower.CreateTower;
import com.huyue.tdtest.popwindow.MockDialog;
import com.huyue.tdtest.popwindow.RetryTheMission;
import com.huyue.tdtest.popwindow.ToPagerSlideActivity;
import com.huyue.tdtest.tdactive.Tower;
import com.huyue.tdtest.tools.AnimManager;
import com.huyue.tdtest.tools.BulletManager;
import com.huyue.tdtest.tools.MissionManager;
import com.huyue.tdtest.tools.MoneyManager;
import com.huyue.tdtest.tools.MonsterManager;
import com.huyue.tdtest.tools.TowerManager;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback,
        View.OnTouchListener, Runnable
{

    SurfaceHolder holder;
    boolean flag = false;
    boolean endthread = false;

    public static int[][] map;
    public static Bitmap mapBitmap;

    public static MonsterManager monstermanager;
    public static TowerManager towermanager;
    public static BulletManager bulletmanager;
    public static MoneyManager moneymanager;
    public static MissionManager missionManager;
    public static AnimManager animManager;

    boolean ismove = false;

    //    OldCreatetower createtower;
    public static CreateTower createTower;

    public GameSurfaceView(Context context)
    {
        super(context);
        holder = this.getHolder();
        holder.addCallback(this);
        monstermanager = new MonsterManager();
        towermanager = new TowerManager();
        bulletmanager = new BulletManager();
        moneymanager = new MoneyManager();
        missionManager = new MissionManager(PagerSlideActivity.missionNumber);
        animManager = new AnimManager();

        this.setOnTouchListener(this);
    }

    public void drawmap(Canvas canvas)
    {
        /*
         * ���ƺ�ɫ����
         */
        Paint blackpaint = new Paint();
        blackpaint.setColor(Color.WHITE);
        canvas.drawRect(new Rect(0, 0, CONST.SCREEN.RIGHT, CONST.SCREEN.BOTTOM), blackpaint);

        Paint paint = new Paint();
        Rect src = CONST.getSrcRect(mapBitmap);
        Rect dst = new Rect(0, CONST.SCREEN.TOP, CONST.MAP.SIZE * CONST.MAP.WIDTH ,
                CONST.SCREEN.TOP + CONST.MAP.SIZE * CONST.MAP.HEIGHT);
        canvas.drawBitmap(mapBitmap, src, dst, paint);
//        Log.i("dst", ""+dst.right+" " + dst.bottom);

        /*
         * ��ʾ������������� debug��
         */
        /*
        Paint freepaint = new Paint();
        freepaint.setColor(Color.LTGRAY);
        Paint roadpaint = new Paint();
        roadpaint.setColor(Color.GRAY);
        for (int ix = 0; ix < CONST.MAP.HEIGHT; ix++)
        {
            for (int iy = 0; iy < CONST.MAP.WIDTH; iy++)
            {
                Paint temppaint = freepaint;
                if (map[ix][iy] >= CONST.MAP.UP && map[ix][iy] <= CONST.MAP.RIGHT)
                {
                    temppaint = roadpaint;
                    canvas.drawRect(CONST.getRect(ix, iy), temppaint);
                }
                else if (map[ix][iy] == CONST.MAP.TARGET)
                {
                    temppaint = new Paint();
                    temppaint.setColor(Color.BLUE);
                    canvas.drawRect(CONST.getRect(ix, iy), temppaint);
                }
                else if (map[ix][iy] == CONST.MAP.FREE || map[ix][iy] == CONST.MAP.NOTFREE)
                {
                    canvas.drawRect(CONST.getRect(ix, iy), temppaint);
                }
            }
        }
        */
    }

    public void draw()
    {
        if (holder == null)
            return;
        Canvas canvas = holder.lockCanvas();

        /*
         * ע�����˳�� ��ͼ->��->����->�ڵ� ����Ŀ��Ը���ǰ���
         */
        drawmap(canvas);

        missionManager.draw(canvas);
        towermanager.allDraw(canvas);
        monstermanager.allDraw(canvas);
        bulletmanager.allDraw(canvas);
        animManager.allDraw(canvas);

        if (createTower != null)
        {
            createTower.draw(canvas);
        }

        holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        int ix, iy;
        iy = (int) (event.getX() / CONST.MAP.SIZE);
        ix = (int) ((event.getY() - CONST.SCREEN.TOP) / CONST.MAP.SIZE);
        Log.i("touch",ix+" "+iy);
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            //������Ϸ
            if (!flag)
            {
                flag = true;
                Thread thread = new Thread(this);
                thread.start();
                return false;
            }
            else
            {
                if(missionManager.isPauseButton(ix, iy))
                {
                    missionManager.touchPauseButton();
                    return false;
                }
                if(missionManager.isRetryButton(ix, iy))
                {
                    missionManager.touchRetryButton();
                    return false;
                }
                if (ix >= 0 && ix < CONST.MAP.HEIGHT && iy >= 0 && iy < CONST.MAP.WIDTH
                        && event.getY() > CONST.SCREEN.TOP)
                {
                    if (map[ix][iy] == CONST.MAP.FREE)
                    {
                        createTower = missionManager.getCreateTower(ix, iy);
                        return true;
                    }
                    else
                    {
                        if (map[ix][iy] == CONST.MAP.NOTFREE)
                        {
                            Tower tower = towermanager.getTower(ix, iy);
                            if (tower != null)
                            {
                                createTower = tower.getUpAndRemove();
                                return true;
                            }
                        }
                    }
                }
                return false;
            }
        }
        else if (event.getAction() == MotionEvent.ACTION_MOVE)
        {
            createTower.choose(ix, iy);
            return true;
        }
        else if (event.getAction() == MotionEvent.ACTION_UP)
        {
            createTower.newTower(ix, iy);
            createTower = null;
            return false;
        }
        return false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        draw();
        flag = false;
        endthread = false;

        Log.i("surface", "created");

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        endthread = true;
    }


    /**
     *
     */
    @Override
    public void run()
    {
        int fps = 0;
        int sl = 0;
        long st, starttime, endtime;
        st = starttime = System.nanoTime();
        long sleeptime = 0;
        ;
        while (!endthread)
        {
            //�ؿ�����
            if(missionManager.endWave())
            {
                if(missionManager.endMission())
                {
                    if (missionManager.isWin())
                    {
                        MockDialog.setText("��ϲ��أ��Ƿ����棿�������ѡ�ء�");
                        MockDialog.setListener(MockDialog.POSITIVE_BUTTON, new RetryTheMission());
                        MockDialog.setListener(MockDialog.NEGATIVE_BUTTON, new ToPagerSlideActivity());

                        Intent intent = new Intent(GameActivity.thisActivity, MockDialog.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        GameActivity.thisActivity.startActivity(intent);
                    }
                    else
                    {
                        MockDialog.setText("����̫�������������ɡ��Ƿ����棿�������ѡ�ء�");
                        MockDialog.setListener(MockDialog.POSITIVE_BUTTON, new RetryTheMission());
                        MockDialog.setListener(MockDialog.NEGATIVE_BUTTON, new ToPagerSlideActivity());
                        Intent intent = new Intent(GameActivity.thisActivity, MockDialog.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        GameActivity.thisActivity.startActivity(intent);
                    }
                    break;
                }
                missionManager.loadWave();
            }

            if(!missionManager.isPause())
            {
                monstermanager.allAct();
                towermanager.allAct();
                bulletmanager.allAct();
            }
            animManager.allAct();
            try
            {
                draw();
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }

            //��֡��
            endtime = System.nanoTime();
            sleeptime += 1000 / CONST.FPS - ((endtime - starttime) / 1000000);
            starttime = endtime;
            if (sleeptime > 0)
            {
                try
                {

                    Thread.sleep(sleeptime);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                sl++;
                sleeptime = 0;
            }

            //FPS��¼
            starttime = System.nanoTime();
            fps++;
            if ((starttime - st) / 1000000 > 1000)
            {
                Log.i("FPS", "fps: " + fps + "   sl  " + sl);
                sl = 0;
                fps = 0;
                st = starttime;
            }
        }
        flag = false;
    }
}