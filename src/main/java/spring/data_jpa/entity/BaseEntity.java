package spring.data_jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@Setter
public class BaseEntity {

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdDate;
  @LastModifiedDate
  private LocalDateTime lastModifiedDate;

  /*
   * createdby, lastmodifiedby 는 main에서 AuditorAware<T> 를 빈으로 등록하고 처리해줘야 한다.
   * */

  @CreatedBy
  @Column(updatable = false)
  private String createdBy;

  @LastModifiedBy
  private String lastModifiedBy;
}
