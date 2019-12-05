package com.mareow.recaptchademo.Retrofit;

import com.mareow.recaptchademo.DataModels.AbleToRunMachine;
import com.mareow.recaptchademo.DataModels.AcceptWorkOrder;
import com.mareow.recaptchademo.DataModels.AccountSetting;
import com.mareow.recaptchademo.DataModels.AccountSettingModel;
import com.mareow.recaptchademo.DataModels.AddDailyLog;
import com.mareow.recaptchademo.DataModels.AddMachineModel;
import com.mareow.recaptchademo.DataModels.AssociatedOperator;
import com.mareow.recaptchademo.DataModels.BookMachine;
import com.mareow.recaptchademo.DataModels.CategoryImage;
import com.mareow.recaptchademo.DataModels.CommonManufacuter;
import com.mareow.recaptchademo.DataModels.ContactOwner;
import com.mareow.recaptchademo.DataModels.CreateFeedback;
import com.mareow.recaptchademo.DataModels.CreateOperator;
import com.mareow.recaptchademo.DataModels.CreateRentalPlan;
import com.mareow.recaptchademo.DataModels.DailyLogsSupervisor;
import com.mareow.recaptchademo.DataModels.FavoriteMachine;
import com.mareow.recaptchademo.DataModels.FeedbackForOwner;
import com.mareow.recaptchademo.DataModels.ForgotPasswordResponse;
import com.mareow.recaptchademo.DataModels.InviteOtherRequest;
import com.mareow.recaptchademo.DataModels.InvoiceByInvoiceId;
import com.mareow.recaptchademo.DataModels.InvoiceByUser;
import com.mareow.recaptchademo.DataModels.JWTToken;
import com.mareow.recaptchademo.DataModels.LoginResponse;
import com.mareow.recaptchademo.DataModels.MachineAttachment;
import com.mareow.recaptchademo.DataModels.MachineFeedBack;
import com.mareow.recaptchademo.DataModels.MareowCharges;
import com.mareow.recaptchademo.DataModels.Message;
import com.mareow.recaptchademo.DataModels.NewUser;
import com.mareow.recaptchademo.DataModels.Notification;
import com.mareow.recaptchademo.DataModels.OfferWorkOrder;
import com.mareow.recaptchademo.DataModels.OperatorMachineCount;
import com.mareow.recaptchademo.DataModels.OperatorWorkOrder;
import com.mareow.recaptchademo.DataModels.OwnerFeedBack;
import com.mareow.recaptchademo.DataModels.PaymentByPID;
import com.mareow.recaptchademo.DataModels.PlanById;
import com.mareow.recaptchademo.DataModels.ProfileData;
import com.mareow.recaptchademo.DataModels.RentalPlan;
import com.mareow.recaptchademo.DataModels.RenterMachine;
import com.mareow.recaptchademo.DataModels.RenterSpecificMachine;
import com.mareow.recaptchademo.DataModels.RenterWorkOrder;
import com.mareow.recaptchademo.DataModels.SendMessageModel;
import com.mareow.recaptchademo.DataModels.StatuTitleMessageResponse;
import com.mareow.recaptchademo.DataModels.Password;
import com.mareow.recaptchademo.DataModels.TermAndCondition;
import com.mareow.recaptchademo.DataModels.TypeOfTermAndCondition;
import com.mareow.recaptchademo.DataModels.UpdateDailyLogs;
import com.mareow.recaptchademo.DataModels.UpdateMachine;
import com.mareow.recaptchademo.DataModels.UpdatedWorkOrder;
import com.mareow.recaptchademo.DataModels.User;
import com.mareow.recaptchademo.DataModels.UserPassword;
import com.mareow.recaptchademo.DataModels.WorkOrderCancel;
import com.mareow.recaptchademo.DataModels.WorkOrderExtend;
import com.mareow.recaptchademo.DataModels.WorkOrderResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;

public interface ApiInterface {

    //Signup
    @GET("lookup/ADMIN/ADMIN")
    Call<ResponseBody> getUserRole();

