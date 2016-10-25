package com.sdpcrew.android.flatapp.BillsManager;

/**
 * Class used to host an enlarged image fragment of photo taken in the bills class
 * Created by David on 23/10/2016.
 */

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageFragment extends DialogFragment {
    public static final String EXTRA_IMAGE_PATH = "path"; //Defines name for pathway

    public static ImageFragment createInstance(String imagePath) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_IMAGE_PATH, imagePath);

        ImageFragment fragment = new ImageFragment();
        fragment.setArguments(args);
        //Defines the visual style of pop out
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

        return fragment;
    }

    private ImageView mImageView;

    /**
     * Creates the view and sets the Bitmap drawable to it
     * @param inflater
     * @param parent
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup parent, Bundle savedInstanceState) {
        mImageView = new ImageView(getActivity());
        String path = (String)getArguments().getSerializable(EXTRA_IMAGE_PATH);
        BitmapDrawable image = PictureUtils.getScaledBitmap(path, getActivity());

        mImageView.setImageDrawable(image);

        return mImageView;
    }

    /**
     * Method destroys the view and cleans it
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PictureUtils.cleanImageView(mImageView);
    }
}