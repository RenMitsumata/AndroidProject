package com.example.androidapp;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity implements View.OnTouchListener
{
    GameView gameSurfaceView;

    public static int screenWid, screenHei;

    //@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        gameSurfaceView = new GameView(this, true);
        setContentView(gameSurfaceView);

        // WindowManagerのインスタンス取得
        WindowManager wm = getWindowManager();
        // Displayのインスタンス取得
        Display display = wm.getDefaultDisplay();
        screenWid = display.getWidth();
        screenHei = display.getHeight();

        // ボタン設置（使わない場合はコメントアウト）
        View layout = View.inflate(this, R.layout.activity_main, null);
        addContentView(layout, new ViewGroup.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.FILL_PARENT));
        int btnId[] = {
                R.id.buttonL, R.id.buttonR, R.id.buttonU, R.id.buttonD, R.id.buttonA, R.id.buttonB,
        };
        for(int i = 0; i < btnId.length; i++) {
            findViewById(btnId[i]).setOnTouchListener(this);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        gameSurfaceView.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        gameSurfaceView.onPause();
    }

    @Override
    // 画面タッチ時処理
    public boolean onTouchEvent( MotionEvent event )
    {
        gameSurfaceView.Touch( event );
        return true;
    }

    @Override
    // ボタンタッチ時処理
    public boolean onTouch(View v, MotionEvent event)
    {
        if (v != null) {
            gameSurfaceView.Button(v.getId(), event);
        }
        return false;
    }
}
