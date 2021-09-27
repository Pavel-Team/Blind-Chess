/**Фрагмент главного меню*/
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.blindchess.BR;
import com.example.blindchess.R;
import com.example.blindchess.constants.ConstantsImageView;
import com.example.blindchess.databinding.FragmentMainMenuBinding;
import com.example.blindchess.viewModel.MainMenuViewModel;


public class FragmentMainMenu extends Fragment {

    private MainMenuViewModel viewModel;     //ViewModel для данного окна
    private FragmentMainMenuBinding binding; //Binding для связывания DataBinding и ViewModel


    /**Функция установки аватарки пользователя
     * Функция принимает 2 параметра:
     * String nameBackground - название заднего фона из SQLite
     * String nameForeground - название передней фигуры из SQLite
     * Внтури происходят все необходимые вычисления и установка двух Bitmap'ов в imageViewUser*/
    private void setAvatarUser(String nameBackground, String nameForeground){
        final Bitmap[] bitmapBackground = new Bitmap[1]; //Bitmap заднего фона
        final Bitmap[] bitmapForeground = new Bitmap[1]; //Bitmap передней фигуры
        ImageView imageViewUser = getView().findViewById(R.id.image_view_user);

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


    /**Функция установки изображения текущей лиги пользователя
     * Функция принимает 2 параметра:
     * ImageView - imageView, в который будет устанавливаться изображение (в нашем случае это @id/image_view_league)
     * int league - текущая лига пользователя
     * Внутри происходит установка Bitmap'а лиги в imageViewLeague*/
    @BindingAdapter("android:src")
    public static void setImageViewLeague(ImageView imageView, int league) {
        int idDrawable = ConstantsImageView.getDrawableLeague(league);

        imageView.setImageResource(idDrawable);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Устнаваливаем ViewModel
        viewModel = ViewModelProviders.of(this).get(MainMenuViewModel.class);

        //Инициализация всех View
        View root = inflater.inflate(R.layout.fragment_main_menu, container, false);

        //Привязываем DataBinding и ViewModel
        binding = FragmentMainMenuBinding.bind(root);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setVariable(BR.viewModelMainMenu, viewModel);
        binding.setVariable(BR.fragmentMainMenu, this);

        return root;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        //Отрисовываем автарку (ВРЕМЕННО - сделать в отдельном потоке)
        setAvatarUser(viewModel.getLiveData().getValue().getBackground().getImage_name(), viewModel.getLiveData().getValue().getForeground().getImage_name());
    }



    /**Функция обработки нажатия на кнопку "Изменить имя"*/
    public void onClickButtonEditName(){
        Toast toast = Toast.makeText(getContext(), getString(R.string.block_function), Toast.LENGTH_SHORT);
        toast.show();
    }

    /**Функция обработки нажатия на кнопку "Друзья онлайн"*/
    public void onClickButtonFriends() {
        Toast toast = Toast.makeText(getContext(), getString(R.string.block_function), Toast.LENGTH_SHORT);
        toast.show();
    }


    /**Функция обработки нажатия на кнопку "Достижения"*/
    public void onClickButtonAchievements() {
        Navigation.findNavController(getView()).navigate(R.id.action_fragmentMainMenu_to_fragmentAchievements);
    }


    /**Функция обработки нажатия на кнопку "Рейтинговый матч"*/
    public void onClickButtonRatingGame() {
        Toast toast = Toast.makeText(getContext(), getString(R.string.block_function), Toast.LENGTH_SHORT);
        toast.show();
    }


    /**Функция обработки нажатия на кнопку "Играть с другом"*/
    public void onClickButtonFriendGame() {
        Toast toast = Toast.makeText(getContext(), getString(R.string.block_function), Toast.LENGTH_SHORT);
        toast.show();
    }


    /**Функция обработки нажатия на кнопку "Быстрая игра"*/
    public void onClickButtonQuickGame() {
        Navigation.findNavController(getView()).navigate(R.id.action_fragmentMainMenu_to_fragmentGameRoom);
    }


    /**Функция обработки нажатия на кнопку "Магазин"*/
    public void onClickButtonShop() {
        Toast toast = Toast.makeText(getContext(), getString(R.string.block_function), Toast.LENGTH_SHORT);
        toast.show();
    }


    /**Функция обработки нажатия на кнопку "Инвентарь"*/
    public void onClickButtonInventory() {
        Toast toast = Toast.makeText(getContext(), getString(R.string.block_function), Toast.LENGTH_SHORT);
        toast.show();
    }


    /**Функция обработки нажатия на аватарку пользователя*/
    public void onClickImageViewAvatar() {
        Toast toast = Toast.makeText(getContext(), getString(R.string.block_function), Toast.LENGTH_SHORT);
        toast.show();
    }


    /**Функция обработки нажатия картинку лиги пользователя*/
    public void onClickImageViewLeague() {
        Toast toast = Toast.makeText(getContext(), getString(R.string.block_function), Toast.LENGTH_SHORT);
        toast.show();
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
                Toast toast = Toast.makeText(getContext(), getString(R.string.block_function), Toast.LENGTH_SHORT);
                toast.show();
                return true;

            //Если нажат пункт "Выйти"
            case R.id.item_to_back:
                viewModel.quitFromAccount();
                Navigation.findNavController(getActivity(), R.id.main_container).navigate(R.id.action_fragmentMainMenu_to_fragmentLogin);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
