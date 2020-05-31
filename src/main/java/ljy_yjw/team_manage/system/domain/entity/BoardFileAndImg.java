package ljy_yjw.team_manage.system.domain.entity;

import java.io.IOException;
import java.io.InputStream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.annotation.JsonBackReference;

import ljy_yjw.team_manage.system.domain.enums.FileType;
import ljy_yjw.team_manage.system.service.read.freeBoard.FreeBoardReadService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardFileAndImg {
	@Id
	@GeneratedValue
	long seq;
	@Column(nullable = false)
	String name;
	@Column(nullable = false)
	String date;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	FileType type;

	@ManyToOne
	@JsonBackReference
	FreeBoard freeBoard;

	@Transient
	byte[] imgByte;

	public void getImgByte(FreeBoardReadService freeBoardReadService) throws IOException {
		InputStream in = freeBoardReadService.fileDownload(freeBoard.getSeq(), this.name).getInputStream();
		imgByte = IOUtils.toByteArray(in);
	}
}
