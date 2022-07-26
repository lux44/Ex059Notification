package com.lux.ex059notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(view -> {
            //알림(Notification)을 관리하는 관리자 객체 소환
            NotificationManager manager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            //알림 객체를 만들어주는 건축가(builder) 객체 생성
            NotificationCompat.Builder builder=null;

            //Oreo(API26) 버전 이상에서는 알림시에 NotificationChannel 이라는 개념이 필수 구성요소가 됨.
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                //NotificationChannel : 알림마다 채널을 설정하여 개별적인 설정(알림시에 무음설정, 알림 차단, ...등)

                //알림 채널 객체 생성
                NotificationChannel channel=new NotificationChannel("ch01","MyChannel",NotificationManager.IMPORTANCE_HIGH);
                //3번째 파라미터에 따라 알림의 중요도가 달라지면 그에따라 보이는 형태도 다름
                //IMPORTANCE_HIGH : 소리도 나고 알림이 펼쳐진 모습이 화면 상단에 잠시 보여졋다 사라짐.
                //IMPORTANCE_DEFAULT : 소리는 나지만 시각적으로 방해하는 화면 상단 박스가 없이 상태표시줄에 작은 이미지만 보여짐.
                //IMPORTANCE_LOW : 소리도 안나고 시각적 방해하는 박스도 없이 아이콘만 보여짐.

                Uri uri=Uri.parse("android.resource://"+getPackageName() +"/" +R.raw.s_select);
                channel.setSound(uri,new AudioAttributes.Builder().build());

                //알림관리자에게 위에서 만든 채널을 알림시스템에 만들어 달라고 요청
                manager.createNotificationChannel(channel);
                //건축가 객체를 생성
                builder=new NotificationCompat.Builder(this,"ch01");

            }else {
                //채널 객체 없이 그냥 건축가 객체 생성
                builder=new NotificationCompat.Builder(this,"");

                //사운드 추가
                Uri uri=Uri.parse("android.resource://"+getPackageName() +"/" +R.raw.s_select);
                builder.setSound(uri);
            }

            //건축가에게 알림에 대한 여러 설정들
            //상태표시줄에 보이는 아이콘 모양
            builder.setSmallIcon(R.drawable.ic_noti);

            //상태바를 드래그하여 아래로 내리면 보이는 알림창(확장 상태바)의 설정
            builder.setContentTitle("Title");  //알림창의 제목 글씨
            builder.setContentText("Message...");  //알림창 메세지
            Bitmap bm= BitmapFactory.decodeResource(getResources(),R.drawable.moana02);
            builder.setLargeIcon(bm);

            //알림창을 클릭시에 실행할 작업(새로운 화면[Second Activity])을 설정
            Intent intent=new Intent(this,SecondActivity.class);
            //인텐트를 바로 실행하지 않고 잠시 보류해 달라고 보류중인 인텐트로 변경
            PendingIntent pendingIntent=PendingIntent.getActivity(this,10,intent,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_MUTABLE);
            builder.setContentIntent(pendingIntent);

            //알림창을 클릭했을때 자동으로 알림 아이콘을 없애기
            builder.setAutoCancel(true);

            //알림에 진동주기 [디바이스에 따라 패턴시간은 적용되지 않을 수 있음]
            //진동은 반드시 정적 허가(Permission) 받아야함. Manifest에 추가
            builder.setVibrate(new long[]{0,2000,0,3000}); //0초 대기, 2초 진동, 0초 대기, 3초 진동

            //요즘 들어 종종 보이는 알림창 스타일
            NotificationCompat.BigPictureStyle bigPictureStyle=new NotificationCompat.BigPictureStyle();
            Bitmap bm2=BitmapFactory.decodeResource(getResources(),R.drawable.moana03);
            bigPictureStyle.bigPicture(bm2);

            builder.setStyle(bigPictureStyle);

            //알림창 하단에 다른 화면으로 이동하는 새로운 액션버튼 추가 가능
            builder.addAction(R.drawable.ic_noti,"more",pendingIntent);
            builder.addAction(R.drawable.ic_launcher_foreground,"setting",pendingIntent);

            //알림 객체 생성
            Notification notification= builder.build();

            //알림 관리자 객체에게 알림을 공지
            manager.notify(0,notification);
        });


    }
}