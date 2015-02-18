package com.origamitecnologia.appmodel.view;

import android.content.res.Configuration;
import android.view.View;

import com.origamitecnologia.appmodel.R;
import com.origamitecnologia.appmodel.control.AdapterTimeline;
import com.origamitecnologia.appmodel.control.CallManager;
import com.origamitecnologia.appmodel.control.OnItemClickListener;
import com.origamitecnologia.appmodel.model.CustomRecyclerView;
import com.origamitecnologia.appmodel.model.Movie;

import butterknife.InjectView;
import io.realm.Realm;
import io.realm.RealmQuery;

public class FragmentTimeline extends BaseFragment {

    private AdapterTimeline adapterTimeline;

    @InjectView(R.id.crvTimeline)
    CustomRecyclerView crvTimeline;

    @Override
    public int layoutToInflate() {
        return R.layout.fragment_timeline;
    }

    @Override
    public void doOnCreated(View view) {
        Realm realm = Realm.getInstance(getActivity());
        RealmQuery<Movie> query = realm.where(Movie.class);

        adapterTimeline = new AdapterTimeline(getActivity(), query.findAll());
        crvTimeline.defineLayoutManager(getActivity());
        crvTimeline.setAdapter(adapterTimeline);
        adapterTimeline.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClickItem(View view, long id) {
                CallManager.getInstance().fragmentNotification(id);
            }
        });
    }

    @Override
    protected boolean isDrawerLocked() {
        return false;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        crvTimeline.defineLayoutManager(getActivity());
    }
}
