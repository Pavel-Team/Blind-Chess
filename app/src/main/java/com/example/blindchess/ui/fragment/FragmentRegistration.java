/**Фрагмент с окном регистрации*/
package com.example.blindchess.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.blindchess.R;

public class FragmentRegistration extends Fragment {

    private Button buttonCompleteRegister; //Кнопка "Зарегистрироваться"
    private Button buttonLoginGuest;       //Кнопка "Войти как гость"
    private Button buttonLoginGoogle;      //Кнопка "Войти через Google"


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Инициализируем объекты кнопок
        buttonCompleteRegister = getView().findViewById(R.id.button_complete_registration);
        buttonLoginGuest = getView().findViewById(R.id.button_login_guest);
        buttonLoginGoogle = getView().findViewById(R.id.button_login_google);

        //Листенер для кнопки "Зарегистрироваться"
        buttonCompleteRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //...
            }
        });

        //Листенер для кнопки "Войти как гость"
        buttonLoginGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //...
                Navigation.findNavController(getActivity(), R.id.main_container).navigate(R.id.action_fragmentRegistration_to_fragmentMainMenu);
            }
        });

        //Листенер для кнопки "Войти через Google"
        buttonLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //...
            }
        });

    }
}
