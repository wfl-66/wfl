package com.xiaoshu.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

public class ContentCategory implements Serializable {
    @Id
    @Column(name = "contentCategoryId")
    private Integer contentcategoryid;

    @Column(name = "contentCategoryname")
    private String contentcategoryname;

    private String status;
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createtime;

    private static final long serialVersionUID = 1L;

    /**
     * @return contentCategoryId
     */
    public Integer getContentcategoryid() {
        return contentcategoryid;
    }

    /**
     * @param contentcategoryid
     */
    public void setContentcategoryid(Integer contentcategoryid) {
        this.contentcategoryid = contentcategoryid;
    }

    /**
     * @return contentCategoryname
     */
    public String getContentcategoryname() {
        return contentcategoryname;
    }

    /**
     * @param contentcategoryname
     */
    public void setContentcategoryname(String contentcategoryname) {
        this.contentcategoryname = contentcategoryname == null ? null : contentcategoryname.trim();
    }

    /**
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * @return createtime
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * @param createtime
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", contentcategoryid=").append(contentcategoryid);
        sb.append(", contentcategoryname=").append(contentcategoryname);
        sb.append(", status=").append(status);
        sb.append(", createtime=").append(createtime);
        sb.append("]");
        return sb.toString();
    }
}