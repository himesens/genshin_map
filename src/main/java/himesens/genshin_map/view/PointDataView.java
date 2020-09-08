package himesens.genshin_map.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="point_view")
public class PointDataView {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int pointid;
	@Column
	private String point_lat;
	@Column
	private String point_lng;
	@Column
	private String pointtype;
	@Column
	private String addperson;
	@Column
	private String addtime;
	@Column
	private String pointname_cn;
	@Column
	private String tips_cn;
	@Column
	private String iconurl;
	@Column
	private String layername;
	
	
	public int getPointid() {
		return pointid;
	}
	public void setPointid(int pointid) {
		this.pointid = pointid;
	}
	public String getPoint_lat() {
		return point_lat;
	}
	public void setPoint_lat(String point_lat) {
		this.point_lat = point_lat;
	}
	public String getPoint_lng() {
		return point_lng;
	}
	public void setPoint_lng(String point_lng) {
		this.point_lng = point_lng;
	}
	public String getPointtype() {
		return pointtype;
	}
	public void setPointtype(String pointtype) {
		this.pointtype = pointtype;
	}
	public String getAddperson() {
		return addperson;
	}
	public void setAddperson(String addperson) {
		this.addperson = addperson;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getPointname_cn() {
		return pointname_cn;
	}
	public void setPointname_cn(String pointname_cn) {
		this.pointname_cn = pointname_cn;
	}
	public String getTips_cn() {
		return tips_cn;
	}
	public void setTips_cn(String tips_cn) {
		this.tips_cn = tips_cn;
	}
	public String getIconurl() {
		return iconurl;
	}
	public void setIconurl(String iconurl) {
		this.iconurl = iconurl;
	}
	public String getLayername() {
		return layername;
	}
	public void setLayername(String layername) {
		this.layername = layername;
	}
}
