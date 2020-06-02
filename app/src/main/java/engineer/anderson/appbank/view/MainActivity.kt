package engineer.anderson.appbank.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import engineer.anderson.appbank.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onBackPressed() {

        super.onBackPressed()
        if(getSupportFragmentManager().getBackStackEntryCount()>0)
        {
            getSupportFragmentManager().popBackStack();
        }
        else
        {
            finish();
        }

    }

}
