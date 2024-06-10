package com.example.adb_connection_demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import com.example.adb_connection_demo.action_enum.Action;
import com.example.adb_connection_demo.action_enum.Status;
import com.example.adb_connection_demo.domian.device.DeviceSignature;
import com.example.adb_connection_demo.domian.device.Screen;
import com.example.adb_connection_demo.domian.interactive.Interactive;
import com.example.adb_connection_demo.service.executerService;
import com.example.adb_connection_demo.util.connection_demo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class executerServiceImpl implements executerService {

    private final AndroidDebugBridge androidDebugBridge;

    private final RestTemplate restTemplate;

    private final CloseableHttpClient httpClient;

    /**
     * 获取连接的设备列表
     * @return
     */
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

    /**
     * 获取指定设备屏幕镜像
     * @param interactive
     */
    @Override
    public InputStream getScreencap(Interactive interactive)throws Exception {
        //获取指定执行器
        IDevice[] devices = androidDebugBridge.getDevices();
        for (IDevice device : devices){
            if (device.getSerialNumber().equals(interactive.getDeviceSerialNo())){
                log.info("发现匹配执行器");
                return connection_demo.getScreencap(device);
            }
        }
        return null;
    }


    /**
     * 点击动作
     * @param interactive
     * @return
     */
    @Override
    public int click(Interactive interactive) throws Exception {
        int status = Status.INITIAL.getValue();

        if (interactive.getAction().equals(Action.CLICK.getValue())){
            //执行动作
            //获取指定执行器
            IDevice[] devices = androidDebugBridge.getDevices();

            for (IDevice device : devices){
                if (device.getSerialNumber().equals(interactive.getDeviceSerialNo())){
                    log.info("发现匹配执行器");
                    //触发所有序列号匹配的设备
                    connection_demo.execClick(device, interactive.getStartIndex_X(), interactive.getStartIndex_Y());
                    status = Status.SUCCESS.getValue();
                }
            }

            log.info("动作执行完成,{}", JSON.toJSONString(interactive));
        }else {
            throw new IllegalAccessException("Action Exception 动作类型不匹配");
        }

        return status;
    }

    @Override
    public void slide(Interactive interactive)throws Exception {
        if (interactive.getAction().equals(Action.SLIDE.getValue())){
            //执行动作
            //获取指定执行器
            IDevice[] devices = androidDebugBridge.getDevices();

            for (IDevice device : devices){
                if (device.getSerialNumber().equals(interactive.getDeviceSerialNo())){
                    log.info("发现匹配执行器");
                    //触发所有序列号匹配的设备
                    connection_demo.execSlide(
                            device,
                            interactive.getStartIndex_X(),
                            interactive.getStartIndex_Y(),
                            interactive.getEndIndex_X(),
                            interactive.getEndIndex_Y(),
                            150); //默认滑动持续时长 150毫秒
                }
            }

            log.info("动作执行完成,{}", JSON.toJSONString(interactive));
        }else {
            throw new IllegalAccessException("Action Exception 动作类型不匹配");
        }
    }

    @Override
    public void goHome(Interactive interactive)throws Exception {
        if (interactive.getAction().equals(Action.SLIDE.getValue())){
            //执行动作
            //获取指定执行器
            IDevice[] devices = androidDebugBridge.getDevices();

            for (IDevice device : devices){
                if (device.getSerialNumber().equals(interactive.getDeviceSerialNo())){
                    log.info("发现匹配执行器");
                    //触发所有序列号匹配的设备
                    connection_demo.execGoHome(device);
                }
            }
            log.info("动作执行完成,{}", JSON.toJSONString(interactive));
        }else {
            throw new IllegalAccessException("Action Exception 动作类型不匹配");
        }
    }
}
