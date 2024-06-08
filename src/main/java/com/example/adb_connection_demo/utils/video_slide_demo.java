package com.example.adb_connection_demo.utils;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import com.example.adb_connection_demo.util.connection_demo;

import java.util.Random;

public class video_slide_demo {

    public static void main(String[] args) {
        // 初始化AndroidDebugBridge
        AndroidDebugBridge.init(false);
        // 连接到ADB服务
        AndroidDebugBridge bridge = AndroidDebugBridge.createBridge("D:/APPOther/ADB/platform-tools/adb", true);

        while(!bridge.hasInitialDeviceList()){
            try {
                Thread.sleep(1000);
                System.out.println("等待连接成功");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try{
            System.out.println("连接成功！开始执行任务");
            IDevice device = bridge.getDevices()[0];
            Random random = new Random();
            for (int i=0; i<300; i++){
                //滑动手机一次
                String str = "input swipe 500 2000 600 1600 150";
//                String str = "input swipe 300 500 700 500 100";
                System.out.println("执行状态: " + connection_demo.exec(device,str));
                System.out.println("循环次数: " + (i+1));
                int randomNumber = random.nextInt(6) + 10;
                System.out.println("-------:"+randomNumber);
                Thread.sleep(randomNumber * 1000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
