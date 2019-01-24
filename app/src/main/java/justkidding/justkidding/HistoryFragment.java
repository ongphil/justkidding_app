package justkidding.justkidding;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    private Map<String, Map<String, Object>> allAppetences;

    private OnFragmentInteractionListener mListener;

    boolean first_activity = true;
    boolean icon_left = true;

    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment newInstance(Map<String, Object> appetences) {
        HistoryFragment fragment = new HistoryFragment();
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
            allAppetences = (Map<String, Map<String, Object>>)b.getSerializable("allAppetences");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_history, container, false);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Histories")
                .document("b8:27:eb:a4:76:ca")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String user_id = documentSnapshot.getString("User_ID");
                        List<Map<String, Object>> activities = (List<Map<String, Object>>) documentSnapshot.get("Activities");

                        for(Map<String, Object> activity : activities)
                        {
                            ViewGroup insertLayout_main = (ViewGroup) view.findViewById(R.id.linearlayout_main);
                            /// 1ère vue (icone de l'activité + date + heure)
                            LayoutInflater layoutInflater_activity_icon = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View activity_icon_view = layoutInflater_activity_icon.inflate(R.layout.history_custom_layout, null);

                            RelativeLayout relativeLayout_Icon = (RelativeLayout) activity_icon_view.findViewById(R.id.relativelayout_Icon);
                            ImageView imageView_Icon = (ImageView) activity_icon_view.findViewById(R.id.activity_icon);
                            ImageView imageView_Icon_Theme = (ImageView) activity_icon_view.findViewById(R.id.theme_icon);
                            TextView textView_Date = (TextView) activity_icon_view.findViewById(R.id.tv_activity_date);

                            String activity_date_str = String.valueOf(activity.get("Activity_Date"));
                            String formatted_date = "";
                            try {
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                SimpleDateFormat formatter_date = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                                Date date = formatter.parse(activity_date_str);
                                formatted_date = formatter_date.format(date);
                                textView_Date.setText(String.valueOf(formatted_date));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            
                            if(String.valueOf(activity.get("Activity_Name")).equals("Histoires")) {
                                imageView_Icon.setImageResource(R.drawable.icon_histoires);
                            } else {
                                imageView_Icon.setImageResource(R.drawable.icon_chansons);
                            }

                            if(String.valueOf(activity.get("Activity_Theme")).equals("Chevaliers")) {
                                imageView_Icon_Theme.setImageResource(R.drawable.icon_chevaliers);
                            } else {
                                imageView_Icon_Theme.setImageResource(R.drawable.icon_animaux);
                            }

                            // Si ce n'est pas la première activité, on ajoute un trait de transition
                            if(!first_activity)
                            {
                                /// 2ème vue (trait de transition entre 2 activités)
                                LayoutInflater layoutInflater_link = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View link_view = layoutInflater_link.inflate(R.layout.history_custom_link, null);

                                ImageView imageView_link = (ImageView) link_view.findViewById(R.id.imageView_link);

                                if(icon_left)
                                {
                                    imageView_link.setImageResource(R.drawable.line_left_to_right);
                                }
                                else {
                                    relativeLayout_Icon.setGravity(Gravity.END);
                                    imageView_link.setImageResource(R.drawable.line_right_to_left);
                                }
                                insertLayout_main.addView(link_view, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            }
                            else
                            {
                                first_activity = false;
                            }

                            icon_left = !icon_left;

                            insertLayout_main.addView(activity_icon_view, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                        }
                    }
                });

        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteractionHistory(uri);
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
        void onFragmentInteractionHistory(Uri uri);
    }

}
