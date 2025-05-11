package spring.data_jpa.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.data_jpa.dto.MemberDto;
import spring.data_jpa.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

  List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

  @Query(name = "Member.findByUsername")
    //@Param("username")  생략해도 됨.
  List<Member> findByUsername(@Param("username") String username);

  // 아래처럼 @Query(name = "Member.findByUsername") 이게 없어도 실행된다.
  // 먼저 namedquery를 먼저 찾고, 없으면 methodname으로 찾는다.
  // List<Member> findByUsername(@Param("username") String username);

  @Query("select m from Member m where m.username=:username and m.age=:age")
  List<Member> findUser(@Param("username") String username, @Param("age") int age);

  @Query("select new spring.data_jpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t ")
  List<MemberDto> findMemberDto(String username);

  @Query("select m from Member m where m.username in :names")
  List<Member> findByNames(@Param("names") List<String> names);


  // 반환 타입 3가지 가능
  // 컬렉션, 단건 엔티티, 단건 옵셔널
  List<Member> findListByUsername(String username);

  Member findMemberByUsername(String username);

  Optional<Member> findOptionalByUsername(String username);
}