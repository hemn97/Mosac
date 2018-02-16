package servlet;

import cn.itcast.commons.CommonUtils;
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
    
    public String edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Post post = CommonUtils.toBean(request.getParameterMap(), Post.class);

        postService.edit(post);

        request.setAttribute("msg", "�༭�ͻ��ɹ�");
        request.setAttribute("lastUrl", "/mosac/PostServlet?method=findAll");
        return "/msg.jsp";
    }
    
    public String preEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String post_id = request.getParameter("post_id");
        Post post = postService.find(post_id);

        request.setAttribute("post", post);

        return "/edit.jsp";
    }
    
    public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       /*
        * 1.��ȡҳ�洫�ݵ�pc
        * 2.����pr��ֵ
        * 3.ʹ��pc��pr����service�������õ�pageBean�����浽request��
        * 4.ת����list.jsp
        */
        int pc = getPc(request);

        int pr = 10;//����pr��ֵ��ÿҳ10�м�¼

        PageBean<Post> pb = postService.findAll(pc, pr);
        pb.setUrl(getUrl(request));

        request.setAttribute("pb", pb);
        return "f:/list.jsp";
    }

    private int getPc(HttpServletRequest request) {
    	/*
         * 1.�õ�pc
         *   ���pc���������ڣ�˵��pc��1
         *   ���pc�������ڣ���Ҫת����int����
         */
        String value = request.getParameter("pc");
        if (value == null || value.trim().isEmpty()) {
            return 1;
        }
        return Integer.parseInt(value);
    }
    
    private String getUrl(HttpServletRequest request) {
        String contextPath = request.getContextPath();	// ��ȡweb��Ŀ�ĸ�·��
        String servletPath = request.getServletPath();	// �õ���ǰҳ������Ŀ¼��ȫ����
        String queryString = request.getQueryString();	// ��ȡ��ѯ�ַ���

        if (queryString.contains("&pc=")) {
            int index = queryString.lastIndexOf("&pc=");
            queryString = queryString.substring(0, index);
        }
        // ���ؾ���Url�����˷������ⲻ����������
        return contextPath + servletPath + "?" + queryString;
    }
    
    public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String post_id = request.getParameter("post_id");

        postService.delete(post_id);

        request.setAttribute("msg", "ɾ�����ӳɹ�");
        request.setAttribute("lastUrl", "/mosac/PostServlet?method=findAll");
        return "/msg.jsp";
    }
}
