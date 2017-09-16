package com.agungsantoso.udacity.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.agungsantoso.udacity.bakingapp.data.StepsParcel;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

public class RecipeStepDetailFragment extends Fragment {

    private int mId;
    private int mSize;
    private ArrayList<StepsParcel> mSteps;
    private String mIngredient;
    private String mVideo;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;

    public RecipeStepDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey("id")) {
            mId = getArguments().getInt("id");
        }

        if (getArguments().containsKey("size")) {
            mSize = getArguments().getInt("size");
        }

        if (getArguments().containsKey("steps")) {
            mSteps = getArguments().getParcelableArrayList("steps");
        }

        if (getArguments().containsKey("ingredient")) {
            mIngredient = getArguments().getString("ingredient");
        }

        if (getArguments().containsKey("video")) {
            mVideo = getArguments().getString("video");
        }

        Log.d("Fragment", "ingr = " + mIngredient);
        Log.d("Fragment", "vid = " + mVideo);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_step_detail_content, container, false);

        // Show the dummy content as text in a TextView.
        if (mIngredient != null) {
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(mIngredient);
        }

        if (mVideo != null) {
            // Initialize the player view.
            mPlayerView = rootView.findViewById(R.id.player);

            // Initialize the player.
            initializePlayer(Uri.parse(mVideo));
        }

        final Button previous = rootView.findViewById(R.id.previous);
        previous.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, RecipeStepDetailActivity.class);
                if(mId != 0) {
                    intent.putExtra("id", mId - 1);
                    intent.putParcelableArrayListExtra("steps", mSteps);
                    context.startActivity(intent);
                }
            }
        });

        final Button next = rootView.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, RecipeStepDetailActivity.class);
                if(mId != mSize - 1) {
                    intent.putExtra("id", mId + 1);
                    intent.putParcelableArrayListExtra("steps", mSteps);
                    context.startActivity(intent);
                }
                context.startActivity(intent);
            }
        });

        if(RecipeDetailActivity.mTwoPane) {
            previous.setVisibility(View.INVISIBLE);
            next.setVisibility(View.INVISIBLE);
        } else {
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                rootView.findViewById(R.id.step_detail).setVisibility(View.INVISIBLE);
                previous.setVisibility(View.INVISIBLE);
                next.setVisibility(View.INVISIBLE);
                getActivity().getWindow().setFlags(
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        }

        return rootView;
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mVideo != null) {
            releasePlayer();
        }
    }
}
