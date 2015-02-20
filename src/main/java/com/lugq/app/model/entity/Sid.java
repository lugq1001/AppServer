package com.lugq.app.model.entity;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import com.lugq.app.dao.mongo.MongoDao;
import com.lugq.app.dao.mongo.MongoManager;

/**
 * 自增id
 * 
 * @author Luguangqing
 *
 */
@Entity(value="sid", noClassnameStored = true)
public class Sid {

	@Id
	private String id;

	private long seq = 10000;

	public enum EntitySeq {
		User("User", 10000);
		
		public String seqName;
		public long seqStart;
		
		private EntitySeq(String seqName, long seqStart) {
			this.seqName = seqName;
			this.seqStart = seqStart;
		}

	}

	/* ======================= DAO ======================= */

	private static MongoDao<Sid> getDao() {
		return new MongoDao<Sid>(Sid.class, new MongoManager().getDb());
	}

	public static long getNextSequence(EntitySeq entitySeq) {
		MongoDao<Sid> dao = getDao();
		Query<Sid> q = dao.createQuery("id", entitySeq.seqName);
		UpdateOperations<Sid> ops = dao.createUpdateOperations().inc("seq");
		Sid sid = dao.findAndModify(q, ops);
		if (sid == null) {
			sid = new Sid();
			sid.setId(entitySeq.seqName);
			sid.setSeq(entitySeq.seqStart);
			dao.save(sid);
		}
		return sid.seq;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

}
