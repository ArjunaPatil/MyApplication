package com.example.vin.myapplication;

/**
 * Created by vin on 09-11-2016.
 */

import com.google.gson.JsonElement;

import org.json.JSONArray;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/*-----------------------*/

public interface ApiService {
    //Retrofit turns our institute WEB API into a Java interface.
    //So these are the list available in our WEB API and the methods look straight forward
   /* @Headers({
            "Cookie: sid=9567200a5879a94f167ac34581a1759614d2f6b3f7ef58add1301cd4"
            *//*,
            "User-Agent: Your-App-Name"*//*
    })*/
    //i.e. http://localhost/api/institute/Students
    @GET("/statemaster/getallitemmaster")
    public void getState(Callback<List<State>> callback);

    //i.e. http://localhost/api/institute/Students/1
    //Get student record base on ID
/*    @GET("/institute/Students/{id}")
    public void getStudentById(@Path("id") Integer id,Callback<Student> callback);

    //i.e. http://localhost/api/institute/Students/1
    //Delete student record base on ID
    @DELETE("/institute/Students/{id}")
    public void deleteStudentById(@Path("id") Integer id,Callback<Student> callback);

    //i.e. http://localhost/api/institute/Students/1
    //PUT student record and post content in HTTP request BODY
    @PUT("/institute/Students/{id}")
    public void updateStudentById(@Path("id") Integer id,@Body Student student,Callback<Student> callback);

    //i.e. http://localhost/api/institute/Students
    //Add student record and post content in HTTP request BODY
    @POST("/institute/Students")
    public void addStudent(@Body Student student,Callback<Student> callback);*/

/*
    // pdf sample
    @GET("/method/frappe.utils.print_format.download_multi_pdf")
    public void getPdf(@Header("Cookie") String contentRange,
                             @Query("doctype") JSONArray doctype,
                             @Query("name") JSONArray name,
                             @Query("format") JSONArray format,
                             @Query("no_letterhead") JSONArray no_letterhead,
                       Callback<POJO> callback);
*/


    //EMAIL ID IS CONFIRM CHECK
    @POST("/method/login")
    public void login(@Query("usr") String usr, @Query("pwd") String pwd,
                      Callback<JsonElement> callback);
   /* public void login(@Field("usr") String usr, @Field("pwd") String pwd,
                      Callback<JSONObject> callback);*/


    //Get Employee Info from user email_id // get fiels like employee code,designation,name,active
    @GET("/resource/Employee/")
    public void Emp_Data(@Header("Cookie") String contentRange, @Query("fields") JSONArray fields, @Query("filters") JSONArray filter, Callback<JsonElement> callback);
/*@GET("/resource/Employee/")
    public void Emp_Data(@Header("Cookie") String contentRange, Callback<JsonElement> callback);*/

    // get all live work from TBM
  /*  @GET("/resource/Live Work/")
    public void getLIVE_WORK(@Header("Cookie") String contentRange,
                             @Query("limit_start") Integer limit,
                             @Query("fields") JSONArray fields,
                             @Query("filters") JSONArray filter,

                             Callback<JsonElement> callback);*/
    @GET("/resource/Live Work/")
    public void getLIVE_WORK(@Header("Cookie") String contentRange,
                             @Query("limit_start") Integer limit,
                             @Query("limit_page_length") Integer pagesize,
                             @Query("fields") JSONArray fields,
                             @Query("filters") JSONArray filter,
                             Callback<JsonElement> callback);

    @GET("/resource/AppVersions/")
    public void getAppVersions(@Header("Cookie") String contentRange,
                               @Query("limit_start") Integer limit,
                               @Query("limit_page_length") Integer pagesize,
                               @Query("fields") JSONArray fields,
                               Callback<JsonElement> callback);

    // get all Doctor for specific doctor from TBM
    @GET("/resource/Doctor Master/")
    public void getDoctor_Master(@Header("Cookie") String contentRange,
                                 @Query("limit_start") Integer limit,
                                 @Query("limit_page_length") Integer pagesize,
                                 @Query("fields") JSONArray fields,
                                 @Query("filters") JSONArray filter,
                                 Callback<JsonElement> callback);

