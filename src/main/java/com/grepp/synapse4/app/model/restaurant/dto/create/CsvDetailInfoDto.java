package com.grepp.synapse4.app.model.restaurant.dto.create;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CsvDetailInfoDto {

    // 기본정보 - restaurant
    @CsvBindByName(column = "식당(ID)")
    private String id;
    @CsvBindByName(column = "식당명")
    private String name;
    @CsvBindByName(column = "지점명")
    private String branch;

    // 상세 정보 - restaurantDetails
    @CsvBindByName(column = "휴무일정보내용")
    private String dayOff;
    @CsvBindByName(column = "영업시간내용")
    private String rowBusinessTime;
    @CsvBindByName(column = "주차가능여부")
    private String parking;
    @CsvBindByName(column = "와이파이제공여부")
    private String wifi;
    @CsvBindByName(column = "배달서비스유무")
    private String delivery;
    @CsvBindByName(column = "홈페이지(URL)")
    private String homePageURL;
}
