package himesens.genshin_map.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import himesens.genshin_map.dao.PointTypeRepository;

@Service
public class PointTypeService {
	
	@Autowired
	PointTypeRepository pdr;
	
	
}
