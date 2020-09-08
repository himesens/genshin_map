package himesens.genshin_map.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import himesens.genshin_map.dao.InviteRecordRepository;
import himesens.genshin_map.entity.InviteRecord;

@Service
public class InviteRecordService {
	
	@Autowired
	InviteRecordRepository irr;
	
	public void save(String usercode) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(new Date());
		InviteRecord ir = new InviteRecord(usercode,now,"申请");
		irr.save(ir);
	}
}
