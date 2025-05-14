package com.grepp.synapse4.app.model.restaurant.dto.create;


import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CsvMenuDto {

    // 기본정보 - restaurant
    @CsvBindByName(column = "식당(ID)")
    private String id;
    @CsvBindByName(column = "식당명")
    private String name;
    @CsvBindByName(column = "지점명")
    private String branch;

    // 메뉴 정보 - menus
    @CsvBindByName(column = "메뉴(ID)")
    private Integer menuId;
    @CsvBindByName(column = "메뉴명")
    private String menuName;
    @CsvBindByName(column = "메뉴가격")
    private Integer price;


}
