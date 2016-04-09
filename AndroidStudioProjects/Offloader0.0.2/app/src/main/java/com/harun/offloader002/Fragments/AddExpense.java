package com.harun.offloader002.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.harun.offloader002.PostTransactionsToServer;
import com.harun.offloader002.R;
import com.harun.offloader002.activities.DetailsActivity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnSendExpenseListener} interface
 * to handle interaction events.
 * Use the {@link AddExpense#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddExpense extends Fragment {
    private static final String LOG_TAG = AddExpense.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String VEHICLE_REG = "param3";

    protected EditText mExpenseInput;
    protected Button mAddExpenseButton;

    // TODO: Rename and change types of parameters
    private int type;
    private int vehicleId;
    private String vehicleReg;

    private OnSendExpenseListener mListener;

    public AddExpense() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddExpense newInstance(int type, int vehicleId, String vehicleReg) {
        AddExpense fragment = new AddExpense();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, type);
        args.putInt(ARG_PARAM2, vehicleId);
        args.putString(VEHICLE_REG, vehicleReg);
        fragment.setArguments(args);
        Log.w(LOG_TAG, "AddExpense: "+type+", "+vehicleId);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(ARG_PARAM1);
            vehicleId = getArguments().getInt(ARG_PARAM2);
            vehicleReg = getArguments().getString(VEHICLE_REG);
            Log.w(LOG_TAG, "onCreate: "+type+", "+vehicleId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_expense, container, false);

        mExpenseInput = (EditText) rootView.findViewById(R.id.expense_input);
        mAddExpenseButton = (Button) rootView.findViewById(R.id.add_expense_button);
        showSoftKeyboard(mExpenseInput);

        mAddExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(mExpenseInput);
                String method = "transact";
                String collection = mExpenseInput.getText().toString();
                String stringVehicleId = String.valueOf(vehicleId);
                String stringType = String.valueOf(type);
                String description = "This is an Expense";
                String dateTime = String.valueOf(System.currentTimeMillis());
                Log.w(LOG_TAG, "create button clicked "+collection +": "+dateTime);

                PostTransactionsToServer postToServerTask = new PostTransactionsToServer(getContext());
                postToServerTask.execute(method, stringVehicleId, collection, stringType, description,  dateTime);

                startActivity(new Intent(getContext(), DetailsActivity.class));

                ((OnSendExpenseListener) getActivity()).onExpenseButtonClicked(vehicleId, vehicleReg);

            }
        });

        return rootView;

    }

    public void showSoftKeyboard(View view) {
        if (mExpenseInput.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput( InputMethodManager.SHOW_IMPLICIT, 0);
        }
    }

    public void hideSoftKeyboard(View view) {
        if (mExpenseInput.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
        }
    }


//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    public interface OnSendExpenseListener {
        // TODO: Update argument type and name
        void onExpenseButtonClicked(int vehicleId, String vehicleReg);
    }
}
