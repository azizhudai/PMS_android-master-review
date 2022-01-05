package com.karatascompany.pys3318.remote;

import static com.karatascompany.pys3318.LoginActivity.IpUrl;

/**
 * Created by azizmahmud on 25.2.2018.
 */

public class ApiUtils {

    public static final String BASE_URL = "http://azizkaratas.com/Services/Service1.svc/"; //"https://vmi474601.contaboserver.net/api/auth/";//"http://azizkaratas.com/Services/Service1.svc/";//"http://"+IpUrl+"/servis17/Service1.svc/";//192.168.1.2/servis5/Service1.svc/

    public static UserService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);

    }
   /* public static UserService getUserProject(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }*/
}
