package com.pojo.mybatisauto;

public class Address {

	private int id;
	private String address;
	private Person person;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	@Override
	public boolean equals(Object o){
		if (this==o) {
			return true;
		}
		if (o instanceof Address) {
			Address a=(Address)o;
			if (this.id==a.id) {
				if (this.address==null?a.address==null:this.address.equals(a.address)) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		int result=17;
		result=31*result+id;
		result=31*result+address.hashCode();
		return result;
	}
	
	@Override
	public String toString(){
		StringBuilder sb=new StringBuilder();
		sb.append("Address[id=").append(id).append(",address=").append(address).append("]");
		return sb.toString();
	}

}
