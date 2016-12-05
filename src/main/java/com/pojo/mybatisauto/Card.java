package com.pojo.mybatisauto;

public class Card {

	private int id;
	private String info;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o instanceof Card) {
			Card c = (Card) o;
			if (this.id == c.id) {
				if (this.info == null ? c.info == null : this.info.equals(c.info)) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + id;
		result = 31 * result + info.hashCode();
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Card[id=").append(id).append(",info=").append(info).append("]");
		return sb.toString();
	}

}
