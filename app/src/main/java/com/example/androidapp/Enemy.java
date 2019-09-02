package com.example.androidapp;

import android.content.Context;
import android.view.MotionEvent;
import java.util.Random;
import javax.microedition.khronos.opengles.GL10;

// Spriteクラスを継承したPlayerクラス
public class Enemy extends Sprite
{
	private float velocity_x = 0, velocity_y = 0;

	private Bullet bullet;

	public void Init(TextureInfo texInfo){
		this.texInfo = texInfo;
		Random random = new Random();
		int randomValue = random.nextInt(200);
		super.Init(texInfo,randomValue,400,100,100,0,false,1.0f,2,2.0f);
	}
	//@Override
	// 更新処理(Spriteクラスの処理をオーバーライド)
	public void Update(float dt)
	{
		y -= 3;
		super.Update(dt);
		if(bullet != null){
			bullet.Update();
		}
		/*
		// 移動処理
		x += velocity_x * dt;
		y += velocity_y * dt;

		// 移動速度を更新
		velocity_x -= velocity_x * 5 * dt;
		velocity_y -= velocity_y * 5 * dt;
		*/
	}

	// タッチイベント処理
	void Touch(MotionEvent event)
	{
		// バレット生成

/*
		// タッチ座標をゲームシステム座標に変換
		float ratio = GameView.BASE_WID / MainActivity.screenWid;
		float tx = ( event.getX() - MainActivity.screenWid / 2) * ratio;
		float ty = (-event.getY() + MainActivity.screenHei / 2) * ratio;

		// 移動速度を更新
		velocity_x += (tx - x) * 0.5f;
		velocity_y += (ty - y) * 0.5f;
		*/
	}



	@Override
	public void Draw(GL10 gl) {

		if(bullet != null) {
			bullet.Draw(gl);
		}
		super.Draw(gl);
	}
}
