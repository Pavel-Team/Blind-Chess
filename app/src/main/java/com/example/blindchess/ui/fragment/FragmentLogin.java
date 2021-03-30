/**Фрагмент с окном входа*/
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

public class FragmentLogin extends Fragment {

    private Button buttonLogin;    //Кнопка входа
    private Button buttonRegister; //Кнопка регистрации


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Инициализируем объекты кнопок
        buttonLogin = getView().findViewById(R.id.button_login);
        buttonRegister = getView().findViewById(R.id.button_registration);

        //Листенер для кнопки "Войти" (ВРЕМЕННО)
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //...
                //Navigation.findNavController(getActivity(), R.id.main_container).navigate(R.id.action_fragmentLogin_to_fragmentMainMenu);
            }
        });

        //Листенер для кнопки "Регистрация"
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(), R.id.main_container).navigate(R.id.action_fragmentLogin_to_fragmentRegistration);
            }
        });

    }

}
