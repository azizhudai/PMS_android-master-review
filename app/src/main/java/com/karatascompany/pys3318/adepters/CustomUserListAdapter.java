package com.karatascompany.pys3318.adepters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.karatascompany.pys3318.R;
import com.karatascompany.pys3318.models.UserFriendModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by azizmahmud on 19.3.2018.
 */

public class CustomUserListAdapter extends RecyclerView.Adapter<CustomUserListAdapter.ViewHolderUserFriendList>{

    private LayoutInflater layoutInflater;
    private static List<UserFriendModel> userFriendModelArrayList = new ArrayList<>();
    private CustomUserListAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(CustomUserListAdapter.OnItemClickListener listener){
        mListener = listener;
    }
    public void setUserList (ArrayList<UserFriendModel> userList){
        this.userFriendModelArrayList = userList;

        //finalicin finalicin11 = new finalicin(userFriendModelArrayList);

    }

    public final static List<UserFriendModel> getUserList (){
        return userFriendModelArrayList;

    }
    public CustomUserListAdapter(Context context){
        layoutInflater = LayoutInflater.from(context);
    }

   /* public List<UserFriendModel> getUserFriendList222() {
        return userFriendList222;
    }*/

    @Override
    public ViewHolderUserFriendList onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_user_list_layout,parent,false);
        CustomUserListAdapter.ViewHolderUserFriendList viewHolderUserFriendList = new ViewHolderUserFriendList(view);
        return viewHolderUserFriendList;
    }

    @Override
    public void onBindViewHolder(ViewHolderUserFriendList holder, int position) {

        UserFriendModel currentUserModel = userFriendModelArrayList.get(position);

        holder.checkedTextViewUserName.setText(currentUserModel.getUserMail());
        if (currentUserModel.getAppoint() == true){
            holder.checkedTextViewUserName.setCheckMarkDrawable(R.drawable.icons8_checkmark_30);
           // holder.checkedTextViewUserName.setChecked(true);
        }else {
            holder.checkedTextViewUserName.setCheckMarkDrawable(R.drawable.icons8_wired_network_24);

            //holder.checkedTextViewUserName.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return userFriendModelArrayList.size();
    }

    public class ViewHolderUserFriendList extends RecyclerView.ViewHolder{
        private CheckedTextView checkedTextViewUserName;

        public ViewHolderUserFriendList(View itemView){
            super(itemView);
            checkedTextViewUserName =  itemView.findViewById(R.id.checkedTextViewUserName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        mListener.onItemClick(pos);

                        UserFriendModel clickedItem = userFriendModelArrayList.get(pos);
                        if(clickedItem.getAppoint() == false){
                            clickedItem.setAppoint(true);

                            checkedTextViewUserName.setCheckMarkDrawable(R.drawable.icons8_checkmark_30);

                        }else {
                            clickedItem.setAppoint(false);
                            checkedTextViewUserName.setCheckMarkDrawable(R.drawable.icons8_wired_network_24);


                        }


                    }
                }
            });
        }
    }

    public class finalicin{
        private final List<UserFriendModel> userFriendList222;

        finalicin(List<UserFriendModel> aa){
            userFriendList222 = aa;

        }

    }
}
