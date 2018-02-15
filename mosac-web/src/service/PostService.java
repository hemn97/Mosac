package service;

import dao.PostDao;
import domain.PageBean;
import domain.Post;

/**
 *
 * @author Administrator
 */
public class PostService {
    PostDao postDao = new PostDao();
    
    public PageBean<Post> findAll(int pc, int pr)
    {
        return postDao.findAll(pc, pr);
    }
}
