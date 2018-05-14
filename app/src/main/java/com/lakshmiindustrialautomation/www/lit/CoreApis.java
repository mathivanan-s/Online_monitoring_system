package com.lakshmiindustrialautomation.www.lit;

import com.lakshmiindustrialautomation.www.lit.current_status.MacRunningStatusResponse;
import com.lakshmiindustrialautomation.www.lit.month_chart.MonthWiseChartResponse;
import com.lakshmiindustrialautomation.www.lit.production_report.ProductionReportResponse;
import com.lakshmiindustrialautomation.www.lit.singlesheetview.SingleSheetResponse;
import com.lakshmiindustrialautomation.www.lit.stop_chart.StopChartResponse;
import com.lakshmiindustrialautomation.www.lit.year_chart.YearChartDetailResponse;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Android on 5/2/2017.
 */

public interface CoreApis {
    @FormUrlEncoded
    @POST("/knitapp/login/loginform")
    public void getUserDetails(@Field("logname") String username,
                               @Field("logpassword") String password,
                               Callback<AuthenticationResponse> response);
    @FormUrlEncoded
    @POST("/api/v1/store/")
    public void updateDeviceDetails(@Field("device_id") String device_id,
                                    Callback<DeviceRegisterResponse> response);
    //main page live activity
    @GET("/knitapp/user/homepage")
    public void liveupdateDetails(Callback<LiveStatusResponse> response);

    //chart
    @GET("/knitapp/user/monthchart/{url}")
    public void monthwisechartDetails(@Path("url") String url,
                                      Callback<MonthWiseChartResponse> response);

    //machine wise month chart Details

    @GET("/knitapp/user/monthchart/{url}/{a_url}")
    public void machinewisemonthchartDetails(@Path("url") String url,
                                             @Path("a_url") String a_url,
                                             Callback<MonthWiseChartResponse> response);

    @GET("/knitapp/user/yearchart")
    public void yearchartDetails(Callback<YearChartDetailResponse> response);

    @GET("/knitapp/user/stopchart")
    public void stopchartDetails(Callback<StopChartResponse> response);

    //current mac report table

    @GET("/knitapp/user/singlesheetdataforapp")
    public void getsinglesheet(Callback<SingleSheetResponse> response);

    @GET("/knitapp/user/runningmachine/{url}")
    public void currentstatusreport(@Path("url") String url,
                                    Callback<MacRunningStatusResponse> response);

    //report
    @GET("/knitapp/user/prodreportprintapp")
    public void productionreport(Callback<ProductionReportResponse> response);


    @GET("/knitapp/user/prodreportprintapp/{url}/{a_url}")
    public void datewiseproductionreport(@Path("url") String url,
                                         @Path("a_url") String a_url,
                                         Callback<ProductionReportResponse> response);




}
