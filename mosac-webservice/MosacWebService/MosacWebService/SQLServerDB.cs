using System;
using System.Data.SqlClient;
using System.Data;
using System.Text;

namespace AndroidFinalWebService
{
    /// <summary>  
    /// 操作SQL Server数据库的类  
    /// </summary>  
    public class SQLServerDB
    {
        //连接数据库  
        public SqlConnection sqlCon;
        //连接数据库字符串  
        public String strSql = @"Data Source=PC-20170414DYWU;Initial Catalog=mosac;Persist Security Info=True;User ID=sa;Password=Hlovely5200"
                + ";Enlist=true;Pooling=true;Max Pool Size=300;Min Pool Size=0;Connection Lifetime=30;packet size=1000";

        //默认构造函数  
        public SQLServerDB()
        {
            if (sqlCon == null)
            {
                sqlCon = new SqlConnection();
                sqlCon.ConnectionString = strSql;
                sqlCon.Open();
            }
        }

        //关闭/销毁函数，相当于Close()  
        public void Dispose()
        {
            if (sqlCon != null)
            {
                sqlCon.Close();
                sqlCon = null;
            }
        }

        public string DataTableToJson(DataTable table)
        {
            var JsonString = new StringBuilder();
            if (table.Rows.Count > 0)
            {
                JsonString.Append("[");
                for (int i = 0; i < table.Rows.Count; i++)
                {
                    JsonString.Append("{");
                    for (int j = 0; j < table.Columns.Count; j++)
                    {
                        if (j < table.Columns.Count - 1)
                        {
                            JsonString.Append("\"" + table.Columns[j].ColumnName.ToString() + "\":" + "\"" + table.Rows[i][j].ToString() + "\",");
                        }
                        else if (j == table.Columns.Count - 1)
                        {
                            JsonString.Append("\"" + table.Columns[j].ColumnName.ToString() + "\":" + "\"" + table.Rows[i][j].ToString() + "\"");
                        }
                    }
                    if (i == table.Rows.Count - 1)
                    {
                        JsonString.Append("}");
                    }
                    else
                    {
                        JsonString.Append("},");
                    }
                }
                JsonString.Append("]");
            }
            return JsonString.ToString();
        }

        public String FindAllPosts(String keyword)
        {
            StringBuilder builder = new StringBuilder();
            builder.Append("select top 50 * from view_posts_2 where title like '%").Append(keyword).Append("%' order by post_id desc;");
            SqlCommand cmd = sqlCon.CreateCommand();
            cmd.CommandText = builder.ToString();
            SqlDataAdapter da = new SqlDataAdapter(cmd.CommandText, sqlCon);
            DataSet ds = new DataSet();
            da.Fill(ds);
            return DataTableToJson(ds.Tables[0]);
        }

        public bool InsertPost(String title, String content, int author_id, String time)
        {
            StringBuilder builder = new StringBuilder();
            builder.Append("insert into posts(title, content, author_id, time) values('")
                .Append(title).Append("','").Append(content).Append("',").Append(author_id).Append(",'").Append(time).Append("');");
            SqlCommand cmd = new SqlCommand(builder.ToString(), sqlCon);
            cmd.ExecuteNonQuery();
            cmd.Dispose();
            return true;
        }

        public String FindMyPosts(int author_id)
        {
            StringBuilder builder = new StringBuilder();
            builder.Append("select * from view_posts_2 where author_id=").Append(author_id);
            SqlCommand cmd = sqlCon.CreateCommand();
            cmd.CommandText = builder.ToString();
            SqlDataAdapter da = new SqlDataAdapter(cmd.CommandText, sqlCon);
            DataSet ds = new DataSet();
            da.Fill(ds);
            return DataTableToJson(ds.Tables[0]);
        }

        public bool LightupPost(int post_id, int user_id)
        {
            try
            {
                StringBuilder builder = new StringBuilder();
                builder.Append("insert into posts_lightup values(").Append(post_id).Append(",").Append(user_id).Append(");");
                SqlCommand cmd = new SqlCommand(builder.ToString(), sqlCon);
                cmd.ExecuteNonQuery();
                cmd.Dispose();
                return true;
            } catch (Exception) {
                return false;
            }
        }

