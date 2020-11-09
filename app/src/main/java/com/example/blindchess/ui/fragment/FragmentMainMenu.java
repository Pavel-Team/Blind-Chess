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

public class FragmentMainMenu extends Fragment {

    private Button buttonQuickGame; //Кнопка быстрой игры


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_menu, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        buttonQuickGame = getView().findViewById(R.id.button_quick_game); //Ищем кнопку быстрой игры

        //Устанавливаем Listener для кнопки быстрой игры
        buttonQuickGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_fragmentMainMenu_to_fragmentGameRoom);
            }
        });

    }
}
