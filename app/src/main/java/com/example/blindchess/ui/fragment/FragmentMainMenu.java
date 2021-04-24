/**Фрагмент главного меню*/
package com.example.blindchess.ui.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.blindchess.R;
import com.example.blindchess.ui.sqlite.DBHelper;

public class FragmentMainMenu extends Fragment {

    private Button buttonQuickGame; //Кнопка быстрой игры


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_menu, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        buttonQuickGame = getView().findViewById(R.id.button_quick_game); //Ищем кнопку быстрой игры

        //Устанавливаем Listener для кнопки быстрой игры
        buttonQuickGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_fragmentMainMenu_to_fragmentGameRoom);
            }
        });

    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Отслеживаем нажатие на пункты меню
        switch (item.getItemId()) {

            //Если нажат пункт "Обратная связь"
            case R.id.item_to_feedback:
                //...
                return true;

            //Если нажат пункт "Выйти"
            case R.id.item_to_back:
                //Изменяем значение isLogin в SQLite на 0
                DBHelper db = new DBHelper(getContext());
                SQLiteDatabase database = db.getWritableDatabase();
                Cursor cursor = database.query(DBHelper.TABLE_NAME_USER, new String[]{DBHelper.KEY_ID_USER}, null, null, null, null, null);
                cursor.moveToFirst();
                ContentValues contentValues = new ContentValues();
                contentValues.put(DBHelper.KEY_IS_LOGIN_USER, 0);
                database.update(DBHelper.TABLE_NAME_USER, contentValues, DBHelper.KEY_ID_USER + "=" + String.valueOf(cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ID_USER))), null);
                cursor.close();
                database.close();
                db.close();
                Navigation.findNavController(getActivity(), R.id.main_container).navigate(R.id.action_fragmentMainMenu_to_fragmentLogin);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
