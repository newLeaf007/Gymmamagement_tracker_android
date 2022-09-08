package com.example.gymmamagement.remote;

public class AppUtils {

  //  public static final String base_url="http://192.168.1.2:5000";
    public static final String base_url="http://Fitnesssquare-env.eba-rrebm6px.us-west-1.elasticbeanstalk.com";

    public static UserService getUserService(){
        return RetrofitClient.getClient(base_url).create(UserService.class);
    }
}
