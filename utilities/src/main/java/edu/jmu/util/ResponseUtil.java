package edu.jmu.util;

/**
 * 信息工具类
 *
 * @author guantianmin
 * @date 2018/9/16 13:03
 */
public class ResponseUtil {
    /**
     * @param response 响应类
     * @param code     代码
     * @param msgText  消息内容
     * @param data     业务数据
     * @param <T>
     */
    public static <T> void set(BasicResponse<T> response, Integer code, String msgText, T data) {
        response.setCode(code);
        response.setMessage(msgText);
        if (data != null) {
            response.setData(data);
        }
    }

    /**
     * @param response 响应类
     * @param code     代码
     * @param msgText  消息内容
     * @param <T>
     */
    public static <T> void set(BasicResponse<T> response, Integer code, String msgText) {
        set(response, code, msgText, null);
    }

    /**
     * @param response  响应类
     * @param code      代码
     * @param msgText   消息内容
     * @param data      业务数据
     * @param exception 异常消息
     * @param <T>
     */
    public static <T> void set(BasicResponse<T> response, Integer code, String msgText, T data, String exception) {
        set(response, code, msgText, data);
        response.setException(exception);
    }

    /**
     * @param response 响应类
     * @param code     代码
     * @param msgText  消息内容
     * @param data     业务数据
     * @param pageNum  页号
     * @param pageSize 页大小
     * @param total    总记录数
     * @param <T>
     */
    public static <T> void set(BasicResponse<T> response, Integer code, String msgText, T data, Integer pageNum, Integer pageSize, Long total) {
        set(response, code, msgText, data);
        response.setPageNum(pageNum);
        response.setPageSize(pageSize);
        response.setTotal(total);
    }
}
