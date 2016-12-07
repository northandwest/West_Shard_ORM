package west.orm.core;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.Transient;

@Table(name = "ulewo_user")
public class UlewoUser implements Serializable {

	@Transient
	private static final long serialVersionUID = 1475752874133l;
	
	@Id
	@Column(name = "user_id")
	private int userId; // userid

	@Column(name = "email")
	private String email; // 邮箱

	@Column(name = "user_name")
	private String userName; // 用户名

	@Column(name = "nick_name")
	private String nickName; // nickname

	@Column(name = "password")
	private String password; // 密码

	@Column(name = "user_icon")
	private String userIcon; // 用户小图像

	@Column(name = "age")
	private String age; // 年龄

	@Column(name = "user_bg")
	private String userBg; // 背景

	@Column(name = "sex")
	private String sex; // 性别 m男 f女

	@Column(name = "characters")
	private String characters; // 个性签名

	@Column(name = "mark")
	private int mark; // 积分

	@Column(name = "address")
	private String address; // 籍贯

	@Column(name = "work")
	private String work; // 职业

	@Column(name = "register_time")
	private Date registerTime; // 注册时间

	@Column(name = "pre_visit_time")
	private Date preVisitTime; // 访问时间

	@Column(name = "birthday")
	private Date birthday; // 生日

	@Column(name = "isvalid")
	private String isvalid; // 是否有效 y有效 n无效

	@Column(name = "center_theme")
	private String centerTheme; // 主题

	@Column(name = "activation_code")
	private String activationCode; // 激活码

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getUserBg() {
		return userBg;
	}

	public void setUserBg(String userBg) {
		this.userBg = userBg;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCharacters() {
		return characters;
	}

	public void setCharacters(String characters) {
		this.characters = characters;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Date getPreVisitTime() {
		return preVisitTime;
	}

	public void setPreVisitTime(Date preVisitTime) {
		this.preVisitTime = preVisitTime;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}

	public String getCenterTheme() {
		return centerTheme;
	}

	public void setCenterTheme(String centerTheme) {
		this.centerTheme = centerTheme;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

}
