package jpabook.jpashop.service;
import jpabook.jpashop.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @DisplayName("단순 회원 가입 테스트")
    @Transactional
    @Test
    public void 회원가입_테스트() {
        Member member = new Member(); // member.id 는 여기에서 자동 생성
        member.setName("hyunsok");
        // 회원 가입 + 리턴 받은 유저 아이디 savedMemberId에 담기
        Long savedMemberId = memberService.join(member);
        System.out.println("savedMemberId : "+ savedMemberId);
        assertEquals(member.getId(), savedMemberId);
    }

    @DisplayName("회원 1명에 대한 조회 함수 findOne 테스트")
    @Transactional
    @Test
    public void 회원_1명_조회_테스트() {
        Member member1 = new Member(); // member.id 는 여기에서 자동 생성
        member1.setName("hyunsok");

        Member member2 = new Member(); // member.id 는 여기에서 자동 생성
        member2.setName("hyunsok2");

        // 회원 1명 가입 <=> by member1
        Long savedMemberId = memberService.join(member1);

        // 회원 1명 조회 by member1 id
        Member findMember = memberService.findOne(member1.getId());
        assertSame(member1, findMember);

    }

    @DisplayName("동일한 이름으로 2번 회원 가입시 IllegalStateException 이 발생해야 한다.")
    @Test
    @Transactional
    public void 중복회원가입방지() throws Exception {
        Member member1 = new Member();
        member1.setName("hyun");
        Member member2 = new Member();
        member2.setName("hyun");

        memberService.join(member1);

        try {
            memberService.join(member2); // 여기에서 에러가 발생해야 함
        } catch (IllegalStateException e) {
            // 예외가 발생할 경우 메세지 출력
            System.out.println("중복 회원 가입 에러 발생!!");
            return;
        }
        // 예외가 발생하지 않았으면 test 실패
        fail("예외가 발생해야 한다.");
    }

}