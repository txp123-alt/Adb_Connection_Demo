package com.example.adb_connection_demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.adb_connection_demo.action_enum.Action;
import com.example.adb_connection_demo.action_enum.Status;
import com.example.adb_connection_demo.domian.device.DeviceSignature;
import com.example.adb_connection_demo.domian.interactive.Interactive;
import com.example.adb_connection_demo.service.executerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/executer")
@RequiredArgsConstructor
@Slf4j
public class executerController {

    private final executerService executerService;

    /**
     * 获取已连接的设备列表
     * @return
     */
    @GetMapping("/getDevicesList")
    public String getDevicesList(){
        log.info("查看设备列表");
        String message = "";
        try{
            List<DeviceSignature> devicesList = executerService.getDevicesList();
            message = JSON.toJSONString(devicesList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return message;
    }

    /**
     * 获取屏镜像
     * @param interactive
     * @return
     */
    @PostMapping("/getScreencap")
    public ResponseEntity<InputStreamResource> getScreencap(@Valid @RequestBody Interactive interactive){
        InputStreamResource resource = null;
        try{
            InputStream screencap = executerService.getScreencap(interactive);
            if (null!=screencap){
                resource = new InputStreamResource(screencap);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        // 设置响应头（不设置Content-Length，因为长度未知）
        // Content-Disposition用于指示这是一个附件，应该被下载而不是在浏览器中显示
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + interactive.getDeviceSerialNo() + "\"")
                .contentType(MediaType.IMAGE_PNG) // 或者使用具体的MIME类型
                .body(resource);
    }

    /**
     * 实现点击动作
     * @param interactive
     * @return
     */
    @PostMapping("/click")
    public ResponseEntity<Interactive> click(@Valid @RequestBody Interactive interactive){
        try{
            int clickStatus = executerService.click(interactive);
            interactive.setStatus(clickStatus);
            return ResponseEntity.ok().body(interactive);
        }catch (Exception e){
            e.printStackTrace();
            interactive.setStatus(Status.FAIL.getValue());
            interactive.setMsg(e.getMessage());
            return ResponseEntity.ok().body(interactive);
        }
    }

    /**
     * 滑动动作
     * @param interactive
     * @return
     */
    @PostMapping("/slide")
    public ResponseEntity<Interactive> slide(@Valid @RequestBody Interactive interactive){
        try{
            executerService.slide(interactive);
            interactive.setStatus(HttpStatus.OK.value());
            return ResponseEntity.ok().body(interactive);
        }catch (Exception e){
            e.printStackTrace();
            interactive.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            interactive.setMsg(e.getMessage());
            return ResponseEntity.ok().body(interactive);
        }
    }

    /**
     * 返回主页
     * @param interactive
     * @return
     */
    @PostMapping("/goHome")
    public ResponseEntity<Interactive> goHome(@Valid @RequestBody Interactive interactive){
        try{
            executerService.goHome(interactive);
            interactive.setStatus(HttpStatus.OK.value());
            return ResponseEntity.ok().body(interactive);
        }catch (Exception e){
            e.printStackTrace();
            interactive.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            interactive.setMsg(e.getMessage());
            return ResponseEntity.ok().body(interactive);
        }
    }
}
