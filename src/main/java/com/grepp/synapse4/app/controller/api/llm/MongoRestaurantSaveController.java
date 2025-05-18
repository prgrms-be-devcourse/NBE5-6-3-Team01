package com.grepp.synapse4.app.controller.api.llm;

import com.grepp.synapse4.app.model.llm.mongo.RestaurantTagsDocument;
import com.grepp.synapse4.app.model.llm.repository.RestaurantTagsDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MongoRestaurantSaveController {
    // 식당 데이터 태깅 저장용 REST API

    @Autowired
    private RestaurantTagsDocumentRepository restaurantTagsDocumentRepository;

    // todo post가 맞는데, 일단 시간관계상.
    // 몽고 테스트 데이터 셋팅이니 get으로 진행 -> 추후 수정 필요
    @GetMapping("/mongo/tagging")
    public String initTags(){
        // 중복 저장 방지! 무조건 첫 시작
        restaurantTagsDocumentRepository.deleteAll();

        // 충격 실화... java에서 map은 그 필드가 10개까지만 추가된다고 함 ㄷㄷ
        Map<Long, List<String>> tags = new HashMap<>();
        tags.put(1L, List.of("불향가득", "든든한한끼", "직장인추천", "점심맛집", "모임하기좋은"));
        tags.put(2L, List.of("깔끔한분위기", "가족외식", "단체모임", "정갈한한상", "회식추천"));
        tags.put(7L, List.of("고급스러운", "조용한", "예약필수", "분위기있는", "정갈한코스"));
        tags.put(9L, List.of("정통느낌", "넓은좌석", "격식있는", "단체식사", "포멀한분위기"));
        tags.put(10L, List.of("아늑한", "디저트즐기기좋은", "브런치분위기", "대화하기좋은", "조용한공간"));
        tags.put(12L, List.of("이국적인", "캐주얼한", "젊은분위기", "편안한", "가성비좋은"));
        tags.put(13L, List.of("활기찬", "매운맛즐기기좋은", "친구끼리추천", "열정적인", "스트레스해소"));
        tags.put(14L, List.of("전망좋은", "인스타감성", "데이트코스", "햇살가득", "여유로운"));
        tags.put(15L, List.of("조용한", "신선한느낌", "고급한상", "미니멀한", "프라이빗"));
        tags.put(17L, List.of("로맨틱한", "이탈리안감성", "데이트추천", "트렌디한", "분위기있는"));
        tags.put(18L, List.of("빠른한끼", "간편한", "출근길추천", "혼밥적합", "활기찬"));
        tags.put(19L, List.of("혼밥하기좋은", "간단한", "활동전한끼", "저렴한", "부담없는"));
        tags.put(20L, List.of("화끈한", "매운맛도전", "친구모임", "시끌벅적", "에너지넘치는"));
        tags.put(22L, List.of("레트로한", "편안한", "소박한", "추억돋는", "가정적인"));
        tags.put(23L, List.of("아늑한", "조용한골목", "정겨운", "포근한", "편안한식사"));

        tags.forEach((restaurantId, tagList) -> {
            RestaurantTagsDocument doc = new RestaurantTagsDocument(null, restaurantId, tagList);
            restaurantTagsDocumentRepository.save(doc);
        });

        return "🤖 식당 태그 데이터 추가 완료";
    }

}
