/**Фрагмент главного меню*/
package com.example.blindchess.ui.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.blindchess.R;
import com.example.blindchess.ui.graphics.User;
import com.example.blindchess.ui.sqlite.DBHelper;


public class FragmentMainMenu extends Fragment {

    private ImageView imageViewUser;        //ImageView аватарки пользователя
    private ImageView imageViewLeague;      //ImageView картинки текущей лиги
    private TextView textViewNameUser;      //TextView имени пользователя
    private TextView textViewIdUser;        //TextView id пользовтеля
    private TextView textViewLeagueUser;    //TextView текущей лиги пользователя
    private ImageButton buttonEditName;     //Кнопка редактирования имени пользователя
    private Button buttonOnlineFriends;     //Кнопка "Друзья онлайн"
    private Button buttonAchievements;      //Кнопка "Достижения"
    private Button buttonRatingGame;        //Кнопка "Рейтинговый матч"
    private Button buttonFriendGame;        //Кнопка "Играть с другом"
    private Button buttonQuickGame;         //Кнопка "Быстрая игра"
    private LinearLayout itemMenuShop;      //Кнопка меню "Магазин"
    private LinearLayout itemMenuInventory; //Кнопка меню "Инвентарь"

    private int idUser;                     //id пользователя
    private String nameUser;                //Имя пользователя
    private String imageNameBackgroundUser; //Название фона аватарки пользователя
    private String imageNameForegroundUser; //Название переднего плана аватарки пользователя
    private int ratingUser;                 //Рейтинг пользователя
    private int leagueUser;                 //Текущая лига пользователя
    private int leagueWinsUser;             //Число побед в текущей лиге пользователя
    private int leagueDefeatsUser;          //Число поражений в текущей лиге пользователя

    private User graphic;                   //Объект для получения всех необходимых Bitmap, связанных с пользователем


    /**Функция инициализации всех View элементов разметки данного экрана*/
    public void initViews() {
        imageViewUser = getView().findViewById(R.id.image_view_user);
        imageViewLeague = getView().findViewById(R.id.image_view_league);
        textViewNameUser = getView().findViewById(R.id.text_view_user_name);
        textViewIdUser = getView().findViewById(R.id.text_view_id_user);
        textViewLeagueUser = getView().findViewById(R.id.text_view_league_user);
        buttonEditName = getView().findViewById(R.id.button_edit_name);
        buttonOnlineFriends = getView().findViewById(R.id.button_friends);
        buttonAchievements = getView().findViewById(R.id.button_achievements);
        buttonRatingGame = getView().findViewById(R.id.button_rating_game);
        buttonFriendGame = getView().findViewById(R.id.button_friend_game);
        buttonQuickGame = getView().findViewById(R.id.button_quick_game);
        itemMenuShop = getView().findViewById(R.id.item_shop);
        itemMenuInventory = getView().findViewById(R.id.item_inventory);
    }


    /**Функция установки информации о пользователе на основе данных из SQLite*/
    public void setInformationAboutUser(){

        //Берем данные из БД
        DBHelper db = new DBHelper(getContext());
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_NAME_USER, null, null, null, null, null, null);
        cursor.moveToFirst();

        int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID_USER);
        int idName = cursor.getColumnIndex(DBHelper.KEY_NAME_USER);
        int idImageNameBackground = cursor.getColumnIndex(DBHelper.KEY_IMAGE_NAME_BACKGROUND);
        int idImageNameForeground = cursor.getColumnIndex(DBHelper.KEY_IMAGE_NAME_FOREGROUND);
        int idRating = cursor.getColumnIndex(DBHelper.KEY_RATING_USER);
        int idLeague = cursor.getColumnIndex(DBHelper.KEY_LEAGUE_USER);
        int idLeagueWins = cursor.getColumnIndex(DBHelper.KEY_LEAGUE_WINS_USER);
        int idLeagueDefeats = cursor.getColumnIndex(DBHelper.KEY_LEAGUE_DEFEATS_USER);

        idUser = cursor.getInt(idIndex);
        nameUser = cursor.getString(idName);
        imageNameBackgroundUser = cursor.getString(idImageNameBackground);
        imageNameForegroundUser = cursor.getString(idImageNameForeground);
        ratingUser = cursor.getInt(idRating);
        leagueUser = cursor.getInt(idLeague);
        leagueWinsUser = cursor.getInt(idLeagueWins);
        leagueDefeatsUser = cursor.getInt(idLeagueDefeats);

        cursor.close();
        database.close();
        db.close();


        //Устанавливаем значения в соответствующие поля
        textViewNameUser.setText(nameUser);
        textViewIdUser.setText(String.valueOf(idUser));
        textViewLeagueUser.setText(String.valueOf(leagueUser));
        setAvatarUser(imageNameBackgroundUser, imageNameForegroundUser);
        setImageViewLeague(leagueUser);
    }


    /**Функция установки аватарки пользователя
     * Функция принимает 2 параметра:
     * String nameBackground - название заднего фона из SQLite
     * String nameForeground - название передней фигуры из SQLite
     * Внтури происходят все необходимые вычисления и установка двух Bitmap'ов в imageViewUser*/
    public void setAvatarUser(String nameBackground, String nameForeground){
        final Bitmap[] bitmapBackground = new Bitmap[1]; //Bitmap заднего фона
        final Bitmap[] bitmapForeground = new Bitmap[1]; //Bitmap передней фигуры

        //Получаем Bitmap'ы для аватарки
        bitmapBackground[0] = graphic.getBitmapBackground(nameBackground);
        bitmapForeground[0] = graphic.getBitmapForeground(nameForeground);


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
     * Функция возвращает новый Bitmap - переделанный souce под новые размеры newWidth * newHeight*/
    public Bitmap scaleCenterCrop(Bitmap source, int newHeight, int newWidth) {
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
     * Функция принимает 1 параметр:
     * int league - текущая лига пользователя
     * Внутри происходит установка Bitmap'а лиги в imageViewLeague*/
    public void setImageViewLeague(int league) {
        Bitmap bitmapLeague = graphic.getBitmapLeague(league);
        imageViewLeague.setImageBitmap(bitmapLeague);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_menu, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        graphic = new User(getContext()); //Инициализируем объект, необходимый для получения всевозможных Bitmap
        initViews(); //Инициализируем все необходимы View разметки данного экрана
        setInformationAboutUser(); //Заполняем информацию о пользователе в соответствующие View

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
