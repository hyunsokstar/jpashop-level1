package jpabook.jpashop.repository;
import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class MemberRepository {
    // PersistenceContext 어노테이션을 달아야 em 객체를 주입 받을 수 있다.
    @PersistenceContext
    private EntityManager em;

    // 멤버 객체를 인자로 받아 멤버 테이블에 값을 저장 하도록 하기
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    // memberId로 멤버 테이블 조회 하기
    public Member find(Long id) {
        return em.find(Member.class, id);
    }


    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    // 모든 멤버 정보 찾기
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    // 멤버 이름으로 찾기
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name= :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
