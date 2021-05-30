/**Фрагмент с окном регистрации*/
package com.example.blindchess.ui.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.blindchess.R;
import com.example.blindchess.ui.sqlite.DBHelper;

import java.util.regex.Pattern;

public class FragmentRegistration extends Fragment {

    private Context context;                   //Контекст приложения
    private DBHelper db;                       //Объект базы данных SQLite

    private Button buttonCompleteRegister;     //Кнопка "Зарегистрироваться"
    private Button buttonLoginGuest;           //Кнопка "Войти как гость"
    private Button buttonLoginGoogle;          //Кнопка "Войти через Google"
    private EditText editTextNick;             //Поле ввода ника
    private EditText editTextLogin;            //Поле ввода логина
    private EditText editTextPassword;         //Поле ввода пароля
    private View viewCorrectnessNick;          //View подсветка правильности ввода ника
    private View viewCorrectnessLogin;         //View подсветка правильности ввода логина
    private View viewCorrectnessPassword;      //View подсветка правильности ввода пароля
    private TextView textViewErrorInput;       //TextView, в который выводится информация об ошибке ввода, если такова имеется

    private boolean isCorrectNick = false;     //Корректность введенного ника
    private boolean isCorrectLogin = false;    //Корректность введенного логина
    private boolean isCorrectPassword = false; //Корректность введеного пароля


