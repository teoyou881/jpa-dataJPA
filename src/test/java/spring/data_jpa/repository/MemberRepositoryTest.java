package spring.data_jpa.repository;

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

    Member findMember = memberRepository.findById(saveMember.getId())
                                        .get();

    Assertions.assertThat(findMember)
              .isEqualTo(saveMember);
    Assertions.assertThat(findMember.getId())
              .isEqualTo(saveMember.getId());
    Assertions.assertThat(findMember.getUsername())
              .isEqualTo(saveMember.getUsername());
    Assertions.assertThat(findMember)
              .isEqualTo(member);
  }
}