package himesens.genshin_map.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import himesens.genshin_map.entity.UserPoint;



public interface UserPointRepository extends JpaRepository<UserPoint,Integer>{

	@Query(value="select * from user_point where usercode = :usercode",nativeQuery=true)
	List<UserPoint> findByUsercode(@Param("usercode")String usercode);
}
