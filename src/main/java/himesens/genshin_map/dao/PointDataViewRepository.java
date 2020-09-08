package himesens.genshin_map.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import himesens.genshin_map.view.PointDataView;



public interface PointDataViewRepository extends JpaRepository<PointDataView,Integer>{

	@Query(value="select * from point_view order by pointtype",nativeQuery=true)
	List<PointDataView> findAllByType();
}
