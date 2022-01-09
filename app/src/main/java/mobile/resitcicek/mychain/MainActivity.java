package mobile.resitcicek.mychain;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static User loggedUser = new User();
    BottomNavigationView bottomNav;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(this);
        if(this.loggedUser.getUsername() == null){
            Intent i = new Intent(MainActivity.this, Login.class);
            startActivity(i);
        }
        bottomNav = findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new HomeFragment()).commit();
        bottomNav.setSelectedItemId(R.id.navigation_home);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch(item.getItemId()){
                    case R.id.navigation_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.navigation_add:
                        fragment = new create();
                        break;
                    case R.id.navigation_explore:
                        fragment = new Explore();
                        break;
                    case R.id.navigation_profile:
                        fragment = new ProfileFragment(loggedUser);
                        break;
                    case R.id.navigation_search:
                        fragment = new SearchFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, fragment).commit();

                return true;
            }
        });

        //ArrayList<Chain> chains = databaseHelper.getChains(this.loggedUser.getID());
        //ListView listView = (ListView) findViewById(R.id.chainlist);
        //ChainAdapter adapter = new ChainAdapter(this, chains);
        //listView.setAdapter(adapter);


    }




}