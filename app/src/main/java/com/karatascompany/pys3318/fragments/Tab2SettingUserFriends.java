package com.karatascompany.pys3318.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.karatascompany.pys3318.R;
import com.karatascompany.pys3318.SendActivity;
import com.karatascompany.pys3318.firebase_poco.Users;
import com.karatascompany.pys3318.models.UserFriendModel;
import com.karatascompany.pys3318.remote.ApiUtils;
import com.karatascompany.pys3318.remote.UserService;
import com.karatascompany.pys3318.session.Session;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by azizmahmud on 1.4.2018.
 */

public class Tab2SettingUserFriends extends Fragment {

    FirebaseFirestore firestore;
    private List<Users> usersList;
    Session session;
    UserService userService;
    ListView listViewUserFirends;
    private List<UserFriendModel> userFriendList;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2_setting_user_friends,container,false);

        firestore = FirebaseFirestore.getInstance();
        session = new Session(getActivity());
        userService = ApiUtils.getUserService();
        usersList = new ArrayList<>();
        listViewUserFirends = view.findViewById(R.id.listViewUserFirends);

       // ListViewDataInsert(session.getUserId());



        listViewUserFirends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), SendActivity.class);

                intent.putExtra("sendUserId", usersList.get(i).userId);
                intent.putExtra("sendUserMail",usersList.get(i).getName());
                //intent.putExtra("sendUserId",String.valueOf(userFriendList.get(i).getUserId()));
               // intent.putExtra("sendUserMail",userFriendList.get(i).getUserMail());
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        usersList.clear();
        firestore.collection("Users").addSnapshotListener(getActivity(),new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                for(DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){

                        String user_id = doc.getDocument().getId();
                      //  Map<String, Object> map = new HashMap<>();
                      //  map = doc.getDocument().getData();
                        Users users = doc.getDocument().toObject(Users.class).withId(user_id);
                    //    String aa = doc.getDocument().get("name").toString();
                        usersList.add(users);

                    }

                }
                final List<String> listTask = new ArrayList<>();
                for(int i=0;i<usersList.size();i++){
                    listTask.add( usersList.get(i).getName());

                }
                ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<>
                        (getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, listTask);

                //(C) adımı
                listViewUserFirends.setAdapter(veriAdaptoru);

            }
        });


    }

    private void ListViewDataInsert(String userId) {

        Call<List<UserFriendModel>> call = userService.GetUserFriend(userId,"4");
        call.enqueue(new Callback<List<UserFriendModel>>() {
            @Override
            public void onResponse(Call<List<UserFriendModel>> call, Response<List<UserFriendModel>> response) {

                try {
                        userFriendList = response.body();
                        final List<String> listTask = new ArrayList<>();
                        for(int i=0;i<userFriendList.size();i++){
                        listTask.add( userFriendList.get(i).getUserMail().toString());

                        }
                        ArrayAdapter<String> veriAdaptoru=new ArrayAdapter<String>
                            (getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, listTask);

                        //(C) adımı
                        listViewUserFirends.setAdapter(veriAdaptoru);

                    }catch (Exception e){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserFriendModel>> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
