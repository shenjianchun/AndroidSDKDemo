package com.example.android.ui.menu;

import com.example.android.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import my.nouilibrary.utils.T;

/**
 * Demonstrates inflating menus from XML. There are different menu XML resources
 * that the user can choose to inflate. First, select an example resource from
 * the spinner, and then hit the menu button. To choose another, back out of the
 * activity and start over.
 * Created by 14110105 on 2016-03-22.
 */
public class MenuInflateFromXml extends AppCompatActivity {

    /**
     * Names corresponding to the different example menu resources.
     */
    private static final String sMenuExampleNames[] = {
            "Title only", "Title and Icon", "Submenu", "Groups",
            "Checkable", "Shortcuts", "Order", "Category and Order",
            "Visible", "Disabled"
    };

    /**
     * Different example menu resources.
     */
    private static final int sMenuExampleResources[] = {
            R.menu.title_only, R.menu.title_icon, R.menu.submenu, R.menu.groups,
            R.menu.checkable, R.menu.shortcuts, R.menu.order, R.menu.category_order,
            R.menu.visible, R.menu.disabled
    };

    Spinner mSpinner;
    Menu mMenu;
    TextView mInstructionsText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        layout.setPadding(16, 16, 16, 16);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, sMenuExampleNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner = new Spinner(this);
        mSpinner.setAdapter(arrayAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                invalidateOptionsMenu();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mInstructionsText = new TextView(this);
        mInstructionsText.setText(R.string.menu_from_xml_instructions_press_menu);

        // Add the help, make it look decent
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10, 10, 10, 10);


        layout.addView(mSpinner);
        layout.addView(mInstructionsText, lp);

        setContentView(layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(sMenuExampleResources[mSpinner.getSelectedItemPosition()], menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_jump:
                T.showShort(this, "Jump up in the air!");
                return true;

            case R.id.menu_dive:
                T.showShort(this, "Dive into the water!");
                return true;

            case R.id.browser_visibility:
                final boolean shouldShowBrowser = mMenu.findItem(R.id.refresh).isVisible();
                mMenu.setGroupVisible(R.id.browser_visibility, !shouldShowBrowser);

            case R.id.email_visibility:
                // The reply item is part of the email group
                final boolean shouldShowEmail = !mMenu.findItem(R.id.reply).isVisible();
                mMenu.setGroupVisible(R.id.email, shouldShowEmail);
                break;

            case R.id.checkable_item_1:
                item.setChecked(!item.isChecked());
                return true;

        }

        return false;
    }
}