    @FormUrlEncoded
    @POST("registration/userValidate")
    Call<StatuTitleMessageResponse> getUserEmailsValidate(@Field("identity") String email);

    @FormUrlEncoded
    @POST("registration/userValidate")
    Call<StatuTitleMessageResponse> getUserNameValidate(@Field("identity") String username);

    @POST("registration")
    Call<StatuTitleMessageResponse> insertRegistration(@Body NewUser newUser);


    @Multipart
    @POST("registration/updateUserDetails")
    Call<StatuTitleMessageResponse> updateProfileDetails(@Header("Authorization") String token,@Part MultipartBody.Part certificateImage,@Part MultipartBody.Part machinecredetialImage,
                                                               @Part MultipartBody.Part govtProffImage,@Part MultipartBody.Part gstProofImage,@Part MultipartBody.Part userImage,@Part("registrationDetailDTO") RequestBody  details);

    //Submit UserDetails
    @GET("lookup/PREFERED_SEGMENT")
    Call<ResponseBody> getPreferedSegment();

    //Login
    @POST("auth")
    Call<JWTToken> getJWT(@Body User user);
    @GET("user/me")
    Call<LoginResponse> getUserDetails(@Header("Authorization") String token);

    //Forgot password
    @FormUrlEncoded
    @POST("forgotPassword")
    Call<ForgotPasswordResponse> getForgotPasswordLinkResponse(@Field("identity") String email);
    @FormUrlEncoded
    @POST("recoverPassword")
    Call<ForgotPasswordResponse> verifyToken(@Field("userId") String userid,@Field("token") String token);


    @POST("updatePassword")
    Call<ForgotPasswordResponse> updatePassword(@Body UserPassword userPassword);



    //change password

    @FormUrlEncoded
    @POST("resetPassword")
    Call<StatuTitleMessageResponse> getResetPassword(@Header("Authorization") String token, @Field("password") String password);

    @POST("updatePassword")
    Call<StatuTitleMessageResponse> getUpdatePassword(@Header("Authorization") String token, @Body Password password);


    // Supervisor Work order

    @GET("workOrders/supervisor")
    Call<List<WorkOrderResponse>> getSupervisorWorkOrder(@Header("Authorization") String token, @Query("partyId") int partyId);
    // Operator Work order

    @GET("workOrders/operator")
    Call<List<WorkOrderResponse>> getOperatorWorkOrder(@Header("Authorization") String token, @Query("partyId") int partyId);

    @GET("lookup/WO_STATUS")
    Call<ResponseBody> getWorkOrderStatus();

    @GET("lookup/getWorkorderStatusByRole/{partyId}/ORDER")
    Call<ResponseBody> getWorkOrderStatusByRole(@Path("partyId") int partyId);

    @GET("lookup/getWorkorderStatusByRole/{partyId}/OFFER")
    Call<ResponseBody> getWorkOrderStatusByRoleOffer(@Path("partyId") int partyId);

    // Daily logs
    @GET("activityLog/{workOrderId}/{partyId}")
    Call<List<DailyLogsSupervisor>> getDailyLogs(@Header("Authorization") String token, @Path("workOrderId") int workOrderId, @Path("partyId") int partyId);

    @POST("activityLog")
    Call<StatuTitleMessageResponse> addDailyLog(@Header("Authorization") String token, @Body AddDailyLog addDailyLog);

    @GET("lookup/{LookupType}")
    Call<ResponseBody> getDailyLogsStatus(@Path("LookupType") String remarkType);

    @POST("activityLog/editLog/")
    Call<StatuTitleMessageResponse> editDailyLog(@Header("Authorization") String token, @Body UpdateDailyLogs updateDailyLogs);


