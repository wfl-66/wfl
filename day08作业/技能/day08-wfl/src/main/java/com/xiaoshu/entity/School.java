package com.xiaoshu.entity;

import java.io.Serializable;
import javax.persistence.*;

public class School implements Serializable {
    @Id
    private Integer sid;

    private String scname;

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
     * @return scname
     */
    public String getScname() {
        return scname;
    }

    /**
     * @param scname
     */
    public void setScname(String scname) {
        this.scname = scname == null ? null : scname.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", sid=").append(sid);
        sb.append(", scname=").append(scname);
        sb.append("]");
        return sb.toString();
    }
}