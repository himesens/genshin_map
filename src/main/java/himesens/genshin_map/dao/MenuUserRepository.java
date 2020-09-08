package himesens.genshin_map.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import himesens.genshin_map.entity.MenuUser;



public interface MenuUserRepository extends JpaRepository<MenuUser,Integer>{

	@Query(value="select * from menu_user where usercode = :usercode",nativeQuery=true)
	public MenuUser findByUsercode(@Param("usercode")String usercode);
	
	@Query(value="select * from menu_user where usercode = :usercode and password=:password",nativeQuery=true)
	public MenuUser findByUsercodePwd(@Param("usercode")String usercode,@Param("password")String password);
	
	@Query(value="select * from menu_user where usercode = :usercode and mail=:mail",nativeQuery=true)
	public MenuUser checkmail(@Param("usercode")String usercode,@Param("mail")String mail);
	
	@Transactional
	@Modifying
	@Query(value="update menu_user set avatar = :avatarurl where usercode = :usercode",nativeQuery = true)
	public void updateavatarurl(@Param("usercode") String usercode,@Param("avatarurl") String avatarurl);
	
	//修改用户信息，包含昵称，邮箱，个人介绍等信息
	@Transactional
	@Modifying 
	@Query(value="update menu_user set username=:username,mail=:mail,introduce=:introduce where usercode = :usercode",nativeQuery = true)
	void updateuser(@Param("usercode") String usercode,@Param("username") String username,@Param("mail")String mail,@Param("introduce")String introduce);
	
	@Transactional
	@Modifying 
	@Query(value="update menu_user set checkcode=:checkcode where usercode = :usercode",nativeQuery = true)
	void updatecheckcode(@Param("usercode") String usercode,@Param("checkcode") String checkcode);
	
	@Transactional
	@Modifying 
	@Query(value="update menu_user set password=:password where usercode = :usercode",nativeQuery = true)
	void changepwd(@Param("usercode") String usercode,@Param("password") String password);
}