    // get all Doctor for specific doctor from TBM
    @GET("/resource/Employee")
    public void getEmployee(@Header("Cookie") String contentRange,
                            @Query("order_by") String order_by,
                            @Query("limit_start") Integer limit,
                            @Query("limit_page_length") Integer pagesize,
                            @Query("fields") JSONArray fields,
                            @Query("filters") JSONArray filter,
                            Callback<JsonElement> callback);

    // get all Doctor for specific doctor from TBM
    @GET("/resource/Patch master/")
    public void getPatch_master(@Header("Cookie") String contentRange,
                                @Query("limit_start") Integer limit,
                                @Query("limit_page_length") Integer pagesize,
                                @Query("fields") JSONArray fields,
                                @Query("filters") JSONArray filter,
                                Callback<JsonElement> callback);


    //Insert into Patch Master
    @POST("/resource/Patch master")
    public void Patch_master_insert(@Header("Cookie") String contentRange,
                                    @Body POJO_Patch_master POJO,
                                    Callback<JsonElement> callback);

    //Update into Patch Master
    @PUT("/resource/Patch master/{request_id}")
    public void Patch_master_update(@Header("Cookie") String contentRange,
                                    @Body POJO_Patch_master POJO,
                                    @Path("request_id") String requestId,
                                    Callback<JsonElement> callback);

    //DELETE into Patch Master
    @PUT("/resource/Patch master/{request_id}")
    public void Patch_master_delete(@Header("Cookie") String contentRange,
                                    @Body POJO_Patch_master POJO,
                                    @Path("request_id") String requestId,
                                    Callback<JsonElement> callback);

    //Insert into Doctor  Master
    @POST("/resource/Doctor Master")
    public void doctor_master_insert(@Header("Cookie") String contentRange,
                                     @Body POJO_Doctor_Master POJO,
                                     Callback<JsonElement> callback);

    //Update  Doctor  Master
    @PUT("/resource/Doctor Master/{request_id}")
    public void doctor_master_update(@Header("Cookie") String contentRange,
                                     @Body POJO_Doctor_Master POJO,
                                     @Path("request_id") String requestId,
                                     Callback<JsonElement> callback);

    //DELETE into Doctor  Master
    @PUT("/resource/Doctor Master/{request_id}")
    public void doctor_master_delete(@Header("Cookie") String contentRange,
                                     @Body POJO_Doctor_Master POJO,
                                     @Path("request_id") String requestId,
                                     Callback<JsonElement> callback);


    // get all Doctor for specific doctor from TBM
    @GET("/resource/Daily Plan/")
    public void getDailyPlan(@Header("Cookie") String contentRange,
                             @Query("fields") JSONArray fields,
                             @Query("filters") JSONArray filter,
                             Callback<JsonElement> callback);


    // get all Employee Plan Details Datewise
    @GET("/resource/Daily Plan/")
    public void getDailyPlanDatewise(@Header("Cookie") String contentRange,
                                     @Query("limit_start") Integer limit,
                                     @Query("limit_page_length") Integer pagesize,
                                     @Query("fields") JSONArray fields,
                                     @Query("filters") JSONArray filter,
                                     Callback<JsonElement> callback);


    //Insert into Daily Plan
    @POST("/resource/Daily Plan")
    public void daily_plan_insert(@Header("Cookie") String contentRange,
                                  @Body POJO_Employee_Daily_Plan POJO,
                                  Callback<JsonElement> callback);

    //Update  Daily Planer
    @PUT("/resource/Daily Plan/{request_id}")
    public void daily_plan_update(@Header("Cookie") String contentRange,
                                  @Body POJO_Employee_Daily_Plan POJO,
                                  @Path("request_id") String requestId,
                                  Callback<JsonElement> callback);

    // get all Daily Doctor Calls Individual Employee Datewise
    @GET("/resource/Daily Doctor Calls/")
    public void getDailyDoctorCalls(@Header("Cookie") String contentRange,
                                    @Query("fields") JSONArray fields,
                                    @Query("filters") JSONArray filter,
                                    Callback<JsonElement> callback);

    //Insert all Daily Doctor Calls Individual Employee Datewise
    @POST("/resource/Daily Doctor Calls")
    public void Daily_Doctor_Calls_insert(@Header("Cookie") String contentRange,
                                          @Body POJO_Daily_Doctor_Calls POJO,
                                          Callback<JsonElement> callback);

