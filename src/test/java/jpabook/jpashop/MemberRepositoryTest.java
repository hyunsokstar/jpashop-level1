package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    // @Rollback
    @DisplayName("회원 정보 저장후 리턴 받은 id를 이용해 제대로 저장 되었는지 확인 !")
    @Transactional
    @Test
    @Rollback(false)
    void saveAndFindForMeber() {
        Member origin_member = new Member();
        origin_member.setName("hyunsok");

        Long saveId = memberRepository.save(origin_member);
        Member savedMember = memberRepository.find(saveId);

        assertEquals(origin_member.getId(), savedMember.getId());

    }

    @DisplayName("유저 멤버를 2명 가입한뒤 토탈 카운트를 조회 하면 2이다 !")
    @Transactional
    @Test
    void testForFindAllForUserEntity() {
        System.out.println("유저 멤버를 2명 가입한뒤 토탈 카운트를 조회 하면 2이다");

        Member origin_member1 = new Member();
        origin_member1.setName("hyunsok");

        Member origin_member2 = new Member();
        origin_member1.setName("chulsu");

        Long saveId1 = memberRepository.save(origin_member1);
        Long saveId2 = memberRepository.save(origin_member2);

        List<Member> allMembers = memberRepository.findAll();

        int totalCount = allMembers.size();

        assertEquals(2, totalCount);

    }

}