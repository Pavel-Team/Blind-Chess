/**Моедль пользователя.
 * Используется в MainActivityViewModel*/
package com.example.blindchess.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

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

}