    //Update all Daily Doctor Calls Individual Employee Datewise
    @PUT("/resource/Daily Doctor Calls/{request_id}")
    public void Daily_Doctor_Calls_update(@Header("Cookie") String contentRange,
                                          @Body POJO_Daily_Doctor_Calls POJO,
                                          @Path("request_id") String requestId,
                                          Callback<JsonElement> callback);

    //DELETE into Daily Doctor Calls
    @DELETE("/resource/Daily Doctor Calls/{request_id}")
    public void Daily_Doctor_Calls_delete(@Header("Cookie") String contentRange,
                                          @Path("request_id") String requestId,
                                          Callback<JsonElement> callback);


    ////////////neww

    @GET("/resource/Objective/")
    public void getObjective(@Header("Cookie") String contentRange,
                             @Query("fields") JSONArray fields,
                             @Query("filters") JSONArray filter,
                             Callback<JsonElement> callback);


    @POST("/resource/Objective")
    public void Objective_insert(@Header("Cookie") String contentRange,
                                 @Body POJO_objective obj,
                                 Callback<JsonElement> callback);


/*    // get all User for popup
    @GET("/resource/User")
    public void getUser_S(@Header("Cookie") String contentRange,
                            @Query("order_by") String order_by,
                            @Query("limit_start") Integer limit,
                            @Query("limit_page_length") Integer pagesize,
                            @Query("fields") JSONArray fields,
                            @Query("filters") JSONArray filter,
                            Callback<JsonElement> callback);*/

    // get all User for popup
    @GET("/resource/User")
    public void getUser_S(@Header("Cookie") String contentRange,
                          @Query("order_by") String order_by,
                          @Query("limit_start") Integer limit,
                          @Query("limit_page_length") Integer pagesize,
                          @Query("fields") JSONArray fields,

                          Callback<JsonElement> callback);


    // Get  Doctor calls get only selected record when click on listview ,use the data for edit
    @GET("/resource/Doctor Calls")
    public void getDoctor_Calls(@Header("Cookie") String contentRange,
                                @Query("order_by") String order_by,
                                @Query("limit_start") Integer limit,
                                @Query("limit_page_length") Integer pagesize,
                                @Query("fields") JSONArray fields,
                                @Query("filters") JSONArray filter,
                                Callback<JsonElement> callback);

    //Insert into Doctor Calls
    @POST("/resource/Doctor Calls")
    public void Doctor_Calls_insert(@Header("Cookie") String contentRange,
                                    @Body POJO_Doctor_Calls POJO,
                                    Callback<JsonElement> callback);

    //Update into Doctor Calls
    @PUT("/resource/Doctor Calls/{request_id}")
    public void Doctor_Calls_update(@Header("Cookie") String contentRange,
                                    @Body POJO_Doctor_Calls POJO,
                                    @Path("request_id") String requestId,
                                    Callback<JsonElement> callback);


    // Get  Chemist Call get only selected record when click on listview ,use the data for edit
    @GET("/resource/Chemist Call")
    public void getChemist_Call(@Header("Cookie") String contentRange,
                                @Query("order_by") String order_by,
                                @Query("limit_start") Integer limit,
                                @Query("limit_page_length") Integer pagesize,
                                @Query("fields") JSONArray fields,
                                @Query("filters") JSONArray filter,
                                Callback<JsonElement> callback);

    //Insert into Chemist Call
    @POST("/resource/Chemist Call")
    public void Chemist_Call_insert(@Header("Cookie") String contentRange,
                                    @Body POJO_chemist_Calls POJO,
                                    Callback<JsonElement> callback);

    //Update into Chemist Call
    @PUT("/resource/Chemist Call/{request_id}")
    public void Chemist_Call_update(@Header("Cookie") String contentRange,
                                    @Body POJO_chemist_Calls POJO,
                                    @Path("request_id") String requestId,
                                    Callback<JsonElement> callback);


    // Get  campaign_booking get only selected record when click on listview ,use the data for edit
    @GET("/resource/campaign_booking")
    public void get_campaign_booking(@Header("Cookie") String contentRange,
                                     @Query("order_by") String order_by,
                                     @Query("limit_start") Integer limit,
                                     @Query("limit_page_length") Integer pagesize,
                                     @Query("fields") JSONArray fields,
                                     @Query("filters") JSONArray filter,
                                     Callback<JsonElement> callback);

