package dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.jdbc.TxQueryRunner;
import domain.Post;

public class AdminDao {
	private QueryRunner qr = new TxQueryRunner();
	
	public String validate(String username, String password)
    {
        try {
            String sql = "select admin_id from admins where username=? and password=?";
            Object[] params={username, password};
            Integer admin_id = (Integer) qr.query(sql, new ScalarHandler<>(), params);
            if(admin_id == null) {
            	return "";
            }
            else return admin_id.toString();
        }catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
