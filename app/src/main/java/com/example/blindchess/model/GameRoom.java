/**Модель игровой комнаты*/
package com.example.blindchess.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.blindchess.BR;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
public class GameRoom extends BaseObservable {

    private User enemyUser;   //Противник
    private User myUser;      //Мой игрок
    private Boolean isMyMove; //Мой ли ход
    private Integer time;     //Оставшееся время на ход


    @Bindable
    public User getEnemyUser() {
        return enemyUser;
    }

    public void setEnemyUser(User enemyUser) {
        this.enemyUser = enemyUser;
        notifyPropertyChanged(BR.enemyUser);
    }

    @Bindable
    public User getMyUser() {
        return myUser;
    }

    public void setMyUser(User myUser) {
        this.myUser = myUser;
        notifyPropertyChanged(BR.myUser);
    }

    @Bindable
    public Boolean getMyMove() {
        return isMyMove;
    }

    public void setMyMove(Boolean myMove) {
        isMyMove = myMove;
        notifyPropertyChanged(BR.myMove);
    }

    @Bindable
    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
        notifyPropertyChanged(BR.time);
    }

}
