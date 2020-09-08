package himesens.genshin_map.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import himesens.genshin_map.dao.UserPointRepository;
import himesens.genshin_map.entity.UserPoint;

@Service
public class UserPointService {
	
	@Autowired
	UserPointRepository upr;
	
	public List<UserPoint> findByUsercode(String usercode){
		return upr.findByUsercode(usercode);
	}
	
	public void addUserPoint(String usercode,int pointid,String time) {
		UserPoint up = new UserPoint(usercode,pointid,time);
		upr.save(up);
	}
}
