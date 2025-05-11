package spring.data_jpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import spring.data_jpa.entity.Member;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberJpaRepositoryTest {

  @Autowired
  MemberJpaRepository memberJpaRepository;

  @Test
  public void testMember() {
    Member member = new Member("memberA");
    Member saveMember = memberJpaRepository.save(member);

    Member findMember = memberJpaRepository.find(saveMember.getId());

    assertThat(findMember).isEqualTo(saveMember);
    assertThat(findMember.getId()).isEqualTo(saveMember.getId());
    assertThat(findMember.getUsername()).isEqualTo(saveMember.getUsername());
    assertThat(findMember).isEqualTo(member);
  }

  @Test
  public void basicCRUD() {
    Member memberA = new Member("memberA");
    Member memberB = new Member("memberB");

    memberJpaRepository.save(memberA);
    memberJpaRepository.save(memberB);

    Member findMemberA = memberJpaRepository.findById(memberA.getId()).get();
    Member findMemberB = memberJpaRepository.findById(memberB.getId()).get();

    assertThat(findMemberA).isEqualTo(memberA);
    assertThat(findMemberB).isEqualTo(memberB);

    long count = memberJpaRepository.count();
    assertThat(count).isEqualTo(2);

    memberJpaRepository.delete(findMemberA);
    memberJpaRepository.delete(findMemberB);

    count = memberJpaRepository.count();
    assertThat(count).isEqualTo(0);
  }

  @Test
  public void findByUsernameAndAgeGreaterThen() {
    Member memberA = new Member("AAA", 20);
    Member memberB = new Member("BBB", 30);
    Member memberC = new Member("CCC", 40);

    memberJpaRepository.save(memberA);
    memberJpaRepository.save(memberB);
    memberJpaRepository.save(memberC);

    List<Member> aaa = memberJpaRepository.findByUsernameAndAgeGreaterThan("AAA", 10);
    assertThat(aaa.size()).isEqualTo(1);
    assertThat(aaa.getFirst().getUsername()).isEqualTo("AAA");
  }
}