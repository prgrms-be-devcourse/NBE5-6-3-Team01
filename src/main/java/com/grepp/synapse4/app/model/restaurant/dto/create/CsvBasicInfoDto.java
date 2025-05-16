package com.grepp.synapse4.app.model.restaurant.dto.create;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CsvBasicInfoDto {

    // 기본정보 - restaurant
    @CsvBindByName(column = "식당(ID)")
    private String id;
    @CsvBindByName(column = "식당명")
    private String name;
    @CsvBindByName(column = "지점명")
    private String branch;
    @CsvBindByName(column = "도로명주소")
    private String roadAddress;
    @CsvBindByName(column = "지번주소")
    private String jibunAddress;
    @CsvBindByName(column = "식당위도")
    private Double latitude;
    @CsvBindByName(column = "식당경도")
    private Double longitude;
    @CsvBindByName(column = "영업신고증업태명")
    private String category;

    // 상세 정보 - restaurantDetails
    @CsvBindByName(column = "식당대표전화번호")
    private String tel;

}
