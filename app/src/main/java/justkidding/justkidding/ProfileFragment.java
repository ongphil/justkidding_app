package justkidding.justkidding;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String Email;
    private String Child_Name;
    private int Child_Age;
    private TextView EmailTextView;
    private TextView NameTextView;
    private TextView AgeTextView;
    private Button comptine;
    private Button histoire;

    private FirebaseAuth Auth;
    private FirebaseFirestore Firestore;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

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

        Auth= FirebaseAuth.getInstance();
        Firestore = FirebaseFirestore.getInstance();

        EmailTextView = view.findViewById(R.id.editTextEmail);
        NameTextView = view.findViewById(R.id.editTextChildName);
        AgeTextView = view.findViewById(R.id.editTextChildAge);
        histoire = view.findViewById(R.id.buttonHistoire);
        comptine = view.findViewById(R.id.buttonComptine);

        Firestore.collection("Users")
                .document("ug5CDvhnTf29prJutrVx")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Email = documentSnapshot.getString("Email");
                        Child_Name = documentSnapshot.getString("Child_name");
                        Child_Age = documentSnapshot.getLong("Child_age").intValue();
                        Map<String, Boolean> activity = (Map<String, Boolean>) documentSnapshot.get("Activity");
                        EmailTextView.setText(Email);
                        NameTextView.setText(Child_Name);
                        AgeTextView.setText("" + Child_Age);
                        for ( Map.Entry<String, Boolean> entry : activity.entrySet()) {
                            String key = entry.getKey();
                            Boolean value = entry.getValue();
                            // do something with key and/or tab
                            switch (key) {
                                case "Comptine":
                                    if (value)
                                    {
                                        Drawable roundDrawable = getResources().getDrawable(R.drawable.circle);
                                        roundDrawable.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

                                            comptine.setBackground(roundDrawable);
                                    }
                                    break;
                                case "Histoire":
                                    if (value)
                                    {
                                        Drawable roundDrawable = getResources().getDrawable(R.drawable.circle);
                                        roundDrawable.setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);

                                            histoire.setBackground(roundDrawable);
                                    }
                                    break;
                            }

                        }
                    }
                });

        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteractionProfile(uri);
        }
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
        void onFragmentInteractionProfile(Uri uri);
    }
}
