package mobile.resitcicek.mychain;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<User> users = new ArrayList<User>();
    ListView listView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        //users = databaseHelper.Search("");
        View rootView = inflater.inflate(R.layout.fragment_search,
                container, false);
            listView = (ListView) rootView.findViewById(R.id.search);
            EditText searchUser = (EditText) rootView.findViewById(R.id.searchuser);
            Button searchBtn = (Button) rootView.findViewById(R.id.searchBtn);
            SearchAdapter adapter = new SearchAdapter(getActivity(), users);
            listView.setAdapter(adapter);
            searchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    users = databaseHelper.Search(searchUser.getText().toString());
                    SearchAdapter adapter = new SearchAdapter(getActivity(), users);
                    listView.setAdapter(adapter);

                }
            });
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Object listItem = listView.getItemAtPosition(position);
                    Fragment fragment = new ProfileFragment(users.get(position));
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, fragment).commit();
                }
            });
        // Inflate the layout for this fragment
        return rootView;
    }
}