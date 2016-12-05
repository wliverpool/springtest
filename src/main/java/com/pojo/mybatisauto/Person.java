package com.pojo.mybatisauto;

import java.util.List;

public class Person {

	private int id;
	private String name;
	private Card card;
	private List<Address> addressList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public List<Address> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<Address> addressList) {
		this.addressList = addressList;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o instanceof Person) {
			Person p = (Person) o;
			if (this.id == p.id) {
				if (this.card == null ? p.card == null : this.card.equals(p.card)) {
					if (this.name == null ? p.name == null : this.name.equals(p.name)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + id;
		result = 31 * result + name.hashCode();
		result = 31 * result + card.hashCode();
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Person[id=").append(id).append(",name=").append(name).append(",card=").append(card).append("]");
		return sb.toString();
	}

}
