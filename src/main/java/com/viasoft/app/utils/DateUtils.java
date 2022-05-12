package com.viasoft.app.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class DateUtils {

    public static String localeDateTimeToString(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm:ss");
        return localDateTime.format(formatter);
    }


    public static LocalDate returnLocalDate(String data) {

        List<String> dataList = List.of(data.split("/"));

        int dia = Integer.parseInt(dataList.get(0));
        int mes = Integer.parseInt(dataList.get(1));
        int ano = Integer.parseInt(dataList.get(2));

        LocalDate date = LocalDate.of(ano , mes ,dia);
        return date;
    }
}
