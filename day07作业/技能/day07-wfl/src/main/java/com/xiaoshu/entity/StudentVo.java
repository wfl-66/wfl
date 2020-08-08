package com.xiaoshu.entity;

import javax.persistence.Transient;

public class StudentVo extends Student {

    
    private String tname;
    
    private int num;

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
    
    
}
