/**Модель достижения
 * Используется в User и AdapterAchievementsFragment*/
package com.example.blindchess.model;

import androidx.databinding.BaseObservable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Achievement extends BaseObservable {

    private Integer user_id;      //id пользователя, получаещего достижение
    private String title;         //Название достижения
    private String description;   //Описание достижения
    private String imageName;     //Названием картикни достижения
    private Integer userProgress; //Прогресс достижения, набранный пользователем
    private Integer maxProgress;  //Прогресс достижения, который нужно набрать для его получения

}
