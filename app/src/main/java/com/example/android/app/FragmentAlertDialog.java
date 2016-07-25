package com.example.android.app;

import com.example.android.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Demonstrates how to show an AlertDialog that is managed by a Fragment.
 * Created by 14110105 on 2016-04-20.
 */
public class FragmentAlertDialog extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dialog);

        View tv = findViewById(R.id.text);
        ((TextView)tv).setText("Example of displaying an alert dialog with a DialogFragment");

        // Watch for button clicks.
        Button button = (Button)findViewById(R.id.show);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog();
            }
        });

    }


    void showDialog() {
        DialogFragment dialogFragment = MyAlertDialogFragment.newInstance(R.string.alert_dialog_two_buttons_title);
        dialogFragment.show(getFragmentManager(), "dialog");
    }


    public void doPositiveClick() {
        // Do stuff here.
        Log.i("FragmentAlertDialog", "Positive click!");
    }

    public void doNegativeClick() {
        // Do stuff here.
        Log.i("FragmentAlertDialog", "Negative click!");
    }

    public static class MyAlertDialogFragment extends DialogFragment {


        public static MyAlertDialogFragment newInstance(int title) {
            MyAlertDialogFragment fragment = new MyAlertDialogFragment();
            Bundle args = new Bundle();
            args.putInt("title", title);
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            int title = getArguments().getInt("title");

            return new AlertDialog.Builder(getActivity())
                    .setIcon(R.drawable.alert_dialog_icon)
                    .setTitle(title)
                    .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface
                            .OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((FragmentAlertDialog)getActivity()).doPositiveClick();

                        }
                    })
                    .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {


                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((FragmentAlertDialog)getActivity()).doNegativeClick();
                        }
                    })
                    .create();


        }
    }

}
