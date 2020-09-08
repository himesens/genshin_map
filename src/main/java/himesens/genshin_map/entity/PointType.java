package himesens.genshin_map.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="point_type")
public class PointType {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int pointtype;
	@Column
	private String layername;
	@Column
	private String pointname_cn;
	@Column
	private String pointname_en;
	@Column
	private String tips_cn;
	@Column
	private String tips_en;
	@Column
	private String iconurl;
	
	public int getPointtype() {
		return pointtype;
	}
	public void setPointtype(int pointtype) {
		this.pointtype = pointtype;
	}
	public String getLayername() {
		return layername;
	}
	public void setLayername(String layername) {
		this.layername = layername;
	}
	public String getPointname_cn() {
		return pointname_cn;
	}
	public void setPointname_cn(String pointname_cn) {
		this.pointname_cn = pointname_cn;
	}
	public String getPointname_en() {
		return pointname_en;
	}
	public void setPointname_en(String pointname_en) {
		this.pointname_en = pointname_en;
	}
	public String getTips_cn() {
		return tips_cn;
	}
	public void setTips_cn(String tips_cn) {
		this.tips_cn = tips_cn;
	}
	public String getTips_en() {
		return tips_en;
	}
	public void setTips_en(String tips_en) {
		this.tips_en = tips_en;
	}
	public String getIconurl() {
		return iconurl;
	}
	public void setIconurl(String iconurl) {
		this.iconurl = iconurl;
	}
}
