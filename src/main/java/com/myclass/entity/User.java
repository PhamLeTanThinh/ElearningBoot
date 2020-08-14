package com.myclass.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "fullname")
	@NotBlank(message = "vui lòng nhập tên")
	@Length(min = 4,max = 50, message = "tên ít nhất 4 ký tự và nhiều nhất 50 ký tự")
	private String fullname;

	@Column(name = "email")
	@NotBlank(message = "vui lòng nhập email")
	@Email(message = "vui lòng nhập đúng định dạng email, VD: phamletanthinh@gmail.com")
	private String email;

	@Column(name = "password")
	@NotBlank(message = "vui lòng nhập password")
	private String password;
	
	@Column(name = "avatar")
	private String avatar;
	
	@Column(name = "phone")
	@NotBlank(message = "vui lòng nhập số điện thoại")
	private String phone;
	
	@Column(name = "role_id")
	private int roleId;
	
	@ManyToOne
	@JoinColumn(name = "role_id", insertable = false, updatable = false)
	@JsonIgnore
	private Role role;
	
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<UserCourse> userCourses;
	
	public User() {}

	public User(int id, String fullname, String email, String password, String avatar, String phone, int roleId) {
		super();
		this.id = id;
		this.fullname = fullname;
		this.email = email;
		this.password = password;
		this.avatar = avatar;
		this.phone = phone;
		this.roleId = roleId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<UserCourse> getUserCourses() {
		return userCourses;
	}

	public void setUserCourses(List<UserCourse> userCourses) {
		this.userCourses = userCourses;
	}
}
