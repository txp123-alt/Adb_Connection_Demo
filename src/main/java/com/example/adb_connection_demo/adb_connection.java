package com.example.adb_connection_demo;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import com.example.adb_connection_demo.util.connection_demo;

import java.util.Map;

public class adb_connection {

    public static void main(String[] args) {
        // 初始化AndroidDebugBridge
        AndroidDebugBridge.init(false);

        // 连接到ADB服务(家)
//        AndroidDebugBridge bridge = AndroidDebugBridge.createBridge("D:/APPOther/ADB/platform-tools/adb", true);
        //公司
        AndroidDebugBridge bridge = AndroidDebugBridge.createBridge("D:/adb/platform-tools-latest-windows/platform-tools/adb", true);

        // 等待直到ADB服务连接成功
        while (!bridge.hasInitialDeviceList()){
            try {
                Thread.sleep(1000);
                System.out.println("等待连接成功");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("链接成功!");

        // 获取连接的设备列表
        for (IDevice device : bridge.getDevices()) {
            try{
                // 打印设备信息
                System.out.println("Device: " + device.getSerialNumber());

                System.out.println("设备序列号: " + connection_demo.getSerialNumber(device));

                System.out.println("系统状态: " + connection_demo.SystemStatus(device));

                //滑动动作
                String str = "input swipe 500 2000 600 1600 150";
                System.out.println("执行状态: " + connection_demo.exec(device,str));

                //String str1 = "exec-out screencap -p > D:/XM1994/image.png";
                //System.out.println("获取屏幕镜像: " + connection_demo.getScreencap(device));
                Map<String, Integer> screenSize = connection_demo.getScreenSize(device);
                System.out.println("获取屏幕尺寸 Width: " + screenSize.get("Width"));
                System.out.println("获取屏幕尺寸 Height: " + screenSize.get("Height"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        // 关闭ADB服务
        AndroidDebugBridge.terminate();
    }
}
