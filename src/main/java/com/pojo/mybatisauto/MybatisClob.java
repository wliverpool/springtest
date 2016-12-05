package com.pojo.mybatisauto;

public class MybatisClob {

	private int id;
	private String title;
	// 如果是数据库类型为blob,则java类型为byte[]
	private String bigType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBigType() {
		return bigType;
	}

	public void setBigType(String bigType) {
		this.bigType = bigType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o instanceof MybatisClob) {
			MybatisClob c = (MybatisClob) o;
			if (id == c.id) {
				if (this.title == null ? c.title == null : this.title.equals(c.title)) {
					if (this.bigType == null ? c.bigType == null : this.bigType.equals(c.bigType)) {
						return true;
					}
					return false;
				}
				return false;
			}
			return false;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = result * 31 + this.id;
		result = result * 31 + this.title.hashCode();
		result = result * 31 + this.bigType.hashCode();
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ClobTest:[id=").append(id).append(",title=").append(title).append(",bigType=").append(bigType)
				.append("]");
		return sb.toString();
	}

}
