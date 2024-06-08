package com.example.adb_connection_demo.domian.interactive;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 交互动作
 */
@Data
public class Interactive {

    /**
     * 匹配率
     */
    @NotEmpty
    private Long matchingRate;

    /**
     * 动作类型
     */
    @NotEmpty
    private String action;

    /**
     * 坐标配置
     */
    private int StartIndex_X;
    private int StartIndex_Y;

    private int EndIndex_X;
    private int EndIndex_Y;

}
