package com.example.zeballos.weatherapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Zeballos on 7/7/15.
 */
public class Frag1 extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private EditText mEditTextMainWeather;
    private EditText mEditTextDescriptionWeather;
    private EditText mEditTextTemperature;
    private EditText mEditTextHumidity;
    private EditText mEditTextPressure;

    private Button mButton;
    private boolean mStarted;

    private String uriAPI;
    HandleJSON obj;

    public static Frag1 newInstance(int sectionNumber){
        Frag1 fragment = new Frag1();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_frag1, container, false);

        uriAPI = "http://api.openweathermap.org/data/2.5/weather?q=Cochabamba,BO";

        mEditTextMainWeather = (EditText) rootView.findViewById(R.id.editTextMainWeather);
        mEditTextDescriptionWeather = (EditText) rootView.findViewById(R.id.editTextDescriptionWeather);
        mEditTextTemperature = (EditText) rootView.findViewById(R.id.editTextTemperature);
        mEditTextHumidity = (EditText) rootView.findViewById(R.id.editTextHumidity);
        mEditTextPressure = (EditText) rootView.findViewById(R.id.editTextPressure);

        mButton = (Button) rootView.findViewById(R.id.buttonCurrent);
        mButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                String msg="Loading current weather";
                Toast toast = Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_LONG);
                toast.show();

                obj = new HandleJSON(uriAPI);
                obj.fetchJSON(1);

                while (!obj.parsingComplete);
                mEditTextMainWeather.setText(obj.getMainWeather());
                mEditTextDescriptionWeather.setText(obj.getDescriptionWeather());
                mEditTextTemperature.setText(obj.getTemperature());
                mEditTextHumidity.setText(obj.getHumidity());
                mEditTextPressure.setText(obj.getPressure());
            }
        });
        return rootView;
    }
}
