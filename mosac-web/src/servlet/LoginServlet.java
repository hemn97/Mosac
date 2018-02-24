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
            cookie.setPath("/");//��֤cookie��ŵĸ�Ŀ¼��ͬ
            //����cookie���ʱ��
            cookie.setMaxAge(-1);
            //��cookie�����ڿͻ���
            response.addCookie(cookie);
        	return "/index.jsp";
        }
        else {
        	request.setAttribute("alert", "�û���/�����������������");
        	return "/welcome.jsp";
        }
    }
}
