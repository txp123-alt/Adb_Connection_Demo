package com.example.adb_connection_demo.action_enum;

public enum Action {
    CLICK("click"),
    SLIDE("slide");

    private final String actionName;

    private Action(String actionName) {
        this.actionName = actionName;
    }

    // getter方法
    public String getValue() {
        return actionName;
    }
}
