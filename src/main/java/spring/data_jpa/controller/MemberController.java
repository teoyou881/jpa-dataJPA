package spring.data_jpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import spring.data_jpa.dto.MemberDto;
import spring.data_jpa.entity.Member;
import spring.data_jpa.repository.MemberRepository;

@RestController
@RequiredArgsConstructor
public class MemberController {

  private final MemberRepository memberRepository;

  @GetMapping("/members/{id}")
  public String findMember(@PathVariable("id") Long id) {
    Member member = memberRepository.findById(id).get();
    return member.getUsername();
  }

  //  이 변환 과정의 핵심은 내부적으로 해당 엔티티의 Spring Data JPA 리포지토리를 호출하여 데이터베이스에서 엔티티를 찾아오는 것입니다 (memberRepository.findById(id)).
  // 직접 reppository에서 쿼리를 날려 가져오는 결과값이 같고, 영속성 컨텍스트에서 관리가 되는것도 같다.
  // 하지만, 웬만하면 조회용으로만 사용할 것을 권장한다.
  @GetMapping("/members2/{id}")
  public String findMember2(@PathVariable("id") Member member) {
    return member.getUsername();
  }

  // @GetMapping("/members")
  // public Page<Member> list(Pageable pageable) {
  //   return memberRepository.findAll(pageable);
  // }

  @GetMapping("/members/default")
  public Page<MemberDto> list(@PageableDefault(size = 12, sort = {"username"}) Pageable pageable) {
    Page<Member> page = memberRepository.findAll(pageable);
    Page<MemberDto> map = page.map(member -> new MemberDto(member.getId(), member.getUsername()));
    return map;
  }

  // @PostConstruct
  // public void init() {
  //   for (int i = 0; i < 100; i++) {
  //     memberRepository.save(new Member("member" + i, i));
  //   }
  // }


}
