package com.example.androidapp;

import javax.microedition.khronos.opengles.GL10;
import java.util.*;
// スプライトクラス
public class Sprite {

    protected TextureInfo texInfo;  // テクスチャ情報(com.example.androidapp.TextureManager.loadTexture()の戻り値)

    public float x, y;       // X、Y座標
    public float wid, hei;   // 横幅、縦幅
    public float rotate;     // 回転角度
    public boolean reverse;  // 反転表示
    public float alpha;      // アルファ値
    public int pattern;      // アニメパターン数
    public float interval;   // アニメ間隔(ms)

    protected float animCnt;   // アニメカウンタ

    // 初期化(簡易設定)
    public void Init(
            TextureInfo texInfo,    // com.example.androidapp.TextureManager.loadTexture()の戻り値
            float x,                // X座標
            float y,                // Y座標
            float wid,              // 横幅
            float hei               // 縦幅
    )
    {
        // オーバーロード関数を呼び出す
        Init(texInfo, x, y, wid, hei, 0, false, 1, 1, 1);
    }

    // 初期化(詳細設定)
    public void Init(
            TextureInfo texInfo,    // com.example.androidapp.TextureManager.loadTexture()の戻り値
            float x,                // X座標
            float y,                // Y座標
            float wid,              // 横幅
            float hei,              // 縦幅
            float rotate,           // 回転角度
            boolean reverse,        // 反転表示
            float alpha,            // アルファ値
            int pattern,            // アニメパターン数
            float interval          // アニメ間隔
        )
    {
        this.texInfo = texInfo;
        this.x = x;
        this.y = y;
        this.wid = wid;
        this.hei = hei;
        this.rotate = rotate;
        this.reverse = reverse;
        this.alpha = alpha;
        this.pattern = pattern;
        this.interval = interval;

        if(this.interval <= 0) this.interval = 1;
        animCnt = 0;
    }

    // 終了
    public void Uninit()
    {
    }

    // 更新
    public void Update(float dt)
    {
        // アニメカウンタ更新
        animCnt += dt;
    }

    public void Update(float dt, int count)
    {
        // アニメカウンタ更新
        animCnt += dt;

        // 座標更新
        x = (float)Math.sin(Math.toRadians(count)) * 200;
        y = (float)Math.cos(Math.toRadians(count)) * 200;
    }
    public void Update(float dt,float x,float y)
    {
        // アニメカウンタ更新
        animCnt += dt;
        /*
        this.x += x;
        this.y += y;
        */
    }
    // 描画
    public void Draw(GL10 gl)
    {
        // 読込失敗時
        if(texInfo == null) return;

        // テクスチャ描画
        TextureDrawer.drawTexture(
                gl,
                texInfo.texId,
                x, y, wid, hei,
                rotate, reverse,
                (float)((int)(animCnt / interval) % pattern) / pattern, 0, 1.0f / pattern, 1,
                1, 1, 1, alpha
        );
    }
}
