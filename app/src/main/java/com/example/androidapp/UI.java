package com.example.androidapp;
import javax.microedition.khronos.opengles.GL10;

public class UI extends Sprite {
    int score;
    public void Update(float dt,int score){
        this.score = score;
    }

    @Override
    public void Draw(GL10 gl){
        // 読込失敗時
        if(texInfo == null) return;

        // テクスチャ描画
        for(int i=0;i<5;i++){
            TextureDrawer.drawTexture(
                    gl,
                    texInfo.texId,
                    (x - 50 * i)-300, y + 400, wid, hei,
                    rotate, reverse,
                    0.1f * ((score / (int)Math.pow(10,i)) % 10), 0, 0.1f, 1.0f,
                    1, 1, 1, alpha
            );
        }

    }
}