    //Machine Model for Operator
    //get
    @GET("user/operatorMachine/{PartyID}")
    Call<List<AbleToRunMachine>> getAbleToRunMachineDetails(@Header("Authorization") String token, @Path("PartyID") int partyId);
    //save
    @POST("user/saveOperatorMachine")
    Call<StatuTitleMessageResponse> addOperatorMachineModel(@Header("Authorization") String token, @Body AddMachineModel addMachineModel);
    //delete
    @DELETE("user/operatorModel/{OPERATOR_MACHINE_ID}")
    Call<StatuTitleMessageResponse> deleteOperatorMachineModel(@Header("Authorization") String token,@Path("OPERATOR_MACHINE_ID") int OperatorMachineId);

    //Get All Add Machine Model Data

    @GET("lookup/userSegments/{PartyId}/PREFERED_SEGMENT/USERSEG")
    Call<ResponseBody> getPreferedFilteredSegment(@Path("PartyId") int partyId);

    @GET("lookup/PREFERED_SEGMENT/{SEGMENT}")
    Call<ResponseBody> getSegmentCategory(@Path("SEGMENT") String segment);

    @GET("lookup/getModelData/{machineSegment}/{categoryCMB}/{subCategoryCMB}/{machineManufacturer}/{partyId}")
    Call<ResponseBody> getSegmentBaseCatSubManuModel(@Header("Authorization") String token,@Path("machineSegment") String segment,@Path("categoryCMB") String category,
                                                     @Path("subCategoryCMB") String subcategory,@Path("machineManufacturer") String manufacture,@Path("partyId") int partyId);


    @GET("lookup/CATEGORY/{Category}")
    Call<ResponseBody> getSegmentSubCategory(@Path("Category") String category);

    @GET("lookup/MANUFACTURER")
    Call<ResponseBody> getManufacturer();

    @GET("lookup/modelNoCombo/{segmentCode}/{categoryCode}/{subCategoryCode}/{manufacturerCode}/{partyId}")
    Call<ResponseBody> getModelNoCombo(@Path("segmentCode") String segmentCode,@Path("categoryCode")String categoryCode,@Path("subCategoryCode")String subCateCode,
                                          @Path("manufacturerCode") String manufacturerCode,@Path("partyId") int partyId);

    @GET("user/modelUrlData/{MachineModelId}")
    Call<String> getMachineUrl(@Header("Authorization") String token,@Path("MachineModelId") int modelId);


    //Notification
    @GET("notifications/{PartyId}/{Days}")
    Call<List<Notification>> getNotificationList(@Header("Authorization") String token, @Path("PartyId") int partyId,@Path("Days") int days);
    @GET("notifications/updateNotification/{PartyId}")
    Call<StatuTitleMessageResponse> updateNotificationFlag(@Header("Authorization") String token,@Path("PartyId") int partyId);

    @GET("notifications/getUnReadNotificationCount/{PartyId}")
    Call<String> getUnReadNotification(@Header("Authorization") String token,@Path("PartyId") int partyId);

    //Message
    @GET("user/messages/{PartyId}")
    Call<List<Message>> getUserMessages(@Header("Authorization") String token,@Path("PartyId") int partyId);

    @GET("user/getMessageHistory/{ToId}/{FromId}")
    Call<List<Message>> getMessagesHistory(@Header("Authorization") String token,@Path("ToId") int toId,@Path("FromId") int fromId);

    @GET("user/getUpdatedMessageHistory/{ToId}/{FromId}")
    Call<List<Message>> getUpdatedMessagesHistory(@Header("Authorization") String token,@Path("ToId") int toId,@Path("FromId") int fromId);

    @POST("user/sendMessages")
    Call<Message> sendMessage(@Header("Authorization") String token, @Body SendMessageModel sendMessageModel) ;


    @GET("user/getMessageCount/{PartyId}")
    Call<String> getUnReadMessage(@Header("Authorization") String token,@Path("PartyId") int partyId);


    //Update Profile
    @Multipart
    @POST("registration/updateUserDetails")
    Call<ResponseBody> updateProfileDetails(@Part MultipartBody.Part file,@Part("update_user_details") NewUser newUser);

