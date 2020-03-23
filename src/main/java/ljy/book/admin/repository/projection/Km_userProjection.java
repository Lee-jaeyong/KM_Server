package ljy.book.admin.repository.projection;

/**
 * 
 * @author LJY
 *
 *         #유저의 정보를 갖는 프로젝션
 */

public interface Km_userProjection {
	Long userSEQ();

	String getId();

	String getRule();

	String getEmail();

	String getPhone();

	String getBirth();

	String getName();

	Long getKmSubject_Seq();

	String getKmSubject_SubjectNM();
}
