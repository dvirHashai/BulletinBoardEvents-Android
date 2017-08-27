package com.cambio.finalprojectandroid;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.cambio.finalprojectandroid.model.Model;

public class MainActivity extends Activity implements EventListFragment.OnFragmentInteractionListener, EventEditFragment.OnFragmentInteractionListener, EventAddFragment.OnFragmentInteractionListener, EventDetailsFragment.OnFragmentInteractionListener {
    EventListFragment eventListFragment;
    EventDetailsFragment eventDetailsFragment;
    EventEditFragment eventEditFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventListFragment = EventListFragment.newInstance();
        FragmentTransaction tran = getFragmentManager().beginTransaction();
        tran.add(R.id.main_fragment_container, eventListFragment, "eventListFragment");

        tran.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        if (getFragmentManager().findFragmentById(R.id.main_fragment_container) instanceof EventListFragment) {
            getMenuInflater().inflate(R.menu.add_list_item, menu);
        } else if (getFragmentManager().findFragmentById(R.id.main_fragment_container) instanceof EventDetailsFragment) {
            getMenuInflater().inflate(R.menu.edit_details_item, menu);
            getMenuInflater().inflate(R.menu.delete_item, menu);
        }else if (getFragmentManager().findFragmentById(R.id.main_fragment_container) instanceof EventEditFragment){
            getMenuInflater().inflate(R.menu.delete_item, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentTransaction tran = null;
        Fragment currentFragment ;
        String eventId;
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.main_add:
                EventAddFragment eventAddFragment = EventAddFragment.newInstance();
                tran = getFragmentManager().beginTransaction();
                tran.replace(R.id.main_fragment_container, eventAddFragment);
                tran.addToBackStack("");
                tran.commit();

                return true;
            case R.id.main_edit:
                EventEditFragment eventEditFragment = EventEditFragment.newInstance(Model.instace.getModelMem().getEventId());
                tran = getFragmentManager().beginTransaction();
                tran.replace(R.id.main_fragment_container, eventEditFragment);
                tran.addToBackStack("");
                tran.commit();

                return true;

            case R.id.main_delete:
                if (getFragmentManager().getBackStackEntryCount() >= 1){
                    currentFragment = getFragmentManager().findFragmentById(R.id.main_fragment_container);
                    if(currentFragment instanceof EventEditFragment){
                        Model.instace.deleteEventItem(((EventEditFragment) currentFragment).getEventId());
                    }else if(currentFragment instanceof EventDetailsFragment){
                        Model.instace.deleteEventItem(((EventDetailsFragment) currentFragment).getEventId());
                        ((EventDetailsFragment) currentFragment).getmListener().onDetailsEventInteraction();
                    }
                }else {
                    Log.d("TAG","MainActivity  R.id.main_delete getFragmentManager().getBackStackEntryCount() < 1");
                }


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public void onEventClickInteraction(String eventID) {
        EventDetailsFragment eventDetailsFragment = EventDetailsFragment.newInstance(eventID);
        FragmentTransaction tran = getFragmentManager().beginTransaction();
        tran.replace(R.id.main_fragment_container, eventDetailsFragment);
        tran.addToBackStack("");
        tran.commit();
        getActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onSaveEventInteraction() {
        cleanBackStack();
    }

    @Override
    public void onDeleteEventInteraction() {

    }

    @Override
    public void onCancelEventInteraction() {
        cleanBackStack();
    }

    @Override
    public void onAddEventInteraction()
    {

        cleanBackStack();
    }

    @Override
    public void onDetailsEventInteraction() {
        cleanBackStack();
    }

    void cleanBackStack() {
        int backStackCount = getFragmentManager().getBackStackEntryCount();

        for (int i = 0; i < backStackCount; i++) {
            int backStackID = getFragmentManager().getBackStackEntryAt(i).getId();
            getFragmentManager().popBackStack(backStackID, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }


}
