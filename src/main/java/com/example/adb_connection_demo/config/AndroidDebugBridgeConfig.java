package com.example.adb_connection_demo.config;

import com.android.ddmlib.AndroidDebugBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AndroidDebugBridgeConfig {

    @Bean
    public AndroidDebugBridge createBridge(){
        AndroidDebugBridge.init(false);

        // 连接到ADB服务(家)
        AndroidDebugBridge bridge = AndroidDebugBridge.createBridge("D:/APPOther/ADB/platform-tools/adb", true);
        //公司
        //AndroidDebugBridge bridge = AndroidDebugBridge.createBridge("D:/adb/platform-tools-latest-windows/platform-tools/adb", true);

        // 等待直到ADB服务连接成功
        while (!bridge.hasInitialDeviceList()){
            try {
                Thread.sleep(1000);
                System.out.println("等待连接成功");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("bridge 链接成功!");
        return bridge;
    }
}
