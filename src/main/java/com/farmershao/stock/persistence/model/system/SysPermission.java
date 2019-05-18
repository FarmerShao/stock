package com.farmershao.stock.persistence.model.system;

/**
 *
 *
 * @author : Zhao Da
 * @since : 2019/4/29 11:33
 */
public class SysPermission {
    private Integer id;

    /**
    * 功能模块名
    */
    private String modelName;

    /**
    * 父级功能名
    */
    private String tagName;

    /**
    * 顺序（同级排序）
    */
    private Integer tag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }
}