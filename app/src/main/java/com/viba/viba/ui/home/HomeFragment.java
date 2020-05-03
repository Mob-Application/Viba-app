package com.viba.viba.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.viba.viba.MainActivity;
import com.viba.viba.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    FirebaseAuth mAuth;
    Button logout;
    ViewFlipper v_flipper;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        // final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
                mAuth = FirebaseAuth.getInstance();
                logout = (Button) getView().findViewById(R.id.button4);
                logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        start();
                    }
                });
                int images[]={R.drawable.group1,R.drawable.group2 ,R.drawable.group11};
                v_flipper=getView().findViewById(R.id.v_flipper);
                for(int i=0;i<images.length;i++){
                    flipperimages(images[i]);
                }
            }
        });
        return root;
    }
    public void start(){
        mAuth.signOut();
        Intent intent = new Intent(HomeFragment.this.getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
    public void flipperimages(int images){
        ImageView imageView=new ImageView(getContext());
        imageView.setBackgroundResource(images);
        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(4000);
        v_flipper.setAutoStart(true);


    }
}

