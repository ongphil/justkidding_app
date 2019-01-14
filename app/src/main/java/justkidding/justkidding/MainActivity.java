package justkidding.justkidding;

import android.app.ActionBar;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;


public class MainActivity extends AppCompatActivity
        implements ViewPager.OnPageChangeListener,
        AppetencesFragment.OnFragmentInteractionListener, HistoryFragment.OnFragmentInteractionListener,
        AdvicesFragment.OnFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener {

    private ViewPager viewPager;
    private BottomNavigationView navigation;

    private AppetencesFragment appetencesFragment;
    private HistoryFragment historyFragment;
    private AdvicesFragment advicesFragment;
    private ProfileFragment profileFragment;

    String email ;
    String child_name;
    int child_age;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(this);

        setTitle("Appétences");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Appetences")
                .document("psww5ttXlMRct2kNzxrT")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Map<String, Object> appetences = (Map<String, Object>) documentSnapshot.get("Themes");
                        //String test = String.valueOf(((Map)((Map)appetences.get("Chansons")).get("Disney")).get("Image"));
                        appetencesFragment = AppetencesFragment.newInstance(appetences);
                        historyFragment = HistoryFragment.newInstance(appetences);
                        advicesFragment = new AdvicesFragment();
                        profileFragment = new ProfileFragment();

                        navigation = (BottomNavigationView)findViewById(R.id.navigation);
                        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

                        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
                            @Override
                            public Fragment getItem(int position) {
                                switch (position) {
                                    case 0:
                                        return appetencesFragment;
                                    case 1:
                                        return historyFragment;
                                    case 2:
                                        return advicesFragment;
                                    case 3:
                                        return profileFragment;
                                }
                                return null;
                            }

                            @Override
                            public int getCount() {
                                return 4;
                            }
                        });


                    }
                });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_appetences:
                    viewPager.setCurrentItem(0);
                    setTitle("Appétences");
                    return true;
                case R.id.navigation_history:
                    viewPager.setCurrentItem(1);
                    setTitle("Parcours");
                    return true;
                case R.id.navigation_advices:
                    viewPager.setCurrentItem(2);
                    setTitle("Conseils");
                    return true;
                case R.id.navigation_profile:
                    viewPager.setCurrentItem(3);
                    setTitle("Profil");
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onFragmentInteractionAppetences() {

    }
    @Override
    public void onFragmentInteractionHistory(Uri uri) {

    }
    @Override
    public void onFragmentInteractionAdvices(Uri uri) {

    }
    @Override
    public void onFragmentInteractionProfile(Uri uri) {

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                setTitle("Appétences");
                break;
            case 1:
                setTitle("Parcours");
                break;
            case 2:
                setTitle("Conseils");
                break;
            case 3:
                setTitle("Profil");
                break;
        }
        navigation.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

}
