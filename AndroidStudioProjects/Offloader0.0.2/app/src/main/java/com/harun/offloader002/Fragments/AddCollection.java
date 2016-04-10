package com.harun.offloader002.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.harun.offloader002.PostToServerTask;
import com.harun.offloader002.R;


public class AddCollection extends Fragment {
    private static final String LOG_TAG = AddCollection.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TYPE = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String VEHICLE_REG = "param3";

    // TODO: Rename and change types of parameters
    private int type;
    private int vehicleId;
    private String vehicleReg;
    protected EditText mCollectionInput;
    protected Button mAddCollectionButton;

    public AddCollection() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddCollection newInstance(int type, int vehicleId, String vehicleReg) {
        AddCollection fragment = new AddCollection();
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        args.putInt(ARG_PARAM2, vehicleId);
        args.putString(VEHICLE_REG, vehicleReg);
        fragment.setArguments(args);
        Log.w(LOG_TAG, "AddCollection: "+type+", "+vehicleId);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt(TYPE);
            vehicleId = getArguments().getInt(ARG_PARAM2);
            vehicleReg = getArguments().getString(VEHICLE_REG);
            Log.w(LOG_TAG, "onCreate "+type+", "+vehicleId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_collection, container, false);

        mCollectionInput = (EditText) rootView.findViewById(R.id.collection_input);
        mAddCollectionButton = (Button) rootView.findViewById(R.id.add_collection_button);
        showSoftKeyboard(mCollectionInput);

        mAddCollectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(mCollectionInput);
                String method = "transact";
                String collection = mCollectionInput.getText().toString();
                String stringVehicleId = String.valueOf(vehicleId);
                String stringType = String.valueOf(type);
                String description = "This is a collection";
                String dateTime = String.valueOf(System.currentTimeMillis());
                Log.w(LOG_TAG, "create button clicked "+collection +": "+dateTime);

                PostToServerTask postToServerTask = new PostToServerTask(getContext());
                postToServerTask.execute(method, stringVehicleId, collection, stringType, description,  dateTime);

             //   startActivity(new Intent(getContext(), DetailsActivity.class));

                ((OnSendCollectionListener) getActivity()).onCollectionButtonClicked(vehicleId, vehicleReg);

            }
        });

        return rootView;
    }

    public void showSoftKeyboard(View view) {
        if (mCollectionInput.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput( InputMethodManager.SHOW_FORCED, 0);
        }
    }

    public void hideSoftKeyboard(View view) {
        if (mCollectionInput.requestFocus()) {
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

    public interface OnSendCollectionListener {
        // TODO: Update argument type and name
        void onCollectionButtonClicked(int vehicleId, String vehicleReg);
    }

}
