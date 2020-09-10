package it.ilfuma.rc.casteldileva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
        MediaController myMediaController;
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

            String path = "android.resource://" + getPackageName() + "/raw/iorestoacasteldilevavid";

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

}