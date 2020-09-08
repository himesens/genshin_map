package himesens.genshin_map.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import himesens.genshin_map.entity.MenuUser;
import himesens.genshin_map.entity.UserPoint;
import himesens.genshin_map.service.InviteRecordService;
import himesens.genshin_map.service.MenuUserService;
import himesens.genshin_map.service.PointDataService;
import himesens.genshin_map.service.PointDataViewService;
import himesens.genshin_map.service.UserPointService;
import himesens.genshin_map.util.AESUtil;
import himesens.genshin_map.util.CreateCheckImage;
import himesens.genshin_map.util.DataUtil;
import himesens.genshin_map.util.DealImage;
import himesens.genshin_map.view.PointDataView;


@RestController
public class APIController {
	
	@Autowired
	MenuUserService mus;
	@Autowired
	InviteRecordService irs;
	@Autowired
	PointDataViewService pdvs;
	@Autowired
	UserPointService ups;
	@Autowired
	PointDataService pds;
	@Autowired
	private JavaMailSender mailSender;
	
	//注册时，获取随机图
	@PostMapping("/api/getcheckimage")
	public Map<String,Object> getCheckImage(){
	        CreateCheckImage createCheckImage = new CreateCheckImage();
	        Map<String, Object> randommap = createCheckImage.getRandcode();//输出验证码图片方法
	        byte[] randomimg = (byte[])randommap.get("randomimg");
	        String randomnum = (String)randommap.get("randomnum");
	        String strimg = "data:image/jpeg;base64,"+Base64.getEncoder().encodeToString(randomimg);
	        String checknum = AESUtil.AESEncode(DataUtil.AESKEY,randomnum);
	        
	        Map<String, Object> outmap = new HashMap<String, Object>();
	        outmap.put("img",strimg);
	        outmap.put("num",checknum);
	        return outmap;
	}
	//用户注册时，校验用户名是否重复
	@PostMapping(value="/api/checkusercode")
	public boolean CheckUsercodeIsUsed(@RequestParam(name="usercode")String usercode){	
		MenuUser mu = mus.findByUsercode(usercode);
		if(mu==null) {
			return false;
		}else {
			return true;
		}
	}
	
	//用户注册
	@PostMapping(value="/api/register")
	public String CreateUser(@RequestParam(name="usercode")String usercode,
							@RequestParam(name="username")String username,
							@RequestParam(name="password")String password,
							@RequestParam(name="num")String num,
							@RequestParam(name="checknum")String checknum,
							@RequestParam(name="mail",defaultValue="")String mail){
		System.out.println(checknum);
		checknum.replace(' ', '+');
		try{
			String newnum = AESUtil.AESDecode(DataUtil.AESKEY, checknum);
			if(num.equals(newnum)){
				mus.createuser(usercode, username, password,mail,"用户");
				return "success";
			}else {
				return "验证码错误";
			}
		}catch(Exception e){
			return "wrong";
		}
	}
	//修改头像时，保存图片到本地
	@PostMapping(value="/api/saveimg")
	public String saveimg(HttpServletRequest request,@RequestParam(name="image")String image) throws UnsupportedEncodingException{	
		
		Cookie[] cookies = request.getCookies();
		String usercode = getUsercode(cookies);
		
		if(usercode.equals("error")) {
			return "error";
		}else {
			DealImage DI = new DealImage();
			Base64.Decoder decoder = Base64.getDecoder();
			String strdeco = new String(decoder.decode(image));
			String filename = String.valueOf((int)(Math.random()*100000000)) +".jpg";
			
			byte[] imagedata = DatatypeConverter.parseBase64Binary(strdeco.substring(strdeco.indexOf(",")+1));
			try {
				String fullurl = "E://avatar/"+filename;
				mus.updateavatarurl(usercode, filename);
				DI.saveImg(imagedata,fullurl);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "success";
		}
	}
	
	//修改个人信息
	@PostMapping(value="/api/updatesetting")
	public String updateSetting(HttpServletRequest request,@RequestParam(name="username")String username,
			@RequestParam(name="mail")String mail,@RequestParam(name="introduce")String introduce) throws UnsupportedEncodingException {
		
		Cookie[] cookies = request.getCookies();
		String usercode = getUsercode(cookies);
		if(usercode.equals("error")) {
			return "error";
		}else {
			mus.updateuser(usercode,username,mail,introduce);
			return "success";
		}
	}
	
	//申请成为开发者
	@PostMapping(value="/api/tobedevelop")
	public String updateSetting(HttpServletRequest request) throws UnsupportedEncodingException {
		
		Cookie[] cookies = request.getCookies();
		String usercode = getUsercode(cookies);
		if(usercode.equals("error")) {
			return "error";
		}else {
			irs.save(usercode);
			return "success";
		}
	}
	
	//获取用户应该显示的点位
	@PostMapping(value="/api/getpoints")
	public Map<String,Object> getPoints(HttpServletRequest request) throws UnsupportedEncodingException{
		
		Cookie[] cookies = request.getCookies();
		Map<String,Object> result = new HashMap<String,Object>();
		List<PointDataView> ls = null;
		
		ls = pdvs.getAll();
		List<Map<String,Object>> listdata = getList(ls);
		result.put("pointdata", listdata);
		
		
		if(cookies==null) {
			
			result.put("status", "success");
		}else {
			
			String usercode = getUsercode(cookies);
			if(usercode.equals("error")) {
				result.put("status", "error");
			}
			List<UserPoint> lsalready = ups.findByUsercode(usercode);
			result.put("lsalready",lsalready);
		}
		return result;
	}
	
	//新增点位
	@PostMapping(value="/api/addicon")
	public String addIcon(HttpServletRequest request,@RequestParam("icontype")String pointtype,
									@RequestParam("pointlat")String pointlat,@RequestParam("pointlng")String pointlng) throws UnsupportedEncodingException{
		Cookie[] cookies = request.getCookies();
		String usercode = getUsercode(cookies);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		pds.save(pointlat, pointlng, pointtype,usercode, sdf.format(new Date()));
		return "success";
	}
	
	//忘记密码时，发送邮件
	@PostMapping(value="/api/sendmail")
	public String forgetPassword(@RequestParam(name="usercode")String usercode,
								@RequestParam(name="mail")String mail){
		//校验账户对应的邮箱
		MenuUser user = mus.checkMail(usercode, mail);
		if(user==null){
			//邮箱和角色对应错误
			return "请输入正确的账号或邮箱";
		}else{
			//发送邮件
			int a = (int)(Math.random()*900000)+100000;
			try{
				SimpleMailMessage SMM = new SimpleMailMessage();
				SMM.setFrom("himesens@163.com");	//发送人
				SMM.setTo(mail);	//接收人
				SMM.setSubject("聚光物联 生产管理平台用户密码重置");	//主题
				SMM.setText("您的校验码为"+a+",请尽快修改密码");
				mailSender.send(SMM);
			}catch(Exception e){
				e.printStackTrace();
			}
			//将校验码写入数据库
			mus.updatecheckcode(usercode, String.valueOf(a));
			return "成功，请查收";
		}
	}
	
	//重置密码
	@PostMapping(value="/api/resetpwd")
	public String resetPwd(@RequestParam(name="usercode")String usercode,
						@RequestParam(name="checkcode",defaultValue="")String checkcode,
						@RequestParam(name="newpassword")String newpassword){
		MenuUser user = mus.findByUsercode(usercode);
		//校验校验码
		if(checkcode.equals(user.getCheckcode())){
			mus.changepwd(usercode, newpassword);
			int a = (int)(Math.random()*900000)+100000;
			//修改密码后重置校验码
			mus.updatecheckcode(usercode, String.valueOf(a));
			return "重置密码成功";
		}else{
			return "校验码错误";
		}
	}
	
	//用户上传已访问点
	@PostMapping(value="/api/adduserpoint")
	public String addUserPoint(HttpServletRequest request,@RequestParam(name="pointid")String pointid) throws UnsupportedEncodingException{
		
		Cookie[] cookies = request.getCookies();
		String usercode = getUsercode(cookies);
		
		int pid = Integer.valueOf(pointid);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());
		
		ups.addUserPoint(usercode,pid,time);
		
		return "success";
	}
	