    //Insert into Chemist Call
    @POST("/resource/campaign_booking")
    public void campaign_booking_insert(@Header("Cookie") String contentRange,
                                        @Body POJO_campaign_booking POJO,
                                        Callback<JsonElement> callback);

    //Update into Chemist Call
    @PUT("/resource/campaign_booking/{request_id}")
    public void campaign_booking_update(@Header("Cookie") String contentRange,
                                        @Body POJO_campaign_booking POJO,
                                        @Path("request_id") String requestId,
                                        Callback<JsonElement> callback);

////////////////////////////////////////
    ///////////////////////////////////////
    ////////////////////////////////
    //Arjun


    //Get User Info from user email_id // get fiels like employee code,designation,name,active
    @GET("/resource/User/")
    public void userdata(@Header("Cookie") String contentRange,
                         @Query("filters") JSONArray filter,
                         @Query("fields") JSONArray fields,
                         Callback<JsonElement> callback);


    ////////////neww

    // Get  Doctor calls get only selected record when click on listview ,use the data for edit
    @GET("/resource/Objective")
    public void getObjectiveList(@Header("Cookie") String contentRange,
                                 @Query("order_by") String order_by,
                                 @Query("limit_start") Integer limit,
                                 @Query("limit_page_length") Integer pagesize,
                                 @Query("fields") JSONArray fields,
                                 @Query("filters") JSONArray filter,
                                 Callback<JsonElement> callback);

    @PUT("/resource/Objective/{request_id}")
    public void updateobjective(@Header("Cookie") String contentRange,
                                @Body POJO_objective obj,
                                @Path("request_id") String requestId,
                                Callback<JsonElement> callback);

    // get all User for popup
    @GET("/resource/User")
    public void getUser_S(@Header("Cookie") String contentRange,
                          @Query("order_by") String order_by,
                          @Query("limit_start") Integer limit,
                          @Query("limit_page_length") Integer pagesize,
                          @Query("fields") JSONArray fields,
                          @Query("filters") JSONArray filter,
                          Callback<JsonElement> callback);
    /*arjun*/

    //get all User Info
    @GET("/resource/User/")
    public void getUser(@Header("Cookie") String contentRange,
                        @Query("limit_start") Integer limit,
                        @Query("limit_page_length") Integer pagesize,
                        @Query("fields") JSONArray fields,
                        @Query("filters") JSONArray filter,
                        Callback<JsonElement> callback);

    //get all User Info Using Method
    @GET("/method/team.emp_and_hq_hierachy.tree_user/")
    public void getUser_Method(
            @Header("Cookie") String contentRange,
            @Query("employee") String employee,
            @Query("designation") String designation,
            @Query("limit") Integer pagesize,
            @Query("offset") Integer limit,
            Callback<JsonElement> callback);

    //Update Specific User Info
    @PUT("/resource/User/{request_id}")
    public void putUser(@Header("Cookie") String contentRange,
                        @Body POJO_User POJO,
                        @Path("request_id") String requestId,
                        Callback<JsonElement> callback);


    // get all Doctor for specific doctor from TBM
    @GET("/resource/Chemist Master/")
    public void getChemist_Master(@Header("Cookie") String contentRange,
                                  @Query("limit_start") Integer limit,
                                  @Query("limit_page_length") Integer pagesize,
                                  @Query("fields") JSONArray fields,
                                  @Query("filters") JSONArray filter,
                                  Callback<JsonElement> callback);


    //Insert into Chemist  Master
    @POST("/resource/Chemist Master")
    public void chemist_master_insert(@Header("Cookie") String contentRange,
                                      @Body POJO_Chemist POJO,
                                      Callback<JsonElement> callback);

    //Update  Chemist  Master
    @PUT("/resource/Chemist Master/{request_id}")
    public void chemist_master_update(@Header("Cookie") String contentRange,
                                      @Body POJO_Chemist POJO,
                                      @Path("request_id") String requestId,
                                      Callback<JsonElement> callback);

    //get all HQ Info Using Method
    @GET("/method/team.emp_and_hq_hierachy.tree_territory_get_hq/")
    public void getTerritory_Method(
            @Header("Cookie") String contentRange,
            @Query("territory") String territory,
            @Query("designation") String designation,
            @Query("limit") Integer pagesize,
            @Query("offset") Integer limit,
            Callback<JsonElement> callback);


