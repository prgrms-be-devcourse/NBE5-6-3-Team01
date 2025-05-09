package com.grepp.synapse4.app.controller.api.samplemember;

import com.grepp.synapse4.app.model.samplemember.SampleMemberService;
import com.grepp.synapse4.infra.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/member")
public class SampleMemberApiController {
    
    private final SampleMemberService sampleMemberService;
    
    @GetMapping("exists/{id}")
    public ResponseEntity<ApiResponse<Boolean>> existsId(@PathVariable String id){
        return ResponseEntity.ok(ApiResponse.success(
            sampleMemberService.isDuplicatedId(id)
        ));
    }
}
