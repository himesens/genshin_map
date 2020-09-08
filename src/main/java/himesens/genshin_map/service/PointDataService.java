package himesens.genshin_map.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import himesens.genshin_map.dao.PointDataRepository;
import himesens.genshin_map.entity.PointData;

@Service
public class PointDataService {
	
	@Autowired
	PointDataRepository pdr;
	
	public void save(String pointlat,String pointlng,String pointtype,String addperson,String addtime) {	
		PointData pd = new PointData(pointlat,pointlng,pointtype,addperson,addtime);
		pdr.save(pd);
	}
}
