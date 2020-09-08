package himesens.genshin_map.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import himesens.genshin_map.entity.MenuUser;
import himesens.genshin_map.service.MenuUserService;
import himesens.genshin_map.util.AESUtil;
import himesens.genshin_map.util.DataUtil;

@Controller
public class UrlController {
	
	@Autowired
	MenuUserService mus;
	
	@GetMapping({"/","/index"})
	public String diaryIndex(HttpServletRequest request,Model model,
			@RequestParam(name="showuser",defaultValue="0")String showuser) throws UnsupportedEncodingException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String today = sdf.format(new Date());
		
		Cookie[] cookies = request.getCookies();
		String usercode = "";
		String password = "";
		String status = "";
		
		if(cookies==null) {
			//以未登录状态访问主页
			model.addAttribute("islogin",false);
			model.addAttribute("isdevelop", false);
			model.addAttribute("showuser", false);
			model.addAttribute("avatarurl", "");
			return "index";
		}else {
			for(Cookie cookie:cookies) {
				if(cookie.getName().equals("usercode")) {
					usercode = URLDecoder.decode(cookie.getValue(),"utf-8");
				}else if(cookie.getName().equals("password")) {
					password = URLDecoder.decode(cookie.getValue(),"utf-8");
				}else if(cookie.getName().equals("status")) {
					status = URLDecoder.decode(cookie.getValue(),"utf-8");
				}
			}
		}
		
		
		String regitime = AESUtil.AESDecode(DataUtil.AESKEY,status);
		if(!regitime.equals(today)) {
			model.addAttribute("errormsg","登录状态已过期，请重新登录！");
			return "login/login_error";
		}
		usercode = AESUtil.AESDecode(DataUtil.AESKEY,usercode);
		password = AESUtil.AESDecode(DataUtil.AESKEY,password);
		
		if(usercode.equals("")) {
			model.addAttribute("errormsg","用户名密码出错");
			return "login/login_error";
		}else {
			MenuUser user = mus.findByUsercodePwd(usercode,password);
			if(user==null) {
				model.addAttribute("errormsg","用户名密码出错");
				return "login/login_error";
			}else {
				model.addAttribute("islogin",true);
				model.addAttribute("username", user.getUsername());
				model.addAttribute("avatarurl", "avatar/"+user.getAvatar());
				String manager = user.getManager();
				if(manager.equals("管理员") || manager.equals("开发者")) {
					model.addAttribute("isdevelop", true);
				}else {
					model.addAttribute("isdevelop", false);
				}
				
				if(showuser.equals("1")) {
					model.addAttribute("showuser",true);
				}else {
					model.addAttribute("showuser",false);
				}
				return "index";
			}
		}
	}
	
	@GetMapping("/login_p")
	public String toLoginPage() {
		return "login/login_p";
	}
	
	@GetMapping("/register")
	public String toRegister() {
		return "login/register";
	}
	
	@GetMapping("/forgetpwd")
	public String toForgetPwd() {
		return "login/forgetpwd";
	}
	
	@GetMapping("/login")
	public String toLogin(@RequestParam(name="usercode",defaultValue="")String usercode,
						@RequestParam(name="password",defaultValue="")String password,Model model) {
		//先校验usercode
		String lower = usercode.toLowerCase();
		if(lower.contains("select")||lower.contains("delete")||lower.contains("truncate")||lower.contains("update")) {
			model.addAttribute("errormsg","账号或密码错误");
			return "login/login_error";
		}
		
		MenuUser user = mus.findByUsercode(usercode);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String status = sdf.format(new Date());
		
		if(password.equals(user.getPassword())) {
			String user_secret = AESUtil.AESEncode(DataUtil.AESKEY, usercode);
			String pwd_secret = AESUtil.AESEncode(DataUtil.AESKEY, password);
			String status_secret = AESUtil.AESEncode(DataUtil.AESKEY, status);
			
			model.addAttribute("usercode", user_secret);
			model.addAttribute("pwd", pwd_secret);
			model.addAttribute("status", status_secret);
			
			return "login/login_success";
		}else {
			model.addAttribute("errormsg","账号或密码错误");
			return "login/login_error";
		}
	}
	@GetMapping("/logout")
	public String toLogout() {
		return "login/logout";
	}
	@GetMapping("/login_success")
	public String toLoginSuccess() {
		return "login/login_success";
	}
	@GetMapping("/login_error")
	public String toLoginError() {
		return "login/login_error";
	}
	
	@GetMapping("/usersetting")
	public String toUsersetting(HttpServletRequest request,Model model) throws UnsupportedEncodingException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String today = sdf.format(new Date());
		
		Cookie[] cookies = request.getCookies();
		String usercode = "";String password = "";String status = "";		
		if(cookies==null) {
			//以未登录状态访问主页
			model.addAttribute("islogin",false);
			model.addAttribute("avatarurl", "images/test.jpg");
			return "index";
		}else {
			for(Cookie cookie:cookies) {
				if(cookie.getName().equals("usercode")) {
					usercode = URLDecoder.decode(cookie.getValue(),"utf-8");
				}else if(cookie.getName().equals("password")) {
					password = URLDecoder.decode(cookie.getValue(),"utf-8");
				}else if(cookie.getName().equals("status")) {
					status = URLDecoder.decode(cookie.getValue(),"utf-8");
				}
			}
		}
		
		String regitime = AESUtil.AESDecode(DataUtil.AESKEY,status);
		if(!regitime.equals(today)) {
			model.addAttribute("errormsg","登录状态已过期，请重新登录！");
			return "login/login_error";
		}
		usercode = AESUtil.AESDecode(DataUtil.AESKEY,usercode);
		
		MenuUser user = mus.findByUsercode(usercode);
		
		model.addAttribute("userid", user.getListid());
		model.addAttribute("usercode", usercode);
		model.addAttribute("username", user.getUsername());
		model.addAttribute("manager", user.getManager());
		model.addAttribute("mail", user.getMail());
		model.addAttribute("introduce", user.getIntroduce());
		model.addAttribute("imgurl", "avatar/"+user.getAvatar());
		
		return "setting/setting";
	}
}
