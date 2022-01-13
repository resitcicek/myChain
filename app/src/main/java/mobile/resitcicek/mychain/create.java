package mobile.resitcicek.mychain;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link create#newInstance} factory method to
 * create an instance of this fragment.
 */
public class create extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText chainname;
    private EditText description;
    private CheckBox reminder;
    private CheckBox priv;
    private Button submit;

    public create() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment create.
     */
    // TODO: Rename and change types and number of parameters
    public static create newInstance(String param1, String param2) {
        create fragment = new create();
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

        View rootView = inflater.inflate(R.layout.fragment_create,
                container, false);

        chainname = rootView.findViewById(R.id.chainname);
        description = rootView.findViewById(R.id.description);
        reminder = rootView.findViewById(R.id.reminder);
        submit = (Button) rootView.findViewById(R.id.createbtn);
        String[] categories ={"Select a category","Hobby","Sport","Education","Behavior"};
        Spinner category = (Spinner) rootView.findViewById(R.id.category);
        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (chainname.getText().length() > 0 && description.getText().length() > 0 && category.getSelectedItem().toString() != "Select a category") {


                    DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
                    long checkinsert = databaseHelper.InsertChain(chainname.getText().toString(), description.getText().toString(), reminder.isChecked() ? 1:0, category.getSelectedItem().toString());
                    if (checkinsert != -1) {
                        long checkRelation = databaseHelper.InsertRelation(MainActivity.loggedUser.getID(), (int)checkinsert);
                        if (checkinsert != -1) {
                            Toast.makeText(getActivity().getApplicationContext(), "Chain created!", Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new HomeFragment()).commit();
                            MainActivity.loggedUser.addChainNum();
                        }
                        else {
                            Toast.makeText(getActivity().getApplicationContext(), "Chain relation error!", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Something is wrong.", Toast.LENGTH_SHORT).show();
                    }




                } else {
                    String toastMessage = "Please fill all blanks.";
                    Toast.makeText(getActivity().getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }


}