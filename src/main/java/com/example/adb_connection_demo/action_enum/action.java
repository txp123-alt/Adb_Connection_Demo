package com.example.adb_connection_demo.action_enum;

public enum action {
    CLICK("click"),
    SLIDE("slide");

    private final String actionName;

    private action(String actionName) {
        this.actionName = actionName;
    }

    // getter方法
    public String getDayName() {
        return actionName;
    }
}