	private String getUsercode(Cookie[] cookies) throws UnsupportedEncodingException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String today = sdf.format(new Date());
		String usercode = "";String status = "";
		
		if(cookies==null) {
			return "error";
		}else {
			for(Cookie cookie:cookies) {
				if(cookie.getName().equals("usercode")) {
					usercode = URLDecoder.decode(cookie.getValue(),"utf-8");
				}else if(cookie.getName().equals("status")) {
					status = URLDecoder.decode(cookie.getValue(),"utf-8");
				}
			}
		}
		String regitime = AESUtil.AESDecode(DataUtil.AESKEY,status);
		if(!regitime.equals(today)) {
			return "error";
		}
		return AESUtil.AESDecode(DataUtil.AESKEY,usercode);
	}
	
	private List<Map<String,Object>> getList(List<PointDataView> ls){
		
		List<Map<String,Object>> lsresult = new ArrayList<Map<String,Object>>();
		
		if(ls.size()<=0) {
			return null;
		}else {
			String compareflag = "";
			List<PointDataView> lsunit = null;
			String pointtype = "";
			String pointtypename = "";
			String iconurl = "";
			String layername = "";
			
			for(PointDataView pdv:ls) {
				if(!pdv.getPointtype().equals(compareflag)) {
					if(lsunit!=null) {
						Map<String,Object> mapunit = new HashMap<String,Object>();
						mapunit.put("pointtype",pointtype);
						mapunit.put("pointtypename", pointtypename);
						mapunit.put("iconurl", iconurl);
						mapunit.put("layername",layername);
						mapunit.put("lsdata",lsunit);
						lsresult.add(mapunit);
					}
					compareflag = pdv.getPointtype();
					lsunit = new ArrayList<PointDataView>();
				}
				lsunit.add(pdv);
				if(!pointtype.equals(pdv.getPointtype())) {
					pointtype = pdv.getPointtype();
					pointtypename = pdv.getPointname_cn();
					iconurl = pdv.getIconurl();
					layername = pdv.getLayername();
				}
			}
			Map<String,Object> mapunit = new HashMap<String,Object>();
			mapunit.put("pointtype",pointtype);
			mapunit.put("pointtypename", pointtypename);
			mapunit.put("iconurl", iconurl);
			mapunit.put("layername",layername);
			mapunit.put("lsdata",lsunit);
			lsresult.add(mapunit);
		}
		
		
		return lsresult;
	}
}