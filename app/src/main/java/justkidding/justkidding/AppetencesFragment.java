package justkidding.justkidding;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Map;


public class AppetencesFragment extends Fragment {

    private Map<String, Map<String, Object>> allAppetences;

    private ImageButton imageButtonChansons, imageButtonHistoires, imageButtonCulture;

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
            allAppetences = (Map<String, Map<String, Object>>)b.getSerializable("allAppetences");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_appetences, container, false);

        imageButtonChansons = (ImageButton)view.findViewById(R.id.imageButtonChansons);
        imageButtonHistoires = (ImageButton)view.findViewById(R.id.imageButtonHistoires);
        imageButtonCulture = (ImageButton)view.findViewById(R.id.imageButtonCulture);

        final Dialog chansons_dialog = loadDialog("Chansons");
        final Dialog histoires_dialog = loadDialog("Histoires");
        final Dialog culture_dialog = loadDialog("Culture");

        imageButtonChansons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chansons_dialog.show();
            }
        });
        imageButtonHistoires.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                histoires_dialog.show();
            }
        });
        imageButtonCulture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                culture_dialog.show();
            }
        });
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

    public Dialog loadDialog(String activity) {

        final Dialog dialog = new Dialog(getActivity());

        LayoutInflater layoutInflater_dialog_view = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialog_view = layoutInflater_dialog_view.inflate(R.layout.theme_dialog, null);

        Map<String, Object> themes = allAppetences.get(activity);

        TableLayout tl = (TableLayout) dialog_view.findViewById(R.id.tl_theme);
        ImageView iv_activity = (ImageView) dialog_view.findViewById(R.id.iv_activity);

        new DownloadImageTask(iv_activity).execute(String.valueOf(((Map)allAppetences.get(activity)).get("Image")));

        TableRow tr = new TableRow(getActivity());
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        tr.setGravity(Gravity.CENTER_HORIZONTAL);

        for(Map.Entry<String, Object> entry : themes.entrySet()) {
            String key = entry.getKey();
            if(!key.equals("Image")) {
                LayoutInflater layoutInflater_theme_progress_item_ = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View theme_progress_item = layoutInflater_theme_progress_item_.inflate(R.layout.theme_progress_item, null);

                ImageView iv_theme_icon = (ImageView) theme_progress_item.findViewById(R.id.iv_theme_icon);
                TextView tv_theme_title = (TextView) theme_progress_item.findViewById(R.id.tv_theme_title);
                TextView tv_theme_percent = (TextView) theme_progress_item.findViewById(R.id.tv_theme_percent);
                ProgressBar p_theme_progress = (ProgressBar) theme_progress_item.findViewById(R.id.p_theme_progress);

                String theme_image_url = String.valueOf(((Map)((Map)allAppetences.get(activity)).get(key)).get("Image"));
                new DownloadImageTask(iv_theme_icon).execute(theme_image_url);

                tv_theme_title.setText(key);
                tv_theme_percent.setText(String.valueOf(Math.round((Double.parseDouble(String.valueOf(((Map)((Map)allAppetences.get(activity)).get(key)).get("Progression")))))) + "%");
                p_theme_progress.setProgress(Integer.parseInt(String.valueOf(Math.round((Double.parseDouble(String.valueOf(((Map)((Map)allAppetences.get(activity)).get(key)).get("Progression"))))))));

                theme_progress_item.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT,1f));

                tr.addView(theme_progress_item);
            }
        }

        tl.addView(tr);

        dialog.setContentView(dialog_view);

        Button buttonDismiss = (Button) dialog.findViewById(R.id.buttonDismiss);
        // if button is clicked, close the custom dialog
        buttonDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        return dialog;
    }

}
