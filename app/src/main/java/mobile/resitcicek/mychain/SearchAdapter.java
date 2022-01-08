package mobile.resitcicek.mychain;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SearchAdapter extends BaseAdapter {
    List<User> userList;
    private LayoutInflater inflater;
    public SearchAdapter (Activity activity, List<User> userList){
        this.userList = userList;
        inflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View rowView;
        rowView = inflater.inflate(R.layout.searchrow,null);
        ImageView medal = (ImageView) rowView.findViewById(R.id.searchmedal);
        ImageView profile = (ImageView) rowView.findViewById(R.id.profile);
        TextView username = (TextView) rowView.findViewById(R.id.searchname);

        return null;
    }
}
