package com.example.androidapp;

import android.content.ContentProvider;
import android.content.Context;
import android.view.MotionEvent;
import javax.microedition.khronos.opengles.GL10;

// Spriteクラスを継承したPlayerクラス
public class Player extends Sprite
{
	private float velocity_x = 0, velocity_y = 0;
	private int button = 0;
	private boolean canBullet = true;
	private int score = 0;
	private ColSphere col;
	private boolean isDeath = false;
	Sprite bomb;
	Vector2 vec;
	public boolean pleaseKillMe = false;
	private int deathCnt=0;

	public void Init(TextureInfo bomb,TextureInfo info, float x, float y, float width, float height){


		col = new ColSphere();
		this.bomb = new Sprite();
		this.bomb.Init(bomb,x,y,width,height,0,false,1,15,1.0f);
		vec = new Vector2();
		vec.x = x;
		vec.y=  y;
		col.Init(vec,50.0f);
		super.Init(info,x,y,width,height);
	}


	//@Override
	// 更新処理(Spriteクラスの処理をオーバーライド)
	public void Update(float dt, GL10 gl, Context context)
	{
		super.Update(dt);

		// 移動処理
		x += velocity_x * dt;
		y += velocity_y * dt;


		vec.x = x;
		vec.y=  y;
		col.Update(vec);
		if(isDeath){
			deathCnt++;
			bomb.Update(1.0f,x,y);
			if(deathCnt>16){
				pleaseKillMe = true;
			}
		}
		else bomb.Update(0,x,y);
		switch(button)
		{
			case R.id.buttonD:
				velocity_y -= 2000 * dt;
				break;

			case R.id.buttonU:
				velocity_y += 2000 * dt;
				break;

			case R.id.buttonL:
				velocity_x -= 2000 * dt;
				break;

			case R.id.buttonR:
				velocity_x += 2000 * dt;
				break;
			case R.id.buttonA:
				if(canBullet){
					Sound.PlaySE(R.raw.explosion000);
					Bullet bullet = new Bullet();
					TextureInfo info = new TextureInfo();
					info = TextureManager.loadTexture(gl, context.getResources(), R.drawable.bullet);
					bullet.Init(info, x, y + 50.0f, 30, 30);
					GameView.AddBullet(bullet);
					canBullet = false;
				}
				break;
		}

		// 移動速度を更新
		velocity_x -= velocity_x * 5 * dt;
		velocity_y -= velocity_y * 5 * dt;


	}

	// タッチイベント処理
	void Touch(MotionEvent event)
	{
		// バレット生成


		// タッチ座標をゲームシステム座標に変換
		float ratio = GameView.BASE_WID / MainActivity.screenWid;
		float tx = ( event.getX() - MainActivity.screenWid / 2) * ratio;
		float ty = (-event.getY() + MainActivity.screenHei / 2) * ratio;

		// 移動速度を更新
		velocity_x += (tx - x) * 0.5f;
		velocity_y += (ty - y) * 0.5f;
	}

	// ボタン処理
	void Button(int id, MotionEvent event)
	{
		int action = event.getAction();
		if(action == MotionEvent.ACTION_DOWN) {
			// ボタンが押されたら押下ボタンを記憶
			button = id;
		}
		else if(action == MotionEvent.ACTION_UP)
		{
			// ボタンが離されたら押下ボタンをリセット
			button = 0;
			canBullet = true;
		}
	}

	@Override
	public void Draw(GL10 gl) {
		if(!isDeath){
			super.Draw(gl);
		}
		else if(deathCnt < 15){
			bomb.Draw(gl);
		}
	}

	public void AddScore(int score){
		this.score += score;
	}
	public int GetScore(){return score;}
	public ColSphere Getcol(){
		return col;
	}
	public void SetDeath() {isDeath = true;}
}
