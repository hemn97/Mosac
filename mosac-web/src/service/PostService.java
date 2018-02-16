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
    
    public void edit(Post post)
    {
    	postDao.edit(post);
    }
    
    public Post find(String post_id)
    {
        return postDao.find(post_id);
    }
    
    public PageBean<Post> findAll(int pc, int pr)
    {
        return postDao.findAll(pc, pr);
    }
    
    public void delete(String post_id)
    {
    	postDao.delete(post_id);
    }
}
