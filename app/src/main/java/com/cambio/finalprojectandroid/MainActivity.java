package com.cambio.finalprojectandroid;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.cambio.finalprojectandroid.model.Model;

public class MainActivity extends Activity implements EventListFragment.OnFragmentInteractionListener, EventEditFragment.OnFragmentInteractionListener, EventAddFragment.OnFragmentInteractionListener, EventDetailsFragment.OnFragmentInteractionListener {
    EventListFragment eventListFragment;

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

        if (getFragmentManager().findFragmentById(R.id.main_fragment_container) instanceof EventListFragment){
            getMenuInflater().inflate(R.menu.add_list_item, menu);
        } else if (getFragmentManager().findFragmentById(R.id.main_fragment_container) instanceof EventDetailsFragment){
            getMenuInflater().inflate(R.menu.edit_details_item, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentTransaction tran = null;
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.main_add:
                EventAddFragment eventAddFragment = EventAddFragment.newInstance();
                tran = getFragmentManager().beginTransaction();
                tran.replace(R.id.main_fragment_container, eventAddFragment);
                tran.addToBackStack("");
                tran.commit();

                return true;
            case R.id.main_edit:
                EventEditFragment eventEditFragment = EventEditFragment.newInstance(Model.instace.getEventId());
                tran = getFragmentManager().beginTransaction();
                tran.replace(R.id.main_fragment_container, eventEditFragment);
                tran.addToBackStack("");
                tran.commit();
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
        tran.commit();

    }

    @Override
    public void onSaveEventInteraction() {

    }

    @Override
    public void onDeleteEventInteraction() {

    }

    @Override
    public void onCancelEventInteraction() {

    }

    @Override
    public void onAddEventInteraction() {
        cleanBackStack();
    }


    @Override
    public void onEditEventInteraction() {

    }

    void cleanBackStack(){
        int backStackCount = getFragmentManager().getBackStackEntryCount();

        for(int i=0; i < backStackCount; i++) {
            int backStackID = getFragmentManager().getBackStackEntryAt(i).getId();
            getFragmentManager().popBackStack(backStackID, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}
