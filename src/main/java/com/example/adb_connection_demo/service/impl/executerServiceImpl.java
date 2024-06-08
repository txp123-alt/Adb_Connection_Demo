package com.example.adb_connection_demo.service.impl;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import com.example.adb_connection_demo.domian.device.DeviceSignature;
import com.example.adb_connection_demo.domian.device.Screen;
import com.example.adb_connection_demo.service.executerService;
import com.example.adb_connection_demo.util.connection_demo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class executerServiceImpl implements executerService {

    private final AndroidDebugBridge androidDebugBridge;

    @Override
    public List<DeviceSignature> getDevicesList() {
        ArrayList<DeviceSignature> deviceSignatures = new ArrayList<>();
        IDevice[] devices = androidDebugBridge.getDevices();

        // 获取连接的设备列表
        for (IDevice device : devices) {
            DeviceSignature deviceSignature = new DeviceSignature();
            try{
                Map<String, Integer> screenSize = connection_demo.getScreenSize(device);
                // 打印设备信息
                deviceSignature.setDeviceSerialNo(device.getSerialNumber());
                deviceSignature.setOnLineStatus(connection_demo.SystemStatus(device));
                Screen screen = new Screen();
                screen.setWidth(screenSize.get("Width"));
                screen.setHeight(screenSize.get("Height"));
                deviceSignature.setScreen(screen);
                deviceSignatures.add(deviceSignature);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return deviceSignatures;
    }
}