    //Get All HQ
    @GET("/resource/Territory")
    public void getTerritory(@Header("Cookie") String contentRange,
                             @Query("order_by") String order_by,
                             @Query("limit_start") Integer limit,
                             @Query("limit_page_length") Integer pagesize,
                             @Query("fields") JSONArray fields,
                             @Query("filters") JSONArray filter,
                             Callback<JsonElement> callback);


    // get all Stockiest Users  Master
    @GET("/resource/Stockiest Users/")
    public void getStockiest_Users(@Header("Cookie") String contentRange,
                                   @Query("limit_start") Integer limit,
                                   @Query("limit_page_length") Integer pagesize,
                                   @Query("fields") JSONArray fields,
                                   @Query("filters") JSONArray filter,
                                   Callback<JsonElement> callback);


    // get all Stockiest Users  Master
    @GET("/resource/Customer/")
    public void getStockiest(@Header("Cookie") String contentRange,
                             @Query("limit_start") Integer limit,
                             @Query("limit_page_length") Integer pagesize,
                             @Query("fields") JSONArray fields,
                             @Query("filters") JSONArray filter,
                             Callback<JsonElement> callback);
    //Start get all Data for DropDown Popup In Doctor Insert

    //@Query("order_by") String order_by,
    //@Query("limit_start") Integer limit,
    //@Query("limit_page_length") Integer pagesize,

    @GET("/resource/Doctor Master")
    public void getDoctor_Update_data(@Header("Cookie") String contentRange,
                                      @Query("fields") JSONArray fields,
                                      @Query("filters") JSONArray filter,
                                      Callback<JsonElement> callback);

    @GET("/resource/Patch master")
    public void getDoctor_Insert_Patch_S(@Header("Cookie") String contentRange,
                                         @Query("order_by") String order_by,
                                         @Query("limit_start") Integer limit,
                                         @Query("limit_page_length") Integer pagesize,
                                         @Query("fields") JSONArray fields,
                                         @Query("filters") JSONArray filter,
                                         Callback<JsonElement> callback);

    @GET("/resource/Doctor Degree")
    public void getDoctor_Degree_S(@Header("Cookie") String contentRange,
                                   @Query("order_by") String order_by,
                                   @Query("limit_start") Integer limit,
                                   @Query("limit_page_length") Integer pagesize,
                                   @Query("fields") JSONArray fields,

                                   Callback<JsonElement> callback);


    @GET("/resource/Doctor Specialization")
    public void getDoctor_Specialization_S(@Header("Cookie") String contentRange,
                                           @Query("order_by") String order_by,
                                           @Query("limit_start") Integer limit,
                                           @Query("limit_page_length") Integer pagesize,
                                           @Query("fields") JSONArray fields,

                                           Callback<JsonElement> callback);


    @GET("/resource/Doctor Type")
    public void getDoctor_Type_S(@Header("Cookie") String contentRange,
                                 @Query("order_by") String order_by,
                                 @Query("limit_start") Integer limit,
                                 @Query("limit_page_length") Integer pagesize,
                                 @Query("fields") JSONArray fields,

                                 Callback<JsonElement> callback);

    @GET("/resource/Chemist Master/")
    public void getChemist_Update_data(@Header("Cookie") String contentRange,
                                       @Query("order_by") String order_by,
                                       @Query("limit_start") Integer limit,
                                       @Query("limit_page_length") Integer pagesize,
                                       @Query("fields") JSONArray fields,
                                       @Query("filters") JSONArray filter,
                                       Callback<JsonElement> callback);

    @GET("/resource/Stockiest Users/")
    public void getStockiest_Users_Update_data(@Header("Cookie") String contentRange,
                                               @Query("order_by") String order_by,
                                               @Query("limit_start") Integer limit,
                                               @Query("limit_page_length") Integer pagesize,
                                               @Query("fields") JSONArray fields,
                                               @Query("filters") JSONArray filter,
                                               Callback<JsonElement> callback);

    //Insert into Stockiest Users  Master
    @POST("/resource/Stockiest Users")
    public void stockiest_users_master_insert(@Header("Cookie") String contentRange,
                                              @Body POJO_Stockiest_Users POJO,
                                              Callback<JsonElement> callback);

    //Update  Stockiest Users  Master
    @PUT("/resource/Stockiest Users/{request_id}")
    public void stockiest_users_master_update(@Header("Cookie") String contentRange,
                                              @Body POJO_Stockiest_Users POJO,
                                              @Path("request_id") String requestId,
                                              Callback<JsonElement> callback);

