package spring.data_jpa.dto;

import static java.util.Objects.requireNonNullElse;

import lombok.Data;
import spring.data_jpa.entity.Member;

@Data
public class MemberDto {

  private Long id;
  private String username;
  private String teamName;

  public MemberDto(Long id, String username, String teamName) {
    this.id = id;
    this.username = username;
    this.teamName = requireNonNullElse(teamName, "");
  }

  public MemberDto(Long id, String username) {
    this.id = id;
    this.username = username;
  }

  public MemberDto(Member member) {
    this.id = member.getId();
    this.username = member.getUsername();
    if (member.getTeam() != null) {
      this.teamName = member.getTeam().getName();
    }
  }
}
