package mobile.resitcicek.mychain;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChainDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChainDetails extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public Chain chain;
    public ChainDetails(Chain chain) {
        this.chain = chain;
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChainDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static ChainDetails newInstance(String param1, String param2) {
        ChainDetails fragment = new ChainDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chain_details, container, false);
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        ArrayList<ImageView> imageArr = new ArrayList<ImageView>();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String date = Integer.toString(day)+"-"+Integer.toString(month)+"-"+Integer.toString(year);
        TextView chainName = (TextView) view.findViewById(R.id.cname);
        chainName.setText(chain.getName());
        int[] chArr = new int[] {R.id.chain1,R.id.chain2,R.id.chain3,R.id.chain4,R.id.chain5,R.id.chain6,R.id.chain7,R.id.chain8,R.id.chain9,R.id.chain10,R.id.chain11,R.id.chain12,R.id.chain13,R.id.chain14,R.id.chain15,R.id.chain16,R.id.chain17,R.id.chain18,R.id.chain19,R.id.chain20,R.id.chain21,R.id.chain22,R.id.chain23,R.id.chain24,R.id.chain25,R.id.chain26,R.id.chain27,R.id.chain28,R.id.chain29,R.id.chain30};
        for(int i=0; i<30; i++){
            imageArr.add(view.findViewById(chArr[i]));
            databaseHelper.isDone(chain.getID())
        }

        return inflater.inflate(R.layout.fragment_chain_details, container, false);
    }
}