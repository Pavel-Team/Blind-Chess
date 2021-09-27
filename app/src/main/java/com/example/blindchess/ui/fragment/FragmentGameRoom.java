/**Фрагмент с игровой комнатой*/
package com.example.blindchess.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.blindchess.BR;
import com.example.blindchess.R;
import com.example.blindchess.constants.ConstantsImageView;
import com.example.blindchess.databinding.FragmentGameRoomBinding;
import com.example.blindchess.databinding.FragmentLoginBinding;
import com.example.blindchess.game.BoardView;
import com.example.blindchess.viewModel.GameRoomViewModel;
import com.example.blindchess.viewModel.LoginViewModel;


public class FragmentGameRoom extends Fragment {

    private GameRoomViewModel viewModel;     //ViewModel данного окна
    private FragmentGameRoomBinding binding; //binding данного окна


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Устанавливаем ViewModel
        viewModel = ViewModelProviders.of(this).get(GameRoomViewModel.class);

        //Инициализация всех View
        View root = inflater.inflate(R.layout.fragment_game_room, container, false);

        //Привязываем DataBinding и ViewModel
        binding = FragmentGameRoomBinding.bind(root);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setVariable(BR.gameRoomViewModel, viewModel);

        return root;
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

        //Отрисовываем автарки противника (ВРЕМЕННО - сделать в отдельном потоке)
        setAvatarUser(R.id.image_view_enemy_user, viewModel.getLiveData().getValue().getEnemyUser().getBackground().getImage_name(), viewModel.getLiveData().getValue().getEnemyUser().getForeground().getImage_name());
        setAvatarUser(R.id.image_view_my_user, viewModel.getLiveData().getValue().getEnemyUser().getBackground().getImage_name(), viewModel.getLiveData().getValue().getEnemyUser().getForeground().getImage_name());
    }


    /**Функция установки аватарки пользователя
     * Функция принимает 2 параметра:
     * String nameBackground - название заднего фона из SQLite
     * String nameForeground - название передней фигуры из SQLite
     * Внтури происходят все необходимые вычисления и установка двух Bitmap'ов в imageViewUser*/
    private void setAvatarUser(int idImageView, String nameBackground, String nameForeground){
        final Bitmap[] bitmapBackground = new Bitmap[1]; //Bitmap заднего фона
        final Bitmap[] bitmapForeground = new Bitmap[1]; //Bitmap передней фигуры
        ImageView imageViewUser = getView().findViewById(idImageView);

        //Получаем Bitmap'ы для аватарки
        bitmapBackground[0] = BitmapFactory.decodeResource(getContext().getResources(), ConstantsImageView.getBitmapBackground(nameBackground));
        bitmapForeground[0] = BitmapFactory.decodeResource(getContext().getResources(), ConstantsImageView.getBitmapForeground(nameForeground));


        //Как только ImageView прогрузится на экране - загружаем в него аватрку (нужно для вычисления ширины imageView)
        imageViewUser.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = imageViewUser.getWidth(); //Ширина ImageView
                bitmapBackground[0] = scaleCenterCrop(bitmapBackground[0], width, width);
                bitmapForeground[0] = scaleCenterCrop(bitmapForeground[0], width*4/5, width*4/5);

                Bitmap output = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(output);

                Paint paint = new Paint();
                RectF rectF = new RectF(0, 0, width, width);
                float roundPx = 30; //ВРЕМЕННО

                paint.setAntiAlias(true);
                canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(bitmapBackground[0], 0, 0, paint);
                canvas.drawBitmap(bitmapForeground[0], width*1/10, width*1/10, new Paint());

                imageViewUser.setImageBitmap(output);
            }
        });
    }


    /**Функция установки Bitmap новых размеров с сохранением исходных пропорций
     * На вход принимает 3 параметра:
     * Bitmap source - исходный Bitmap, который нужно отредактировать
     * int newHeight - новая высота, до которой нужно изменить исходный Bitmap
     * int newWidth - новая ширина, до которой нужно изменить исходный Bitmap
     * Функция возвращает новый Bitmap - переделанный source под новые размеры newWidth * newHeight*/
    private Bitmap scaleCenterCrop(Bitmap source, int newHeight, int newWidth) {
        int sourceWidth = source.getWidth();
        int sourceHeight = source.getHeight();
        float xScale = (float) newWidth / sourceWidth;
        float yScale = (float) newHeight / sourceHeight;
        float scale = Math.min(xScale, yScale);
        float scaledWidth = scale * sourceWidth;
        float scaledHeight = scale * sourceHeight;
        float left = (newWidth - scaledWidth) / 2;
        float top = (newHeight - scaledHeight) / 2;

        Bitmap dest = Bitmap.createBitmap(newWidth, newHeight, source.getConfig());
        Canvas canvas = new Canvas(dest);
        Bitmap result = Bitmap.createScaledBitmap(source, (int) scaledWidth, (int) scaledHeight, true); //Масштабируем изображение
        canvas.drawBitmap(result, left, top, new Paint());

        return dest;
    }

}
