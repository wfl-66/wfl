package com.xiaoshu.entity;

import java.util.Date;

import javax.persistence.Column;

import org.springframework.format.annotation.DateTimeFormat;

public class GoodsVo extends Goods {
	@Column(name = "t_typename")
    private String tTypename;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date start;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date end;

	public String gettTypename() {
		return tTypename;
	}

	public void settTypename(String tTypename) {
		this.tTypename = tTypename;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
	
	
}
