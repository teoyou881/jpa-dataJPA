package spring.data_jpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import spring.data_jpa.dto.MemberDto;
import spring.data_jpa.entity.Member;
import spring.data_jpa.entity.Team;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {

  @Autowired
  MemberRepository memberRepository;
  @Autowired
  TeamJpaRepository teamJpaRepository;


  @BeforeEach
  public void before() {

    Team teamA = new Team("TeamA");
    Team teamB = new Team("TeamB");
    teamJpaRepository.save(teamA);
    teamJpaRepository.save(teamB);

    Member memberA = new Member("AAA", 20, teamA);
    Member memberB = new Member("BBB", 30, teamA);
    Member memberC = new Member("CCC", 40, teamB);

    memberRepository.save(memberA);
    memberRepository.save(memberB);
    memberRepository.save(memberC);


  }

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

    long count = memberRepository.count();
    assertThat(count).isEqualTo(3);

    List<Member> all = memberRepository.findAll();

    memberRepository.deleteAll(all);

    count = memberRepository.count();
    assertThat(count).isEqualTo(0);
  }

  @Test
  public void findByUsernameAndAgeGreaterThen() {

    List<Member> aaa = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 10);
    assertThat(aaa.size()).isEqualTo(1);
    assertThat(aaa.getFirst().getUsername()).isEqualTo("AAA");
  }

  @Test
  public void testNamedQuery() {

    List<Member> members = memberRepository.findByUsername("AAA");
    assertThat(members.size()).isEqualTo(1);
    Member findMember = members.get(0);
    assertThat(findMember.getUsername()).isEqualTo("AAA");
  }

  @Test
  public void testQuery() {
    List<Member> members = memberRepository.findUser("AAA", 20);
    assertThat(members.size()).isEqualTo(1);
    Member findMember = members.get(0);
    assertThat(findMember.getUsername()).isEqualTo("AAA");
  }

  @Test
  public void findMemberDto() {
    List<MemberDto> result = memberRepository.findMemberDto("AAA");
    for (MemberDto memberDto : result) {
      System.out.println("memberDto = " + memberDto);
    }
  }

  @Test
  public void findByNames() {
    List<Member> members = memberRepository.findByNames(List.of("AAA", "BBB"));
    assertThat(members.size()).isEqualTo(2);
  }

  @Test
  public void returnType() {
    List<Member> aaa = memberRepository.findListByUsername("AAA");
    assertThat(aaa.size()).isEqualTo(1);
    Member findMember = aaa.get(0);
    assertThat(findMember.getUsername()).isEqualTo("AAA");

    Member bbb = memberRepository.findMemberByUsername("BBB");
    assertThat(bbb.getUsername()).isEqualTo("BBB");

    Member memberC = memberRepository.findOptionalByUsername("CCC").orElseGet(() -> new Member("ccc"));
    System.out.println("memberC = " + memberC);
  }


}