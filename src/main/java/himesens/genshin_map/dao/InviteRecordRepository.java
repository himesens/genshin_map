package himesens.genshin_map.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import himesens.genshin_map.entity.InviteRecord;



public interface InviteRecordRepository extends JpaRepository<InviteRecord,Integer>{

	
}
