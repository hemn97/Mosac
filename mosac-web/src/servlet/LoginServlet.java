package servlet;

import cn.itcast.servlet.BaseServlet;
import domain.Post;
import service.LoginService;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpCookie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Administrator
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends BaseServlet  {
    private LoginService loginService = new LoginService();
    
    public String validate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String admin_id = loginService.validate(request.getParameter("username"), request.getParameter("password"));
        if(!admin_id.equals("")) {
        	Cookie cookie=new Cookie("admin_id", admin_id);
            cookie.setPath("/");//保证cookie存放的根目录相同
            //设置cookie存活时间
            cookie.setMaxAge(-1);
            //将cookie保存在客户端
            response.addCookie(cookie);
        	return "/index.jsp";
        }
        else {
        	request.setAttribute("alert", "用户名/密码错误，请重新输入");
        	return "/welcome.jsp";
        }
    }
}
