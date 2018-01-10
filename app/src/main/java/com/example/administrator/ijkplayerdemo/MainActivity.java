package com.example.administrator.ijkplayerdemo;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dou361.ijkplayer.widget.IRenderView;
import com.dou361.ijkplayer.widget.IjkVideoView;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class MainActivity extends AppCompatActivity {

    private IjkVideoView ijk_video_view;

    {
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_find).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });

        ijk_video_view = (IjkVideoView) findViewById(R.id.ijk_video_view);
        ijk_video_view.setAspectRatio(IRenderView.AR_ASPECT_FIT_PARENT);
//        ijk_video_view.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/raw/splash_video.mp4"));
        ijk_video_view.setVideoPath("rtmp://live.hkstv.hk.lxdns.com/live/hks");
        ijk_video_view.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                cursor.moveToFirst();
                String number = cursor.getString(0); // 视频编号
                String path = cursor.getString(1); // 视频文件路径
                String size = cursor.getString(2); // 视频大小
                String name = cursor.getString(3); // 视频文件名
                Log.e("-----", "number=" + number);
                Log.e("-----", "v_path=" + path);
                Log.e("-----", "v_size=" + size);
                Log.e("-----", "v_name=" + name);

            }
        }
    }
}
