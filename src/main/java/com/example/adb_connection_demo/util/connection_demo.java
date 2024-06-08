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
