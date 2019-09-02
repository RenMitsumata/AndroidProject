package com.example.androidapp;

import static java.lang.Math.sin;

// 敵クラスB
public class EnemyB extends Enemy
{

    private int sin_angle = 0;
    private float x_init;

    @Override
    public void Init(
            float x,
            float y
    )
    {
        super.Init(x, y);

        // sinカーブの計算用に初期位置を覚えておく
        x_init = x;
    }

    @Override
    public void Update(float dt)
    {
        super.Update(dt);

        if(visible)
        {
            // sinカーブ移動
            sin_angle = (sin_angle + 2) % 360;
            x = x_init + (float)sin(sin_angle * 6.28f / 360) * 100;
            y -= 4 * GameView.BASE_FPS * dt;

            // 画面外に出たら無効化
            if (y < -(GameView.BASE_HEI / 2 + hei / 2)) {
                visible = false;
                GameView.AddDeleteEnemyList(this);
            }
            vec.x = this.x;
            vec.y = this.y;
            col.Update(vec);
        }
    }
}
