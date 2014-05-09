package com.example.heartrateadjuster;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Class handling the UI popup for calculating target heartrate
 * @author Revan
 */
public class CalculateTargetFragment extends DialogFragment{
    NumberPicker np;
    SeekBar seekBar;
    TextView text;

    /**
     * Interface for communication between {@link com.example.heartrateadjuster.CalculateTargetFragment}
     * and {@link com.example.heartrateadjuster.MainActivity}
     */
    public interface CalculateListener{
        public void onDialogPositiveClick(DialogFragment dialog, int newTarget);
    }
    CalculateListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mListener = (CalculateListener) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_calculate, null);

        np = (NumberPicker)view.findViewById(R.id.numberPicker);
        np.setMinValue(1);
        np.setMaxValue(100);
        np.setValue(30);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                updatePrediction();
            }
        });

        seekBar = (SeekBar)view.findViewById(R.id.seekBar);
        seekBar.setMax(35);
        seekBar.setProgress(60);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updatePrediction();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        text = (TextView)view.findViewById(R.id.textTarget);

        builder.setView(view)
            .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mListener.onDialogPositiveClick(CalculateTargetFragment.this,
                            updatePrediction());
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CalculateTargetFragment.this.getDialog().cancel();
                }
            });

        updatePrediction();
        return builder.create();
    }

    /**
     * Calculates target heart rate based on age and intensity.
     * peak = (220-age) * percentage, where percentage is between 50% and 85%
     * http://www.urmc.rochester.edu/encyclopedia/content.aspx?ContentTypeID=1&ContentID=1209
     * @return new peak heartrate
     */
    private int updatePrediction(){
        int val = (220-np.getValue()) * (50 + seekBar.getProgress())/100;
        text.setText("" + val);
        return val;
    }


}
