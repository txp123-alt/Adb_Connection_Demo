package com.example.adb_connection_demo.util;
import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import com.example.adb_connection_demo.executer.ShellCommandExecutor;

public class connection_demo {

    //获取设备状态
    public static String SystemStatus(IDevice device){
        IDevice.DeviceState state = device.getState();
        return state.toString();
    }

    //获取设备序列号
    public static String getSerialNumber(IDevice iDevice){
        return iDevice.getSerialNumber();
    };

    //执行指定命令
    //滑动命令 adb shell input swipe 300 500 700 500 1000
    public static String exec(IDevice device, String command) throws Exception{
        //操作手机
        ShellCommandExecutor shellCommandExecutor = new ShellCommandExecutor();
        device.executeShellCommand(command,shellCommandExecutor);

        // 获取执行结果
        return shellCommandExecutor.getOutput();
    };
}
