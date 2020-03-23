package ljy.book.admin.common.object;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@Scope("prototype")
public class CustomBlock {
	int startBlock;
	int endBlock;
	int totalBlock;

	public CustomBlock getBlockInfo(Long totalCount, int pageNum) {
		getTotalCount(totalCount, pageNum);
		return this;
	}

	private void getTotalCount(Long totalCount, int pageNum) {
		this.totalBlock = (int) Math.ceil((double) totalCount / 10 * 1.0);
		calculBlock(pageNum);
	}

	private void calculBlock(int pageNum) {
		this.startBlock = pageNum / 10 * 10;
		this.endBlock = this.startBlock + 10;
		if (this.endBlock > this.totalBlock)
			this.endBlock = this.totalBlock;
	}
}