    //Method by vinayak
    //get all dashboard data
    //@GET("/method/team.dashboard.get_count_of_objectives_of_bottom_emp/")dashboard3
    //@GET("/method/team.dashboard4.get_count_of_objectives_of_bottom_emp/")
    @GET("/method/team.dashboard5.get_count_of_objectives_of_bottom_emp/")
    public void getDashboad_data_Method(
            @Header("Cookie") String contentRange,
            @Query("employee") String employee,
            @Query("designation") String designation,
            @Query("date_pass") String date_pass,
            @Query("app_ver") String versionName,
            Callback<JsonElement> callback);

    @GET("/method/team.branch_verification.check_branch/")
    public void getCheck_branch(
            @Header("Cookie") String contentRange,
            @Query("userid") String userid,
            @Query("employee") String employee,
            Callback<JsonElement> callback);

    //@GET("/method/team.presenty_daily_call_count_details.presenty_daily_call_count/")
    @GET("/method/team.presenty_daily_call_count_details1.presenty_daily_call_count/")//05-05-2018
    public void getPresenty_data_Method(
            @Header("Cookie") String contentRange,
            @Query("fromdate") String fromdate,
            @Query("todate") String todate,
            @Query("designation") String designation,
            @Query("branch") String branch,
            Callback<JsonElement> callback);

    //@GET("/method/team.presenty_daily_call_count_details.missing_daily_obj_names/")
    @GET("/method/team.presenty_daily_call_count_details1.missing_daily_obj_names/")
    public void getMissOBJ_data_Method(
            @Header("Cookie") String contentRange,
            @Query("date") String fromdate,
            @Query("designation") String designation,
            @Query("branch") String branch,
            Callback<JsonElement> callback);

    @GET("/method/team.doctor_count_active_camp.count_active_camp_doctors/")
    public void getDoctor_cnt_active_camp(
            @Header("Cookie") String contentRange,
            @Query("employee") String employee,
            Callback<JsonElement> callback);

    //get all current SERVER date and  app_version_support or noty
    @GET("/method/team.check_date_and_app_ver.get_date_and_app_support/")
    public void getToday_server_and_app_version_Method(
            @Header("Cookie") String contentRange,
            @Query("date_pass") String date_pass,
            @Query("app_ver") String versionName,
            Callback<JsonElement> callback);


    @GET("/resource/Patch master")
    public void getPatchUserWise(@Header("Cookie") String contentRange,
                                 @Query("order_by") String order_by,
                                 @Query("limit_start") Integer limit,
                                 @Query("limit_page_length") Integer pagesize,
                                 @Query("fields") JSONArray fields,
                                 @Query("filters") JSONArray filter,
                                 Callback<JsonElement> callback);


    // get all Doctor for specific doctor from TBM
    @GET("/resource/Doctor Master")
    public void getDoctor_List_PatchWise(@Header("Cookie") String contentRange,
                                         @Query("order_by") String order_by,
                                         @Query("limit_start") Integer limit,
                                         @Query("limit_page_length") Integer pagesize,
                                         @Query("fields") JSONArray fields,
                                         @Query("filters") JSONArray filter,
                                         Callback<JsonElement> callback);


    // get all Doctor for specific doctor from TBM
    @GET("/resource/Chemist Master")
    public void getChemist_List_UserWise(@Header("Cookie") String contentRange,
                                         @Query("order_by") String order_by,
                                         @Query("limit_start") Integer limit,
                                         @Query("limit_page_length") Integer pagesize,
                                         @Query("fields") JSONArray fields,
                                         @Query("filters") JSONArray filter,
                                         Callback<JsonElement> callback);


    //Insert into Chemist  Master
    @POST("/resource/Employee Location")
    public void employee_location_insert(@Header("Cookie") String contentRange,
                                         @Body POJO_Employee_Location POJO,
                                         Callback<JsonElement> callback);

    //method by arjun
    //get master form lock flag 1 or 0
    @GET("/method/team.masterlocking.lock_master_forms")
    public void getMasterFormLockOrNot(
            @Header("Cookie") String contentRange,
            @Query("employee") String employee,
            @Query("formname") String designation,
            Callback<JsonElement> callback);

