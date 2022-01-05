package com.karatascompany.pys3318;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.karatascompany.pys3318.fragments.GraphFragment;
import com.karatascompany.pys3318.fragments.MyProjectFragment;
import com.karatascompany.pys3318.fragments.MyTaskFragment;
import com.karatascompany.pys3318.fragments.NotificationFragment;
import com.karatascompany.pys3318.remote.ApiUtils;
import com.karatascompany.pys3318.remote.UserService;
import com.karatascompany.pys3318.session.Session;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarBadge;
import com.roughike.bottombar.OnMenuTabClickListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragmentActivity extends AppCompatActivity {

    private TextView mTextMessage;
    BottomBar mBottomBar;
    private Session session;
    //private FirebaseAuth mAuth;
    private UserService userService;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);

        session = new Session(this);
      //  mAuth = FirebaseAuth.getInstance();
        userService = ApiUtils.getUserService();

        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItemsFromMenu(R.menu.menu_main, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(int menuItemId) {
                if (menuItemId == R.id.bottombarItemgraph) {
                //    Bundle extras = getIntent().getExtras();
                    //    int userId = extras.getInt("userId");
                    //   String uid = String.valueOf(userId);
                        GraphFragment fr = new GraphFragment();

                  //  FragmentMainTabbedMyGraphsActivity frr = new FragmentMainTabbedMyGraphsActivity();

                   /* FragmentTransaction transaction = getSupportFragmentManager()
                            .beginTransaction();
                   // Fragment newFragment = new Fragment1();
                    transaction.add(R.id.frame, frr);
                    transaction.addToBackStack(null);
                    transaction.commit();//*/

                    //  bundle.putString("userId",uid);
                    //  fr.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, fr).commit();
                } else if (menuItemId == R.id.bottombarItemmyproject) {
                    MyProjectFragment fr = new MyProjectFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, fr).commit();
                } else if (menuItemId == R.id.bottombarItemtmytask) {
                    MyTaskFragment fr = new MyTaskFragment();

                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, fr).commit();
                } else if (menuItemId == R.id.bottombarItemnotification) {
                    NotificationFragment fr = new NotificationFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, fr).commit();
                }
            }

            @Override
            public void onMenuTabReSelected(int menuItemId) {
                if (menuItemId == R.id.bottombarItemgraph) {

                    FragmentMainTabbedMyGraphsActivity frr = new FragmentMainTabbedMyGraphsActivity();

                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, frr).commit();
                }
            }

        });
        mBottomBar.mapColorForTab(0, "#4DB6AC");
        mBottomBar.mapColorForTab(1, "#81D4FA");
        mBottomBar.mapColorForTab(2, "#7986CB");
        mBottomBar.mapColorForTab(3, "#FF7043");

        BottomBarBadge unread;
        unread = mBottomBar.makeBadgeForTabAt(3, "#EF5350", 13);


     /*   mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
   */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_fragment_main_setting, menu);

        /*MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search,menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = null;
        if(searchItem != null){
            searchView = (SearchView) searchItem.getActionView();
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
             //   customProjectAdepter.getFilter().filter(s);
                return true;
            }
        });

        super.onCreateOptionsMenu(menu);
*/


        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int i = item.getItemId();

        if(i == R.id.action_user_appointed_task_list){
            Intent intent = new Intent(MainFragmentActivity.this, UserAppointedTaskListActivity.class);
            //intent.putExtra("userMail", session.getUserMail());
            startActivity(intent);
        }
        if (i == R.id.action_user_setting) {
            Intent intent = new Intent(MainFragmentActivity.this, UserSettingActivity.class);
            intent.putExtra("userMail", session.getUserMail());
            startActivity(intent);
        }
        if(i== R.id.action_user_management_detail_count){
            Intent intent = new Intent(MainFragmentActivity.this, ManagementTotalCountActivity.class);
            //intent.putExtra
            startActivity(intent);
        }
        if (i == R.id.action_user_sign_out) {

          //  if (mAuth.getCurrentUser() != null) {
           // mAuth.signOut();

            Call<String> call = userService.RemoveTokenId(session.getUserId());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        try {
                            String result = response.body();
                            if(result.equals("OK")){
                                Toast.makeText(MainFragmentActivity.this, "Çıkış Başarılı", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainFragmentActivity.this, LoginActivity.class));

                            }else
                                Toast.makeText(MainFragmentActivity.this, "ERR", Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            Toast.makeText(MainFragmentActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(MainFragmentActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

           //    }
        }
 return false;
    }


}