    @GET("lookup/{LookupType}")
    Call<ResponseBody> getCommonLookUp(@Path("LookupType") String lookupType);
    @GET("lookup/{LookupType}")
    Call<ResponseBody> getReferTypeForAllExceptSuper(@Path("LookupType") String lookupType);
    @GET("lookup/{LookupType}/{LookupCode}")
    Call<ResponseBody> getGovermentIdCombo(@Path("LookupType") String lookupType,@Path("LookupCode") String lookupCode);


    @GET("user/getUserListByRole/{ROLE}")
    Call<ResponseBody> getUserListBaseOnRole(@Header("Authorization") String token,@Path("ROLE") String role);

    @GET("user/profile/{PartyId}")
    Call<ProfileData> getUserProfileAllData(@Header("Authorization") String token, @Path("PartyId") int userid);




    //invite Other

    @POST("user/invite")
    Call<StatuTitleMessageResponse> inviteOtherUser(@Header("Authorization") String token, @Body InviteOtherRequest inviteOtherRequest);

    //DashBoard API
    @GET("user/woInProgressCount/{PartyId}")
    Call<String> getWorkOrderInProgressCount(@Header("Authorization") String token,@Path("PartyId") int partyId);

    @GET("user/woCompletedCount/{PartyId}")
    Call<String> getWorkOrderInCompletedCount(@Header("Authorization") String token,@Path("PartyId") int partyId);

    @GET("user/hoursLoggedCount/{PartyId}")
    Call<String> getHoursLoggedCount(@Header("Authorization") String token,@Path("PartyId") int partyId);

    @GET("user/kmsMachineRunCount/{PartyId}")
    Call<String> getKmMachineRunCount(@Header("Authorization") String token,@Path("PartyId") int partyId);

    @GET("user/operatorMachienCount/{PartyId}")
    Call<List<OperatorMachineCount>> getOperatorMachineCount(@Header("Authorization") String token, @Path("PartyId") int partyId);

    @GET("user/machienCount/{PartyId}")
    Call<List<OperatorMachineCount>> getOwnerMachineCount(@Header("Authorization") String token, @Path("PartyId") int partyId);


    //Term and Condition
    @GET("terms/{PartyId}")
    Call<TermAndCondition> getTermAndCondition(@Header("Authorization") String token, @Path("PartyId") int partyId);

    //FeedBack
    @POST("feedback/createMachineFeedback")
    Call<StatuTitleMessageResponse> saveMachineFeedBack(@Header("Authorization") String token, @Body MachineFeedBack machineFeedBack);

    @POST("feedback/createOwnerFeedback")
    Call<StatuTitleMessageResponse> saveOwnerFeedBack(@Header("Authorization") String token, @Body OwnerFeedBack ownerFeedBack);

    //Comman look up
    @GET("lookup/OPERATOR_TYPE")
    Call<ResponseBody> getLookupForWorkAssociation();

    //Account Setting
    @POST("user/updateUserSettings")
    Call<StatuTitleMessageResponse> saveAccountSettingForUser(@Header("Authorization") String token, @Body AccountSettingModel accountSettingModel);


    //Renter Machine api;
     //All Machine For Renter
    @GET("machines")
    Call<List<RenterMachine>> getMachineForRenter(@Header("Authorization") String token, @QueryMap Map<String,String> params);

    //A machine Details
    @GET("machines/machineDetail")
    Call<RenterSpecificMachine> getSpecificMachineForRenter(@Header("Authorization") String token,@Query("machineId") int machineId,@Query("partyId") int partyId);
    //Get FeedbackFor owner
    @GET("feedback/getFeedback/{OwnerId}")
    Call<FeedbackForOwner> getFeedBackForOwner(@Header("Authorization") String token,@Path("OwnerId") int partyId);
   //BookMark Machine
    @POST("machines/favourite/{MachineId}")
    Call<StatuTitleMessageResponse> bookMarkFavoriteMachine(@Header("Authorization") String token,@Path("MachineId") int machineId,@Query("partyId") int partyId);

   //Rental plan
    @GET("plans/partyId/{PartyId}")
    Call<List<RentalPlan>> getAllPlanForMachine(@Header("Authorization") String token, @Path("PartyId") int partyId);

