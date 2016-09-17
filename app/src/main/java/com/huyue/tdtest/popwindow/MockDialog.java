package com.huyue.tdtest.popwindow;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.com.huyue.tdtest.R;
import com.huyue.tdtest.GameSurfaceView;

public class MockDialog extends Activity
{
    public static String text;
    private static PopWindowListener pos;
    private static PopWindowListener neg;
    public static int POSITIVE_BUTTON = 0;
    public static int NEGATIVE_BUTTON = 1;

    public interface PopWindowListener
    {
        public void onTouch(MockDialog mockDialog);
    }

    public static void setText(String text)
    {
        MockDialog.text = text;
    }

    public static void setListener(int button, PopWindowListener pop)
    {
        switch (button)
        {
        // POSITIVE_BUTTON
        case 0:
            pos = pop;
            break;
        // NEGATIVE_BUTTON
        case 1:
            neg = pop;
            break;
        default:
            break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setTheme(R.style.translucent);
        if(GameSurfaceView.missionManager!=null)
            GameSurfaceView.missionManager.pause();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popwindow);

        ImageButton positive = (ImageButton) findViewById(R.id.positive);
        ImageButton negative = (ImageButton) findViewById(R.id.negative);

        positive.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                Log.i("popwindow", "true");
                if (pos != null)
                {
                    pos.onTouch(MockDialog.this);
                }
                finish();
            }
        });
        negative.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                Log.i("popwindow", "false");
                if (neg != null)
                {
                    neg.onTouch(MockDialog.this);
                }
            }
        });
        TextView textView = (TextView) findViewById(R.id.textview);
        textView.setText(text);
    }

    @Override
    public void finish()
    {
        super.finish();
        if(GameSurfaceView.missionManager!=null)
            GameSurfaceView.missionManager.play();
    }
}