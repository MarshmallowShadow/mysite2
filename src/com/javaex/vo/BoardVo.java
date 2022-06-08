package com.javaex.vo;

public class BoardVo extends UserVo {
	private int no;
	private String title;
	private String content;
	private int hit;
	private String regDate;
	private int userNo;
	
	public BoardVo() {
		super();
	}
	public BoardVo(int no, String title, String content, int hit, String regDate, int userNo) {
		this.title = title;
		this.content = content;
		this.userNo = userNo;
		this.no = no;
		this.hit = hit;
		this.regDate = regDate;
	}
	public BoardVo(int no, String title, String content, int hit, String regDate, int userNo, String name) {
		this(no, title, content, hit, regDate, userNo);
		super.setName(name);
	}
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public int getUserNo() {
		return userNo;
	}
	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	
	@Override
	public String toString() {
		return "BoardVo[no=" + no + ", title=" + title + ", content" + content + ", hit=" + hit + ", regDate=" + regDate + ", userNo=" + userNo + "]";
	}
}