    //get master form lock flag 1 or 0
    @GET("/method/team.masterlocking.lock_transaction_forms")
    public void getTransactionFormLockOrNot(
            @Header("Cookie") String contentRange,
            @Query("employee") String employee,
            @Query("formname") String designation,
            @Query("date") String date,
            Callback<JsonElement> callback);

    //get master form lock flag 1 or 0
    @GET("/method/team.user_lock.lock_check_with_std_lock")
    public void getUserLockData(
            @Header("Cookie") String contentRange,
            @Query("user") String user,
            Callback<JsonElement> callback);

    //get all User Info
    @GET("/resource/report ip address/")
    public void getReportIP(@Header("Cookie") String contentRange,
                            @Query("fields") JSONArray fields,
                            Callback<JsonElement> callback);

    @POST("/resource/temp_location_from_app")
    public void temp_location_from_app(@Header("Cookie") String contentRange,
                                       @Body POJO_Employee_Location POJO,
                                       Callback<JsonElement> callback);


    //get all User Info
    @GET("/resource/Standard Lock/?")
    public void getStd_Lock_time_date(@Header("Cookie") String contentRange,
                                      @Query("limit_start") Integer limit,
                                      @Query("limit_page_length") Integer pagesize,
                                      @Query("fields") JSONArray fields,
                                      Callback<JsonElement> callback);

    //get master form lock flag 1 or 0
    @GET("/method/team.user_lock.update_user_lock_time_and_date")
    public void putUser_lock_time_date(
            @Header("Cookie") String contentRange,
            @Query("m_pro") Integer m_pro,
            @Query("m_pat") Integer m_pat,
            @Query("m_doc") Integer m_doc,
            @Query("m_che") Integer m_che,
            @Query("t_obj_time") String t_obj_time,
            @Query("t_drc_s_time") String t_drc_s_time,
            @Query("t_chc_s_time") String t_chc_s_time,
            Callback<JsonElement> callback);


    //get master form lock flag 1 or 0
    @GET("/method/team.user_lock.update_user_lock_time_and_date")
    public void putUser_lock_time_date1(
            @Header("Cookie") String contentRange,
            @Query("send_opr_flag") String send_opr_flag,
            Callback<JsonElement> callback);

    //get master form lock flag 1 or 0
    @GET("/method/team.user_lock.update_reset_user_lock_time_and_date")
    public void putResetAllLockedMasters(
            @Header("Cookie") String contentRange,
            @Query("send_opr_flag") String send_opr_flag,
            Callback<JsonElement> callback);

    //get all User Info Using Method For User Lock/Unlock List
    @GET("/method/team.user_lock.retrun_user_list_with_lock_flag/")
    public void getUser_List_with_lock_flag(
            @Header("Cookie") String contentRange,
            @Query("limit") Integer pagesize,
            @Query("offset") Integer limit,
            Callback<JsonElement> callback);

    //,,,,,,
 /*   @POST("/")
    public void filesownload(
            @Query("name") String id,
            @Query("parameter") String  parameter,
            Callback<PDF> callback);

    @GET
    Call<ResponseBody> downlload(@Url String fileUrl);*/


    /*-------------------*/
//fail
    @Multipart
    @POST("/home/frappe/temp/")
    Call<Response> uploadImage(
            @Part MultipartBody.Part image
    );

    //fail
    @GET("http://139.59.63.181/private/files/IMG-20171002-WA0016.jpg")
    public void getLoadedFiles(
            @Header("Cookie") String contentRange,
            Callback<JsonElement> callback);

    //Ok success
    @GET("/resource/File/")
    public void getUploadedFiles(@Header("Cookie") String contentRange,
                                 @Query("order_by") String order_by,
                                 @Query("limit_start") Integer limit,
                                 @Query("limit_page_length") Integer pagesize,
                                 @Query("fields") JSONArray fields,
                                 @Query("filters") JSONArray filter,
                                 Callback<JsonElement> callback);

    //Insert into Stockiest Users  Master
    @POST("/resource/File")
    public void UploadedFilesToServer(@Header("Cookie") String contentRange,
                                      @Body POJO_Upload_Files POJO,
                                      Callback<JsonElement> callback);


    //Insert into Stockiest Users  Master
    @POST("")
    public void UploadedFilesToServer1(@Header("Cookie") String contentRange,
                                       @Query("cmd") String cmd,
                                       @Query("file_size") Integer file_size,
                                       @Query("filedata") String filedata,
                                       @Query("filename") String filename,
                                       @Query("folder") String folder,
                                       @Query("from_form") Integer from_form,
                                       @Query("is_private") Integer is_private,
                                       Callback<JsonElement> callback);

