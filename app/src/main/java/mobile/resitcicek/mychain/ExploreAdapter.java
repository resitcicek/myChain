package mobile.resitcicek.mychain;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class ExploreAdapter  extends BaseAdapter {
    List<Chain> chains;
    private LayoutInflater inflater;
    public ExploreAdapter(Activity activity, List<Chain> chains){
        this.chains = chains;
        inflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }
    public int getCount() {
        return chains.size();
    }
    public Object getItem(int position) {
        return chains.get(position);
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View view, ViewGroup viewGroup) {
        View rowView;
        rowView = inflater.inflate(R.layout.row,null);
        TextView txtName = (TextView) rowView.findViewById(R.id.rowname);
        TextView txtDesc = (TextView) rowView.findViewById(R.id.rowdesc);
        TextView txtDuration = (TextView) rowView.findViewById(R.id.rowdur);
        TextView txtCategory = (TextView) rowView.findViewById(R.id.rowdur);
        Chain chain = chains.get(position);
        txtName.setText(chain.getName());
        txtDesc.setText(chain.getDescription());
        txtDuration.setText(chain.getDuration());
        txtCategory.setText(chain.getCategory());
        return rowView;
    }
}
