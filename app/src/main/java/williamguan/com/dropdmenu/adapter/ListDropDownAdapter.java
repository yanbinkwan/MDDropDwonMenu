package williamguan.com.dropdmenu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import williamguan.com.dropdmenu.R;


public class ListDropDownAdapter extends BaseAdapter {

    private Context context;
    private List<String> userEntityList;
    private int checkItemPosition = 0;

    public ListDropDownAdapter(Context context, List<String> userEntityList) {
        this.context = context;
        this.userEntityList = userEntityList;
    }

    public void setCheckItem(int position) {
        checkItemPosition = position;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return userEntityList.size();
    }

    @Override
    public Object getItem(int position) {
        return userEntityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_default_drop_down, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        fillValue(position, viewHolder);
        return convertView;
    }

    private void fillValue(int position, ViewHolder viewHolder) {
        viewHolder.mText.setText(userEntityList.get(position));
//        if (checkItemPosition != -1) {
//            if (checkItemPosition == position) {
//                viewHolder.mText.setTextColor(context.getResources().getColor(R.color.drop_down_selected));
//                viewHolder.mText.setBackgroundResource(R.color.check_bg);
//            } else {
//                viewHolder.mText.setTextColor(context.getResources().getColor(R.color.drop_down_unselected));
//                viewHolder.mText.setBackgroundResource(R.color.white);
//            }
//        }
    }

    static class ViewHolder {
        TextView mText;

        ViewHolder(View view) {
            mText = (TextView) view.findViewById(R.id.text);
        }
    }
}
