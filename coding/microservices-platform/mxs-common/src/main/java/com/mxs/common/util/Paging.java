package com.mxs.common.util;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * com.mxs.common.util.Paging
 * @author j.yang
 *
 */
@Data
@NoArgsConstructor
@Builder
public class Paging implements Serializable {
	private int pageNo;
	private int pageSize;
	private long totalHits;
    private boolean hasMore;
	
	public Paging(int pageNo, int pageSize, long totalHits,boolean hasMore) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalHits = totalHits;
        this.hasMore = hasMore;
	}
}
