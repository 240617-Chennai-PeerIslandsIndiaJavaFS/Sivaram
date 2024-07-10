package org.example.dao;


import org.example.connection.DBConnection;
import org.example.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamDetailDAO implements BaseDAO<TeamDetail>{
    public  static Logger logger = LoggerFactory.getLogger(TeamDetailDAO.class);
    Connection conn;
    UserDAO user_dao;
    TeamDAO team_dao;
    TaskDAO task_dao;

    public TeamDetailDAO() {
        conn = DBConnection.getInstance().getDBConnection();
        user_dao = new UserDAO();
        team_dao = new TeamDAO();
        task_dao = new TaskDAO();
    }

    @Override
    public boolean create(TeamDetail teamDetail) {
        String query = "insert into team_details(teamId,teamMemberId,taskId) value(?,?,?)";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,teamDetail.getTeam().getTeamId());
            pst.setInt(2,teamDetail.getTeamMember().getUserId());
            pst.setInt(3,teamDetail.getTask().getTaskId());
            int rows = pst.executeUpdate();
            logger.info("TeamMember {} is add to the Team with Task {}",user_dao.get(teamDetail.getTeamMember().getUserId()).getUserName(),task_dao.get(teamDetail.getTask().getTaskId()).getTitle());
            return  rows > 0;

        } catch (SQLException e) {
            logger.error("Error while Adding a TeamMember: {}", e.getMessage());
            System.out.println("Error while Creating a TeamDetail: "+e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(TeamDetail teamDetail) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public TeamDetail get(int id) {
        return null;
    }

    @Override
    public List<TeamDetail> getAll() {
        List<TeamDetail> team_details = new ArrayList<>();
        String query = "select * from team_details";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                TeamDetail team_detail = createTeamDetailsFromResultSet(rs);
                team_details.add(team_detail);
            }
        }
        catch (SQLException e) {
            System.out.println("Error while Retrieving List of TeamDetails: "+e.getMessage());
        }
        return team_details;
    }

    public List<TeamDetail> getAllByTeamMemberById(int team_id){
        List<TeamDetail> team_details = new ArrayList<>();
        String query = "select * from team_details where teamId ="+team_id;
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                TeamDetail team_detail = createTeamDetailsFromResultSet(rs);
                team_details.add(team_detail);
            }
        }
        catch (SQLException e) {
            System.out.println("Error while Retrieving List of TeamDetails: "+e.getMessage());
        }
        return team_details;
    }

    public List<TeamDetail> getAllUniqueTeamMember(int team_id){
        List<TeamDetail> team_details = new ArrayList<>();
        String query = "select distinct teamMemberId,taskId,teamID from team_details where teamId ="+team_id;
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                TeamDetail team_detail = createTeamDetailsFromResultSet(rs);
                team_details.add(team_detail);
            }
        }
        catch (SQLException e) {
            System.out.println("Error while Retrieving List of TeamDetails: "+e.getMessage());
        }
        return team_details;
    }


    public boolean deleteByTaskID(int id) {
        String query= "delete from team_details where TaskId=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,id);
            int rows = pst.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Error while Deleting a Task: "+e.getMessage());
        }
        return false;
    }


    public List<TeamDetail> getProjectDetailsByUserId(User user){
        List<TeamDetail> team_details = new ArrayList<>();
        String query = "select p.projectId,p.projectName,p.projectDescription,p.startDate,p.dueDate,tsk.taskId,tsk.title,tsk.taskDescription,c.clientName,c.clientEmail,c.clientDescription,c.address,c.city,c.phone from clients as c inner join projects as p on c.clientId = p.clientId inner join teams as t on p.projectId = t.projectId inner join team_details as td on t.teamId = td.teamId inner join tasks as tsk on td.taskId = tsk.taskId  where td.teamMemberId = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,user.getUserId());
            ResultSet rs = pst.executeQuery();

            while(rs.next()){
                TeamDetail team_detail = extractProjectDetailsFromResultSet(rs);
                team_details.add(team_detail);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return team_details;
    }

    public List<ReportDetail> getReports(){
        List<ReportDetail> report_details = new ArrayList<>();
        String query = "SELECT p.projectName,pm.userName AS projectManager,COUNT(DISTINCT td.teamMemberId) AS uniqueTeamMembers,COUNT(DISTINCT t.taskId) AS totalTasks,SUM(CASE WHEN t.taskStatus = 5 THEN 1 ELSE 0 END) AS completedTasks FROM projects p JOIN users pm ON p.projectManagerId = pm.userId JOIN teams tm ON p.projectId = tm.projectId JOIN team_details td ON tm.teamId = td.teamId JOIN tasks t ON td.taskId = t.taskId GROUP BY p.projectId, p.projectName, pm.userName";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while(rs.next()){
                ReportDetail report_detail = extractReportDetailsFromResultSet(rs);
                report_details.add(report_detail);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return report_details;
    }

    private ReportDetail extractReportDetailsFromResultSet(ResultSet rs) throws SQLException {
        ReportDetail report_detail = new ReportDetail();
        report_detail.setProjectName(rs.getString("projectName"));
        report_detail.setProjectManager(rs.getString("projectManager"));
        report_detail.setUniqueTeamMembers(rs.getInt("uniqueTeamMembers"));
        report_detail.setTotalTasks(rs.getInt("totalTasks"));
        report_detail.setCompletedTasks(rs.getInt("completedTasks"));
        return report_detail;
    }


    private TeamDetail extractProjectDetailsFromResultSet(ResultSet rs) throws SQLException {
        Client client= new Client();
        client.setClientName(rs.getString("clientName"));
        client.setClientEmail(rs.getString("clientEmail"));
        client.setClientDescription(rs.getString("clientDescription"));
        client.setAddress(rs.getString("address"));
        client.setCity(rs.getString("city"));
        client.setPhone(rs.getString("phone"));

        Project project = new Project();
        project.setProjectId(rs.getInt("p.projectId"));
        project.setProjectName(rs.getString("projectName"));
        project.setProjectDescription(rs.getString("projectDescription"));
        project.setStartDate(rs.getDate("startDate").toLocalDate());
        project.setDueDate(rs.getDate("dueDate").toLocalDate());
        project.setClient(client);

        Team team = new Team();
        team.setProject(project);

        Task task = new Task();
        task.setTaskId(rs.getInt("tsk.taskId"));
        task.setTitle(rs.getString("title"));
        task.setTaskDescription(rs.getString("taskDescription"));

        TeamDetail team_detail = new TeamDetail();
        team_detail.setTeam(team);
        team_detail.setTask(task);

        return team_detail;

    }

    public List<Integer> getProjectManagerAndTeam(User user,int project_id){
        int get_manager_one_time = 0;
        List<Integer> ids = new ArrayList<>();

        String query = "select t.projectManagerId,td.teamId,td.teamMemberId from  projects as p inner join teams as t on p.projectId = t.projectId inner join team_details as td on t.teamId = td.teamId where p.projectId = ? and not teamMemberId = ?;";

        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,project_id);
            pst.setInt(2,user.getUserId());
            ResultSet rs = pst.executeQuery();

            while(rs.next()){
                if(get_manager_one_time == 0){
                    ids.add(rs.getInt("t.projectManagerId"));
                    get_manager_one_time++;
                }
                int team_member_ids = rs.getInt("td.teamMemberId");
                ids.add(team_member_ids);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ids;
    }

    public List<Task> getAllTaskByProjectManager(User user){
        List<Task> tasks = new ArrayList<>();
        String query = "select * from teams as t inner join team_details as td on t.teamId = td.teamId inner join tasks as tsk on td.taskId = tsk.taskId inner join milestones as m on tsk.taskStatus = m.milestoneId where t.projectManagerId = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,user.getUserId());
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Task task = extractTaskFromResultSet(rs);
                tasks.add(task);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tasks;
    }

    //reassign task
    public boolean reassignTask(TeamDetail team_detail,User new_team_member){
        String query = "update team_details set teamMemberId=? where taskId=? and teamMemberId=?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1,new_team_member.getUserId());
            pst.setInt(2,team_detail.getTask().getTaskId());
            pst.setInt(3,team_detail.getTeamMember().getUserId());
            int rows = pst.executeUpdate();
            logger.info("Task {} is reassigned to {}",task_dao.get(team_detail.getTask().getTaskId()).getTitle(),user_dao.get(new_team_member.getUserId()).getUserName());
            return  rows > 0;

        } catch (SQLException e) {
            logger.error("Error while Reassigning a Task: {}", e.getMessage());
            System.out.println("Error while Updating a Task: "+e.getMessage());
        }
        return false;
    }

    private Task extractTaskFromResultSet(ResultSet rs) throws SQLException {
        Task task = new Task();

        task.setTaskId(rs.getInt("tsk.taskId"));
        task.setTitle(rs.getString("title"));
        task.setTaskDescription(rs.getString("taskDescription"));
        task.setStartDate(rs.getDate("startDate").toLocalDate());
        task.setDueDate(rs.getDate("dueDate").toLocalDate());
        Milestone milestone = new Milestone();
        milestone.setMilestoneId(rs.getInt("milestoneId"));
        milestone.setMilestoneName(rs.getString("milestoneName"));
        milestone.setMilestoneDescription(rs.getString("milestoneDescription"));
        task.setTaskStatus(milestone);

        return task;
    }


    private TeamDetail createTeamDetailsFromResultSet(ResultSet rs) throws SQLException {
        TeamDetail team_detail = new TeamDetail();

        Team team = team_dao.get(rs.getInt("teamId"));
        team_detail.setTeam(team);

        User team_member = user_dao.get(rs.getInt("teamMemberId"));
        team_detail.setTeamMember(team_member);

        Task task = task_dao.get(rs.getInt("taskId"));
        team_detail.setTask(task);

        return team_detail;
    }
}
