package it.ilfuma.rc.casteldileva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class HowItWorksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_it_works);
        new Holder(c);
    }

    Context c = this;

    public class Holder implements View.OnClickListener{
        MediaController myMediaController; //view containg controls for playing the video
        Button btnPlay;
        VideoView vvHowItWorks;
        Context mC;

        Holder(Context context){
            mC = context;
            vvHowItWorks = findViewById(R.id.vvHowItWorks);
            btnPlay = findViewById(R.id.btnPlay);
            btnPlay = findViewById(R.id.btnPlay);

            btnPlay.setOnClickListener(this);
            btnPlay.setVisibility(View.VISIBLE);
        }

        public void playVideo(){
            myMediaController = new MediaController(mC);
            vvHowItWorks.setMediaController(myMediaController);
            //take the video resource from the raw folder
            String path = "android.resource://" + getPackageName() + "/raw/iorestoacasteldilevavid";
            //Creates a Uri which parses the given encoded URI string.
            Uri u = Uri.parse(path);

            vvHowItWorks.setVideoURI(u);
            vvHowItWorks.start();
            btnPlay.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onClick(View v) {
            if (v.getId()==R.id.btnPlay){
                playVideo();
            }
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }

}