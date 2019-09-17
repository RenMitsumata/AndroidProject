package com.example.androidapp;
import javax.microedition.khronos.opengles.GL10;


public class Bg extends Sprite {
    float mover;
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
        mover = 0.0f;
    }
    @Override
    public void Update(float dt){
        mover -= 0.01f;
    }

    @Override
    public void Draw(GL10 gl){
        // 読込失敗時
        if(texInfo == null) return;

        // テクスチャ描画
        TextureDrawer.drawTexture(
                gl,
                texInfo.texId,
                x, y, wid, hei,
                rotate, reverse,
                0, mover, 1.0f, 1.0f,
                1, 1, 1, alpha
        );
    }
}
