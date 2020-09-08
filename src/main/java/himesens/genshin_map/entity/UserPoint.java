package himesens.genshin_map.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_point")
public class UserPoint {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int listid;
	@Column
	private String usercode;
	@Column
	private int pointid;
	@Column
	private String addtime;
	
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
	public int getPointid() {
		return pointid;
	}
	public void setPointid(int pointid) {
		this.pointid = pointid;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	
	public UserPoint() {
		
	}
	
	public UserPoint(String usercode, int pointid, String addtime) {
		this.usercode = usercode;
		this.pointid = pointid;
		this.addtime = addtime;
	}

}
