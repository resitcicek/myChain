package mobile.resitcicek.mychain;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public Fragment fragment = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfile newInstance(String param1, String param2) {
        EditProfile fragment = new EditProfile();
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
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        TextView user = (TextView) view.findViewById(R.id.useredit);
        EditText desc = (EditText) view.findViewById(R.id.editDesc);
        EditText tw = (EditText) view.findViewById(R.id.editTw);
        EditText insta = (EditText) view.findViewById(R.id.editInsta);
        Button save = (Button) view.findViewById(R.id.SaveBtn);
        desc.setText(MainActivity.loggedUser.getBio());
        tw.setText(MainActivity.loggedUser.getTwitter());
        insta.setText(MainActivity.loggedUser.getInsta());
        user.setText(MainActivity.loggedUser.getUsername());
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
                boolean update = databaseHelper.Update(MainActivity.loggedUser.getID(),desc.getText().toString(),tw.getText().toString(),insta.getText().toString());
                if(update) {
                    MainActivity.loggedUser.setBio(desc.getText().toString());
                    MainActivity.loggedUser.setTwitter(tw.getText().toString());
                    MainActivity.loggedUser.setInsta(insta.getText().toString());
                    fragment = new ProfileFragment(MainActivity.loggedUser);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, fragment).commit();
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(), "Something is wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}