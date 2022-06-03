package com.javaex.vo;

public class GuestVo {
	private int no;
	private String name;
	private String password;
	private String content;
	private String regDate;
	
	public GuestVo() { }
	public GuestVo(String name, String password, String content) {
		this.name = name;
		this.password = password;
		this.content = content;
	}
	public GuestVo(int no, String name, String password, String content, String regDate) {
		this(name, password, content);
		this.no = no;
		this.regDate = regDate;
	}
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	
	@Override
	public String toString() {
		return "GuestVo[no=" + no + ", name=" + name + ", password=" + password + ", content=" + content + ", regDate=" + regDate + "]"; 
	}
}
