package williamguan.com.dropdmenu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import williamguan.com.dropdmenu.R;
import williamguan.com.dropdmenu.entity.UserEntity;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {


    List<UserEntity> userEntityList;

    public MyRecyclerAdapter(List<UserEntity> userEntityList) {
        this.userEntityList = userEntityList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item_layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_info, parent, false);
        return new ViewHolder(item_layout);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserEntity userEntity = userEntityList.get(position);
        holder.tv_user_name.setText(userEntity.getName());
        holder.tv_age.setText(String.valueOf(userEntity.getAge()));
        holder.tv_gender.setText(userEntity.getGender());
    }

    @Override
    public int getItemCount() {
        return userEntityList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_user_name;
        TextView tv_gender;
        TextView tv_age;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_user_name = (TextView) itemView.findViewById(R.id.tv_user_name);
            tv_gender = (TextView) itemView.findViewById(R.id.tv_gender);
            tv_age = (TextView) itemView.findViewById(R.id.tv_age);
        }
    }
}
