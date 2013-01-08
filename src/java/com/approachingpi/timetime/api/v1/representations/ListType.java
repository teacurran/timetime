package com.approachingpi.timetime.api.v1.representations;

/**
 * Date: 12/9/12
 *
 * @author T. Curran
 */
public class ListType {

	private Integer max;
	private Integer offset;
	private Long total;
	private Long listTotal;

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getListTotal() {
		return listTotal;
	}

	public void setListTotal(Long listTotal) {
		this.listTotal = listTotal;
	}

}
