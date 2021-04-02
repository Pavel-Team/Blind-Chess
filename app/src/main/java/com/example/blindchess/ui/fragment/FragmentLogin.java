/**Фрагмент с окном входа*/
package com.example.blindchess.ui.fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.blindchess.R;

public class FragmentLogin extends Fragment {

    private EditText editTextLogin;    //Поле ввода логина
    private EditText editTextPassword; //Поле ввода пароля
    private Button buttonLogin;        //Кнопка входа
    private Button buttonRegister;     //Кнопка регистрации


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View thisView = getView(); //Текущий view
        //Инициализируем объекты кнопок
        buttonLogin = thisView.findViewById(R.id.button_login);
        buttonRegister = thisView.findViewById(R.id.button_registration);
        //И EditText
        editTextLogin = thisView.findViewById(R.id.edit_text_login);
        editTextPassword = thisView.findViewById(R.id.edit_text_password);

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

        //Обработчик последнего ентера при вводе пароля (если пользователь закончил заполнять пароль и нажал ентер) - нажимается кнопка "Войти"
        editTextPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    //buttonName.performClick();
                    System.out.println("Нажата кнопка входа");
                    return true;
                }
                return false;
            }
        });

    }

}
