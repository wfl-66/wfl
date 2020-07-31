package com.xiaoshu.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

public class Content implements Serializable {
    @Id
    @Column(name = "contenId")
    private Integer contenid;

    @Column(name = "contentCategoryId")
    private Integer contentcategoryid;

    private String contenttitle;

    private String contenturl;

    private String picpath;

    private Integer price;

    private String status;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createtime;
    
    @Transient
    private  String contentcategoryname;
    

    

	

	public String getContentcategoryname() {
		return contentcategoryname;
	}

	public void setContentcategoryname(String contentcategoryname) {
		this.contentcategoryname = contentcategoryname;
	}

	private static final long serialVersionUID = 1L;

    /**
     * @return contenId
     */
    public Integer getContenid() {
        return contenid;
    }

    /**
     * @param contenid
     */
    public void setContenid(Integer contenid) {
        this.contenid = contenid;
    }

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
     * @return contenttitle
     */
    public String getContenttitle() {
        return contenttitle;
    }

    /**
     * @param contenttitle
     */
    public void setContenttitle(String contenttitle) {
        this.contenttitle = contenttitle == null ? null : contenttitle.trim();
    }

    /**
     * @return contenturl
     */
    public String getContenturl() {
        return contenturl;
    }

    /**
     * @param contenturl
     */
    public void setContenturl(String contenturl) {
        this.contenturl = contenturl == null ? null : contenturl.trim();
    }

    /**
     * @return picpath
     */
    public String getPicpath() {
        return picpath;
    }

    /**
     * @param picpath
     */
    public void setPicpath(String picpath) {
        this.picpath = picpath == null ? null : picpath.trim();
    }

    /**
     * @return price
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(Integer price) {
        this.price = price;
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
        sb.append(", contenid=").append(contenid);
        sb.append(", contentcategoryid=").append(contentcategoryid);
        sb.append(", contenttitle=").append(contenttitle);
        sb.append(", contenturl=").append(contenturl);
        sb.append(", picpath=").append(picpath);
        sb.append(", price=").append(price);
        sb.append(", status=").append(status);
        sb.append(", createtime=").append(createtime);
        sb.append("]");
        return sb.toString();
    }
}