    //Term and condition flag
    @GET("terms/{PartyId}")
    Call<TypeOfTermAndCondition> getAllTypeOfTermAndCondition(@Header("Authorization") String token, @Path("PartyId") int partyId);

    // Get Attachments
    @GET("machines/machineAttachment/{MachineId}")
    Call<List<MachineAttachment>> getAllAttachmentForMachine(@Header("Authorization") String token,@Path("MachineId") int machineId);

    //Renter DashBoard

    @GET("user/woOfferCount/{PartyId}")
    Call<String> getWorkOrderOfferCount(@Header("Authorization") String token,@Path("PartyId") int partyId);

    @GET("user/invoiceCount/{PartyId}")
    Call<String> getInvoiceCount(@Header("Authorization") String token,@Path("PartyId") int partyId);

    @GET("user/paymentDueCount/{PartyId}")
    Call<String> getPaymentPendingCount(@Header("Authorization") String token,@Path("PartyId") int partyId);

    //Book Machine

    @POST("machines/bookMachine")
    Call<StatuTitleMessageResponse> bookMachineForRenter(@Header("Authorization") String token,@Body BookMachine bookMachine);

    @GET("machines/party/{PartyId}")
    Call<List<FavoriteMachine>> bookMarkForRenter(@Header("Authorization") String token, @Path("PartyId") int partyId);



    //Extend WorkoRder....

    @POST("workOrders/extendWorkOrder")
    Call<StatuTitleMessageResponse> extendworkOrder(@Header("Authorization") String token, @Body WorkOrderExtend workOrderExtend);

    // Cancel WorkOrder.....
    @POST("workOrders/cancelWorkOrder")
    Call<StatuTitleMessageResponse> cancelworkOrder(@Header("Authorization") String token, @Body WorkOrderCancel workOrderCancel);

    // Offer  for Renter

    @GET("workOrders/party/{PartyId}/{WoStatus}")
    Call<List<OfferWorkOrder>> getofferWorkOrderForRenter(@Header("Authorization") String token, @Path("PartyId") int partyId,@Path("WoStatus") String offer);

    //Payment for Renter
    @GET("payments/getInvoiceByUserId/{PartyId}")
    Call<List<InvoiceByUser>> getInvoiceByUserId(@Header("Authorization") String token, @Path("PartyId") int partyId);

    @GET("payments/ownerInvoice/{OwnerId}")
    Call<List<InvoiceByUser>> getOwnerInvoiceById(@Header("Authorization") String token, @Path("OwnerId") int ownerId);

    //get Payment by paymentId

    @GET("payments/getPayment/{PaymentId}")
    Call<PaymentByPID> getPaymentByPaymentId(@Header("Authorization") String token, @Path("PaymentId") int paymentId);

    // create Payment...
    //@POST("payments/createPayment")
   // Call<StatuTitleMessageResponse> createPayment(@Header("Authorization") String token, @Body InvoiceByUser invoiceCreate);

    @GET("invoices/ownerInvoice/{OwnerId}")
    Call<List<InvoiceByUser>> getInvoiceFromOwnerId(@Header("Authorization") String token, @Path("OwnerId") int OwnerId);



    //Owner Dashboard

    @GET("user/operatorCount/{PartyId}")
    Call<String> getOperatorCountForOwner(@Header("Authorization") String token,@Path("PartyId") int partyId);

    @GET("user/rentalPlanCount/{PartyId}")
    Call<String> getRentalPlanCountForOwner(@Header("Authorization") String token,@Path("PartyId") int partyId);


    //GET STATE

    @GET("lookup/getStateMap/IN")
    Call<ResponseBody> getStateOFIndiaList(@Header("Authorization") String token);
    //Get District
    @GET("lookup/getDistrictMapData/{stateCode}")
    Call<ResponseBody> getDistrictList(@Header("Authorization") String token,@Path("stateCode") String stateCode);
    // Get City
    @GET("lookup/getCityMapData/{distictCode}")
    Call<ResponseBody> getCityList(@Header("Authorization") String token,@Path("distictCode") String districCode);
    //Get Postal Code
    @GET("lookup/getPostalCodeMapData/{cityName}")
    Call<ResponseBody> getPostalCodeList(@Header("Authorization") String token,@Path("cityName") String cityName);

