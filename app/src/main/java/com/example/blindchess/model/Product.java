/**Модель игрового предмета
 * Используется в User, ...*/
package com.example.blindchess.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {

    private Integer product_id;     //id товара
    private String title = "";      //Название товара
    private String type = "";       //Тип товара (BACKGROUND, FOREGROUND или SKIN)
    private Integer price;          //Стоимость
    private String image_name = ""; //Название картинки товара

}
