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
        * 1.��ȡҳ�洫�ݵ�pc
        * 2.����pr��ֵ
        * 3.ʹ��pc��pr����service�������õ�pageBean�����浽request��
        * 4.ת����list.jsp
        */
       /*
        * 1.�õ�pc
        *   ���pc���������ڣ�˵��pc��1
        *   ���pc�������ڣ���Ҫת����int����
        */
        int pc = getPc(request);

        int pr = 10;//����pr��ֵ��ÿҳ10�м�¼

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
