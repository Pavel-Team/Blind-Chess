/**Фрагмент страницы с достижениями пользователя*/
package com.example.blindchess.ui.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.blindchess.R;
import com.example.blindchess.ui.DTO.AchievementDTO;
import com.example.blindchess.ui.fragment.adapter.AdapterAchievementsFragment;
import com.example.blindchess.ui.sqlite.DBHelper;

import java.util.ArrayList;


public class FragmentAchievements extends Fragment {

    private GridView gridView; //GridView страницы с достижениями


    /**Функция получения информации о всех достиженях, полученных пользователем
     * Возвращает ArrayList<AchievementDTO> - список всех достижений пользователя, отсортированных по критерию isGet*/
    public ArrayList<AchievementDTO> getAchievementsFromSQLite(){
        ArrayList<AchievementDTO> listAchievements = new ArrayList<AchievementDTO>(); //Результирующий лист
        int id, isGetAchievement, userProgress, maxProgress;                          //Все свойства объекта achievementDTO
        String title, description, image_name;

        //Берем данные из БД и заносим их в наш результирующий список в виде объектов achievementDTO
        DBHelper db = new DBHelper(getContext());
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_NAME_ACHIEVEMENT, null, null, null, null, null, DBHelper.KEY_IS_GET_ACHIEVEMENT + " DESC");
        cursor.moveToFirst();

        do {
            AchievementDTO achievementDTO = new AchievementDTO(); //Объект одного достижения
            database.beginTransaction(); //Начинаем транзакцию взятия одной строки
            try {
                id = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_ID_USER_ACHIEVEMENT));
                title = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_TITLE_ACHIEVEMENT));
                description = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_DESCRIPTION_ACHIEVEMENT));
                image_name = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_IMAGE_NAME_ACHIEVEMENT));
                userProgress = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_USER_PROGRESS_ACHIEVEMENT));
                maxProgress = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_MAX_PROGRESS_ACHIEVEMENT));
                isGetAchievement = cursor.getInt(cursor.getColumnIndex(DBHelper.KEY_IS_GET_ACHIEVEMENT));

                database.setTransactionSuccessful();
            } finally {
                database.endTransaction();
            }

            //Заносим в объект
            achievementDTO.setTitle(title);
            achievementDTO.setDescription(description);
            achievementDTO.setImageName(image_name);
            achievementDTO.setUserProgress(userProgress);
            achievementDTO.setMaxProgress(maxProgress);
            if (isGetAchievement == 0)
                achievementDTO.setGet(false);
            else
                achievementDTO.setGet(true);

            listAchievements.add(achievementDTO);
        } while (cursor.moveToNext());

        //Закрываем соединения
        cursor.close();
        database.close();
        db.close();

        return listAchievements;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_achievements, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //ВРЕМЕННО
        ArrayList<AchievementDTO> listAchievements = getAchievementsFromSQLite();
        gridView = getView().findViewById(R.id.grid_view_achievements);

        AdapterAchievementsFragment partnerAdapter = new AdapterAchievementsFragment(getContext(), listAchievements); //Создаем адаптер для достижений
        gridView.setAdapter(partnerAdapter); //Передаем в GridView наш адаптер с данными

    }
}
