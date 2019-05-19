package com.farmershao.stock.dto;

import com.farmershao.stock.constant.ResponseCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * ResponseDto
 *
 * @author : Shao Yu
 * @since 2019/5/5 19:19
 **/
@ApiModel
@AllArgsConstructor
@Getter
@Setter
public class ResponseDto<T> {

    @ApiModelProperty(value="响应码 0:响应成功",name="code")
    private int code;
    @ApiModelProperty(value="响应消息",name="msg")
    private String msg;
    @ApiModelProperty(value="响应数据",name="data")
    private T data;

    public ResponseDto(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseDto(ResponseCodeEnum codeEnum) {
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMsg();
    }

}