    // download Agreement

    @Streaming
    @GET("workOrders/downloadPDF/{workorderId}")
    Call<ResponseBody> downloadAgreementPDF(@Header("Authorization") String token,@Path("workorderId") int workOrderId);

    //Get All Attachment

    @GET("lookup/getAttachmentMap")
    Call<ResponseBody> getAllAttachmentMap();

    //Reject Extended WO

    @POST("workOrders/rejectExtendWo")
    Call<StatuTitleMessageResponse> rejectExtendedWO(@Header("Authorization") String token,@Body WorkOrderExtend workOrderExtend);


    //Create Feedback........


    @POST("feedback/getFeedbackData/{workorderId}/{partyId}")
    Call<StatuTitleMessageResponse> createFeedBack(@Header("Authorization") String token,@Path("partyId") int partyId,@Body CreateFeedback createFeedback);

    //Create Payment........

    @Multipart
    @POST("payments/createPayment")
    Call<StatuTitleMessageResponse> createPayment(@Header("Authorization") String token,@Part MultipartBody.Part supportImage,@Part("createPaymentDTO") RequestBody paymentDetails);


    // create invoice
    @Multipart
    @POST("invoices/")
    Call<StatuTitleMessageResponse> createInvoice(@Header("Authorization") String token,@Part MultipartBody.Part supportImage,@Part("createInvoiceDTO") RequestBody invoiceDetails);

    // get updated Workorder Details..

    @GET("workOrders/getPlanByWorkOrder/{workorderId}")
    Call<UpdatedWorkOrder> getUpdatedWorkOrderDetials(@Header("Authorization") String token, @Path("workorderId") int workOrderId);

    //get MAREOW charges.......


    @GET("lookup/getLookupMaster/{lookupType}")
    Call<List<MareowCharges>> getMareowCharges(@Header("Authorization") String token, @Path("lookupType") String lookupType);

   // get user Account setting ...

    @GET("user/getAccountSetting/{partyId}")
    Call<AccountSetting> getUserAccountingSetting(@Header("Authorization") String token, @Path("partyId") int partyId);

// Create rental Plan.........


    @POST("plans/")
    Call<StatuTitleMessageResponse> createRentalPlan(@Header("Authorization") String token,@Body CreateRentalPlan createRentalPlan);


    // Create Owner Machine...........
    @Multipart
    @POST("machines")
    Call<StatuTitleMessageResponse> createMachineForOwner(@Header("Authorization") String token,
                                                          @Part MultipartBody.Part[] machineImage,
                                                          @Part MultipartBody.Part RCbook,
                                                          @Part MultipartBody.Part PUCcertificate,
                                                          @Part MultipartBody.Part national_permit,
                                                          @Part MultipartBody.Part road_tax,
                                                          @Part("createMachineDTO") RequestBody machineDetails);

    // get Operator association...

    @GET("associations/{PartyId}")
    Call<List<AssociatedOperator>> getAssosicatedOperatorList(@Header("Authorization") String token, @Path("PartyId") int partyId);
  // get Independent Operator....
    @GET("associations/IndependentOpt/{ownerId}")
    Call<ResponseBody> getIndependentOperator(@Header("Authorization") String token, @Path("ownerId") int ownerId);
    // get associated Operator....
    @GET("associations/operators/{ownerId}")
    Call<ResponseBody> getAssociatedOperator(@Header("Authorization") String token, @Path("ownerId") int ownerId);
    //get operator Details...

    @GET("associations/getOwners/{operatorId}")
    Call<List<AssociatedOperator>> getOperatorDetailsAssociatedwithOwner(@Header("Authorization") String token, @Path("operatorId") int operatorId);

