package com.example.androidapp;

import android.content.Context;
import java.util.regex.Pattern;
import javax.microedition.khronos.opengles.GL10;

// 敵ベースクラス
public class Enemy extends Sprite
{
    ColSphere col;
    Vector2 vec;
    // 敵情報テーブル(敵を作る際ここに追加)
    private static EnemyInfo info[] = {
            new EnemyInfo(
                    "EnemyA",               // クラス名
                    R.drawable.enemy001,    // 画像リソースID
                    100, 100,               // 横幅・縦幅
                    2,                      // アニメパターン数
                    0.1f,                   // アニメ間隔
                    100                     // 得点
            ),
            new EnemyInfo(
                    "EnemyB",               // クラス名
                    R.drawable.enemy000,    // 画像リソースID
                    100, 100,               // 横幅・縦幅
                    2,                      // アニメパターン数
                    0.1f,                   // アニメ間隔
                    200                     // 得点
            ),
    };

    // 敵の種類数＝テーブルのサイズ
    public static final int TYPE_MAX = info.length;

    // テクスチャ情報テーブル
    private static TextureInfo[] texInfo = new TextureInfo[info.length];

    // 敵の種類(テーブルのインデックス)
    private int type;
    // 有効フラグ(GameView側からこれを元にインスタンスの破棄を行う)
    public boolean visible;

    // 使用画像を全て読み込み
    public static void LoadTexture(GL10 gl, Context context)
    {
        for(int i = 0; i < info.length; i++)
        {
            texInfo[i] = TextureManager.loadTexture(gl, context.getResources(), info[i].resId);
        }
    }

    // 初期化
    public void Init(
            float x,    // X座標
            float y     // Y座標
    )
    {
        // クラス名を取得し情報テーブルを使用して初期化
        String[] s = this.getClass().getName().split(Pattern.quote("."));
        for(int i = 0; i < info.length; i++)
        {
            if(s[s.length - 1].equals(info[i].className))
            {
                Init(texInfo[i], x, y, info[i].wid, info[i].hei, 0, false, 1, info[i].pattern, info[i].interval);
                col = new ColSphere();
                vec = new Vector2();
                vec.x = x;
                vec.y=  y;
                col.Init(vec,50.0f);
                visible = true;
                type = i;
                break;
            }
        }



    }

    // 得点をテーブルから取得
    public int Score()
    {
        return info[type].score;
    }

    public ColSphere Getcol(){
        return col;
    }
}