        public bool UpdatePostViews(int post_id, int user_id)
        {
            try
            {
                StringBuilder builder = new StringBuilder();
                builder.Append("insert into posts_view values(").Append(post_id).Append(",").Append(user_id).Append(");");
                SqlCommand cmd = new SqlCommand(builder.ToString(), sqlCon);
                cmd.ExecuteNonQuery();
                cmd.Dispose();
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }

        public String FindAllComments(int post_id)
        {
            StringBuilder builder = new StringBuilder();
            builder.Append("select * from view_comment where post_id=").Append(post_id);
            SqlCommand cmd = sqlCon.CreateCommand();
            cmd.CommandText = builder.ToString();
            SqlDataAdapter da = new SqlDataAdapter(cmd.CommandText, sqlCon);
            DataSet ds = new DataSet();
            da.Fill(ds);
            return DataTableToJson(ds.Tables[0]);
        }

        public bool CommentPost(int post_id, int user_id, String comment, String time)
        {
            try
            {
                StringBuilder builder = new StringBuilder();
                builder.Append("insert into posts_comment(post_id, user_id, comment, time) values(")
                    .Append(post_id).Append(",").Append(user_id).Append(",'").Append(comment).Append("','").Append(time).Append("');");
                SqlCommand cmd = new SqlCommand(builder.ToString(), sqlCon);
                cmd.ExecuteNonQuery();
                cmd.Dispose();
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }

        public bool LoginValidate(String number, String password)
        {
            bool flag = false;
            try
            {
                StringBuilder builder = new StringBuilder();
                builder.Append("select COUNT(*) from users_auths where number='").Append(number).Append("' and password='").Append(password).Append("';");
                SqlCommand cmd = new SqlCommand(builder.ToString(), sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();
                while (reader.Read())
                {
                    if(reader[0].ToString().Equals("1"))
                    {
                        flag =  true;
                    }
                }
                reader.Close();
                cmd.Dispose();
                return flag;
            }
            catch (Exception)
            {
                return false;
            }
        }

        public bool RegisterUser(String username, String number, String password, String contact_number, String email, String department)
        {
            try
            {
                StringBuilder builder = new StringBuilder();
                builder.Append("insert into users(name, contact_number, email, department) values('")
                    .Append(username).Append("','").Append(contact_number).Append("','").Append(email).Append("','").Append(department).Append("')");
                builder.Append("insert into users_auths values(@@identity, '").Append(number).Append("','").Append(password).Append("');");
                SqlCommand cmd = new SqlCommand(builder.ToString(), sqlCon);
                cmd.ExecuteNonQuery();
                cmd.Dispose();
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }

        public String FindPassword(String number, String username, String contact_number, String email)
        {
            StringBuilder builder = new StringBuilder();
            builder.Append("select password from users inner join users_auths on users.user_id = users_auths.user_id where name='")
                .Append(username).Append("' and number='").Append(number).Append("' and contact_number='")
                .Append(contact_number).Append("' and email='").Append(email).Append("';");
            SqlCommand cmd = sqlCon.CreateCommand();
            cmd.CommandText = builder.ToString();
            SqlDataAdapter da = new SqlDataAdapter(cmd.CommandText, sqlCon);
            DataSet ds = new DataSet();
            da.Fill(ds);
            return DataTableToJson(ds.Tables[0]);
        }

        public String GetUserMessage(String number)
        {
            StringBuilder builder = new StringBuilder();
            builder.Append("select users.user_id, name, contact_number, email, department, number from users,users_auths where users.user_id = users_auths.user_id ")
                .Append("and number='").Append(number).Append("';");
            SqlCommand cmd = sqlCon.CreateCommand();
            cmd.CommandText = builder.ToString();
            SqlDataAdapter da = new SqlDataAdapter(cmd.CommandText, sqlCon);
            DataSet ds = new DataSet();
            da.Fill(ds);
            return DataTableToJson(ds.Tables[0]);
        }

        public String GetUserMessage2(String user_id)
        {
            StringBuilder builder = new StringBuilder();
            builder.Append("select users.user_id, name, contact_number, email, department, number from users,users_auths where users.user_id = users_auths.user_id ")
                .Append("and users.user_id='").Append(user_id).Append("';");
            SqlCommand cmd = sqlCon.CreateCommand();
            cmd.CommandText = builder.ToString();
            SqlDataAdapter da = new SqlDataAdapter(cmd.CommandText, sqlCon);
            DataSet ds = new DataSet();
            da.Fill(ds);
            return DataTableToJson(ds.Tables[0]);
        }

        public String FindAllTeam(String type)
        {
            StringBuilder builder = new StringBuilder();
            if(type.Equals(""))
            {
                builder.Append("select top 50 * from view_team order by team_id desc");
            } else
            {
                builder.Append("select top 50 * from view_team where type='").Append(type).Append("' order by team_id desc;");
            }
            SqlCommand cmd = sqlCon.CreateCommand();
            cmd.CommandText = builder.ToString();
            SqlDataAdapter da = new SqlDataAdapter(cmd.CommandText, sqlCon);
            DataSet ds = new DataSet();
            da.Fill(ds);
            return DataTableToJson(ds.Tables[0]);
        }

        public String FindMyTeam(String captain_id)
        {
            StringBuilder builder = new StringBuilder();
            builder.Append("select * from view_team where captain_id=").Append(captain_id).Append(";");
            SqlCommand cmd = sqlCon.CreateCommand();
            cmd.CommandText = builder.ToString();
            SqlDataAdapter da = new SqlDataAdapter(cmd.CommandText, sqlCon);
            DataSet ds = new DataSet();
            da.Fill(ds);
            return DataTableToJson(ds.Tables[0]);
        }

        public String FindJoinTeam(String user_id)
        {
            StringBuilder builder = new StringBuilder();
            builder.Append("select * from view_team where team_id = (select team_id from teammates where user_id=").Append(user_id).Append(");");
            SqlCommand cmd = sqlCon.CreateCommand();
            cmd.CommandText = builder.ToString();
            SqlDataAdapter da = new SqlDataAdapter(cmd.CommandText, sqlCon);
            DataSet ds = new DataSet();
            da.Fill(ds);
            return DataTableToJson(ds.Tables[0]);
        }

        public String FindAllTeammates(String team_id)
        {
            StringBuilder builder = new StringBuilder();
            builder.Append("select teammates.user_id, name, contact_number, email, department, number from teammates inner join users on teammates.user_id = users.user_id ")
                .Append("inner join users_auths on users_auths.user_id = users.user_id where team_id=").Append(team_id).Append(";");
            SqlCommand cmd = sqlCon.CreateCommand();
            cmd.CommandText = builder.ToString();
            SqlDataAdapter da = new SqlDataAdapter(cmd.CommandText, sqlCon);
            DataSet ds = new DataSet();
            da.Fill(ds);
            return DataTableToJson(ds.Tables[0]);
        }

        public String FindAllApplicants(String team_id)
        {
            StringBuilder builder = new StringBuilder();
            builder.Append("select * from view_applicants where team_id=").Append(team_id);
            SqlCommand cmd = sqlCon.CreateCommand();
            cmd.CommandText = builder.ToString();
            SqlDataAdapter da = new SqlDataAdapter(cmd.CommandText, sqlCon);
            DataSet ds = new DataSet();
            da.Fill(ds);
            return DataTableToJson(ds.Tables[0]);
        }

        public bool IfInTeam(String team_id, String user_id)
        {
            bool flag = false;
            try
            {
                StringBuilder builder = new StringBuilder();
                builder.Append("select COUNT(*) as 'inTeam' from teammates where team_id=")
                    .Append(team_id).Append(" and user_id = ").Append(user_id);
                SqlCommand cmd = new SqlCommand(builder.ToString(), sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();
                while (reader.Read())
                {
                    if (reader[0].ToString().Equals("1"))
                    {
                        flag = true;
                    }
                }
                reader.Close();
                cmd.Dispose();
                return flag;
            }
            catch (Exception)
            {
                return false;
            }
        }

        public bool DeleteTeam(String team_id)
        {
            try
            {
                StringBuilder builder = new StringBuilder();
                builder.Append("delete from teams where team_id='")
                    .Append(team_id).Append("';");
                SqlCommand cmd = new SqlCommand(builder.ToString(), sqlCon);
                cmd.ExecuteNonQuery();
                cmd.Dispose();
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }

        public bool LeaveTeam(String team_id, String user_id)
        {
            try
            {
                StringBuilder builder = new StringBuilder();
                builder.Append("delete from teammates where team_id=")
                    .Append(team_id).Append(" and user_id=").Append(user_id);
                SqlCommand cmd = new SqlCommand(builder.ToString(), sqlCon);
                cmd.ExecuteNonQuery();
                cmd.Dispose();
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }

        public bool InsertTeam(String captain_id, String team_name, String time, String description, String place, String type)
        {
            try
            {
                StringBuilder builder = new StringBuilder();
                builder.Append("insert into teams(captain_id,team_name,time,description,place,type) values(")
                    .Append(captain_id).Append(",'").Append(team_name).Append("','").Append(time).Append("','")
                    .Append(description).Append("','").Append(place).Append("','").Append(type).Append("');");
                SqlCommand cmd = new SqlCommand(builder.ToString(), sqlCon);
                cmd.ExecuteNonQuery();
                cmd.Dispose();
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }

        public bool JoinTeam(String team_id, String user_id)
        {
            try
            {
                StringBuilder builder = new StringBuilder();
                builder.Append("insert into team_applicationlist(team_id,user_id) values(").Append(team_id).Append(",").Append(user_id).Append(");");
                SqlCommand cmd = new SqlCommand(builder.ToString(), sqlCon);
                cmd.ExecuteNonQuery();
                cmd.Dispose();
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }

        public bool AgreeJoinTeam(String team_id, String user_id)
        {
            try
            {
                StringBuilder builder = new StringBuilder();
                builder.Append("delete from team_applicationlist where team_id=").Append(team_id).Append(" and user_id=").Append(user_id).Append(";");
                builder.Append("insert into teammates values(").Append(team_id).Append(",").Append(user_id).Append(");");
                SqlCommand cmd = new SqlCommand(builder.ToString(), sqlCon);
                cmd.ExecuteNonQuery();
                cmd.Dispose();
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }

        public bool DenyJoinTeam(String team_id, String user_id)
        {
            try
            {
                StringBuilder builder = new StringBuilder();
                builder.Append("delete from team_applicationlist where team_id=").Append(team_id).Append(" and user_id=").Append(user_id).Append(";");
                SqlCommand cmd = new SqlCommand(builder.ToString(), sqlCon);
                cmd.ExecuteNonQuery();
                cmd.Dispose();
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }
    }
}
