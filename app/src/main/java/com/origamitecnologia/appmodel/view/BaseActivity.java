package com.origamitecnologia.appmodel.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import com.origamitecnologia.appmodel.R;
import com.origamitecnologia.appmodel.control.CallManager;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by pokawa on 25/11/14.
 */

public abstract class BaseActivity extends ActionBarActivity {

    /*===========================*/
    /* STATIC ATTRIBUTES - START */
    /*===========================*/

    private static String DRAWER_NULL_EXCEPTION = "There's no drawer associated with this activity.";
    private static String TOOLBAR_NULL_EXCEPTION = "There's no toolbar associated with this activity.";

    /*=========================*/
    /* STATIC ATTRIBUTES - END */
    /*=========================*/

    /*====================*/
    /* ATTRIBUTES - START */
    /*====================*/

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private FragmentManager fragmentManager;

    /*==================*/
    /* ATTRIBUTES - END */
    /*==================*/

    /*===========================*/
    /* ACTIVITY CREATION - START */
    /*===========================*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutToInflate());
        ButterKnife.inject(this);
        fragmentManager = getSupportFragmentManager();
        CallManager.getInstance().setActivity(this);
        setToolbar();
        setDrawer();

        if(savedInstanceState == null) {
            doOnFirstTime();
        }

        doOnCreated(savedInstanceState);
    }

    protected abstract void doOnFirstTime();
    protected abstract void doOnCreated(Bundle savedInstanceState);

    protected abstract int layoutToInflate();

    /*=========================*/
    /* ACTIVITY CREATION - END */
    /*=========================*/

    /*========================*/
    /* DRAWER METHODS - START */
    /*========================*/

    protected abstract boolean hasDrawer();

    private void setDrawer() {
        if(hasDrawer()) {
            drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
            if(drawerLayout == null) {
                throw new NullPointerException(DRAWER_NULL_EXCEPTION);
            }
        }
    }

    protected boolean isDrawerOpen() {
        return ((hasDrawer()) && (drawerLayout.isDrawerOpen(Gravity.LEFT)));
    }

    protected boolean isDrawerClosed() {
        return ((hasDrawer()) && (!drawerLayout.isDrawerOpen(Gravity.LEFT)));
    }

    protected boolean openDrawer() {
        if(isDrawerClosed()) {
            drawerLayout.openDrawer(Gravity.LEFT);
            return true;
        }
        return false;
    }

    protected boolean closeDrawer() {
        if(isDrawerOpen()) {
            drawerLayout.closeDrawer(Gravity.LEFT);
            return true;
        }
        return false;
    }

    public void lockDrawer() {
        if(hasDrawer()) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    public void unlockDrawer() {
        if(hasDrawer()) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                if(openDrawer()) {
                    return true;
                }
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if(!closeDrawer()) {
            super.onBackPressed();
        }
    }

    /*======================*/
    /* DRAWER METHODS - END */
    /*======================*/

    /*=========================*/
    /* TOOLBAR METHODS - START */
    /*=========================*/

    public void setToolbar() {
        setToolbar((Toolbar) findViewById(R.id.toolbar));
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
        if(this.toolbar != null) {
            setSupportActionBar(this.toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            manageToolbarIcon();
        }
    }

    public Toolbar getToolbar() {
        if(toolbar == null) {
            throw new NullPointerException(TOOLBAR_NULL_EXCEPTION);
        }
        return toolbar;
    }

    private void manageToolbarIcon() {
        if(hasDrawer()) {
            toolbar.setNavigationIcon(R.drawable.ic_menu);
        } else {
            toolbar.setNavigationIcon(R.drawable.ic_navigation_back);
        }
    }

    /*=======================*/
    /* TOOLBAR METHODS - END */
    /*=======================*/

    /*===========================*/
    /* EVENT BUS METHODS - START */
    /*===========================*/

    protected void registerOnEventBus() {
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    protected void unregisterOnEventBus() {
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void onDestroy() {
        unregisterOnEventBus();
        super.onDestroy();
    }

    /*=========================*/
    /* EVENT BUS METHODS - END */
    /*=========================*/

    /*==================================*/
    /* FRAGMENT MANAGER METHODS - START */
    /*==================================*/

    public void setFragment(Fragment fragment, boolean backStack) {
        String fragmentName = fragment.getClass().getName();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(backStack) {
            fragmentTransaction
                    .addToBackStack(fragmentName);
        }

        fragmentTransaction
                .replace(R.id.flMainContent, fragment, fragmentName)
                .commit();

        fragmentManager.executePendingTransactions();
    }

    protected void resetToHomeFragment() {
        for(int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
            fragmentManager.popBackStack();
        }
    }

    /*================================*/
    /* FRAGMENT MANAGER METHODS - END */
    /*================================*/
}
