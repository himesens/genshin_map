package himesens.genshin_map.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="inviterecord")
public class InviteRecord {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int listid;
	@Column
	private String usercode;
	@Column
	private String invitetime;
	@Column
	private String status;
	
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
	public String getInvitetime() {
		return invitetime;
	}
	public void setInvitetime(String invitetime) {
		this.invitetime = invitetime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public InviteRecord() {
		
	}
	
	public InviteRecord(String usercode, String invitetime, String status) {
		this.usercode = usercode;
		this.invitetime = invitetime;
		this.status = status;
	}
}
