package com.example.android.ui.menu;

import com.example.android.R;
import com.uilibrary.app.BaseActivity;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;


/**
 * Created by 14110105 on 2016-03-23.
 */
public class ContextualActionMode extends BaseActivity {

    ActionMode mActionMode;

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.contextual_action_mode;
    }

    public void onCheck(View view) {
        if (((CheckBox) view).isChecked()) {
            mActionMode = startActionMode(mActionModeCallback);
        } else {
            mActionMode.finish();
        }
    }


    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {



        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {

            mode.setTitle("My Action Mode!");
            mode.setSubtitle("sub title");
            mode.setTitleOptionalHint(false);


            menu.add("Sort by size").setIcon(android.R.drawable.ic_menu_sort_by_size);
            menu.add("Sort by Alpha").setIcon(android.R.drawable.ic_menu_sort_alphabetically);

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };

}
