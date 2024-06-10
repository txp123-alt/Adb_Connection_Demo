package com.example.adb_connection_demo.service;

import com.example.adb_connection_demo.domian.device.DeviceSignature;
import com.example.adb_connection_demo.domian.interactive.Interactive;

import java.io.InputStream;
import java.util.List;

public interface executerService {

    /**
     * 已连接设备列表
     * @return
     */
    List<DeviceSignature> getDevicesList();

    /**
     * 获取屏幕截图
     * @param interactive
     */
    InputStream getScreencap(Interactive interactive)throws Exception;

    /**
     * 点击动作
     * @param interactive
     * @return
     */
    int click(Interactive interactive) throws Exception;

    /**
     * 滑动动作
     * @param interactive
     */
    void slide(Interactive interactive) throws Exception;

    /**
     * 返回主界面
     * @param interactive
     */
    void goHome(Interactive interactive) throws Exception;
}
