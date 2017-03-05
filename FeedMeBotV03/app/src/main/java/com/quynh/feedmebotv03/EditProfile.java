package com.quynh.feedmebotv03;

/**
 * Created by qaphan3007 on 05.03.2017.
 */

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

// In this class, any changes made are activated by a button.
// After button press, all info should be changed in the database under
// the correct userID. (child of UserInfo)

public class EditProfile extends AppCompatActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user_info);

    }
}