    // Create Operator Api............


    @POST("associations/createOperator")
    Call<StatuTitleMessageResponse> createAssociatedOperator(@Header("Authorization") String token, @Body CreateOperator createOperator);

    /// get operator Work order........
    @GET("workOrders/operator")
    Call<List<OperatorWorkOrder>> getSpecificOperatorWorkOrder(@Header("Authorization") String token, @Query("partyId") int partyId);

    //
    @GET("lookup/getCategoryListData/{LookupCode}")
    Call<List<CategoryImage>> getCategoryImages(@Path("LookupCode") String LookupCode);

    //get User saved Segment...

    @GET("lookup/userSegments/{PartyId}/PREFERED_SEGMENT/USERSEG")
    Call<ResponseBody> getUserPerferedSegment(@Path("PartyId") int partyId);


    //get multiple combo...
    @GET("lookup/getMulticomboData/{LookupType}/{LookupCode}")
    Call<ResponseBody> getMultipleSegmentBaseCategory(@Path("LookupType") String LookupType,@Path("LookupCode") String lookupCode);

    // get All Manufacturer.....
    @GET("lookup/machineManufacturer")
    Call<List<CommonManufacuter>> getCommonManufacture();

    // Get Booking Reason....
    @GET("lookup/machineBook/MACHINE_BOOK")
    Call<ResponseBody> getBookingReason();

    //get Plan by planId
    @GET("plans/{PlanId}")
    Call<RentalPlan> getRentalPlanById();
    //Accept workOrder.......
    @POST("workOrders/acceptWorkOrder")
    Call<StatuTitleMessageResponse> acceptworkOrderApibyOwner(@Header("Authorization") String token, @Body AcceptWorkOrder acceptWorkOrder);

    @POST("workOrders/orderWorkOrder")
    Call<StatuTitleMessageResponse> acceptWorkOrderByRenter(@Header("Authorization") String token, @Body AcceptWorkOrder acceptWorkOrder);

    // Reject workOrder....
    @POST("workOrders/rejectWorkOrder/{WorkOrderId}")
    Call<StatuTitleMessageResponse> rejectWorkOrderApi(@Header("Authorization") String token, @Path("WorkOrderId") int workoderId);

   //get Payment status...
   @GET("lookup/INVOICE_STATUS")
   Call<ResponseBody> getInvoiceStatus();

   //get Bank getails owner...
   @GET("user/bankDetail/{PartyId}")
   Call<ProfileData> getOwnerBankDetails(@Header("Authorization") String token, @Path("PartyId") int partyId);

   //Download Invoice.....

    @Streaming
    @GET("invoices/downloadInvoicePDF/{invoiceId}")
    Call<ResponseBody> downloadInvoicePDF(@Header("Authorization") String token,@Path("invoiceId") int invoiceId);
/// .....download payment.........receipt....

    @Streaming
    @GET("payments/downloadReceiptPDF/{paymentId}")
    Call<ResponseBody> downloadPaymentPDF(@Header("Authorization") String token,@Path("paymentId") int paymentId);

    //get Fuel count...
    @GET("user/fuelCount/{partyId}")
    Call<String> getFuelCount(@Header("Authorization") String token,@Path("partyId") int partyId);

    // get Operator associated owner name...
    @GET("user/assoOwnerName/{partyId}")
    Call<String> getAssociatedOwnerName(@Header("Authorization") String token,@Path("partyId") int partyId);

    // get Operator associated owner name...
    @GET("invoices/invoiceCombo/{WorkOrdderId}/{partyId}")
    Call<ResponseBody> getInvoiceByWorkorderId(@Path("WorkOrdderId") int workorderId,@Path("partyId") int partyId);
    // get Operator count
    @GET("user/operatorCount/{PartyId}")
    Call<String> getOperatorCount(@Header("Authorization") String token,@Path("PartyId") int partyId);
    // get Rental Plan Count
    @GET("user/rentalPlanCount/{PartyId}")
    Call<String> getRentalPlanCount(@Header("Authorization") String token,@Path("PartyId") int partyId);

