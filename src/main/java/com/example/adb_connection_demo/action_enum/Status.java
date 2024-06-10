package com.example.adb_connection_demo.action_enum;

public enum Status {
    INITIAL(0),
    SUCCESS(1),

    FAIL(-1);

    private final int status_index;

    private Status(int status_index) {
        this.status_index = status_index;
    }

    // getter方法
    public int getValue() {
        return status_index;
    }
}
