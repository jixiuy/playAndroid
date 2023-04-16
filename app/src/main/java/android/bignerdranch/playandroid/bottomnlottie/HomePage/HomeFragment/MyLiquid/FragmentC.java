package android.bignerdranch.playandroid.bottomnlottie.HomePage.HomeFragment.MyLiquid;

import android.bignerdranch.playandroid.util.ActivityCollector;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.readapp.R;
import android.widget.TextView;

/**
 * 液态的第三个界面，就是一个界面
 */
public class FragmentC extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public FragmentC() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FragmentC newInstance(String param1, String param2) {
        FragmentC fragment = new FragmentC();
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
            // TODO: Rename and change types of parameters
            String param1 = getArguments().getString(ARG_PARAM1);
            String param2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_c, container, false);
        TextView textView = view.findViewById(R.id.liquid_exit);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ActivityCollector.finishAll();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }


}