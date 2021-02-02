package com.example.blindchess.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.blindchess.R;
import com.example.blindchess.ui.game.BoardView;

public class FragmentGameRoom extends Fragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_room, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayout layoutBoard = getView().findViewById(R.id.gameBoard);   //Ищем в разметке наш layout с игровым полем
        layoutBoard.addView(new BoardView(requireContext(), "WHITE")); //Добавляем туда BoardView и передаем цвет команды игрока (БЕЛЫЙ ВРЕМЕННО)

        //Получаем ширину экрана и делаем высоту равной ширине
        int widthScreen = getActivity().getWindowManager().getDefaultDisplay().getWidth();          //Ширина экрана
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthScreen,widthScreen);  //Создаем параметры для игрового поля
        layoutBoard.setLayoutParams(params);                                                        //Устанавливаем параметры
    }

}
