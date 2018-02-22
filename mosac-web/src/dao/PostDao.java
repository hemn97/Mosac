package dao;

import cn.itcast.jdbc.TxQueryRunner;
import domain.PageBean;
import domain.Post;

import java.util.ArrayList;
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
	
    public PageBean<Post> FuzzyQuery(int pc, int pr, String selectType, String keyword)
    {
        try{
            PageBean<Post> pb=new PageBean<>();
            pb.setPc(pc);	// ���õ�ǰҳ��
            pb.setPr(pr);	// ����ÿҳ��¼��
            
            StringBuilder cntSql = new StringBuilder("select count(*) from view_post ");
            StringBuilder whereSql=new StringBuilder(" where 1=1 ");
            List<Object> params = new ArrayList<>();
            
            if(selectType.equals("title")) {
            	whereSql.append("and title like ?");
            } else if(selectType.equals("content")) {
            	whereSql.append("and content like ?");
            } else if(selectType.equals("author")) {
            	whereSql.append("and name like ?");
            }
            params.add("%" + keyword + "%");
            
            Number num=qr.query(cntSql.append(whereSql).toString(),new ScalarHandler<>(),params.toArray());
            
            int tr=num.intValue();
            pb.setTr(tr);

            StringBuilder sql=new StringBuilder("select * from view_post ");
            StringBuilder lmitSql=new StringBuilder(" limit ?,?");

            params.add((pc-1)*pr);
            params.add(pr);
            
            List<Post> beanList=qr.query(sql.append(whereSql).append(lmitSql).toString(),new BeanListHandler<Post>(Post.class),params.toArray());
            pb.setBeanList(beanList);

            return pb;
        }catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    
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
             * 1.����Ҫ����pageBean����pb
             * 2.����pb��pc��pr
             * 3.�õ�tr�����ø�pb
             * 4.�õ�beanList���ø�pb
             * 5.��󷵻ظ�pb
             */
            PageBean<Post> pb=new PageBean<>();
            pb.setPc(pc);	// ���õ�ǰҳ��
            pb.setPr(pr);	// ����ÿҳ��¼��
            // ͳ���ܼ�¼��
            String sql="select count(*) from view_post";
            Number number=(Number) qr.query(sql, new ScalarHandler<>());
            // �����ܼ�¼��
            int tr=number.intValue();
            pb.setTr(tr);
            // ��ѯ��ǰҳ��Ľ����������beanList��
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
