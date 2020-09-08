package himesens.genshin_map.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="point_data")
public class PointData {
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
	
	public PointData() {
		
	}
	
	public PointData(String point_lat, String point_lng, String pointtype, String addperson, String addtime) {
		super();
		this.point_lat = point_lat;
		this.point_lng = point_lng;
		this.pointtype = pointtype;
		this.addperson = addperson;
		this.addtime = addtime;
	}
	
}
