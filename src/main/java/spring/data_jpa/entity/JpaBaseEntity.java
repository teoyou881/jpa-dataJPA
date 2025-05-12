package spring.data_jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class JpaBaseEntity {

  @Column(updatable = false)
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;

  @PrePersist
  public void prePersist() {
    createdDate = LocalDateTime.now();
    updatedDate = LocalDateTime.now();
  }

  @PreUpdate
  public void poreUpdate() {
    updatedDate = LocalDateTime.now();
  }
}
