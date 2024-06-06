package com.example.adb_connection_demo.util;
import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;
import com.example.adb_connection_demo.executer.ShellCommandExecutor;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

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
    //获取屏幕镜像 adb shell screencap -p | adb pull /sdcard/screen.png C:/path/to/save/image.png
    //adb exec-out screencap -p > C:/path/to/save/image.png
    public static String exec(IDevice device, String command) throws Exception{
        //操作手机
        ShellCommandExecutor shellCommandExecutor = new ShellCommandExecutor();
        device.executeShellCommand(command,shellCommandExecutor);

        // 获取执行结果
        return shellCommandExecutor.getOutput();
    };

    //获取屏幕截图
    public static Object getScreencap(IDevice iDevice) throws Exception{

        //获取设备ID
        String serialNumber = getSerialNumber(iDevice);
        System.out.println("@--"+serialNumber);

        // 调用adb命令，例如使用exec-out获取设备屏幕截图
        String command = "adb -s " + serialNumber + " exec-out screencap -p";
        Process process = Runtime.getRuntime().exec(command);

        // 读取命令输出
//        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//        String line;
//        while ((line = reader.readLine())!= null) {
//            // 处理每一行输出，例如写入文件或进行其他操作
//            System.out.println(line);
//        }

        // 将命令输出重定向到文件
        InputStream inputStream = process.getInputStream();
        FileOutputStream outputStream = new FileOutputStream("D:/XM1994/screen_" + serialNumber + ".png");

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        outputStream.close();
        inputStream.close();

        int exitCode = process.waitFor();
        System.out.println("@--"+exitCode);
        return null;
    }
}
