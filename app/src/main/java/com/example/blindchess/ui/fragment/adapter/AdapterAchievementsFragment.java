/**Адаптер для GridView страницы с достижениями пользователя*/
package com.example.blindchess.ui.fragment.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.blindchess.R;
import com.example.blindchess.ui.DTO.AchievementDTO;
import com.example.blindchess.ui.graphics.Achievements;

import java.util.ArrayList;


public class AdapterAchievementsFragment extends BaseAdapter {

    private Context context;                            //Контекст приложения
    private ArrayList<AchievementDTO> listAchievements; //Лист со всеми достижениями игрока
    private LayoutInflater layoutInflater;              //Объект View, полученный из XML-разметки (item_achievement.xml)
    private Achievements graphic;                       //Объект для получения всех необходимых R.id.drawable достижений


    /**Конструктор класса
     * На вход принимает 2 параметра:
     * Context context - контекст приложения
     * ArrayList<AchievementDTO> listAchievements - Лист с названиями достижений*/
    public AdapterAchievementsFragment(Context context, ArrayList<AchievementDTO> listAchievements){
        this.context = context;
        this.listAchievements = listAchievements;
        layoutInflater = LayoutInflater.from(context);
        graphic = new Achievements(context);
    }


    /**Функция получения числа записей (числа достижений)*/
    @Override
    public int getCount() {
        return listAchievements.size();
    }


    /**Функция получения данного элемента по индексу position (получение данного достижения)*/
    @Override
    public Object getItem(int position) {
        return listAchievements.get(position);
    }


    /**Функция возвращающая id данного элемента*/
    @Override
    public long getItemId(int position) {
        return position;
    }


    /**Функция, возвращающая созданный View для данного элемента*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder; //Части View создаваемого элемента

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_achievement, null);
            holder = new ViewHolder();

            holder.layout = (LinearLayout) convertView.findViewById(R.id.layout_item_achievement);
            holder.imageView = (ImageView) convertView.findViewById(R.id.image_view_item_achievement);
            holder.titleView = (TextView) convertView.findViewById(R.id.text_view_item_title_achievement);
            holder.descriptionView = (TextView) convertView.findViewById(R.id.text_view_item_description_achievement);
            holder.progress = (TextView) convertView.findViewById(R.id.text_view_item_progress_achievement);

            convertView.setTag(holder); //Устанавливаем tag для того, чтобы показать, что наши элементы различны
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AchievementDTO achievementDTO = this.listAchievements.get(position); //Получеем данный объект из списка всех достижений

        //Заполняем поля
        Glide.with(convertView).load(graphic.getBitmapAchievement(achievementDTO.getImageName())).into(holder.imageView);
        holder.titleView.setText(achievementDTO.getTitle());
        holder.descriptionView.setText(achievementDTO.getDescription());
        holder.progress.setText(String.valueOf(achievementDTO.getUserProgress()) + "/" + String.valueOf(achievementDTO.getMaxProgress()));
        if (!achievementDTO.isGet()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.layout.setForeground(context.getDrawable(R.drawable.background_lock));
            }
        }

        return convertView;
    }



    /**Вложенный класс с компонентами для создаваемого элемента*/
    static class ViewHolder {
        LinearLayout layout;      //Layout достижения
        ImageView imageView;      //ImageView с картинкой достижения
        TextView titleView;       //TextView с названием достижения
        TextView descriptionView; //TextView с описанием достижения
        TextView progress;        //TextView с прогрессом выполнения достижения
    }
}
