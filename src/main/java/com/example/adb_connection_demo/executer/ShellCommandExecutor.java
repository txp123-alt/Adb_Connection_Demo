package com.example.adb_connection_demo.executer;

import com.android.ddmlib.IShellOutputReceiver;

public class ShellCommandExecutor implements IShellOutputReceiver {

    private StringBuilder outputBuilder = new StringBuilder();

    // 接收命令的输出
    @Override
    public void addOutput(byte[] data, int offset, int length) {
        // 将字节数据转换为字符串并添加到输出构建器中
        outputBuilder.append(new String(data, offset, length));
    }

    @Override
    public void flush() {
        // 如果需要，可以在这里处理任何剩余的输出或执行清理操作
        // 但在这个简单的例子中，我们不需要做任何事情
    }

    @Override
    public boolean isCancelled() {
        // 如果需要取消命令执行，返回 true；否则返回 false
        // 在这个例子中，我们始终不取消命令
        return false;
    }

    // 获取完整的输出字符串
    public String getOutput() {
        return outputBuilder.toString();
    }
}
