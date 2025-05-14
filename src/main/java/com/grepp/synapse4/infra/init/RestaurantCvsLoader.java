package com.grepp.synapse4.infra.init;

import com.grepp.synapse4.app.model.restaurant.dto.create.CreateRestaurantDto;
import com.grepp.synapse4.app.model.restaurant.dto.create.CsvBasicInfoDto;
import com.grepp.synapse4.app.model.restaurant.dto.create.CsvDetailInfoDto;
import com.grepp.synapse4.app.model.restaurant.entity.Restaurant;
import com.grepp.synapse4.app.model.restaurant.repository.KakaoRestaurantVerifierService;
import com.grepp.synapse4.app.model.restaurant.repository.RestaurantRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RestaurantCvsLoader {

    private final RestaurantRepository restaurantRepository;
    private final KakaoRestaurantVerifierService kakaoRestaurantVerifierService;

    public RestaurantCvsLoader(RestaurantRepository restaurantRepository,
                               KakaoRestaurantVerifierService kakaoRestaurantVerifierService){
        this.restaurantRepository = restaurantRepository;
        this.kakaoRestaurantVerifierService = kakaoRestaurantVerifierService;
    }

    @PostConstruct
    public void init(){
//        todo : 이미 있는 id값 데이터는 삽입 로직 삽입(RestaurantRepository)
        if (restaurantRepository.count() > 0) {
            System.out.println("식당 데이터 최초 셋팅 완료 상태");
            return;
        }
        try {
            // 1. Reader로 파싱
            Reader reader = new FileReader("src/main/resources/restaurantdata/basicInfo_30.csv");
            Reader reader2 = new FileReader("src/main/resources/restaurantdata/detailInfo_30.csv");
            CsvToBean<CsvBasicInfoDto> csvToBean = new CsvToBeanBuilder<CsvBasicInfoDto>(reader)
                    .withType(CsvBasicInfoDto.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            CsvToBean<CsvDetailInfoDto> csvToBean2 = new CsvToBeanBuilder<CsvDetailInfoDto>(reader2)
                    .withType(CsvDetailInfoDto.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<CsvBasicInfoDto> basics = csvToBean.parse();
            List<CsvDetailInfoDto> details = csvToBean2.parse();
            basics.forEach(System.out::println);
            details.forEach(System.out::println);

            // 2. detail list map 으로 반환 (basic에 종속되는 리스트)
            Map<String, CsvDetailInfoDto> detailMap = details.stream()
                    .collect(Collectors.toMap(CsvDetailInfoDto::getId, Function.identity()));

            // 3. CreateRestaurantDto
            List<CreateRestaurantDto> mergeDto = basics.stream()
                    .map(basic ->{
                        CreateRestaurantDto dto = new CreateRestaurantDto();

                        dto.setPublicId(basic.getId());
                        dto.setName(basic.getName());
                        dto.setBranch(basic.getBranch());
                        dto.setRoadAddress(basic.getRoadAddress());
                        dto.setJibunAddress(basic.getJibunAddress());
                        dto.setLatitude(basic.getLatitude());
                        dto.setLongitude(basic.getLongitude());
                        dto.setCategory(basic.getCategory());

                        CsvDetailInfoDto detail = detailMap.get(basic.getId());
                        if (detail != null) {
                            dto.setTel(basic.getTel());
                            dto.setDayOff(detail.getDayOff());
                            dto.setRowBusinessTime(detail.getRowBusinessTime());
                            dto.setParking("Y".equalsIgnoreCase(detail.getParking()));
                            dto.setWifi("Y".equalsIgnoreCase(detail.getWifi()));
                            dto.setDelivery("Y".equalsIgnoreCase(detail.getDelivery()));
                            dto.setHomePageURL(detail.getHomePageURL());
                        }

                        return dto;
                    })
                    .toList();

            // 4. db set
            for(CreateRestaurantDto dto : mergeDto){
                Restaurant restaurant = restaurantRepository.save(dto.toEntity());
                restaurant.setDetail(dto.toDetailEntity(restaurant));
                restaurantRepository.save(restaurant);
            }

            System.out.println("식당 정보 " + mergeDto.size() + "개 저장");

            // 5. activated 여부 카카오 검증 - @postConstruct 에서는 @Transactional이 실행되지 않음
//              -> 컨트롤러로 뺌 ; 수동 검증 버튼 제작...........
//            System.out.println("✅ 카카오 검증 시작");
//            kakaoRestaurantVerifierService.verifyAndActivateRestaurant();
//            System.out.println("✅ 카카오 검증 완료");

        } catch (Exception e) {     //FileNotFoundException
            e.printStackTrace();
        }
    }
}
