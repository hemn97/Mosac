package dao;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import domain.Comment;
import domain.PageBean;
import domain.Post;

public class CommentDao {
	QueryRunner qr;
	public CommentDao() {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		qr = new QueryRunner(dataSource, true);
	}
	public PageBean<Comment> findComment(String post_id, int pc, int pr)
    {
        try{
            PageBean<Comment> pb=new PageBean<>();
            pb.setPc(pc);	// ���õ�ǰҳ��
            pb.setPr(pr);	// ����ÿҳ��¼��
            // ͳ���ܼ�¼��
            String sql="select count(*) from view_comment where post_id=?";
            Number number=(Number) qr.query(sql, new ScalarHandler<>(), post_id);
            // �����ܼ�¼��
            int tr=number.intValue();
            pb.setTr(tr);
            // ��ѯ��ǰҳ��Ľ����������beanList��
            // ȡ��m����n����m=(pc-1)*pr, n=m+pr
            int m = (pc-1)*pr;
            sql = "select top " + Integer.toString(pr) + " * from view_comment where post_id=? and comment_id not in (select top "
                	+ Integer.toString(m) + " comment_id from view_comment order by comment_id) order by comment_id";
            List<Comment> beanList=qr.query(sql, new BeanListHandler<>(Comment.class), post_id);

            pb.setBeanList(beanList);

            return pb;
        }catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
	
	public void edit(Comment comment)
    {
        try{
            String sql="update posts_comment set comment=? where comment_id=?";
            Object[] params={comment.getComment(), comment.getComment_id()};

            qr.update(sql,params);
        }catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
	
	public void delete(String comment_id) {
        try {
            String sql = "delete from posts_comment where comment_id=?";

            qr.update(sql, comment_id);
        }catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
	
	public PageBean<Comment> findAll(int pc, int pr)
    {
        try{
            PageBean<Comment> pb=new PageBean<>();
            pb.setPc(pc);	// ���õ�ǰҳ��
            pb.setPr(pr);	// ����ÿҳ��¼��
            // ͳ���ܼ�¼��
            String sql="select count(*) from view_comment";
            Number number=(Number) qr.query(sql, new ScalarHandler<>());
            // �����ܼ�¼��
            int tr=number.intValue();
            pb.setTr(tr);
            // ��ѯ��ǰҳ��Ľ����������beanList��
            int m = (pc-1)*pr;
            sql = "select top " + Integer.toString(pr) + " * from view_comment where comment_id not in (select top "
                	+ Integer.toString(m) + " comment_id from view_comment order by comment_id) order by comment_id";

            List<Comment> beanList=qr.query(sql, new BeanListHandler<>(Comment.class));

            pb.setBeanList(beanList);

            return pb;
        }catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
