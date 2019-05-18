package com.farmershao.stock.dto;

import com.farmershao.stock.constant.ResponseCodeEnum;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * PageResponseDto
 *
 * @author : Zhao Da
 * @since : 2019/4/26 10:24
 */
@ApiModel
@Setter
@Getter
@NoArgsConstructor
public class PageResponseDto<T> {

    @ApiModelProperty(value="是否执行成功,true成功；false失败",name="flag")
    private boolean flag = true;

    @ApiModelProperty(value="响应码 0:响应成功",name="code")
    private int code = ResponseCodeEnum.SUCCESS.getCode();

    @ApiModelProperty(value="响应消息",name="msg")
    private String msg = "page search success";

    @ApiModelProperty(value="每页数量",name="pageSize")
    private int pageSize;

    @ApiModelProperty(value="当前页码",name="pageNum")
    private int pageNum;

    @ApiModelProperty(value="总记录数",name="total")
    private long total;

    @ApiModelProperty(value="总页数",name="total")
    private int totalPage;

    @ApiModelProperty(value="响应数据",name="data")
    private List<T> data;

    public PageResponseDto(PageInfo<T> pageInfo) {
        this.data = pageInfo.getList();
        this.pageNum = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
        this.total = pageInfo.getTotal();
        this.totalPage= pageInfo.getPages();
    }
}
