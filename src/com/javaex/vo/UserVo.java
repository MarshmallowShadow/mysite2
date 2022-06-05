package com.javaex.vo;

public class UserVo {
	private int no;
	private String id;
	private String password;
	private String name;
	private String gender;
	
	public UserVo() { }
	public UserVo(String id, String password) {
		this.id = id;
		this.password = password;
	}
	public UserVo(String id, String password, String name, String gender){
		this(id, password);
		this.name = name;
		this.gender = gender;
	}
	public UserVo(int no, String id, String password, String name, String gender) {
		this(id, password, name, gender);
		this.no = no;
	}
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	@Override
	public String toString() {
		return "UserVo[no=" + no + ", name=" + name + ", id=" + id + ", password=" + password + ", gender=" + gender + "]";
	}
}
