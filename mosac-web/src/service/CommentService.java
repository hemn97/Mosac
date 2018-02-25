package service;

import dao.CommentDao;
import domain.PageBean;
import domain.Post;
import domain.Comment;

public class CommentService {
	CommentDao commentDao = new CommentDao();
	public PageBean<Comment> findComment(String post_id, int pc, int pr)
    {
    	return commentDao.findComment(post_id, pc, pr);
    }
	public void edit(Comment comment)
    {
		commentDao.edit(comment);
    }
	public void delete(String comment_id)
    {
		commentDao.delete(comment_id);
    }
	public PageBean<Comment> findAll(int pc, int pr)
    {
    	return commentDao.findAll(pc, pr);
    }
}