    @GET("user/bookmarkMachineCount/{partyId}")
    Call<String> getBookmarkMachineCount(@Header("Authorization") String token,@Path("partyId") int partyId);

    @GET("user/paymentAcknowledgeCount/{partyId}")
    Call<String> getpaymentAcknowlegemntCount(@Header("Authorization") String token,@Path("partyId") int partyId);


    // Delete Owner Machine......

    @DELETE("machines/{MachineId}")
    Call<StatuTitleMessageResponse> deleteOwnerMachine(@Header("Authorization") String token, @Path("MachineId") int machineId);

   // Delete Owner Plan.........

    @DELETE("plans/{PlanId}")
    Call<StatuTitleMessageResponse> deleteOwnerPlan(@Header("Authorization") String token, @Path("PlanId") int planId);

    // Update Plan for Owner Temp;;;;

    @POST("plans/updatePlan/{PlanId}")
    Call<StatuTitleMessageResponse> updateOwnerRentalPlan(@Header("Authorization") String token,@Body CreateRentalPlan createRentalPlan, @Path("PlanId") int planId);

    @POST("associations/deAssoRequest/{party_operator_Id}")
    Call<StatuTitleMessageResponse> ownerDeassociationwithOperator(@Header("Authorization") String token,@Path("party_operator_Id") int opearatorId);


    ///  supervisor list for Workoffer ....

    @GET("workOrders/getSupervisor")
    Call<ResponseBody> getWorkOfferSupervisors(@Header("Authorization") String token);

    /// Operator list for Work offer ........

    @GET("workOrders/getPlanOperator/{WorkOrderId}")
    Call<ResponseBody> getWorkOfferOperators(@Header("Authorization") String token,@Path("WorkOrderId") int workorderId);

    /// Delete Invoice

    @DELETE("invoices/{InvoiceId}")
    Call<StatuTitleMessageResponse> deleteInvoiceById(@Header("Authorization") String token, @Path("InvoiceId") int invoiceId);

    // updates Terms And condition...

    @PUT("terms/updateTerms/{PartyId}")
    Call<StatuTitleMessageResponse> updatesTermsAndCondition(@Header("Authorization") String token,@Path("PartyId") int partyId,@Body TypeOfTermAndCondition termAndCondition);

   /// get Invoice By Invoice Id...
    @GET("invoices/{InvoiceId}")
    Call<InvoiceByInvoiceId> getInvoiceByInvoiceId(@Header("Authorization") String token, @Path("InvoiceId") int invoiceId);

   // Contact Owner

    @POST("machines/contactOwner")
    Call<StatuTitleMessageResponse> contactOwnerByRenter(@Header("Authorization") String token, @Body ContactOwner contactOwner);

    // Acknowlegement....of payment by owner......

    @POST("payments/updatePayment/{PaymentId}/{partyId}")
    Call<StatuTitleMessageResponse> acknowledgementByOwnerForPayment(@Header("Authorization") String token,@Path("PaymentId") int paymentId,@Path("partyId") int partyId);

    @GET("plans/{PlanId}")
    Call<PlanById> getRentalPlanById(@Header("Authorization") String token, @Path("PlanId") int planId);

    @GET("machines/{MachineId}")
    Call<UpdateMachine> getMachineDetailsForById(@Header("Authorization") String token, @Path("MachineId") int machineId);

    @GET("workOrders/getRenterWorkOrderDetail/{workOrderId}")
    Call<RenterWorkOrder> getWorkOrderEsign(@Path("workOrderId") int workOrderId);

    @GET("workOrders/getCancelWorkorder/{workorderId}")
    Call<WorkOrderCancel> getDetailsOfCancelWorkorder(@Header("Authorization") String token,@Path("workorderId") int workOrderId);

    @GET("workOrders/getExtendWorkOrder/{workorderId}")
    Call<WorkOrderExtend> getDetailsOfExtendWorkorder(@Header("Authorization") String token,@Path("workorderId") int workOrderId);






}
