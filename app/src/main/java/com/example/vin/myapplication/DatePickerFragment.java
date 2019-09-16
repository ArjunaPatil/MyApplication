package com.example.vin.myapplication;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends android.support.v4.app.DialogFragment {

    private DatePicker datePicker;

    public interface DateDialogListener {
        void onFinishDialog(Date date);
    }

    /*@Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);
        datePicker = (DatePicker) v.findViewById(R.id.dialog_date_date_picker);
        datePicker.init(2012,1,1,  new DatePicker.OnDateChangedListener(){

                    public void onDateChanged(DatePicker view, int yy, int mm, int dd){
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(yy, mm, dd);
                        int doy = calendar.get(Calendar.DAY_OF_YEAR);
                        populateDayOfYear(doy);
                    }
                }
        );
    }*/

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        try {
            View v = LayoutInflater.from(getActivity())
                    .inflate(R.layout.dialog_date, null);
            datePicker = (DatePicker) v.findViewById(R.id.dialog_date_date_picker);
            return new android.support.v7.app.AlertDialog.Builder(getActivity())
                    .setView(v)
                    .setTitle("Select Date")/*setTitle(R.string.date_picker_title);*/
                    .setPositiveButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        int year = datePicker.getYear();
                                        int mon = datePicker.getMonth();
                                        int day = datePicker.getDayOfMonth();

                                        Calendar calendar = Calendar.getInstance();
                                        calendar.clear();
                                        calendar.set(Calendar.DAY_OF_MONTH, day);
                                        calendar.set(Calendar.MONTH, mon);
                                        calendar.set(Calendar.YEAR, year);
                                        Date date = calendar.getTime();
                                        // Date date = new GregorianCalendar(year, mon, day).getTime();
                                        DateDialogListener activity = (DateDialogListener) getTargetFragment();
                                        activity.onFinishDialog(date);
                                        dismiss();
                                    } catch (Exception ex) {
                                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            })
                    .create();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            return null;
        }
    }
}

/*https://www.codeproject.com/Articles/1112812/Android-Alert-Dialog-Tutorial-Working-with-Time-Pi*/