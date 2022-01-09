package mobile.resitcicek.mychain;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

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
        //DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ImageView medal = (ImageView) rowView.findViewById(R.id.searchmedal);
        ImageView profile = (ImageView) rowView.findViewById(R.id.profile);
        TextView username = (TextView) rowView.findViewById(R.id.searchname);
        User user = userList.get(position);
        username.setText(user.getUsername());
        int cNum = user.getChainNum();
        if(cNum<=3) {
            medal.setImageResource(R.mipmap.bronze_foreground);
        }
        else if(cNum>3 && cNum<=5) {//lol
            medal.setImageResource(R.mipmap.silver_foreground);
        }
        else if(cNum<=7 && cNum>5) {
            medal.setImageResource(R.mipmap.gold_foreground);
        }else {
            medal.setImageResource(R.mipmap.plat_foreground);
        }
        return rowView;
    }
}
