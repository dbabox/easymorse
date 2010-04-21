package com.miop.api.bean;

public class Edu {

	/**学位，0：其他，1：博士，2：研究生，3：本科，4：大专*/
	private String degree = null;
	/**学校名称*/
	private String schoolName = null;
	/**学院*/
	private String department = null;
	/**教育经历的ID*/
	private String eduId = null;
	/**入学年份**/
	private String eduStartDate = null;
	/**学校所在省份**/
	private String province = null;
	
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getEduId() {
		return eduId;
	}
	public void setEduId(String eduId) {
		this.eduId = eduId;
	}
	public String getEduStartDate() {
		return eduStartDate;
	}
	public void setEduStartDate(String eduStartDate) {
		this.eduStartDate = eduStartDate;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	
	
}
