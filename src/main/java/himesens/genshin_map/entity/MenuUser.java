package himesens.genshin_map.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="menu_user")
public class MenuUser {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int listid;
	@Column
	private String usercode;
	@Column
	private String username;
	@Column
	private String password;
	@Column
	private String avatar;
	@Column
	private String registertime;
	@Column
	private String manager;
	@Column
	private String mail;
	@Column
	private String introduce;
	@Column
	private String checkcode;
	
	public int getListid() {
		return listid;
	}
	public void setListid(int listid) {
		this.listid = listid;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public String getRegistertime() {
		return registertime;
	}
	public void setRegistertime(String registertime) {
		this.registertime = registertime;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getCheckcode() {
		return checkcode;
	}
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	
	public MenuUser() {
		
	}
	
	public MenuUser(String usercode, String username, String password, String avatar, String registertime,
			String manager, String mail, String introduce) {
		super();
		this.usercode = usercode;
		this.username = username;
		this.password = password;
		this.avatar = avatar;
		this.registertime = registertime;
		this.manager = manager;
		this.mail = mail;
		this.introduce = introduce;
		this.checkcode = "";
	}
	
	
}
