/**Фрагмент с окном входа*/
package com.example.blindchess.ui.fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.blindchess.BR;
import com.example.blindchess.R;
import com.example.blindchess.databinding.FragmentLoginBinding;
import com.example.blindchess.viewModel.LoginViewModel;


public class FragmentLogin extends Fragment {

    private LoginViewModel viewModel;     //ViewModel для данного экрана
    private FragmentLoginBinding binding; //Binding для связывания DataBinding и ViewModel


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Устанавливаем ViewModel
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        //Инициализация всех View
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        //Привязываем DataBinding и ViewModel
        binding = FragmentLoginBinding.bind(root);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setVariable(BR.loginViewModel, viewModel);
        binding.setVariable(BR.loginFragment, this);

        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //ВРЕМЕННО
        //Обработчик последнего ентера при вводе пароля (если пользователь закончил заполнять пароль и нажал ентер) - нажимается кнопка "Войти"
        getView().findViewById(R.id.edit_text_password).setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    //buttonName.performClick();
                    System.out.println("Нажата кнопка входа");
                    onClickButtonLogin();
                    return true;
                }
                return false;
            }
        });
    }


    /**Метод нажатия на кнопку "Войти"*/
    public void onClickButtonLogin() {
        String response = viewModel.onClickButtonLogin();
        Toast toast;

        switch (response) {
            case "NOT_FOUND":
                toast = Toast.makeText(getContext(), getString(R.string.error_login_email), Toast.LENGTH_SHORT);
                toast.show();
                break;
            case "ERROR_PASSWORD":
                toast = Toast.makeText(getContext(), getString(R.string.error_login_password), Toast.LENGTH_SHORT);
                toast.show();
                break;
            default:
                if (response != null) {
                    //...addToSQLite()
                    Navigation.findNavController(getActivity(), R.id.main_container).navigate(R.id.action_fragmentLogin_to_fragmentMainMenu);
                } else {
                    toast = Toast.makeText(getContext(), getString(R.string.error_server), Toast.LENGTH_SHORT);
                    toast.show();
                }
        }
    }


    /**Метод нажатия на кнопку "Регистрация"*/
    public void onClickButtonRegister() {
        Navigation.findNavController(getActivity(), R.id.main_container).navigate(R.id.action_fragmentLogin_to_fragmentRegistration);
    }

}
