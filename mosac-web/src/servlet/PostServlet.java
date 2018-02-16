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

        request.setAttribute("msg", "编辑客户成功");
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
        * 1.获取页面传递的pc
        * 2.给定pr的值
        * 3.使用pc和pr调用service方法，得到pageBean，保存到request域
        * 4.转发到list.jsp
        */
        int pc = getPc(request);

        int pr = 10;//给定pr的值，每页10行纪录

        PageBean<Post> pb = postService.findAll(pc, pr);
        pb.setUrl(getUrl(request));

        request.setAttribute("pb", pb);
        return "f:/list.jsp";
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
    
    public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String post_id = request.getParameter("post_id");

        postService.delete(post_id);

        request.setAttribute("msg", "删除帖子成功");
        request.setAttribute("lastUrl", "/mosac/PostServlet?method=findAll");
        return "/msg.jsp";
    }
}
