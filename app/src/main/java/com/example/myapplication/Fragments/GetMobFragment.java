package com.example.myapplication.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.myapplication.R;


public class GetMobFragment extends Fragment {
    Button btnGetCode;
    TextInputEditText inputMob;
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_get_mob, container, false);
        btnGetCode = rootView.findViewById(R.id.btn_get_code);
        inputMob = rootView.findViewById(R.id.mobile_edittext);
        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   String number =  inputMob.getText().toString();
                    if(!number.startsWith("09") || number.length() != 11 ){
                        inputMob.setError("خطا! شماره صحیح نیست");
                        inputMob.requestFocus();
                    }
                    else {
                        getFragmentManager().beginTransaction().replace(R.id.login_fragment_container,new ConfirmMobFragment().newInstance(number)).commit();
                    }
            }
        });
        return rootView;
    }
}
