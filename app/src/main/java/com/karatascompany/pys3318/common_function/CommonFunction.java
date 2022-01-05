package com.karatascompany.pys3318.common_function;

import com.karatascompany.pys3318.firebase.PushNotifHttp;
import com.karatascompany.pys3318.models.GetUserTokenModel;
import com.karatascompany.pys3318.remote.ApiUtils;
import com.karatascompany.pys3318.remote.UserService;
import com.karatascompany.pys3318.session.Session;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by azizmahmud on 3.4.2018.
 */

public class CommonFunction {

    private static UserService userService;
  //  private static boolean status = false;
    private static String tokenId;
  //  private static Session session;

   /* public static void ControlUser(String userId){
        if(userId)
    }*/

    public static void GetUserTokenId(String userId, final String sendUserId, final String data){


        userService = ApiUtils.getUserService();
        Call<GetUserTokenModel> call = userService.GetUserTokenId(userId);
        call.enqueue(new Callback<GetUserTokenModel>() {
            @Override
            public void onResponse(Call<GetUserTokenModel> call, Response<GetUserTokenModel> response) {
                try {
                    GetUserTokenModel userToken = response.body();
                   //  if(userToken.getTokenId().equals("0")){
                         tokenId = userToken.getTokenId();
                    if(tokenId != "0" && !tokenId.isEmpty() && tokenId != null){
                        PushNotifHttp not2 =new PushNotifHttp(); //taskName.substring(0,40)
                        not2.execute(tokenId, "Proje Yönetimi",sendUserId+" kişisi tarafından ("+ data+")");
//Görev Güncelleme
                    }
                   //   status = true;
                    // }
                }catch (Exception e){
                 //   Toast.makeText(CommonFunction.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetUserTokenModel> call, Throwable t) {
                //Toast.makeText(CommonFunction.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        /*if(status == true){
            return tokenId;
        }else return "0";
*/
      //  return tokenId;
    }
}
