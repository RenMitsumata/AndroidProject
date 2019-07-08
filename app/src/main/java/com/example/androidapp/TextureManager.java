package com.example.androidapp;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.microedition.khronos.opengles.GL10;

public class TextureManager {
    private static Map<Integer, Integer> mTextures =
            new Hashtable<Integer, Integer>();

    private static final BitmapFactory.Options options =
            new BitmapFactory.Options();
    static {
        options.inScaled = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
    }

    public static TextureInfo loadTexture(GL10 gl,
                                          Resources resources, int resId) {
        int[] textures = new int[1];

        // 画像読み込み
        Bitmap bmp = BitmapFactory.decodeResource(resources, resId, options);
        if(bmp == null) {
            return null;
        }

        // 画像サイズを格納
        TextureInfo info = new TextureInfo();
        info.wid = bmp.getWidth();
        info.hei = bmp.getHeight();

        // テクスチャ生成用に、縦横それぞれを2の冪乗にリサイズ
        int dst_wid = 2, dst_hei = 2;
        while(dst_wid < info.wid) dst_wid *= 2;
        while(dst_hei < info.hei) dst_hei *= 2;
        bmp = Bitmap.createScaledBitmap(bmp, dst_wid, dst_hei, true);
        if(bmp == null) {
            return null;
        }

        // 画像からテクスチャを生成
        gl.glGenTextures(1, textures, 0);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D,
                GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D,
                GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);

        bmp.recycle();

        addTexture(resId, textures[0]);

        // テクスチャ情報を返す
        info.texId = textures[0];
        return info;
    }

    public static final void addTexture(int resId, int texId)
    {
        mTextures.put(resId, texId);
    }

    public static final void deleteTexture(GL10 gl, int resId) {
        if(mTextures.containsKey(resId)) {
            int[] texId = new int[1];
            texId[0] = mTextures.get(resId);
            gl.glDeleteTextures(1, texId, 0);
            mTextures.remove(resId);
        }
    }

    public static final void deleteAll(GL10 gl) {
        List<Integer> keys = new ArrayList<Integer>(mTextures.keySet());
        for(Integer key : keys) {
            deleteTexture(gl, key);
        }
    }
}
