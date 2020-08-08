package com.xiaoshu.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

public class Student implements Serializable {
    @Id
    private Integer sid;

    private String sname;

    private String sex;

    private String pic;
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date rtime;

    private Integer tid;
    
    @Transient
    private String tname;
    
    public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	private static final long serialVersionUID = 1L;

    /**
     * @return sid
     */
    public Integer getSid() {
        return sid;
    }

    /**
     * @param sid
     */
    public void setSid(Integer sid) {
        this.sid = sid;
    }

    /**
     * @return sname
     */
    public String getSname() {
        return sname;
    }

    /**
     * @param sname
     */
    public void setSname(String sname) {
        this.sname = sname == null ? null : sname.trim();
    }

    /**
     * @return sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    /**
     * @return pic
     */
    public String getPic() {
        return pic;
    }

    /**
     * @param pic
     */
    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    /**
     * @return rtime
     */
    public Date getRtime() {
        return rtime;
    }

    /**
     * @param rtime
     */
    public void setRtime(Date rtime) {
        this.rtime = rtime;
    }

    /**
     * @return tid
     */
    public Integer getTid() {
        return tid;
    }

    /**
     * @param tid
     */
    public void setTid(Integer tid) {
        this.tid = tid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", sid=").append(sid);
        sb.append(", sname=").append(sname);
        sb.append(", sex=").append(sex);
        sb.append(", pic=").append(pic);
        sb.append(", rtime=").append(rtime);
        sb.append(", tid=").append(tid);
        sb.append("]");
        return sb.toString();
    }
}