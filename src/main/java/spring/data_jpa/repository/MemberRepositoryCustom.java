package spring.data_jpa.repository;

import java.util.List;
import spring.data_jpa.entity.Member;

public interface MemberRepositoryCustom {

  List<Member> findMemberCustom();

}
