package edu.jmu.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 基础响应对象，是应用所有的VO对象的父类
 *
 * @author guantianmin
 * @date 2018/5/25 15:51
 */
@ApiModel(value = "BasicResponse", description = "RESTFul接口响应对象")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class BasicResponse<T> {

    /**
     * 消息代码
     * 0-200为正常响应，201-400为错误响应
     */
    @ApiModelProperty(value = "代码", name = "code", example = "0-200为成功代码，200以上为失败代码")
    private Integer code;

    /**
     * 消息内容
     */
    @ApiModelProperty(value = "消息内容", name = "message")
    private String message;

    /**
     * 分页，页号
     */
    @ApiModelProperty(value = "页号", name = "pageNum")
    private Integer pageNum;

    /**
     * 分页，页大小
     */
    @ApiModelProperty(value = "页大小", name = "pageSize")
    private Integer pageSize;

    /**
     * 记录总数
     */
    @ApiModelProperty(value = "记录总数", name = "total")
    private Long total;

    /**
     * 数据内容
     */
    @ApiModelProperty(value = "响应数据", name = "data")
    private T data;

    /**
     * 异常信息
     */
    @ApiModelProperty(value = "异常信息", name = "exception")
    private String exception;
}
