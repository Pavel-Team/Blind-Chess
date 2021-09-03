/**Фрагмент страницы с достижениями пользователя
 * P.S. Все проверки на получение достижений смотреть в классе ThreadCheckAchievements*/
package com.example.blindchess.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.blindchess.R;
import com.example.blindchess.model.Achievement;
import com.example.blindchess.repository.SQLiteRepository;
import com.example.blindchess.ui.fragment.adapter.AdapterAchievementsFragment;

import java.util.ArrayList;


public class FragmentAchievements extends Fragment {

    private GridView gridView; //GridView страницы с достижениями


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_achievements, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //ВРЕМЕННО
        SQLiteRepository sqLiteRepository = new SQLiteRepository();
        ArrayList<Achievement> listAchievements = sqLiteRepository.getAchievementsFromSQLite(1); //ВРЕМЕННО - взять user_id из VewModel
        gridView = getView().findViewById(R.id.grid_view_achievements);

        AdapterAchievementsFragment partnerAdapter = new AdapterAchievementsFragment(getContext(), listAchievements); //Создаем адаптер для достижений
        gridView.setAdapter(partnerAdapter); //Передаем в GridView наш адаптер с данными

    }
}
