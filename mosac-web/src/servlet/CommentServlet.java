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

        int pr = 5;//给定pr的值，每页5行纪录

        PageBean<Comment> pb = commentService.findComment(request.getParameter("post_id"), pc, pr);
        pb.setUrl(getUrl(request));

        request.setAttribute("pb", pb);
        return "f:/findComment.jsp";
    }
	
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pc = getPc(request);

        int pr = 5;//给定pr的值，每页5行纪录

        PageBean<Comment> pb = commentService.findAll(pc, pr);
        pb.setUrl(getUrl(request));

        request.setAttribute("pb", pb);
        return "f:/allComments.jsp";
    }
	
	public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String comment_id = request.getParameter("comment_id");

        commentService.delete(comment_id);

        request.setAttribute("msg", "删除评论成功");

        return "/msgPost.jsp";
    }
	
	public String edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("receive");
        Comment comment = CommonUtils.toBean(request.getParameterMap(), Comment.class);

        commentService.edit(comment);

        request.setAttribute("msg", "编辑评论成功");

        return "/msgPost.jsp";
    }
	
	private int getPc(HttpServletRequest request) {
    	/*
         * 1.得到pc
         *   如果pc参数不存在，说明pc＝1
         *   如果pc参数存在，需要转换成int类型
         */
        String value = request.getParameter("pc");
        if (value == null || value.trim().isEmpty()) {
            return 1;
        }
        return Integer.parseInt(value);
    }
    
    private String getUrl(HttpServletRequest request) {
        String contextPath = request.getContextPath();	// 获取web项目的根路径
        String servletPath = request.getServletPath();	// 得到当前页面所在目录下全名称
        String queryString = request.getQueryString();	// 获取查询字符串

        if (queryString.contains("&pc=")) {
            int index = queryString.lastIndexOf("&pc=");
            queryString = queryString.substring(0, index);
        }
        // 返回绝对Url，除了方法名外不带其他参数
        return contextPath + servletPath + "?" + queryString;
    }

}
