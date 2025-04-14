package com.example.shopifygp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shopifygp.Database.User;
import com.example.shopifygp.Database.UserDao;
import com.example.shopifygp.MyApplication;
import com.example.shopifygp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class ProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    TextView fullNameTextBox;
    TextView emailTextBox;
    TextView phoneTextBox;
    TextView genderTextBox;
    // Firebase
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    private UserDao userDao = MyApplication.getUserDao();

    public ProfileFragment() {

    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        // Getting Views
        fullNameTextBox = view.findViewById(R.id.fullNameTextBox);
        emailTextBox = view.findViewById(R.id.emailTextBox);
        phoneTextBox = view.findViewById(R.id.phoneTextBox);
        genderTextBox = view.findViewById(R.id.genderTextBox);


        // Firebase
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        User user = userDao.getUser(firebaseUser.getEmail());
        fullNameTextBox.setText(user.getFullName());
        emailTextBox.setText(user.getEmail());
        phoneTextBox.setText(user.getPhoneNumber());
        genderTextBox.setText(user.getGender());
        return view;
    }
}