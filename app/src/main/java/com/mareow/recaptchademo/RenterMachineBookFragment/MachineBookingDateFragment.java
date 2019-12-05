package com.mareow.recaptchademo.RenterMachineBookFragment;


import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener;
import com.applikeysolutions.cosmocalendar.selection.RangeSelectionManager;
import com.applikeysolutions.cosmocalendar.utils.SelectionType;
import com.mareow.recaptchademo.R;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MachineBookingDateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MachineBookingDateFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    com.applikeysolutions.cosmocalendar.view.CalendarView newCalender;
    AppCompatButton btnSave;
    public MachineBookingDateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MachineBookingDateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MachineBookingDateFragment newInstance(String param1, String param2) {
        MachineBookingDateFragment fragment = new MachineBookingDateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_machine_booking_date, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        newCalender=(com.applikeysolutions.cosmocalendar.view.CalendarView)view.findViewById(R.id.sir_calenderview);
        btnSave=(AppCompatButton)view.findViewById(R.id.sir_calenderview_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newCalender.getSelectedDates();
                if (newCalender.getSelectedDates().size()==0){
                    Toast.makeText(getActivity(), "Please Select date", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (int i=0;i<newCalender.getSelectedDates().size();i++){
                    Toast.makeText(getActivity(), "Date"+i, Toast.LENGTH_SHORT).show();
                }

                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        newCalender.setSelectionType(SelectionType.RANGE);
       /* newCalender.setSelectedDayBackgroundColor(R.color.colorPrimary);*/
       /* newCalender.setSelectedDayBackgroundStartColor(R.color.colorPrimary)*/;
       /* newCalender.setSelectedDayBackgroundEndColor(R.color.colorPrimary);*/
        newCalender.setSelectionManager(new RangeSelectionManager(new OnDaySelectedListener() {
            @Override
            public void onDaySelected() {
                Date date=newCalender.getSelectedDays().get(newCalender.getSelectedDates().size()-1).getCalendar().getTime();
                Toast.makeText(getContext(), date.toString(), Toast.LENGTH_SHORT).show();
            }
        }));

      /*  Calendar calendar = Calendar.getInstance();
        Set<Long> days = new TreeSet<>();
        days.add(calendar.getTimeInMillis());

        calendar.add(Calendar.DATE,5);
        days.add(calendar.getTimeInMillis());

        int textColor = Color.parseColor("#ff0000");
        int selectedTextColor = Color.parseColor("#ff4000");
        int disabledTextColor = Color.parseColor("#ff8000");
        ConnectedDays connectedDays = new ConnectedDays(days, textColor, selectedTextColor, disabledTextColor);*/

        //Connect days to calendar
       // newCalender.addConnectedDays(connectedDays);





    }

}