    /**Функция добавления нового пользователя в БД SQLite
     * На вход принимает 2 параметра:
     * int id - ид пользователя (для гостя id = 1)
     * String name - имя пользователя (для гостя name = Guest)
     * Внутри происходит занесение пользователя в таблицы User и Achievement*/
    private void addNewUserToSQLite(int id, String name){

        //Получаем объекты нашей БД для дальнейшей записи информации в них
        db = new DBHelper(context);
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //Создаем транзакцию
        database.beginTransaction();
        try {
            //Заполняем таблицу User
            contentValues.put(DBHelper.KEY_ID_USER, id);
            contentValues.put(DBHelper.KEY_NAME_USER, name);
            contentValues.put(DBHelper.KEY_IMAGE_NAME_BACKGROUND, "background_board");
            contentValues.put(DBHelper.KEY_IMAGE_NAME_FOREGROUND, "foreground_pawn_white");
            contentValues.put(DBHelper.KEY_RATING_USER, 0);
            contentValues.put(DBHelper.KEY_WINS_USER, 0);
            contentValues.put(DBHelper.KEY_DEFEATS_USER, 0);
            contentValues.put(DBHelper.KEY_BEST_LEAGUE_USER, 15);
            contentValues.put(DBHelper.KEY_LEAGUE_USER, 15);
            contentValues.put(DBHelper.KEY_LEAGUE_WINS_USER, 0);
            contentValues.put(DBHelper.KEY_LEAGUE_DEFEATS_USER, 0);
            contentValues.put(DBHelper.KEY_IS_LOGIN_USER, 1);
            database.insert(DBHelper.TABLE_NAME_USER, null, contentValues);
            contentValues.clear();

            //Заполняем таблицу Achievement
            String sql = "INSERT INTO ACHIEVEMENT VALUES(?, ?, ?, ?, ?, ?, ?);";
            SQLiteStatement statement = database.compileStatement(sql);
            statement.clearBindings(); //Очищаем все текущие привязки
            statement.bindLong(1, id); //Привязываем данные к столбцам
            statement.bindString(2, getResources().getString(R.string.achievement_title_first_game));
            statement.bindString(3, getResources().getString(R.string.achievement_description_first_game));
            statement.bindString(4, getResources().getString(R.string.achievement_image_name_first_game));
            statement.bindLong(5, 0);
            statement.bindLong(6, 1);
            statement.bindLong(7, 0);
            statement.execute();
            statement.clearBindings();
            statement.bindLong(1, id);
            statement.bindString(2, getResources().getString(R.string.achievement_title_first_win));
            statement.bindString(3, getResources().getString(R.string.achievement_description_first_win));
            statement.bindString(4, getResources().getString(R.string.achievement_image_name_first_win));
            statement.bindLong(5, 0);
            statement.bindLong(6, 1);
            statement.bindLong(7, 0);
            statement.execute();

            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }

        //Закрываем соединение
        database.close();
        db.close();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        context = getContext();
        View thisView = getView(); //Текущий view
        textViewErrorInput = thisView.findViewById(R.id.text_view_error_input_in_registration);
        //Инициализируем объекты кнопок
        buttonCompleteRegister = thisView.findViewById(R.id.button_complete_registration);
        buttonLoginGuest = thisView.findViewById(R.id.button_login_guest);
        buttonLoginGoogle = thisView.findViewById(R.id.button_login_google);
        //И EditText
        editTextNick = thisView.findViewById(R.id.edit_text_registration_nick);
        editTextLogin = thisView.findViewById(R.id.edit_text_registration_login);
        editTextPassword = thisView.findViewById(R.id.edit_text_registration_password);
        //И View
        viewCorrectnessNick = thisView.findViewById(R.id.view_correctness_nickname);
        viewCorrectnessLogin = thisView.findViewById(R.id.view_correctness_login);
        viewCorrectnessPassword = thisView.findViewById(R.id.view_correctness_password);

        //Листенер для кнопки "Зарегистрироваться"
        buttonCompleteRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Проверяем на корректность всех данных - если все успешно, отправляем запрос на сервер и переходим в главное меню
                if (isCorrectNick && isCorrectLogin && isCorrectPassword) {
                    //...отправка запроса на сервер
                    Navigation.findNavController(getActivity(), R.id.main_container).navigate(R.id.action_fragmentRegistration_to_fragmentMainMenu);
                }
            }
        });

        //Листенер для кнопки "Войти как гость"
        buttonLoginGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Проверяем, есть ли пользователь в SQLite: если есть - заходим, делая isLogin = true, иначе - заносим новые данные в БД
                db = new DBHelper(getContext());
                SQLiteDatabase database = db.getWritableDatabase();
                Cursor cursor = database.query(DBHelper.TABLE_NAME_USER, new String[]{DBHelper.KEY_ID_USER}, null, null, null, null, null);
                cursor.moveToFirst();
                //Если пользователь есть - просто изменяем значение isLogin в таблице
                if (cursor != null && cursor.getCount() > 0) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DBHelper.KEY_IS_LOGIN_USER, 1);
                    database.update(DBHelper.TABLE_NAME_USER, contentValues, DBHelper.KEY_ID_USER + "=" + String.valueOf(cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ID_USER))), null);
                } else {
                    //Если пользователя нету - заносим новую запись в БД
                    addNewUserToSQLite(1, "Guest");
                }

                //Закрываем соединения
                cursor.close();
                database.close();
                db.close();

                //Переходим на фрагмент
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


        //Создаем регулярные выражения для EditText
        Pattern patternNick = Pattern.compile("^[А-Яа-яa-zA-Z_0-9]+$");
        Pattern patternLoginAndPassword = Pattern.compile("^[a-zA-Z_0-9]+$");


        //Листенер для ввода ника
        editTextNick.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Если не удовлетворяет допустимым символам - выводим ошибку, иначе проверяем размер введенного сообщения
                if (!patternNick.matcher(s).matches()) {
                    viewCorrectnessNick.setBackgroundResource(R.drawable.vertical_red_line);
                    textViewErrorInput.setText(R.string.error_input_nick_symbols);
                } else {
                    if (s.toString().length() < 4) {
                        viewCorrectnessNick.setBackgroundResource(R.drawable.vertical_red_line);
                        textViewErrorInput.setText(R.string.error_input_nick_size);
                    } else {
                        viewCorrectnessNick.setBackgroundResource(R.drawable.vertical_green_line);
                        textViewErrorInput.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        //Листенер для ввода логина
        editTextLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Если не удовлетворяет допустимым символам - выводим ошибку, иначе проверяем размер введенного сообщения
                if (!patternLoginAndPassword.matcher(s).matches()) {
                    viewCorrectnessLogin.setBackgroundResource(R.drawable.vertical_red_line);
                    textViewErrorInput.setText(R.string.error_input_login_symbols);
                } else {
                    if (s.toString().length() < 6) {
                        viewCorrectnessLogin.setBackgroundResource(R.drawable.vertical_red_line);
                        textViewErrorInput.setText(R.string.error_input_login_size);
                    } else {
                        viewCorrectnessLogin.setBackgroundResource(R.drawable.vertical_green_line);
                        textViewErrorInput.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        //Листенер для ввода пароля
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Если не удовлетворяет допустимым символам - выводим ошибку, иначе проверяем размер введенного сообщения
                if (!patternLoginAndPassword.matcher(s).matches()) {
                    viewCorrectnessPassword.setBackgroundResource(R.drawable.vertical_red_line);
                    textViewErrorInput.setText(R.string.error_input_password_symbols);
                } else {
                    if (s.toString().length() < 6) {
                        viewCorrectnessPassword.setBackgroundResource(R.drawable.vertical_red_line);
                        textViewErrorInput.setText(R.string.error_input_password_size);
                    } else {
                        viewCorrectnessPassword.setBackgroundResource(R.drawable.vertical_green_line);
                        textViewErrorInput.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        //Обработчик последнего ентера при вводе пароля (если пользователь закончил заполнять пароль и нажал ентер) - нажимается кнопка "Регистрация"
        editTextPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    //buttonName.performClick();
                    System.out.println("Нажата кнопка регистрации");
                    return true;
                }
                return false;
            }
        });

    }
}
