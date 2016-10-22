package com.dao.mongodb;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * 批量更新mongodb的数据的操作对象
 * @author 吴福明
 *
 */
public class BatchUpdateOptions {
	
	private Query query;
	private Update update;
	private boolean upsert = false;
	private boolean multi = false;
	
	public BatchUpdateOptions(){
		
	}
	
	public BatchUpdateOptions(Query query,Update update,boolean upsert,boolean multi){
		this.query = query;
		this.update = update;
		this.multi = multi;
		this.upsert = upsert;
	}
	
	public Query getQuery() {
		return query;
	}
	
	public void setQuery(Query query) {
		this.query = query;
	}
	
	public Update getUpdate() {
		return update;
	}
	
	public void setUpdate(Update update) {
		this.update = update;
	}
	
	public boolean isUpsert() {
		return upsert;
	}
	
	public void setUpsert(boolean upsert) {
		this.upsert = upsert;
	}
	
	public boolean isMulti() {
		return multi;
	}
	
	public void setMulti(boolean multi) {
		this.multi = multi;
	}

}
