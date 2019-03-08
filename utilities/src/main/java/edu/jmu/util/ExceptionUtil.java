package edu.jmu.util;

/**
 * 异常工具类
 *
 * @author guantianmin
 * @date 2018/6/3 11:28
 */
public class ExceptionUtil {
    /**
     * 打印异常栈
     *
     * @param ex 异常实例
     * @return 返回异常栈字符串信息
     */
    public static String getStackTrace(Exception ex) {
        String lineSeparator = System.getProperty("line.separator", "\n");
        StringBuffer buffer = new StringBuffer();
        buffer.append(ex.getMessage()).append(lineSeparator);
        for (StackTraceElement elem : ex.getStackTrace()) {
            buffer.append("\tat ").append(elem).append(lineSeparator);
        }
        return buffer.toString();
    }
}
