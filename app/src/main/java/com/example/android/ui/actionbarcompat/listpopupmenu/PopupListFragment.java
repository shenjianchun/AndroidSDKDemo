package com.example.android.ui.actionbarcompat.listpopupmenu;

import com.example.android.R;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

import my.nouilibrary.utils.T;

/**
 * Created by 14110105 on 2016-03-07.
 */
public class PopupListFragment extends ListFragment implements View.OnClickListener {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ArrayList<String> items = new ArrayList<>();

        Collections.addAll(items, Cheeses.CHEESES);

        setListAdapter(new PopupAdapter(items));
    }


    @Override
    public void onListItemClick(ListView listView, View v, int position, long id) {
        String item = listView.getItemAtPosition(position).toString();
        T.showShort(getActivity(), "Select Item: " + item);
    }

    @Override
    public void onClick(View v) {

        showPopupMenu(v);

    }


    private void showPopupMenu(final View v) {
        final PopupAdapter adapter = (PopupAdapter) getListAdapter();



        PopupMenu popupMenu = new PopupMenu(getActivity(), v);
        popupMenu.inflate(R.menu.menu_actionbarcompat_popup);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_remove:
                        adapter.remove((String) v.getTag());
                        return true;
                }

                return false;
            }
        });

        popupMenu.show();

    }

    private class PopupAdapter extends ArrayAdapter<String> {


        public PopupAdapter(ArrayList<String> items) {
            super(getActivity(), R.layout.item_list_popupmenu, R.id.tv_text, items);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);

            View popupButton = view.findViewById(R.id.iv_popup);

            popupButton.setTag(getItem(position));

            popupButton.setOnClickListener(PopupListFragment.this);

            return view;
        }
    }

}
