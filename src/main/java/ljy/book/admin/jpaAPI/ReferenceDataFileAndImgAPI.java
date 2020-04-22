package ljy.book.admin.jpaAPI;

import org.springframework.data.jpa.repository.JpaRepository;

import ljy.book.admin.entity.ReferenceFileAndImg;

public interface ReferenceDataFileAndImgAPI extends JpaRepository<ReferenceFileAndImg, Long> {
}
