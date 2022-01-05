package com.karatascompany.pys3318.remote;

import com.karatascompany.pys3318.firebase_poco.EvaluationStatus;
import com.karatascompany.pys3318.models.GetUserTokenModel;
import com.karatascompany.pys3318.models.Graphs.ManagerProjectDaysModel;
import com.karatascompany.pys3318.models.Graphs.ProjectTaskStatusModel;
import com.karatascompany.pys3318.models.Graphs.UserAssignedTaskStatusModel;
import com.karatascompany.pys3318.models.ProjectModel;
import com.karatascompany.pys3318.models.StatusModel;
import com.karatascompany.pys3318.models.TaskDetailModel;
import com.karatascompany.pys3318.models.TaskModel;
import com.karatascompany.pys3318.models.TaskStatusModel;
import com.karatascompany.pys3318.models.UserAppointedTaskModel;
import com.karatascompany.pys3318.models.UserFriendModel;
import com.karatascompany.pys3318.models.UserModel;
import com.karatascompany.pys3318.models.UserScoringModel;
import com.karatascompany.pys3318.models.UserTokenModel;
import com.karatascompany.pys3318.poco.Login2;
import com.karatascompany.pys3318.poco.Project;
import com.karatascompany.pys3318.poco.Task;
import com.karatascompany.pys3318.poco.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by azizmahmud on 25.2.2018.
 */

public interface UserService {

    @POST("login")
    Call<String> login2(@Body Login2 Login2Model);

    @GET("login/{userEmail}/{userPassword}")
    Call<UserModel> login(@Path("userEmail") String userEmail, @Path("userPassword") String userPassword);

    @GET("GetProject/{userId}")
    Call<List<ProjectModel>> GetProject(@Path("userId") String userId);

    @GET("GetUserTask/{userId}/{projectId}")
    Call<List<TaskModel>> GetTask(@Path("userId") String userId, @Path("projectId") String projectId);

    @GET("GetMyTask/{userId}")
    Call<List<TaskModel>> GetMyTask(@Path("userId") String userId);

    @GET("GetTaskDetail/{userId}/{taskId}")
    Call<TaskDetailModel> GetTaskDetail (@Path("userId") String userId, @Path("taskId") String taskId);

    @POST("SetTaskStatus/{taskId}/{isTaskStatu}")
    Call<TaskStatusModel> SetTaskStatus (@Path("taskId") String taskId , @Path("isTaskStatu") String isTaskStatu);

    @POST("InsertProject/{managerId}/{projectSeriliaze}")
    Call<StatusModel> InsertProject (@Path("managerId") String managerId, @Path("projectSeriliaze") String projectSeriliaze);

    @POST("DenemeSeri/{seri}")
    Call<StatusModel> DenemeSeri(@Path("seri") String seri);

    @POST("InsertProject/{managerId}/{projectTitle}/{projectDetail}/{projectStartDate}/{projectEndDate}")
    Call<String> InsertProjectManuel(@Path("managerId")String managerId,@Path("projectTitle") String projectTitle,@Path("projectDetail") String projectDetail,@Path("projectStartDate") String projectStartDate,@Path("projectEndDate")String projectEndDate);

    @POST ("EditProjectTaskStartDate/{userId}/{taskId}/{taskStartDate}")
    Call<String> EditProjectTaskStartDate(@Path("userId") String userId,@Path("taskId") String taskId,@Path("taskStartDate")String taskStartDate);


    @POST ("EditProjectTaskEndDate/{userId}/{taskId}/{taskEndDate}")
    Call<String> EditProjectTaskEndDate(@Path("userId") String userId,@Path("taskId") String taskId,@Path("taskEndDate")String taskEndDate);

    @GET("GetProjectTaskDetail/{userId}/{taskId}")
    Call<TaskDetailModel> GetProjectTaskDetail(@Path("userId") String userId,@Path("taskId")String taskId);

    @GET("GetUserFriend/{userId}/{taskId}")
      Call<List<UserFriendModel>> GetUserFriend(@Path("userId") String userId, @Path("taskId")String taskId);

    @POST("InsertProjectClass")
    Call<String> InsertProjectClass(@Body Project projectModel);

    @POST("DeleteTask/{userId}/{taskId}")
    Call<String> DeleteTask (@Path("userId") String userId,@Path("taskId") String taskId);

    @POST("EditTaskName/{userId}/{taskId}/{taskName}")
    Call<String> EditTaskName(@Path("userId") String userId,@Path("taskId") String taskId,@Path("taskName") String taskName);

    @GET("UserTaskStatus/{userId}")
    Call<List<UserAssignedTaskStatusModel>> UserTaskStatus(@Path("userId") String userId);

    @GET("ManagerProjectTaskStatus/{userId}/{projectId}")
    Call<List<ProjectTaskStatusModel>> GetManagerProjectTaskStatus(@Path("userId") String userId,@Path("projectId") String projectId);

    @GET("ManagerProjectDays/{userId}")
    Call<List<ManagerProjectDaysModel>> GetManagerProjectDays(@Path("userId") String userId);

    @POST("UpdateTokenId") // giriş esnasında...
    Call<String> UpdateTokenId(@Body UserTokenModel userToken);

    @POST("RemoveTokenId/{userId}") // çıkış yapınca olacak bu...
    Call<String> RemoveTokenId(@Path("userId") String userId);

    @GET("GetUserTokenId/{userId}")
    Call<GetUserTokenModel> GetUserTokenId(@Path("userId") String userId);


    @POST("PostAppointUserList")
    Call<String> PostAppointUserList(@Body List<EvaluationStatus> evaluationStatusList);
    //@Headers("Content-Type: application/json")
    @GET("GetUserAppointedTaskList/{userId}")
    Call<List<UserAppointedTaskModel>> GetUserAppointedTaskList(@Path("userId") String userId);

    @POST("SetUserScoring")
    Call<String> SetUserScoring(@Body UserScoringModel userScore);

    @POST("GetTotalCountProjectDetail/{userId}")
    Call<String> GetTotalCountProjectDetail(@Path("userId") String userId);

    @POST("GetTaskPointAnalize/{userId}")
    Call<String> GetTaskPointAnalize(@Path("userId") String userId);

    ////
    @POST("InsertUserInf")  ///{userMail}/{userFullName}/{userPassword}
    Call<String> InsertUserInf(@Body User user);
//@Path("userMail") String userMail,@Path("userMail") String userFullName,@Path("userMail") String userPassword
    @POST("InsertTaskClass")
    Call<String> InsertTaskClass(@Body Task taskModel);

 //   @POST("SetTask/{taskName}/{TaskDetail}/{taskStartDate},/{taskEndDate}")
   // Call<String> SetTask(@Path("taskName") String userMail,@Path("TaskDetail") String userFullName,@Path("taskStartDate") String userPassword,@Path("taskStartDate") String taskEndDate);

}
