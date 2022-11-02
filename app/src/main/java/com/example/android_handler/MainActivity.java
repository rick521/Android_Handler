package com.example.android_handler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendMessage();//thread can't influence next line to run
        post();
    }

    void post(){
        Handler handler=new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                /**
                 耗时操作
                 */
                //Toast.makeText(MainActivity.this,"Starting",Toast.LENGTH_LONG).show();

                try {Thread.sleep(5000);} catch (InterruptedException e) {e.printStackTrace();}
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"finished",Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).start();
    }

    void sendMessage(){
         Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {      //判断标志位
                    case 1:
                        /**
                         获取数据，更新UI
                         */
                        Toast.makeText(MainActivity.this,"receive:"+msg.obj,Toast.LENGTH_LONG).show();

                        msg.obj.toString();
                        break;
                }
            }
        };
         class WorkThread extends Thread {
            @Override
            public void run() {
                super.run();
                /**
                 耗时操作
                 */
                try {Thread.sleep(3000);} catch (InterruptedException e) {e.printStackTrace();}

                //从全局池中返回一个message实例，避免多次创建message（如new Message）
                Message msg = Message.obtain();
                msg.obj = "data";
                msg.what=1;   //标志消息的标志
                handler.sendMessage(msg);
            }

        }
        new WorkThread().start();
    }
}