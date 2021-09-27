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
import androidx.lifecycle.ViewModelProviders;

import com.example.blindchess.R;
import com.example.blindchess.databinding.FragmentLoginBinding;
import com.example.blindchess.model.Achievement;
import com.example.blindchess.repository.SQLiteRepository;
import com.example.blindchess.ui.fragment.adapter.AdapterAchievementsFragment;
import com.example.blindchess.viewModel.AchievementsViewModel;
import com.example.blindchess.viewModel.LoginViewModel;

import java.util.ArrayList;
import java.util.List;


public class FragmentAchievements extends Fragment {

    private GridView gridView; //GridView страницы с достижениями

    private AchievementsViewModel viewModel; //ViewModel данной страницы

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Устанавливаем ViewModel
        viewModel = ViewModelProviders.of(this).get(AchievementsViewModel.class);

        //Инициализация всех View
        View root = inflater.inflate(R.layout.fragment_achievements, container, false);

        //ВРЕМЕННО (сделать потом привязку к DataBinding)
        //...

        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //ВРЕМЕННО (сделать потом привзяку через DataBinding)
        gridView = getView().findViewById(R.id.grid_view_achievements);

        ArrayList<Achievement> listAchievements = viewModel.getLiveData().getValue();        //Получаем сприсок наших достижения для пользователя
        AdapterAchievementsFragment partnerAdapter = new AdapterAchievementsFragment(getContext(), listAchievements); //Создаем адаптер для достижений
        gridView.setAdapter(partnerAdapter); //Передаем в GridView наш адаптер с данными

    }
}
