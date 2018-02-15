package dao;

import cn.itcast.jdbc.TxQueryRunner;
import domain.PageBean;
import domain.Post;
import java.util.List;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;


/**
 *
 * @author Administrator
 */
public class PostDao {
    private QueryRunner qr = new TxQueryRunner();
	
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
             * 1.����Ҫ����pageBean����pb
             * 2.����pb��pc��pr
             * 3.�õ�tr�����ø�pb
             * 4.�õ�beanList���ø�pb
             * 5.��󷵻ظ�pb
             */
            PageBean<Post> pb=new PageBean<>();
            pb.setPc(pc);
            pb.setPr(pr);

            String sql="select count(*) from posts";
            Number number=(Number) qr.query(sql, new ScalarHandler<>());

            int tr=number.intValue();
            pb.setTr(tr);

            sql="select * from posts limit ?,?";
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
