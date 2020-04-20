package ljy.book.admin.jpaAPI;

import org.springframework.data.jpa.repository.JpaRepository;

import ljy.book.admin.entity.Notice;

public interface NoticeAPI extends JpaRepository<Notice, Long>{
	
}
