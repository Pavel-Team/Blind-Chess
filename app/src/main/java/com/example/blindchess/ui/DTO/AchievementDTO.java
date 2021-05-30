/**Класс для создания объекта одного достижения*/
package com.example.blindchess.ui.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AchievementDTO {

    private String title;       //Название достижения
    private String description; //Описание достижения
    private String imageName;   //Названием картикни достижения
    private int userProgress;   //Прогресс достижения, набранный пользователем
    private int maxProgress;    //Прогресс достижения, который нужно набрать для его получения
    private boolean isGet;      //Получено ли достижение пользователем

}
