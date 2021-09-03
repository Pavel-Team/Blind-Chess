/**Фрагмент с окном регистрации*/
package com.example.blindchess.ui.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.blindchess.BR;
import com.example.blindchess.R;
import com.example.blindchess.databinding.FragmentRegistrationBinding;
import com.example.blindchess.model.Registration;
import com.example.blindchess.sqlite.DBHelper;
import com.example.blindchess.viewModel.RegistrationViewModel;

import java.util.regex.Pattern;


public class FragmentRegistration extends Fragment {

    private Context context;                    //Контекст приложения
    private DBHelper db;                        //Объект базы данных SQLite

    private EditText editTextPassword;          //Поле ввода пароля
    private View viewCorrectnessNick;           //View подсветка правильности ввода ника
    private View viewCorrectnessLogin;          //View подсветка правильности ввода логина
    private View viewCorrectnessPassword;       //View подсветка правильности ввода пароля

    private RegistrationViewModel viewModel;    //ViewModel для данного экрана
    private FragmentRegistrationBinding binding;//Binding для связывания DataBinding и ViewModel
    private Registration liveModel;             //Текущая модель привзяки данных

    private Pattern patternNick = Pattern.compile("^[А-Яа-яa-zA-Z_0-9]+$");       //Регулярное выражение для EditText имени
    private Pattern patternLoginAndPassword = Pattern.compile("^[a-zA-Z_0-9]+$"); //Регулярное выражение для EditText логина и пароля


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Устанавливаем ViewModel
        viewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);

        //Инициализация всех View
        View root = inflater.inflate(R.layout.fragment_registration, container, false);

        //Привязываем DataBinding и ViewModel
        binding = FragmentRegistrationBinding.bind(root);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setVariable(BR.registrationViewModel, viewModel);
        binding.setVariable(BR.registrationFragment, this);

        liveModel = viewModel.getLiveData().getValue();

        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //ВРЕМЕННО инициализируем необходимые VIEW
        context = getContext();
        View thisView = getView();
        editTextPassword = thisView.findViewById(R.id.edit_text_registration_password);
        viewCorrectnessNick = thisView.findViewById(R.id.view_correctness_nickname);
        viewCorrectnessLogin = thisView.findViewById(R.id.view_correctness_login);
        viewCorrectnessPassword = thisView.findViewById(R.id.view_correctness_password);


        //ВРЕМЕННО
        //Обработчик последнего ентера при вводе пароля (если пользователь закончил заполнять пароль и нажал ентер) - нажимается кнопка "Регистрация"
        editTextPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    //buttonName.performClick();
                    System.out.println("Нажата кнопка регистрации");
                    onClickButtonRegistration();
                    return true;
                }
                return false;
            }
        });

    }


    /**Метод прослушивания изменения текста в поле "Имя"*/
    public void onTextChangedName(){
        String text = liveModel.getNameRegistration();

        //Если не удовлетворяет допустимым символам - выводим ошибку, иначе проверяем размер введенного сообщения
        if (!patternNick.matcher(text).matches()) {
            viewCorrectnessNick.setBackgroundResource(R.drawable.vertical_red_line);
            liveModel.setErrorInputRegistration(getString(R.string.error_input_nick_symbols));
        } else {
            if (text.toString().length() < 4) {
                viewCorrectnessNick.setBackgroundResource(R.drawable.vertical_red_line);
                liveModel.setErrorInputRegistration(getString(R.string.error_input_nick_size));
            } else {
                viewCorrectnessNick.setBackgroundResource(R.drawable.vertical_green_line);
                liveModel.setErrorInputRegistration("");
            }
        }
    }


    /**Метод прослушивания изменения текста в поле "Email"*/
    public void onTextChangedEmail(){
        String text = liveModel.getEmailRegistration();

        //Если не удовлетворяет допустимым символам - выводим ошибку, иначе проверяем размер введенного сообщения
        if (!patternLoginAndPassword.matcher(text).matches()) {
            viewCorrectnessLogin.setBackgroundResource(R.drawable.vertical_red_line);
            liveModel.setErrorInputRegistration(getString(R.string.error_input_login_symbols));
        } else {
            if (text.toString().length() < 6) {
                viewCorrectnessLogin.setBackgroundResource(R.drawable.vertical_red_line);
                liveModel.setErrorInputRegistration(getString(R.string.error_input_login_size));
            } else {
                viewCorrectnessLogin.setBackgroundResource(R.drawable.vertical_green_line);
                liveModel.setErrorInputRegistration("");
            }
        }
    }


    /**Метод прослушивания изменения текста в поле "Пароль"*/
    public void onTextChangedPassword(){
        String text = liveModel.getPasswordRegistration();

        //Если не удовлетворяет допустимым символам - выводим ошибку, иначе проверяем размер введенного сообщения
        if (!patternLoginAndPassword.matcher(text).matches()) {
            viewCorrectnessPassword.setBackgroundResource(R.drawable.vertical_red_line);
            liveModel.setErrorInputRegistration(getString(R.string.error_input_password_symbols));
        } else {
            if (text.toString().length() < 6) {
                viewCorrectnessPassword.setBackgroundResource(R.drawable.vertical_red_line);
                liveModel.setErrorInputRegistration(getString(R.string.error_input_password_size));
            } else {
                viewCorrectnessPassword.setBackgroundResource(R.drawable.vertical_green_line);
                liveModel.setErrorInputRegistration("");
            }
        }
    }


    /**Метод нажатия на кнопку "Зарегистрироваться"*/
    public void onClickButtonRegistration() {
        String response = viewModel.onClickButtonRegistration();
        Toast toast;
        String error;

        switch(response) {
            case "ERROR_INPUT":
                error = getString(R.string.error_input);
                liveModel.setErrorInputRegistration(error);
                toast = Toast.makeText(getContext(), error, Toast.LENGTH_SHORT);
                toast.show();
                break;
            case "ACCOUNT_CREATED":
                Navigation.findNavController(getActivity(), R.id.main_container).navigate(R.id.action_fragmentRegistration_to_fragmentMainMenu);
                break;
            case "ERROR_EMAIL":
                error = getString(R.string.error_email);
                liveModel.setErrorInputRegistration(error);
                toast = Toast.makeText(getContext(), error, Toast.LENGTH_SHORT);
                toast.show();
                break;
            case "ERROR_NAME":
                error = getString(R.string.error_name);
                liveModel.setErrorInputRegistration(error);
                toast = Toast.makeText(getContext(), error, Toast.LENGTH_SHORT);
                toast.show();
                break;
            default:
                toast = Toast.makeText(getContext(), getString(R.string.error_server), Toast.LENGTH_SHORT);
                toast.show();
        }
    }


    /**Метод нажатия на кнопку "Войти как гость"*/
    public void onClickButtonLoginGuest() {
        viewModel.onClickButtonLoginGuest();

        //Переходим на нужный фрагмент
        Navigation.findNavController(getActivity(), R.id.main_container).navigate(R.id.action_fragmentRegistration_to_fragmentMainMenu);
    }


    /**Метод нажатия на кнопку "Войти через Google"*/
    public void onClickButtonLoginGoogle() {
        //String response = viewModel.onClickButtonLoginGoogle();
        //...if response
        //else
        Toast toast = Toast.makeText(getContext(), getString(R.string.block_function), Toast.LENGTH_SHORT);
        toast.show();
    }
}
