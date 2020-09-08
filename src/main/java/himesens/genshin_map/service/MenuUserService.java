package himesens.genshin_map.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import himesens.genshin_map.dao.MenuUserRepository;
import himesens.genshin_map.entity.MenuUser;

@Service
public class MenuUserService {
	
	@Autowired
	MenuUserRepository mur;
	
	public MenuUser findByUsercode(String usercode) {
		return mur.findByUsercode(usercode);
	}
	
	public MenuUser findByUsercodePwd(String usercode,String password) {
		return mur.findByUsercodePwd(usercode,password);
	}
	
	public void updateavatarurl(String usercode,String avatarurl) {
		mur.updateavatarurl(usercode,avatarurl);
	}
	
	public void updateuser(String usercode,String username,String mail,String introduce) {
		mur.updateuser(usercode,username,mail,introduce);
	}
	
	public void createuser(String usercode, String username, String password,String mail,String manager) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		MenuUser mu = new MenuUser(usercode,username,password,"bbb.jpg",sdf.format(new Date()),manager,mail,"");
		mur.save(mu);
	}
	
	public MenuUser checkMail(String usercode,String mail) {
		return mur.checkmail(usercode,mail);
	}
	
	public void updatecheckcode(String usercode,String checkcode) {
		mur.updatecheckcode(usercode,checkcode);
	}
	public void changepwd(String usercode,String password) {
		mur.changepwd(usercode,password);
	}
}
