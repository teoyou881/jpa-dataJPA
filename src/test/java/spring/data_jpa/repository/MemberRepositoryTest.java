package spring.data_jpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import spring.data_jpa.entity.Member;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

  @Autowired
  MemberRepository memberRepository;

  @Test
  public void testMember() {
    Member member = new Member("memberA");
    Member saveMember = memberRepository.save(member);

    Member findMember = memberRepository.findById(saveMember.getId()).get();

    Assertions.assertThat(findMember).isEqualTo(saveMember);
    Assertions.assertThat(findMember.getId()).isEqualTo(saveMember.getId());
    Assertions.assertThat(findMember.getUsername()).isEqualTo(saveMember.getUsername());
    Assertions.assertThat(findMember).isEqualTo(member);
  }


  @Test
  public void basicCRUD() {
    Member memberA = new Member("memberA");
    Member memberB = new Member("memberB");

    memberRepository.save(memberA);
    memberRepository.save(memberB);

    Member findMemberA = memberRepository.findById(memberA.getId()).get();
    Member findMemberB = memberRepository.findById(memberB.getId()).get();

    assertThat(findMemberA).isEqualTo(memberA);
    assertThat(findMemberB).isEqualTo(memberB);

    long count = memberRepository.count();
    assertThat(count).isEqualTo(2);

    memberRepository.delete(findMemberA);
    memberRepository.delete(findMemberB);

    count = memberRepository.count();
    assertThat(count).isEqualTo(0);
  }

  @Test
  public void findByUsernameAndAgeGreaterThen() {
    Member memberA = new Member("AAA", 20);
    Member memberB = new Member("BBB", 30);
    Member memberC = new Member("CCC", 40);

    memberRepository.save(memberA);
    memberRepository.save(memberB);
    memberRepository.save(memberC);

    List<Member> aaa = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 10);
    assertThat(aaa.size()).isEqualTo(1);
    assertThat(aaa.getFirst().getUsername()).isEqualTo("AAA");
  }
}