package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import domain.Comment;
import domain.PageBean;
import domain.Post;
import service.CommentService;

@WebServlet("/CommentServlet")
public class CommentServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private CommentService commentService = new CommentService();
	
	public String findComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pc = getPc(request);

        int pr = 5;//����pr��ֵ��ÿҳ5�м�¼

        PageBean<Comment> pb = commentService.findComment(request.getParameter("post_id"), pc, pr);
        pb.setUrl(getUrl(request));

        request.setAttribute("pb", pb);
        return "f:/findComment.jsp";
    }
	
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pc = getPc(request);

        int pr = 5;//����pr��ֵ��ÿҳ5�м�¼

        PageBean<Comment> pb = commentService.findAll(pc, pr);
        pb.setUrl(getUrl(request));

        request.setAttribute("pb", pb);
        return "f:/allComments.jsp";
    }
	
	public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String comment_id = request.getParameter("comment_id");

        commentService.delete(comment_id);

        request.setAttribute("msg", "ɾ�����۳ɹ�");

        return "/msgPost.jsp";
    }
	
	public String edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("receive");
        Comment comment = CommonUtils.toBean(request.getParameterMap(), Comment.class);

        commentService.edit(comment);

        request.setAttribute("msg", "�༭���۳ɹ�");

        return "/msgPost.jsp";
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

}
