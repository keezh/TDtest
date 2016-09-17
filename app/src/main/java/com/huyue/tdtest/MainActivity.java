package com.huyue.tdtest;

import com.example.com.huyue.tdtest.R;
import com.huyue.tdtest.popwindow.ExitApp;
import com.huyue.tdtest.popwindow.MockDialog;
import com.huyue.tdtest.popwindow.NullListener;
import com.huyue.tdtest.popwindow.RetryTheMission;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity implements OnClickListener
{

    ImageButton starBtn;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        /*
         * 全屏和无标题栏以及锁定竖屏
         */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main);

        starBtn = (ImageButton) this.findViewById(R.id.starGameBtn);
        starBtn.setOnClickListener(this);

    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event)
//    {
//        if (keyCode == KeyEvent.KEYCODE_BACK)
//        {
//            // 创建退出对话框
//            AlertDialog isExit = new AlertDialog.Builder(this).create();
//            // 设置对话框标题
//            isExit.setTitle("提示");
//            // 设置对话框消息
//            isExit.setMessage("确定要退出吗");
//            // 添加选择按钮并注册监听
//            isExit.setButton(AlertDialog.BUTTON_POSITIVE,"确定", listener);
//            isExit.setButton(AlertDialog.BUTTON_NEGATIVE,"取消", listener);
//            // 显示对话框
//            isExit.show();
//
//        }

//        MockDialog.setText("确认退出码?");
//        MockDialog.setListener(MockDialog.POSITIVE_BUTTON, new ExitApp());
//        MockDialog.setListener(MockDialog.NEGATIVE_BUTTON, new NullListener());
//        Intent intent = new Intent(MainActivity.this, MockDialog.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        MainActivity.this.startActivity(intent);
//        return false;
//
//    }

    /** 监听对话框里面的button点击事件 */
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
    {
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                    finish();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onClick(View arg0)
    {
        Intent intent = new Intent(this, PagerSlideActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
