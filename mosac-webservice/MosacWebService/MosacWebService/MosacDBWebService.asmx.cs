using AndroidFinalWebService;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace MosacWebService
{
    /// <summary>
    /// MosacDBWebService 的摘要说明
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // 若要允许使用 ASP.NET AJAX 从脚本中调用此 Web 服务，请取消注释以下行。 
    // [System.Web.Script.Services.ScriptService]
    public class MosacDBWebService : System.Web.Services.WebService
    {

        SQLServerDB dbOperation = new SQLServerDB();

        [WebMethod(Description = "查询所有帖子")]
        public String FindAllPosts(String keyword)
        {
            return dbOperation.FindAllPosts(keyword);
        }

        [WebMethod(Description = "发表帖子")]
        public bool InsertPost(String title, String content, int author_id, String time)
        {
            return dbOperation.InsertPost(title, content, author_id, time);
        }

        [WebMethod(Description = "查询我的所有发帖")]
        public String FindMyPosts(int author_id)
        {
            return dbOperation.FindMyPosts(author_id);
        }

        [WebMethod(Description = "点亮帖子")]
        public bool LightupPost(int post_id, int user_id)
        {
            return dbOperation.LightupPost(post_id, user_id);
        }

        [WebMethod(Description = "插入帖子访问记录")]
        public bool UpdatePostViews(int post_id, int user_id)
        {
            return dbOperation.UpdatePostViews(post_id, user_id);
        }

        [WebMethod(Description = "查看所有评论")]
        public String FindAllComment(int post_id)
        {
            return dbOperation.FindAllComments(post_id);
        }

        [WebMethod(Description = "评论帖子")]
        public bool CommentPost(int post_id, int user_id, String comment, String time)
        {
            return dbOperation.CommentPost(post_id, user_id, comment, time);
        }

        [WebMethod(Description = "用户登录验证")]
        public bool LoginValidate(String number, String password)
        {
            return dbOperation.LoginValidate(number, password);
        }

        [WebMethod(Description = "注册用户")]
        public bool RegisterUser(String username, String number, String password, String contact_number, String email, String department)
        {
            return dbOperation.RegisterUser(username, number, password, contact_number, email, department);
        }

        [WebMethod(Description = "找回密码")]
        public String FindPassword(String number, String username, String contact_number, String email)
        {
            return dbOperation.FindPassword(number, username, contact_number, email);
        }

        [WebMethod(Description = "查询用户信息")]
        public String GetUserMessage(String number)
        {
            return dbOperation.GetUserMessage(number);
        }

        [WebMethod(Description = "查询用户信息")]
        public String GetUserMessage2(String user_id)
        {
            return dbOperation.GetUserMessage2(user_id);
        }

        [WebMethod(Description = "查询队伍")]
        public String FindAllTeam(String type)
        {
            return dbOperation.FindAllTeam(type);
        }
        [WebMethod(Description = "查询我创建的队伍")]
        public String FindMyTeam(String captain_id)
        {
            return dbOperation.FindMyTeam(captain_id);
        }
        [WebMethod(Description = "查询我加入的队伍")]
        public String FindJoinTeam(String user_id)
        {
            return dbOperation.FindJoinTeam(user_id);
        }
        [WebMethod(Description = "查看申请列表")]
        public String FindAllApplicants(String team_id)
        {
            return dbOperation.FindAllApplicants(team_id);
        }
        [WebMethod(Description = "查询队员")]
        public String FindAllTeammates(String team_id)
        {
            return dbOperation.FindAllTeammates(team_id);
        }
        [WebMethod(Description = "查看是否在队内")]
        public bool IfInTeam(String team_id, String user_id)
        {
            return dbOperation.IfInTeam(team_id, user_id);
        }

        [WebMethod(Description = "删除队伍")]
        public bool DeleteTeam(String team_id)
        {
            return dbOperation.DeleteTeam(team_id);
        }

        [WebMethod(Description = "离开队伍")]
        public bool LeaveTeam(String team_id, String user_id)
        {
            return dbOperation.LeaveTeam(team_id, user_id);
        }

        [WebMethod(Description = "创建队伍")]
        public bool InsertTeam(String captain_id, String team_name, String time, String description, String place, String type)
        {
            return dbOperation.InsertTeam(captain_id, team_name, time, description, place, type);
        }

        [WebMethod(Description = "申请加入队伍")]
        public bool JoinTeam(String team_id, String user_id)
        {
            return dbOperation.JoinTeam(team_id, user_id);
        }

        [WebMethod(Description = "允许加入队伍")]
        public bool AgreeJoinTeam(String team_id, String user_id)
        {
            return dbOperation.AgreeJoinTeam(team_id, user_id);
        }

        [WebMethod(Description = "拒绝加入队伍")]
        public bool DenyJoinTeam(String team_id, String user_id)
        {
            return dbOperation.DenyJoinTeam(team_id, user_id);
        }

    }

}
