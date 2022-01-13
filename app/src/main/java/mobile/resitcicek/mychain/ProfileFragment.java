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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

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
    private static int userID;
    public static User userInfo = new User();
    public Fragment fragment = null;

    public ProfileFragment(User user) {
        // Required empty public constructor
        //DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        this.userInfo = user;
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
        ProfileFragment fragment = new ProfileFragment(userInfo);
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
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        TextView user = (TextView) view.findViewById(R.id.user);
        ImageView medal = (ImageView) view.findViewById(R.id.symbole);
        TextView desc = (TextView) view.findViewById(R.id.desc);
        TextView rank = (TextView) view.findViewById(R.id.rank);
        Button twitterBtn = (Button) view.findViewById(R.id.twitter);
        Button instaBtn = (Button) view.findViewById(R.id.instagram);
        TextView chainNum = (TextView) view.findViewById(R.id.chainNum);
        ListView listView = (ListView) view.findViewById(R.id.profilelist);
        ArrayList<Chain> chains = databaseHelper.getChains(userInfo.getID());
        ChainAdapter adapter = new ChainAdapter(getActivity(), chains);
        listView.setAdapter(adapter);
        Button edit = (Button) view.findViewById(R.id.editProf);
        if(userInfo.getID() != MainActivity.loggedUser.getID()) edit.setVisibility(view.GONE);
        user.setText(userInfo.getUsername());
        desc.setText(userInfo.getBio());

        int cNum = databaseHelper.getChainNumber(userInfo.getID());
        if(cNum<=3) {
            rank.setText("Bronze");
            medal.setImageResource(R.mipmap.bronze_foreground);

        }
        else if(cNum>3 && cNum<=5) {//lol
            rank.setText("Silver");
            medal.setImageResource(R.mipmap.silver_foreground);
        }
        else if(cNum<=7 && cNum>5) {
            rank.setText("Gold");
            medal.setImageResource(R.mipmap.gold_foreground);

        }else {
            rank.setText("VIP");
            medal.setImageResource(R.mipmap.plat_foreground);

        }

        chainNum.setText(Integer.toString(cNum)+" Chains");
        twitterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userInfo.getTwitter() != "") {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.twitter.com/" + userInfo.getTwitter()));
                    startActivity(browserIntent);
                }
            }
        });
        instaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userInfo.getInsta() != "") {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.instagram.com/" + userInfo.getInsta()));
                    startActivity(browserIntent);
                }
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new EditProfile();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, fragment).commit();
            }
        });
        return view;
    }
}