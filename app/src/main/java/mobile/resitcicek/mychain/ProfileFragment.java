package mobile.resitcicek.mychain;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView user = (TextView) view.findViewById(R.id.user);
        TextView desc = (TextView) view.findViewById(R.id.desc);
        TextView rank = (TextView) view.findViewById(R.id.rank);
        Button twitterBtn = (Button) view.findViewById(R.id.twitter);
        Button instaBtn = (Button) view.findViewById(R.id.instagram);
        TextView chainNum = (TextView) view.findViewById(R.id.chainNum);
        user.setText(MainActivity.loggedUser.getUsername());
        desc.setText(MainActivity.loggedUser.getBio());
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        int cNum = databaseHelper.getChainNumber(MainActivity.loggedUser.getID());
        if(cNum<=5) {
            rank.setText("Bronze Member");
            rank.setTextColor(Color.parseColor("#CD7F32"));
        }
        else if(cNum>5 && cNum<=10) {//lol
            rank.setText("Silver Member");
            rank.setTextColor(Color.parseColor("#999a98"));//
        }
        else if(cNum<=15 && cNum>10) {
            rank.setText("Gold Member");
            rank.setTextColor(Color.parseColor("#FFDF00"));
        }else {
            rank.setText("VIP Member");
            rank.setTextColor(Color.parseColor("#702963"));
        }

        chainNum.setText(Integer.toString(cNum)+" Chains");
        twitterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twitter.com/"+MainActivity.loggedUser.getTwitter()));
                startActivity(browserIntent);
            }
        });
        return view;
    }
}