package jhon.tabares.com.grabilitytest.Utility;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import jhon.tabares.com.grabilitytest.MainActivity;
import jhon.tabares.com.grabilitytest.R;

/**
 * Created by jhon on 11/01/2017.
 */

public class SplashScreen extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread timerTread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerTread.start();
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }

}
