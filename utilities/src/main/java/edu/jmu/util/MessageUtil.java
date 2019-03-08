package edu.jmu.util;

/**
 * 信息工具类
 *
 * @author guantianmin
 * @date 2018/9/16 13:03
 */
public class MessageUtil {
    public static Message create(Integer code, String msgText) {
        Message message = new Message();
        message.setCode(code);
        message.setMessage(msgText);
        return message;
    }
}
