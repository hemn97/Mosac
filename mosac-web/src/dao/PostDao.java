package dao;

import cn.itcast.jdbc.TxQueryRunner;
import domain.PageBean;
import domain.Post;
import java.util.List;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;


/**
 *
 * @author Administrator
 */
public class PostDao {
    private QueryRunner qr = new TxQueryRunner();
	
    public void edit(Post post)
    {
        try{
            String sql="update posts set title=?,content=? where post_id=?";
            Object[] params={post.getTitle(), post.getContent(), post.getPost_id()};

            qr.update(sql,params);
        }catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    
    public Post find(String post_id)
    {
        try {
            String sql = "select * from view_post where post_id=?";
            return qr.query(sql, new BeanHandler<Post>(Post.class), post_id);
        }catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    
    public void delete(String post_id) {
        try {
            String sql = "delete from posts where post_id=?";

            qr.update(sql, post_id);
        }catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
        
    public PageBean<Post> findAll(int pc, int pr)
    {
        try{
            /*
             * 1.他需要创建pageBean对象pb
             * 2.设置pb的pc和pr
             * 3.得到tr，设置给pb
             * 4.得到beanList设置给pb
             * 5.最后返回给pb
             */
            PageBean<Post> pb=new PageBean<>();
            pb.setPc(pc);	// 设置当前页码
            pb.setPr(pr);	// 设置每页记录数
            // 统计总记录数
            String sql="select count(*) from view_post";
            Number number=(Number) qr.query(sql, new ScalarHandler<>());
            // 设置总记录数
            int tr=number.intValue();
            pb.setTr(tr);
            // 查询当前页码的结果，保存在beanList中
            sql="select * from view_post limit ?,?";
            Object[] params={(pc-1)*pr, pr};
            List<Post> beanList=qr.query(sql, new BeanListHandler<>(Post.class), params);

            pb.setBeanList(beanList);

            return pb;
        }catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
