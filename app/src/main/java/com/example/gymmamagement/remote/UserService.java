package com.example.gymmamagement.remote;

import com.example.gymmamagement.modals.Dashboard;
import com.example.gymmamagement.modals.Login;
import com.example.gymmamagement.modals.Member;
import com.example.gymmamagement.modals.MemberWithTotalAmount;
import com.example.gymmamagement.modals.Payment;
import com.example.gymmamagement.modals.PaymentMember;
import com.example.gymmamagement.modals.ResponseToken;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserService {

    @POST("/api/v1/auth/login")
    Call<ResponseToken> login(@Body Login login);

    @GET("/member")
    Call<List<Member>> getMember();

    @POST("/member")
    Call<Member> createMember(@Body Member member);

    @GET("/member/payment/all")
    Call<List<PaymentMember>> gettAllPayment();


    @POST("/payment/member/{memberId}")
    Call<Payment> createPayment(@Body Payment payment, @Path("memberId") Integer memberId);


    @Multipart
    @POST("/member/image/{memberId}")
    Call<Member> uploadImage(@Part MultipartBody.Part imageName, @Path("memberId") Integer memberId);


    @GET("/dashboard")
    Call<Dashboard> getDashBoard();


    @GET("/memberwithamount")
    Call<MemberWithTotalAmount>  getMemberWithAmount();




}
