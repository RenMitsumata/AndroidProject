package com.example.myapplication;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GameView extends GLSurfaceView implements GLSurfaceView.Renderer {

    // 描画基準とする解像度を指定
    // 縦横比は端末により異なるため、幅/高さの一方だけを指定し
    // もう一方は0にしておいて計算で求める
    public static float BASE_WID = 768;
    public static float BASE_HEI = 0;

    // 目標FPS
    public static final int BASE_FPS = 30;

    private GL10 gl10;
    private Context context;

    // サーフェイスの幅・高さ
    public static int surfaceWid;
    public static int surfaceHei;

    // FPS計測
    protected FPSManager fpsManager;
    // 毎フレーム何ミリ秒か
    private long frameTime;
    // Sleep時間
    private long sleepTime;
    // フレームスキップを行うかどうか
    private boolean frameSkipEnable;
    // フレームスキップが無効かどうか
    private boolean frameSkipState;

    Player player;

    public GameView(Context context, boolean fs_enable)
    {
        super(context);
        this.context = context;

        setRenderer(this);

        // FPS周りの初期化
        frameSkipEnable = fs_enable;
        frameSkipState = false;
        fpsManager = new FPSManager(10);
        sleepTime = 0l;
        frameTime = (long) (1000.0f / BASE_FPS);
    }

    @Override
    public void onDrawFrame(GL10 gl)
    {
        fpsManager.calcFPS();

        // フレームスキップ有効時のみ処理
        if (frameSkipState)
        {
            // 前回呼び出し時からの経過時間を取得
            long elapsedTime = fpsManager.getElapsedTime();
            // 直前のSleep時間を引く
            elapsedTime -= sleepTime;

            // 設定されている単位時間より小さければ、差分だけSleepし、経過時間を0に
            if (elapsedTime < frameTime && elapsedTime > 0l)
            {
                sleepTime = frameTime - elapsedTime;
                try
                {
                    Thread.sleep(sleepTime);
                }
                catch (InterruptedException e)
                {
                }
                elapsedTime = 0l;
            }
            else
            {
                // スリープ時間を0に
                sleepTime = 0;
                // 単位時間を引く
                elapsedTime -= frameTime;
            }
            // それでもまだ、単位時間より経過時間が大きければ
            if (elapsedTime >= frameTime)
            {
                // フレームスキップ(更新処理を全部一度に実行してしまう)
                if (frameSkipEnable)
                {
                    for (; elapsedTime >= frameTime; elapsedTime -= frameTime)
                        Update((float)frameTime / 1000);
                }
            }
        }
        else
        {
            // 次回のフレームから有効に
            frameSkipState = true;
        }

        Update((float)frameTime / 1000);

        // 描画用バッファをクリア
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        Draw();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
        gl10 = gl;

        // 大きな遅延が起こるので、次回フレーム処理時のフレームスキップを無効に
        frameSkipState = false;

        // サーフェイスの幅・高さを更新
        surfaceWid = width;
        surfaceHei = height;

        // ビューポートをサイズに合わせてセットしなおす
        gl.glViewport(0, 0, width, height);

        // 射影行列を選択
        gl.glMatrixMode(GL10.GL_PROJECTION);
        // 現在選択されている行列(射影行列)に、単位行列をセット
        gl.glLoadIdentity();
        // 平行投影用のパラメータをセット
        if(BASE_WID == 0) {
            BASE_WID = (float)width * BASE_HEI / height;
        }
        else {
            BASE_HEI = (float)height * BASE_WID / width;
        }
        GLU.gluOrtho2D(gl, -BASE_WID / 2, BASE_WID / 2, -BASE_HEI / 2, BASE_HEI / 2);

        // スプライト初期化
        TextureInfo info;
        info = TextureManager.loadTexture(gl, context.getResources(), R.drawable.player000);
        player = new Player();
        player.Init(info, 0, 0, 100, 100);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
        // 大きな遅延が起こるので、次回フレーム処理時のフレームスキップを無効に
        frameSkipState = false;

        // アルファブレンド有効
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        // ディザを無効化
        gl.glDisable(GL10.GL_DITHER);
        // カラーとテクスチャ座標の補間精度を、最も効率的なものに指定
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

        // バッファ初期化時のカラー情報をセット
        gl.glClearColor(0, 0, 0, 1);

        // 片面表示を有効に
        gl.glEnable(GL10.GL_CULL_FACE);
        // カリング設定をCCWに
        gl.glFrontFace(GL10.GL_CCW);

        // 深度テストを無効に
        gl.glDisable(GL10.GL_DEPTH_TEST);

        // フラットシェーディングにセット
        gl.glShadeModel(GL10.GL_FLAT);
    }

    public void onPause()
    {
        // 大きな遅延が起こるので、次回フレーム処理時のフレームスキップを無効に
        frameSkipState = false;
    }

    public void onResume()
    {
        // 大きな遅延が起こるので、次回フレーム処理時のフレームスキップを無効に
        frameSkipState = false;
    }

    // 更新
    private void Update(float dt)
    {
        player.Update(dt);
    }

    // 描画
    private void Draw()
    {
        player.Draw(gl10);
    }

	// タッチイベントをプレイヤークラスに渡す
    public void Touch(MotionEvent event)
    {
        player.Touch(event);
    }
	
	// ボタンイベントをプレイヤークラスに渡す
    public void Button(int id, MotionEvent event)
    {
        player.Button(id, event);
    }
}
