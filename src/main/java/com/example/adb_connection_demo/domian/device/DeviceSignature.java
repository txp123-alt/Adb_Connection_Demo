package com.example.adb_connection_demo.domian.device;

import lombok.Data;

/**
 * 设备签名
 */
@Data
public class DeviceSignature {
    /**
     * 设备序列号
     */
    private String deviceSerialNo;

    /**
     * 连接状态
     */
    private String onLineStatus;

    /**
     * 屏幕参数
     */
    private Screen screen;
}
