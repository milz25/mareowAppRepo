package com.mareow.recaptchademo.Utils;

import android.util.Log;

import com.mareow.recaptchademo.DataModels.AbleToRunMachine;
import com.mareow.recaptchademo.DataModels.DailyLogsSupervisor;
import com.mareow.recaptchademo.DataModels.Notification;
import com.mareow.recaptchademo.DataModels.WorkOrderMachine;
import com.mareow.recaptchademo.DataModels.WorkOrderResponse;
import com.mareow.recaptchademo.MainActivityFragments.WorkOrderFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class parseWorkOrderData {


    public static void parseWorkorderDetailsData(JSONArray jsonArray){

        for (int i=0;i<jsonArray.length();i++){

            try {
                JSONObject jsonObject=jsonArray.getJSONObject(i);

                WorkOrderResponse workOrderResponse=new WorkOrderResponse();
                WorkOrderMachine workOrderMachine=new WorkOrderMachine();

                int workordeIs=jsonObject.getInt("workOrderId");
                workOrderResponse.setWorkOrderId(jsonObject.getInt("workOrderId"));
                String workorderno=jsonObject.getString("workOrderNo");
                workOrderResponse.setWorkOrderNo(jsonObject.getString("workOrderNo"));
                String workStartDate=jsonObject.getString("workStartDate");
                workOrderResponse.setWorkStartDate(jsonObject.getString("workStartDate"));
                String workEndDate=jsonObject.getString("workEndDate");
                workOrderResponse.setWorkEndDate(jsonObject.getString("workEndDate"));
                String workOrderStatus=jsonObject.getString("workOrderStatus");
                workOrderResponse.setWorkOrderStatus(jsonObject.getString("workOrderStatus"));
                String operatorStartDate=jsonObject.getString("operatorStartDate");
                workOrderResponse.setOperatorStartDate(jsonObject.getString("operatorStartDate"));
                String operatorEndDate=jsonObject.getString("operatorEndDate");
                workOrderResponse.setOperatorEndDate(jsonObject.getString("operatorEndDate"));
                String operatorDays=jsonObject.getString("operatorDays");
                workOrderResponse.setOperatorDays(jsonObject.getInt("operatorDays"));
                String operatorHours=jsonObject.getString("operatorHours");
                workOrderResponse.setOperatorHours(jsonObject.getInt("operatorHours"));
                String siteLocation=jsonObject.getString("siteLocation");
                workOrderResponse.setSiteLocation(jsonObject.getString("siteLocation"));
                workOrderResponse.setStatusCode(jsonObject.getString("statusCode"));

                workOrderResponse.setPlanDays(jsonObject.getInt("planDays"));
                int planHours=jsonObject.getInt("planHours");
                workOrderResponse.setPlanHours(jsonObject.getInt("planHours"));


                JSONObject machineJsonObject=jsonObject.getJSONObject("machine");

                workOrderMachine.setMachineId(machineJsonObject.getInt("machineId"));
                workOrderMachine.setMachineName(machineJsonObject.getString("machineName"));
                workOrderMachine.setCategoryName(machineJsonObject.getString("categoryName"));
                workOrderMachine.setSubCategoryName(machineJsonObject.getString("subCategoryName"));
                workOrderMachine.setModelNo(machineJsonObject.getString("modelNo"));


                WorkOrderFragment.workOrderList.add(workOrderResponse);
                WorkOrderFragment.workOrderMachineList.add(workOrderMachine);

            } catch (JSONException e) {
                e.printStackTrace();
                String error=e.getMessage();
                Log.e("MainActivity",e.getMessage());
            }

        }
    }


     public static List<DailyLogsSupervisor> parseDailyLogsData(JSONArray jsonArray){

        List<DailyLogsSupervisor> dailyLogsList=new ArrayList<>();
        for (int i=0;i<jsonArray.length();i++){

            try {

                JSONObject jsonObject=jsonArray.getJSONObject(i);

                DailyLogsSupervisor dailyLogsSupervisor=new DailyLogsSupervisor();

                dailyLogsSupervisor.setActivityLogId(jsonObject.getInt("activityLogId"));
                dailyLogsSupervisor.setWorkOrderId(jsonObject.getInt("workOrderId"));
                dailyLogsSupervisor.setMachineId(jsonObject.getInt("machineId"));
                dailyLogsSupervisor.setPartyId(jsonObject.getInt("partyId"));

                dailyLogsSupervisor.setLogDate(jsonObject.getString("logDate"));
                dailyLogsSupervisor.setLogDateStr(jsonObject.getString("logDateStr"));

                dailyLogsSupervisor.setLogStartTime(jsonObject.getString("logStartTime"));
                dailyLogsSupervisor.setLogStartTimeStr(jsonObject.getString("logStartTimeStr"));

                dailyLogsSupervisor.setLogEndTime(jsonObject.getString("logEndTime"));
                dailyLogsSupervisor.setLogEndTimeStr(jsonObject.getString("logEndTimeStr"));

                dailyLogsSupervisor.setLogHours(jsonObject.getInt("logHours"));

                dailyLogsSupervisor.setStartKms(jsonObject.getInt("startKms"));
                dailyLogsSupervisor.setEndKms(jsonObject.getInt("endKms"));
                dailyLogsSupervisor.setNoOfKms(jsonObject.getInt("noOfKms"));

                dailyLogsSupervisor.setFuel(jsonObject.getInt("fuel"));
                dailyLogsSupervisor.setRemarkType(jsonObject.getString("remarkType"));
                dailyLogsSupervisor.setRemarkDesc(jsonObject.getString("remarkDesc"));
                dailyLogsSupervisor.setWoStartDate(jsonObject.getString("woStartDate"));
                dailyLogsSupervisor.setWoEndDate(jsonObject.getString("woEndDate"));



                dailyLogsList.add(dailyLogsSupervisor);


            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

        }

        return dailyLogsList;
    }


    public static List<AbleToRunMachine> parseAbleToRunMachine(JSONArray jsonArray){

        List<AbleToRunMachine> ableToRunMachines=new ArrayList<>();
        for (int i=0;i<jsonArray.length();i++){

            try {
                JSONObject jsonObject=jsonArray.getJSONObject(i);

                AbleToRunMachine ableToRunMachine=new AbleToRunMachine();

                ableToRunMachine.setOperatorMachineId(jsonObject.getInt("operatorMachineId"));
                ableToRunMachine.setPartyId(jsonObject.getInt("partyId"));
                ableToRunMachine.setCreatedBy(jsonObject.getString("createdBy"));
                ableToRunMachine.setCreatedDate(jsonObject.getString("createdDate"));
                ableToRunMachine.setCatagoryMeaning(jsonObject.getString("catagoryMeaning"));
                ableToRunMachine.setSubCategoryMeaning(jsonObject.getString("subCategoryMeaning"));
                ableToRunMachine.setManufacturerMeaning(jsonObject.getString("manufacturerMeaning"));
                ableToRunMachine.setModelNo(jsonObject.getString("modelNo"));
                ableToRunMachine.setMachineModelId(jsonObject.getInt("machineModelId"));
                ableToRunMachine.setModelName(jsonObject.getString("modelName"));
                ableToRunMachine.setUrl(jsonObject.getString("url"));


                ableToRunMachines.add(ableToRunMachine);

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

        }
        return ableToRunMachines;
    }

    public static List<Notification> parseNotificationArray(JSONArray jsonArray){

        List<Notification> notifications=new ArrayList<>();
        for (int i=0;i<jsonArray.length();i++){

            try {
                JSONObject jsonObject=jsonArray.getJSONObject(i);

                Notification notification=new Notification();

                notification.setUserId(jsonObject.getInt("userId"));
                notification.setMessage(jsonObject.getString("message"));
                notification.setTime(jsonObject.getString("time"));
                notification.setUserName(jsonObject.getString("userName"));

                notifications.add(notification);

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }

        }
        return notifications;
    }



}
