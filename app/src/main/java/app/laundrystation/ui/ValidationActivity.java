package app.laundrystation.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import app.laundrystation.R;
import app.laundrystation.ui.fragments.FragmentCodeValidation;
import app.laundrystation.ui.fragments.FragmentPhoneValidation;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ValidationActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/restaurant_font.otf")
//                .setFontAttrId(R.attr.fontPath)
//                .build());
        setContentView(R.layout.activity_validation);
        if (getIntent().getStringExtra("code").equals("confirm")) {
            setFragment(new FragmentCodeValidation());
        } else {
            setFragment(new FragmentPhoneValidation());

        }

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.validation_FrameLayout, fragment);
        fragmentTransaction.commit();
    }
}
