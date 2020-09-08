package himesens.genshin_map.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import himesens.genshin_map.dao.PointDataViewRepository;
import himesens.genshin_map.view.PointDataView;

@Service
public class PointDataViewService {
	
	@Autowired
	PointDataViewRepository pdvr;
	
	public List<PointDataView> getAll(){
		return pdvr.findAllByType();
	}
	
}
