package com.grepp.synapse4.app.model.samplemember;

import com.grepp.synapse4.app.model.samplemember.entity.SampleMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleMemberRepository extends JpaRepository<SampleMember, String> {


}
