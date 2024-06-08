package com.example.adb_connection_demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.adb_connection_demo.domian.device.DeviceSignature;
import com.example.adb_connection_demo.domian.interactive.Interactive;
import com.example.adb_connection_demo.service.executerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
     * 实现点击动作
     */
    @PostMapping("/click")
    public ResponseEntity<Object> click(@Valid @RequestBody Interactive interactive){
//        executerService.click(interactive);
        return null;
    }
}
