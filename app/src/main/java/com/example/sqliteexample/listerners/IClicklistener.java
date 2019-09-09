package com.example.sqliteexample.listerners;

import com.example.sqliteexample.models.Hero;

public interface IClicklistener {
     int UPDATE =1;
     int DELETE =0;
     void onclick(Hero hero, int Flag);
}
