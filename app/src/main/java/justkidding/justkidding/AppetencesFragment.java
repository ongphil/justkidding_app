package justkidding.justkidding;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Map;


public class AppetencesFragment extends Fragment {

    private Map<String, Object> allAppetences;

    private OnFragmentInteractionListener mListener;

    public AppetencesFragment() {
        // Required empty public constructor
    }

    public static AppetencesFragment newInstance(Map<String, Object> appetences) {
        AppetencesFragment fragment = new AppetencesFragment();
        Bundle args = new Bundle();
        args.putSerializable("allAppetences", (Serializable) appetences);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = this.getArguments();
        if(b.getSerializable("allAppetences") != null)
            allAppetences = (Map<String, Object>)b.getSerializable("allAppetences");
        int i = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_appetences, container, false);

        /*test_button = view.findViewById(R.id.test_button);
        test_tv = view.findViewById(R.id.test_tv);
        test_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test_tv.setText("Test");
                mListener.onFragmentInteractionAppetences();
            }
        });*/
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteractionAppetences();
    }
}
