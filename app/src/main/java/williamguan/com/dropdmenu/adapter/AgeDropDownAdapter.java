package williamguan.com.dropdmenu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import williamguan.com.dropdmenu.entity.AgePackage;
import williamguan.com.dropdmenu.R;


public class AgeDropDownAdapter extends BaseAdapter {

    private Context context;
    private List<AgePackage> ageEntityList;

    public AgeDropDownAdapter(Context context, List<AgePackage> ageEntityList) {
        this.context = context;
        this.ageEntityList = ageEntityList;
    }

    @Override
    public int getCount() {
        return ageEntityList.size();
    }

    @Override
    public Object getItem(int position) {
        return ageEntityList.get(position);
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
        viewHolder.mText.setText(ageEntityList.get(position).getAgeText());
    }

    static class ViewHolder {
        TextView mText;

        ViewHolder(View view) {
            mText = (TextView) view.findViewById(R.id.text);
        }
    }
}
