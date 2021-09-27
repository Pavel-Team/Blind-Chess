/**Моедль пользователя.
 * Используется в MainActivityViewModel*/
package com.example.blindchess.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.blindchess.BR;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseObservable {

    private Integer user_id;                   //id пользователя
    private String name = "";                  //Имя пользователя
    private Integer wins;                      //Число побед
    private Integer defeats;                   //Число поражений
    private Integer best_league;               //Лучшая лига в рейтинговых матчах
    private Integer league_max_in_this_season; //Максимальная лига в текущем сезоне
    private Integer league;                    //Текущая лига пользователя
    private Integer league_rating;             //Рейтинг в текущем сезоне
    private Integer money;                     //Число монет
    private Product background;                //Фон автарки пользователя
    private Product foreground;                //Название переднего плана аватарки пользователя
    private Product skin;                      //Название набора шахмат пользователя
    private List<Achievement> achievements;    //Достижения пользователя
    private List<Product> products;            //Игровые предметы пользователя
    //P.S. друзья и чаты отстутствуют в модели по причине невозможности гостя добавлять в друщья и переписываться с ними


    /**Метод для передачи объекта на сервер в виде строки
     * На вход принимает 1 параметр:
     * User user - модель пользователя
     * Возвращает модель пользователя в виде строки для дальнейшей передачи на сервер*/
    public static String convertToServer(User user) {
        return null;
    }


    /**Метод для извлечения объекта при получении строки с сервера
     * На вход принимает 1 параметр:
     * String User - полученную с сервера модель пользователя в виде строки
     * Возвращает модель пользователя в User-виде*/
    public static User convertFromServer(String user) {
        return null;
    }


    @Bindable
    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
        notifyPropertyChanged(BR.user_id);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
        notifyPropertyChanged(BR.wins);
    }

    @Bindable
    public Integer getDefeats() {
        return defeats;
    }

    public void setDefeats(Integer defeats) {
        this.defeats = defeats;
        notifyPropertyChanged(BR.defeats);
    }

    @Bindable
    public Integer getBest_league() {
        return best_league;
    }

    public void setBest_league(Integer best_league) {
        this.best_league = best_league;
        notifyPropertyChanged(BR.best_league);
    }

    @Bindable
    public Integer getLeague_max_in_this_season() {
        return league_max_in_this_season;
    }

    public void setLeague_max_in_this_season(Integer league_max_in_this_season) {
        this.league_max_in_this_season = league_max_in_this_season;
        notifyPropertyChanged(BR.league_max_in_this_season);
    }

    @Bindable
    public Integer getLeague() {
        return league;
    }

    public void setLeague(Integer league) {
        this.league = league;
        notifyPropertyChanged(BR.league);
    }

    @Bindable
    public Integer getLeague_rating() {
        return league_rating;
    }

    public void setLeague_rating(Integer league_rating) {
        this.league_rating = league_rating;
        notifyPropertyChanged(BR.league_rating);
    }

    @Bindable
    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
        notifyPropertyChanged(BR.money);
    }

    @Bindable
    public Product getBackground() {
        return background;
    }

    public void setBackground(Product background) {
        this.background = background;
        notifyPropertyChanged(BR.background);
    }

    @Bindable
    public Product getForeground() {
        return foreground;
    }

    public void setForeground(Product foreground) {
        this.foreground = foreground;
        notifyPropertyChanged(BR.foreground);
    }

    @Bindable
    public Product getSkin() {
        return skin;
    }

    public void setSkin(Product skin) {
        this.skin = skin;
        notifyPropertyChanged(BR.skin);
    }

    @Bindable
    public List<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<Achievement> achievements) {
        this.achievements = achievements;
        notifyPropertyChanged(BR.achievements);
    }

    @Bindable
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyPropertyChanged(BR.products);
    }
}
