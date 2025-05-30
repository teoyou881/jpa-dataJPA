package spring.data_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.data_jpa.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
