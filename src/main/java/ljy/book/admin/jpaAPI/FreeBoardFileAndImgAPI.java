package ljy.book.admin.jpaAPI;

import org.springframework.data.jpa.repository.JpaRepository;

import ljy.book.admin.entity.BoardFileAndImg;

public interface FreeBoardFileAndImgAPI extends JpaRepository<BoardFileAndImg, Long> {

}
