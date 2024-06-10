package com.example.adb_connection_demo.util;
import com.android.ddmlib.IDevice;
import com.example.adb_connection_demo.resultExecuter.ShellCommandExecutor;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class connection_demo {

    /**
     * 获取设备状态
     * @param device
     * @return
     */
    public static String SystemStatus(IDevice device){
        IDevice.DeviceState state = device.getState();
        return state.toString();
    }

    /**
     * 获取设备序列号
     * @param iDevice
     * @return
     */
    public static String getSerialNumber(IDevice iDevice){
        return iDevice.getSerialNumber();
    };

    /**
     * 执行指定命令
     * @param device
     * @param command
     * @return
     * @throws Exception
     */
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

    /**
     * 点击动作
     * @param device
     * @param Width 坐标
     * @param Hight 坐标
     * @return
     * @throws Exception
     */
    public static String execClick(IDevice device, int Width, int Hight) throws Exception{
        String command = "input tap "+Width+" "+Hight;
        //操作手机
        ShellCommandExecutor shellCommandExecutor = new ShellCommandExecutor();
        device.executeShellCommand(command,shellCommandExecutor);

        // 获取执行结果
        return shellCommandExecutor.getOutput();
    };

    /**
     * 滑动动作
     * @param device
     * @param startIndexX 坐标
     * @param startIndexY 坐标
     * @param endIndexX 坐标
     * @param endIndexY 坐标
     * @return  String str = "input swipe 500 2000 600 1600 150";
     */
    public static String execSlide(IDevice device, int startIndexX, int startIndexY, int endIndexX, int endIndexY, int sustainTime) throws Exception {

        String command = "input swipe "+startIndexX+" "+startIndexY+" "+endIndexX+" "+endIndexY+" "+sustainTime;
        //操作手机
        ShellCommandExecutor shellCommandExecutor = new ShellCommandExecutor();
        device.executeShellCommand(command,shellCommandExecutor);

        // 获取执行结果
        return shellCommandExecutor.getOutput();
    }

    public static String execGoHome(IDevice device)throws Exception {
        String command = "input keyevent KEYCODE_HOME";
        //操作手机
        ShellCommandExecutor shellCommandExecutor = new ShellCommandExecutor();
        device.executeShellCommand(command,shellCommandExecutor);

        // 获取执行结果
        return shellCommandExecutor.getOutput();
    }

    /**
     * 获取屏幕尺寸
     * @param device
     * @return
     * @throws Exception
     */
    public static Map<String,Integer> getScreenSize(IDevice device)throws Exception{
        HashMap<String, Integer> screenSizeMap = new HashMap<>();

        //获取设备ID
        String serialNumber = getSerialNumber(device);
        String command = "adb -s " + serialNumber + " shell dumpsys display | grep 'mBaseDisplayInfo='";
        Process process = Runtime.getRuntime().exec(command);
        //读取命令输出
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            //System.out.println("Standard Output: " + line);
            // 使用正则表达式来提取尺寸
            Pattern pattern = Pattern.compile("real (\\d+) x (\\d+)");
            Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {
                int width = Integer.parseInt(matcher.group(1));
                int height = Integer.parseInt(matcher.group(2));
                //System.out.println("Width: " + width + ", Height: " + height);
                screenSizeMap.put("Width",width);
                screenSizeMap.put("Height",height);
            } else {
                System.out.println("No match found for screen size.");
            }
        }

        reader.close();

        // 读取标准错误（如果需要）
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        while ((line = errorReader.readLine()) != null) {
            System.out.println("Standard Error: " + line);
        }
        errorReader.close();

        // 等待命令执行完成
        int exitCode = process.waitFor();
        System.out.println("Command exited with code " + exitCode);

        return screenSizeMap;
    };

    /**
     * 获取屏幕截图
     * @param iDevice
     * @return
     * @throws Exception
     */
    public static InputStream getScreencap(IDevice iDevice) throws Exception{

        //获取设备ID
        String serialNumber = getSerialNumber(iDevice);
        System.out.println("@--"+serialNumber);

        // 调用adb命令，例如使用exec-out获取设备屏幕截图
        String command = "adb -s " + serialNumber + " exec-out screencap -p";
        Process process = Runtime.getRuntime().exec(command);
        //获取输出流
        InputStream inputStream = process.getInputStream();
        inputStream.close();

        return inputStream;
    }
}