    @POST("/")
    @FormUrlEncoded
    public void UploadedFilesToServer2(@Header("Cookie") String contentRange,
                                       @PartMap Map<String, String> params,
                                       Callback<JsonElement> callback);

    @POST("/")
    @Headers({
            "Accept: application/json",
            "Connection: Keep-Alive",
            "Cache-Control: no-cache",
            "Content-Type: application/x-www-form-urlencoded; charset=UTF-8"
    })
    @FormUrlEncoded
    public void UploadedFilesToServer3(@Header("Cookie") String contentRange,
                                       @PartMap Map<String, String> params1,
                                       @PartMap Map<String, Integer> params2,
                                       @PartMap Map<String, String> params3,
                                       @PartMap Map<String, String> params4,
                                       @PartMap Map<String, String> params5,
                                       @PartMap Map<String, Integer> params6,
                                       @PartMap Map<String, Integer> params7,
                                       Callback<JsonElement> callback);

    // get all Doctor for specific doctor from TBM
    @GET("/resource/Products For Branch Doctors")
    public void getProductOfBranch(@Header("Cookie") String contentRange,
                                   @Query("order_by") String order_by,
                                   @Query("limit_start") Integer limit,
                                   @Query("limit_page_length") Integer pagesize,
                                   @Query("fields") JSONArray fields,
                                   @Query("filters") JSONArray filter,
                                   Callback<JsonElement> callback);

    @GET("/resource/Products For Branch Doctors")
    public void getBranchwiseProduct(@Header("Cookie") String contentRange,
                                     @Query("order_by") String order_by,
                                     @Query("limit_start") Integer limit,
                                     @Query("limit_page_length") Integer pagesize,
                                     @Query("fields") JSONArray fields,
                                     @Query("filters") JSONArray filter,
                                     Callback<JsonElement> callback);

    //get master form lock flag 1 or 0
    @GET("/method/team.branch_products.product_list1")
    public void getproduct_list(
            @Header("Cookie") String contentRange,
            @Query("branch") String branch,
            Callback<JsonElement> callback);

    @GET("/method/team.team.doctype.test_gps_map.test_gps_map.get_lat_long_details/")
    public void getLat_Lon_of_Emp(
            @Header("Cookie") String contentRange,
            @Query("date") String date,
            @Query("emp") String emp,
            Callback<JsonElement> callback);

    @GET("/method/team.primary_secondary_on_app.get_date_and_app_support")
    public void getPrimary_Secondary_App(
            @Header("Cookie") String contentRange,
            @Query("User") String user,
            @Query("Stockist") String stockist,
            @Query("FromDate") String fromdate,
            @Query("ToDate") String todate,
            @Query("Products") String products,
            Callback<JsonElement> callback);

    //User=kasimmevekari@gmail.com&Stockist=Drug Distributor&FromDate=2018-05-01&ToDate=2018-05-30&Products=EMPOWER - OD TAB&branch=Main
    @GET("/method/team.primary_secondary_on_app.get_primary_data_of_stockist")
    public void get_primary_data_of_stockist(
            @Header("Cookie") String contentRange,
            @Query("User") String user,
            @Query("Stockist") String stockist,
            @Query("FromDate") String fromdate,
            @Query("ToDate") String todate,
            @Query("Products") String products,
            @Query("branch") String branch,
            Callback<JsonElement> callback);

    @GET("/method/team.primary_secondary_on_app.stockist_list_for_top_hierarchy")
    public void getStockist_list_for_top_hierarchy(
            @Header("Cookie") String contentRange,
            @Query("employee") String employee,
            @Query("designation") String designation,
            @Query("limit") Integer limit,
            @Query("offset") Integer offset,
            Callback<JsonElement> callback);

    // get all User for popup
    @GET("/resource/Branch")
    public void getBranch_list(@Header("Cookie") String contentRange,
                          @Query("order_by") String order_by,
                          @Query("limit_start") Integer limit,
                          @Query("limit_page_length") Integer pagesize,
                          @Query("fields") JSONArray fields,
                          @Query("filters") JSONArray filter,
                          Callback<JsonElement> callback);

}
