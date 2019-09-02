package com.example.androidapp;

// 敵情報クラス(構造体の代わり)
public class EnemyInfo
{

    String className;   // クラス名
    int resId;          // リソースID
    float wid, hei;     // 横幅・縦幅
    int pattern;        // アニメパターン数
    float interval;     // アニメ間隔
    int score;          // 得点

    public EnemyInfo(String className, int resId, float wid, float hei, int pattern, float interval, int score)
    {
        this.className = className;
        this.resId = resId;
        this.wid = wid;
        this.hei = hei;
        this.pattern = pattern;
        this.interval = interval;
        this.score = score;
    }
}
