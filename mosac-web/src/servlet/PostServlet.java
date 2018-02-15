package servlet;

import cn.itcast.servlet.BaseServlet;
import domain.PageBean;
import domain.Post;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.PostService;

/**
 *
 * @author Administrator
 */
@WebServlet("/PostServlet")
public class PostServlet extends BaseServlet  {
    private PostService postService = new PostService();
    
    public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       /*
        * 1.获取页面传递的pc
        * 2.给定pr的值
        * 3.使用pc和pr调用service方法，得到pageBean，保存到request域
        * 4.转发到list.jsp
        */
       /*
        * 1.得到pc
        *   如果pc参数不存在，说明pc＝1
        *   如果pc参数存在，需要转换成int类型
        */
        int pc = getPc(request);

        int pr = 10;//给定pr的值，每页10行纪录

        PageBean<Post> pb = postService.findAll(pc, pr);
        pb.setUrl(getUrl(request));

        request.setAttribute("pb", pb);

        return "f:/list.jsp";
    }

    private int getPc(HttpServletRequest request) {
        String value = request.getParameter("pc");
        if (value == null || value.trim().isEmpty()) {
            return 1;
        }
        return Integer.parseInt(value);
    }
    
    private String getUrl(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        String queryString = request.getQueryString();

        if (queryString.contains("&pc=")) {
            int index = queryString.lastIndexOf("&pc=");
            queryString = queryString.substring(0, index);
        }

        return contextPath + servletPath + "?" + queryString;
    }
}
