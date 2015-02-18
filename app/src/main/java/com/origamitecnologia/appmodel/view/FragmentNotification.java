package com.origamitecnologia.appmodel.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.origamitecnologia.appmodel.R;
import com.origamitecnologia.appmodel.control.DisplayCentral;
import com.origamitecnologia.appmodel.control.ServerCentral;
import com.origamitecnologia.appmodel.model.Movie;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import io.realm.Realm;
import io.realm.RealmQuery;

public class FragmentNotification extends BaseFragment implements ObservableScrollViewCallbacks {

    private static String BUNDLE_CURRENT_BASE_COLOR = "current_base_color";
    private static String BUNDLE_CURRENT_ALPHA = "current_alpha";
    public static String BUNDLE_NOTIFICATION_ID = "notification_id";
    public static String BUNDLE_MOVIE_TITLE = "movie_title";

    @InjectView(R.id.ivNotificationPhoto)
    ImageView ivPhoto;

    @InjectView(R.id.tvNotificationBody)
    TextView tvBody;

    private long notificationId;
    private long movieId;
    private int imageHeight;
    private int baseColor;
    private float alpha;

    @Override
    public int layoutToInflate() {
        return R.layout.fragment_notification;
    }

    @Override
    protected void doOnWithoutRestore() {
        if(hasObservableScrollView()) {
            setViewAlpha(getToolbar(), 0);
        }
    }

    @Override
    protected void doOnStateRestored(Bundle savedInstanceState) {
        if(hasObservableScrollView()) {
            baseColor = savedInstanceState.getInt(BUNDLE_CURRENT_BASE_COLOR);
            setViewAlpha(getToolbar(), savedInstanceState.getFloat(BUNDLE_CURRENT_ALPHA));
        }
    }

    @Override
    public void doOnCreated(View view) {
        if(hasObservableScrollView()) {
            baseColor = getResources().getColor(R.color.primary_dark);
            ObservableScrollView osvNotificationDetails = (ObservableScrollView) view.findViewById(R.id.osvNotificationDetails);
            osvNotificationDetails.setScrollViewCallbacks(this);
        }

        imageHeight = getResources().getDimensionPixelSize(R.dimen.parallax_header_image_height_phone);
        movieId = getArguments().getLong(BUNDLE_NOTIFICATION_ID);
        Realm realm = Realm.getInstance(getActivity());
        RealmQuery<Movie> query = realm.where(Movie.class);
        Movie movie = query.equalTo("id", movieId).findFirst();

        Picasso.with(getActivity())
                .load(ServerCentral.getInstance().getImageWithPath(getActivity(), movie.getPhoto()))
                .centerCrop()
                .error(R.drawable.ic_edit)
                .fit()
                .into(ivPhoto);

        tvBody.setText(movie.getDescription());
    }

    @Override
    protected boolean isDrawerLocked() {
        return true;
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        alpha = 1 - (float) Math.max(0, imageHeight - scrollY) / imageHeight;
        setViewAlpha(getToolbar(), alpha);
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

    private void setViewAlpha(View view, float alpha) {
        int a = Math.min(255, Math.max(0, (int) (alpha * 255))) << 24;
        int rgba = (0x00ffffff & baseColor) + a;
        view.setBackgroundColor(rgba);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_CURRENT_BASE_COLOR, baseColor);
        outState.putFloat(BUNDLE_CURRENT_ALPHA, alpha);
        outState.putLong(BUNDLE_NOTIFICATION_ID, notificationId);
        super.onSaveInstanceState(outState);
    }

    private boolean hasObservableScrollView() {
        return (DisplayCentral.getInstance().isScreenNormal(getActivity()) ||
                DisplayCentral.getInstance().isScreenSmall(getActivity()));
    }
}
