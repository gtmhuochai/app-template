package edu.jmu.util;

import org.slf4j.Logger;

import java.util.function.Consumer;

/**
 * 业务包裹器
 *
 * @author guantianmin
 * @date 2018/6/18 0:20
 */
public class BusinessWrapper {

    public static <T> BasicResponse<T> wrap(Consumer<BasicResponse<T>> consumer, Logger logger) {
        BasicResponse<T> response = new BasicResponse<>();
        try {
            consumer.accept(response);
        } catch (Exception e) {
            executeException(logger, response, e);
        }
        return response;
    }

    private static <T> void executeException(Logger logger, BasicResponse<T> response, Exception e) {
        logger.error(ExceptionUtil.getStackTrace(e));
        response.setException(e.getMessage());
        response.setCode(201);
    }
}
