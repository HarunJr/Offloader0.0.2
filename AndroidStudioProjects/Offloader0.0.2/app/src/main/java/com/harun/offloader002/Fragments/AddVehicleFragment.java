package com.harun.offloader002.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.harun.offloader002.activities.MainActivity;
import com.harun.offloader002.Constants;
import com.harun.offloader002.tasks.PostToServerTask;
import com.harun.offloader002.R;

public class AddVehicleFragment extends Fragment {
    public static final String LOG_TAG = AddVehicleFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    protected EditText mVehicleInput;
    protected Button mCreateVehicleButton;

//    private OnFragmentInteractionListener mListener;

    public AddVehicleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddVehicle.
     */
    // TODO: Rename and change types and number of parameters
    public static AddVehicleFragment newInstance(String param1, String param2) {
        AddVehicleFragment fragment = new AddVehicleFragment();
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

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_vehicle, container, false);
        Constants.toolbar = (Toolbar) rootView.findViewById(R.id.add_Vehicle_tool_bar);

        mVehicleInput = (EditText) rootView.findViewById(R.id.vehicle_input);
        mCreateVehicleButton = (Button) rootView.findViewById(R.id.create_vehicle_button);

        mCreateVehicleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String method = "post";
                String vehicleRegistration = mVehicleInput.getText().toString();
                String dateTime = String.valueOf(System.currentTimeMillis());
                Log.w(LOG_TAG, "create button clicked "+vehicleRegistration +": "+dateTime);

                PostToServerTask postToServerTask = new PostToServerTask(getContext());
                postToServerTask.execute(method, vehicleRegistration, dateTime);

                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int title = R.string.create_vehicle_label;

        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(Constants.toolbar);

        assert appCompatActivity.getSupportActionBar() != null;
        appCompatActivity.getSupportActionBar().setHomeButtonEnabled(true);
        appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appCompatActivity.getSupportActionBar().setTitle(title);

        Log.w(LOG_TAG, "Toolbar has been added");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){

            startActivity(new Intent(getContext(), MainActivity.class));
            Log.w(LOG_TAG, "Home button clicked");
        }

        return super.onOptionsItemSelected(item);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
