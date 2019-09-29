package com.example.android.bakingapp.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.activities.RecipeStepActivity;
import com.example.android.bakingapp.model.RecipeStep;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.parceler.Parcels;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class StepDetailFragment extends Fragment implements RecipeStepActivity.FragmentLifecycle {

    private TextView stepDescriptionTv;
    private PlayerView stepExoPlayerView;
    private CardView stepDetailsCv;

    private RecipeStep selectedStep;
    private int selectedStepIndex;
    private BandwidthMeter meter;
    private String videoURL;
    private String thumbNailURL;
    private SimpleExoPlayer simplePlayer;
    private boolean playWhenReady;
    private int currentWindow;
    private long playbackPosition;
    private String currentName;

    //Mandatory empty constructor

    public StepDetailFragment() {
    }

    public static StepDetailFragment newInstance(RecipeStep recipeStep) {
        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        if (recipeStep != null) {
            Bundle args = new Bundle();
            args.putParcelable("step", Parcels.wrap(recipeStep));
            stepDetailFragment.setArguments(args);

        }
        return stepDetailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container,
                false);
        stepDescriptionTv = rootView.findViewById(R.id.recipe_step_fragment_shortDescription_tv);
        stepExoPlayerView = rootView.findViewById(R.id.recipe_step_fragment_exoPlayer_view);
        stepDetailsCv = rootView.findViewById(R.id.recipe_step_fragment_card_view);

        try {
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                selectedStep = Parcels.unwrap(bundle.getParcelable("step"));
                selectedStepIndex = bundle.getInt("Selected_Index");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Handler mainHandle = new Handler();
        meter = new DefaultBandwidthMeter();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupExoPlayer();
        stepDescriptionTv.setText(String.format(selectedStep.getRecipeStepFullDescription()));
    }

    private void setupExoPlayer() {
        stepExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
        videoURL = selectedStep.getRecipeStepVideoURL();
        thumbNailURL = selectedStep.getRecipeStepThumbURL();

        if (!videoURL.isEmpty() || !thumbNailURL.isEmpty()) {
            initializePlayer();
        } else {
            simplePlayer = null;
            stepExoPlayerView.setForeground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_cancel_black_64dp));
            stepExoPlayerView.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_cancel_black_64dp));
        }

    }

    private void initializePlayer() {
        Uri videoSource;
        Uri imageSource;

        if (simplePlayer == null) {
            MediaSource mediaSource = null;
            simplePlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(stepExoPlayerView.getContext()),
                    new DefaultTrackSelector(), new DefaultLoadControl());
            stepExoPlayerView.setPlayer(simplePlayer);
            if (!videoURL.isEmpty()) {
                videoSource = Uri.parse(videoURL);
                mediaSource = buildMediaSource(videoSource);
            } else if (thumbNailURL.endsWith(".mp4")) {
                imageSource = Uri.parse(thumbNailURL);
                mediaSource = buildMediaSource(imageSource);

            } else if (thumbNailURL.isEmpty()) {
                stepExoPlayerView.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.exo_icon_stop));
            }

            simplePlayer.setPlayWhenReady(playWhenReady);
            simplePlayer.prepare(mediaSource, true, false);
            simplePlayer.seekTo(currentWindow, playbackPosition);
            stepExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);

            simplePlayer.addListener(new Player.DefaultEventListener() {
                @Override
                public void onPlayerStateChanged(boolean play, int playbackState) {
                    if (play && playbackState == Player.STATE_READY) {
                        playWhenReady = play;
                        simplePlayer.setPlayWhenReady(playWhenReady);
                    } else if (play) {
                        playWhenReady = play;
                    } else {
                        playWhenReady = play;
                    }
                }
            });
            simplePlayer.setPlayWhenReady(playWhenReady);
        }

    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            stepDetailsCv.setVisibility(View.GONE);
            getActivity().getWindow().getDecorView().setSystemUiVisibility
                    (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            stepDetailsCv.setVisibility(View.VISIBLE);
            getActivity().getWindow().getDecorView().setSystemUiVisibility
                    (View.SYSTEM_UI_FLAG_VISIBLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    private void releasePlayer() {
        if (simplePlayer != null) {
            playbackPosition = simplePlayer.getCurrentPosition();
            currentWindow = simplePlayer.getCurrentWindowIndex();
            playWhenReady = simplePlayer.getPlayWhenReady();
            simplePlayer.release();
            simplePlayer = null;
        }
    }

    private void hideSystemUi() {
        stepExoPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("Selected_Steps", Parcels.wrap(selectedStep));
        outState.putInt("Selected_Index", selectedStepIndex);
        outState.putString("Title", currentName);
        outState.putLong("player_pos", playbackPosition);
        outState.putBoolean("state", playWhenReady);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (simplePlayer != null) {
            releasePlayer();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        initializePlayer();
    }

    @Override
    public void onPauseFragment() {
        if (simplePlayer != null) {
            simplePlayer.setPlayWhenReady(playWhenReady);
            releasePlayer();
        }

    }

    @Override
    public void onResumeFragment() {
        if (simplePlayer == null) {
            simplePlayer.setPlayWhenReady(playWhenReady);
            initializePlayer();
        }
    }
}
