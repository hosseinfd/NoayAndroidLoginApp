package com.example.myapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.myapplication.Activity.Register;
import com.example.myapplication.R;

public class ConfirmMobFragment extends Fragment implements View.OnClickListener {
    Button btnConfrim;
    TextView mobNum, backToLogin, timer;
    TextInputEditText inputCode;
    String mob;

    public static ConfirmMobFragment newInstance(String mobNum) {
        ConfirmMobFragment mobFragment = new ConfirmMobFragment();
        Bundle bundle = new Bundle();
        bundle.putString("mobNum", mobNum);
        mobFragment.setArguments(bundle);
        return mobFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_confirm_mob, container, false);
        btnConfrim = rootView.findViewById(R.id.btn_confirm_code);
        mobNum = rootView.findViewById(R.id.tv_mob_number);
        inputCode = rootView.findViewById(R.id.input_code);
        backToLogin = rootView.findViewById(R.id.tv_back);
        timer = rootView.findViewById(R.id.tv_timer);

        backToLogin.setOnClickListener(this);
        mob = getArguments().getString("mobNum");
        String editedMob = "+98 " + mob.substring(1, 4) + " " + mob.substring(4, 7) + " " + mob.substring(7, 11) + ".";
        mobNum.setText(editedMob);
        btnConfrim.setOnClickListener(this);


        return rootView;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == backToLogin.getId()) {
            getFragmentManager().beginTransaction().replace(R.id.login_fragment_container, new GetMobFragment()).commit();

        } else if (id == btnConfrim.getId()) {

               /* if (isRegistered(mob)){  // check if the user is already registered in database  - if true : dont show register activity
                    new Intent();
                } */
                startActivity(new Intent(getActivity(), Register.class));
            }
    }
}
