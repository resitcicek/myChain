package mobile.resitcicek.mychain;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

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
    public static Chain chain = new Chain();
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
        ChainDetails fragment = new ChainDetails(chain);
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
        Calendar currentDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        TextView chName = (TextView) view.findViewById(R.id.cname);
        TextView chDesc = (TextView) view.findViewById(R.id.chdesc);
        chName.setText(chain.getName());
        chDesc.setText(chain.getDescription());
        Button check = (Button) view.findViewById(R.id.checkbtn);
        Button uncheck = (Button) view.findViewById(R.id.uncheck);
        Button joinBtn = (Button) view.findViewById(R.id.joinBtn);
        int[] chainArr = new int[]{R.id.chain1, R.id.chain2, R.id.chain3, R.id.chain4, R.id.chain5, R.id.chain6, R.id.chain7, R.id.chain8, R.id.chain9, R.id.chain10, R.id.chain11, R.id.chain12, R.id.chain13, R.id.chain14, R.id.chain15, R.id.chain16, R.id.chain17, R.id.chain18, R.id.chain19, R.id.chain20, R.id.chain21, R.id.chain22, R.id.chain23, R.id.chain24, R.id.chain25, R.id.chain26, R.id.chain27, R.id.chain28, R.id.chain29, R.id.chain30};

        if(databaseHelper.isInChain(chain.getID())) {
            joinBtn.setVisibility(View.INVISIBLE);
                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            String today = format.format(currentDate.getTime());
            try {
                if (databaseHelper.isDone(chain.getID(), today)) {
                    check.setVisibility(View.INVISIBLE);
                } else {
                    uncheck.setVisibility(View.INVISIBLE);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                startDate.setTime(format.parse(databaseHelper.getDate(chain.getID())));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            int n = 0;
            while (startDate.compareTo(currentDate) <= 0) {
                imageArr.add(view.findViewById(chainArr[n]));
                try {
                    if (!databaseHelper.isDone(chain.getID(), format.format(startDate.getTime()))) {
                        imageArr.get(n).setImageResource(R.mipmap.broken_foreground);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                n++;
                startDate.add(Calendar.DATE, 1);
            }
            while (n < 30) {
                imageArr.add(view.findViewById(chainArr[n]));
                imageArr.get(n).setVisibility(View.INVISIBLE);
                n++;
            }
            check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean insert = databaseHelper.done(chain.getID());
                    if (insert) {
                        Toast.makeText(getActivity().getApplicationContext(), "Congrulations!", Toast.LENGTH_SHORT).show();
                        Fragment fragment = new ChainDetails(chain);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, fragment).commit();

                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Something is wrong!", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            uncheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean delete = databaseHelper.uncheck(chain.getID());
                    if (delete) {
                        Fragment fragment = new ChainDetails(chain);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, fragment).commit();

                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Something is wrong!", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
        else{
            ImageView table = view.findViewById(R.id.imageView2);
            table.setVisibility(View.INVISIBLE);
            for(int i=0; i<30; i++){
                ImageView im = view.findViewById(chainArr[i]);
                im.setVisibility(View.INVISIBLE);
            }
            TextView txt = view.findViewById(R.id.textView4);
            txt.setVisibility(View.INVISIBLE);
            check.setVisibility(View.INVISIBLE);
            uncheck.setVisibility(View.INVISIBLE);
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long checkinsert = databaseHelper.InsertRelation(MainActivity.loggedUser.getID(),chain.getID());
                if (checkinsert != -1) {
                    Toast.makeText(getActivity().getApplicationContext(), "Joined the Chain!", Toast.LENGTH_SHORT).show();
                    Fragment fragment = new ChainDetails(chain);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, fragment).commit();

                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(), "Chain relation error!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        }

        return view;
    }
}