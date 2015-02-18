package com.origamitecnologia.appmodel.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.origamitecnologia.appmodel.R;
import com.origamitecnologia.appmodel.control.CallManager;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {
    private static String TOOLBAR_NULL_EXCEPTION = "There's no toolbar associated with this fragment.";

    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutToInflate(), container, false);
        ButterKnife.inject(this, view);
        setToolbar(view);

//        manageDrawer();

        if(savedInstanceState != null) {
            doOnStateRestored(savedInstanceState);
        } else {
            doOnWithoutRestore();
        }

        doOnCreated(view);
        return view;
    }

    private void setToolbar(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        if(toolbar != null) {
//            getBaseActivity().setToolbar(toolbar);
        }
    }

    protected abstract int layoutToInflate();
    protected abstract void doOnCreated(View view);

    protected abstract boolean isDrawerLocked();

//    private void manageDrawer() {
//        if(isDrawerLocked()) {
//            getBaseActivity().lockDrawer();
//        } else {
//            getBaseActivity().unlockDrawer();
//        }
//    }

    protected void doOnStateRestored(Bundle savedInstanceState) { }
    protected void doOnWithoutRestore() { }

    public Toolbar getToolbar() {
        if(toolbar == null) {
            throw new NullPointerException(TOOLBAR_NULL_EXCEPTION);
        }
        return toolbar;
    }

//    protected BaseActivity getBaseActivity() {
//        return CallManager.getInstance().getActivity();
//    }
}
