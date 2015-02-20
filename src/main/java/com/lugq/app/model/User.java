package com.lugq.app.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.query.Query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lugq.app.dao.mongo.MongoDao;
import com.lugq.app.dao.mongo.MongoManager;

@Entity("User")
public class User {

	private static Logger logger = LogManager.getLogger(User.class);
	
	@Id
	private ObjectId id;

	private String username = "";

	@JsonIgnore
	private String password = "";

	private String nickname = "";

	private String avatar = "";

	private String cellphone = "";

	@JsonIgnore
	private long lateseLogin = 0;

	@JsonIgnore
	private long registerTime = 0;

	/* ======================= DAO ======================= */

	private static MongoDao<User> getDao() {
		return new MongoDao<User>(User.class, new MongoManager().getDb());
	}

	public void save() {
		MongoDao<User> dao = getDao();
		dao.save(this);
	}

	public static User findByUsername(String username) {
		MongoDao<User> dao = getDao();
		Query<User> q = dao.createQuery("username", username);
		User u = dao.findOne(q);
		return u;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getLateseLogin() {
		return lateseLogin;
	}

	public void setLateseLogin(long lateseLogin) {
		this.lateseLogin = lateseLogin;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public long getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(long registerTime) {
		this.registerTime = registerTime;
	}

}
