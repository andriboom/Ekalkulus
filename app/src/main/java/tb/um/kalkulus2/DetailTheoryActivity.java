package tb.um.kalkulus2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import tb.um.kalkulus2.assets.AppData;
import tb.um.kalkulus2.assets.model.Materi;

public class DetailTheoryActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.video_player)
    VideoView videoPlayer;
    @BindView(R.id.tv_deskripsi)
    TextView tvDeskripsi;
    @BindView(R.id.tv_judul)
    TextView tvJudul;
    private Context context;
    private Materi data;
    // posisi sekarang (in milliseconds).
    private int mCurrentPosition = 0;
    Intent intentData;
    // Tag for the instance state bundle.
    private static final String PLAYBACK_TIME = "play_time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_theory);
        ButterKnife.bind(this);
        context = this;
        intentData = getIntent();
        data = (Materi) intentData.getSerializableExtra("data");
        initComponent(savedInstanceState);
        if (data.getUrlVideo().isEmpty()) {
            videoPlayer.setVisibility(View.GONE);
            tvDeskripsi.setVisibility(View.VISIBLE);
            tvJudul.setVisibility(View.VISIBLE);
        } else {
            videoPlayer.setVisibility(View.VISIBLE);
            tvDeskripsi.setVisibility(View.GONE);
            tvJudul.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!data.getUrlVideo().isEmpty()) {
            // Load the media each time onStart() is called.
            initializePlayer();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            videoPlayer.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Media playback takes a lot of resources, so everything should be
        // stopped and released at this time.
        releasePlayer();
    }

    private void releasePlayer() {
        videoPlayer.stopPlayback();
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current playback position (in milliseconds) to the
        // instance state bundle.
        outState.putInt(PLAYBACK_TIME, videoPlayer.getCurrentPosition());
    }

    private void initializePlayer() {
        // Buffer and decode the video sample.
        Uri videoUri = getMedia(AppData.URL_VIDEO + data.getUrlVideo());
        videoPlayer.setVideoURI(videoUri);

        // Listener for onPrepared() event (runs after the media is prepared).
        videoPlayer.setOnPreparedListener(
                mediaPlayer -> {
                    // Hide buffering message.
                    Toast.makeText(context, "memproses video...", Toast.LENGTH_SHORT).show();
                    // Restore saved position, if available.
                    if (mCurrentPosition > 0) {
                        videoPlayer.seekTo(mCurrentPosition);
                    } else {
                        // Skipping to 1 shows the first frame of the video.
                        videoPlayer.seekTo(1);
                    }
                    // Start playing!
                    videoPlayer.start();
                });

        // Listener for onCompletion() event (runs after media has finished
        // playing).
        videoPlayer.setOnCompletionListener(
                mediaPlayer -> {
                    Toast.makeText(context, "video selesai", Toast.LENGTH_SHORT).show();
                    // Return the video position to the start.
                    videoPlayer.seekTo(0);
                });
    }

    @SuppressLint("SetTextI18n")
    private void initComponent(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }
        //set up title and desc
        assert data != null;
        //init toolbar
        toolbar.setTitle(data.getJudul());
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        tvDeskripsi.setText("" + data.getDeskripsi());
        tvJudul.setText(""+data.getJudul());
        // Set up the media controller widget and attach it to the video view.
        MediaController controller = new MediaController(context);
        controller.setMediaPlayer(videoPlayer);
        videoPlayer.setMediaController(controller);
    }

    private Uri getMedia(String mediaName) {
        if (URLUtil.isValidUrl(mediaName)) {
            // Media name is an external URL.
            return Uri.parse(mediaName);
        } else {
            Toast.makeText(context, "video tidak ditemukan", Toast.LENGTH_SHORT).show();
            return Uri.parse(mediaName);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
