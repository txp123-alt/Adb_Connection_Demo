package com.example.adb_connection_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;

@SpringBootApplication
public class AdbConnectionDemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(AdbConnectionDemoApplication.class, args);
    }

}
