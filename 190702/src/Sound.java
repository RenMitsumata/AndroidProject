package com.example.myapplication;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.util.Hashtable;
import java.util.Map;

public class Sound
{
	static private MediaPlayer bgm;
	static private SoundPool se;
	static private Context context;

	static private Map<Integer, Integer> seList = new Hashtable<Integer, Integer>();

	static void Init(Context context)
	{
		Sound.context = context;
        se = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
	}

	static void Uninit()
	{
		bgm.stop();
		bgm.release();
		for(Integer id : seList.values())
		{
			se.unload(id);
		}
	}

	static boolean LoadSE(int resId)
	{
		int id = se.load(context, resId, 1);
		if(id < 0) return false;
		seList.put(resId, id);
		return true;
	}
	
	static void PlaySE(int resId)
	{
		int id = seList.get(resId);
		if(id >= 0) se.play(seList.get(resId), 1.0f, 1.0f, 0, 0, 1.0f);
	}

	static void PlayBGM(int resId)
	{
		if(bgm != null)
		{
			bgm.stop();
			bgm.release();
		}
		bgm = MediaPlayer.create(context, resId);
		bgm.setLooping(true);
		if(bgm != null) bgm.start();
	}

	static void PauseBGM()
    {
        if(bgm != null && bgm.isPlaying())  bgm.stop();
    }

    static void ResumeBGM()
    {
        if(bgm != null && !bgm.isPlaying())  bgm.start();
    }

    static void SetBGMVolume(float v)
	{
		if(bgm != null) bgm.setVolume(v, v);
	}
}
