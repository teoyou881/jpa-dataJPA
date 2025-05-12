package spring.data_jpa.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

  Page<Member> findByAgeGreaterThan(int age, Pageable pageable);

  // @Modifying 이 없으면, getsingleresult or getreulstlist를 호출한다.
  // update를 치기 위해서는 @Modifying 이 필요하고, 이 어노테이션이 executeUpdate()를 해준다.
  // error
  // org.springframework.dao.InvalidDataAccessApiUsageException: Query executed via 'getResultList()' or 'getSingleResult()' must be a 'select' query [update Member m set m.age=m.age+:age where m.age>=:age]
  @Modifying(clearAutomatically = true)
  @Query("update Member m set m.age=m.age+:age where m.age>=:age")
  int bulkAgePlus(@Param("age") int age);

  @Query("select m from Member m join fetch m.team t")
  List<Member> findMemberFetchJoin();

  // 공통 메서드 오버라이드
  @Override
  @EntityGraph(attributePaths = {"team"})
  List<Member> findAll();

  // JPQL + 엔티티 그래프
  @EntityGraph(attributePaths = {"team"})
  @Query("select m from Member m")
  List<Member> findMemberEntityGraph();

  // 메서드 이름으로 쿼리에서 특히 편리하다.
  // @EntityGraph(attributePaths = {"team"})
  // List<Member> findByUsername(String username);

}