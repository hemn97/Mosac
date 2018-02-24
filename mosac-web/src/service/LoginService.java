package service;

import dao.AdminDao;

public class LoginService {
	private AdminDao adminDao = new AdminDao();
	
	public String validate(String username, String password) {
		String admin_id = adminDao.validate(username, password);
		if(admin_id == null) {
			return "";
		} else {
			return admin_id;
		}
	}
}
