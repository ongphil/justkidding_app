package justkidding.justkidding;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements AppetencesFragment.OnFragmentInteractionListener, HistoryFragment.OnFragmentInteractionListener,
        AdvicesFragment.OnFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener {

    private AppetencesFragment appetencesFragment;
    private HistoryFragment historyFragment;
    private AdvicesFragment advicesFragment;
    private ProfileFragment profileFragment;

    public enum fragmentType {
        APPETENCES_FRAGMENT,
        HISTORY_FRAGMENT,
        ADVICES_FRAGMENT,
        PROFILE_FRAGMENT;
    }

    public fragmentType currentFragmentIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appetencesFragment = new AppetencesFragment();
        historyFragment = new HistoryFragment();
        advicesFragment = new AdvicesFragment();
        profileFragment = new ProfileFragment();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().add(R.id.mainFragment, appetencesFragment).commitNow();
        currentFragmentIndex = fragmentType.APPETENCES_FRAGMENT;
        setTitle("Appétences");
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_appetences:
                    if(!currentFragmentIndex.equals(fragmentType.APPETENCES_FRAGMENT))
                    {
                        switch(currentFragmentIndex)
                        {
                            case HISTORY_FRAGMENT:
                                getSupportFragmentManager().beginTransaction().remove(historyFragment).commitNow();
                                break;
                            case ADVICES_FRAGMENT:
                                getSupportFragmentManager().beginTransaction().remove(advicesFragment).commitNow();

                                break;
                            case PROFILE_FRAGMENT:
                                getSupportFragmentManager().beginTransaction().remove(profileFragment).commitNow();
                                break;
                            default:
                                break;
                        }
                        getSupportFragmentManager().beginTransaction().add(R.id.mainFragment, appetencesFragment).commitNow();
                        currentFragmentIndex = fragmentType.APPETENCES_FRAGMENT;
                        setTitle("Appétences");
                    }
                    return true;
                case R.id.navigation_history:
                    if(!currentFragmentIndex.equals(fragmentType.HISTORY_FRAGMENT))
                    {
                        switch(currentFragmentIndex)
                        {
                            case APPETENCES_FRAGMENT:
                                getSupportFragmentManager().beginTransaction().remove(appetencesFragment).commitNow();
                                break;
                            case ADVICES_FRAGMENT:
                                getSupportFragmentManager().beginTransaction().remove(advicesFragment).commitNow();
                                break;
                            case PROFILE_FRAGMENT:
                                getSupportFragmentManager().beginTransaction().remove(profileFragment).commitNow();
                                break;
                            default:
                                break;
                        }
                        getSupportFragmentManager().beginTransaction().add(R.id.mainFragment, historyFragment).commitNow();
                        currentFragmentIndex = fragmentType.HISTORY_FRAGMENT;
                        setTitle("Parcours");
                    }

                    return true;
                case R.id.navigation_advices:
                    if(!currentFragmentIndex.equals(fragmentType.ADVICES_FRAGMENT))
                    {
                        switch(currentFragmentIndex)
                        {
                            case APPETENCES_FRAGMENT:
                                getSupportFragmentManager().beginTransaction().remove(appetencesFragment).commitNow();
                                break;
                            case HISTORY_FRAGMENT:
                                getSupportFragmentManager().beginTransaction().remove(historyFragment).commitNow();
                                break;
                            case PROFILE_FRAGMENT:
                                getSupportFragmentManager().beginTransaction().remove(profileFragment).commitNow();
                                break;
                            default:
                                break;
                        }
                        getSupportFragmentManager().beginTransaction().add(R.id.mainFragment, advicesFragment).commitNow();
                        currentFragmentIndex = fragmentType.ADVICES_FRAGMENT;
                        setTitle("Conseils");
                    }
                    return true;
                case R.id.navigation_profile:
                    if(!currentFragmentIndex.equals(fragmentType.PROFILE_FRAGMENT))
                    {
                        switch(currentFragmentIndex)
                        {
                            case APPETENCES_FRAGMENT:
                                getSupportFragmentManager().beginTransaction().remove(appetencesFragment).commitNow();
                                break;
                            case HISTORY_FRAGMENT:
                                getSupportFragmentManager().beginTransaction().remove(historyFragment).commitNow();
                                break;
                            case ADVICES_FRAGMENT:
                                getSupportFragmentManager().beginTransaction().remove(advicesFragment).commitNow();
                                break;
                            default:
                                break;
                        }
                        getSupportFragmentManager().beginTransaction().add(R.id.mainFragment, profileFragment).commitNow();
                        currentFragmentIndex = fragmentType.PROFILE_FRAGMENT;
                        setTitle("Profil");
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onFragmentInteractionAppetences(Uri uri) {

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

}