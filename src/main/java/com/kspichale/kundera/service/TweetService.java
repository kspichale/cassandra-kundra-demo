package com.kspichale.kundera.service;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.kspichale.kundera.entity.TweetEntity;
import com.kspichale.kundera.entity.TweetEntityPK;

@Service
public class TweetService {

	@PersistenceUnit(unitName = "cassandra_pu")
	private EntityManagerFactory emf;

	private EntityManager createEntityManager() {
		EntityManager em = emf.createEntityManager();
		em.setProperty("cql.version", "3.0.0");
		return em;
	}

	public List<TweetEntity> getTweetsForUser(String userId) {
		EntityManager em = createEntityManager();
		String queryString = String.format("select * from tweet where user_id = '%s' limit %d;", userId, 10);
		Query query = em.createNativeQuery(queryString, TweetEntity.class);
		List<TweetEntity> results = query.getResultList();
		return results;
	}

	public void addTweet(String author, String message) {
		// must be one tweet per timeline
		TweetEntity newTweet = new TweetEntity();
		TweetEntityPK pk = new TweetEntityPK();
		pk.setTweetId(UUID.randomUUID().toString());
		// TODO
		pk.setUserId(author);
		newTweet.setPk(pk);
		newTweet.setAuthor(author);
		newTweet.setMessage(message);

		EntityManager em = createEntityManager();
		em.persist(newTweet);
	}
}
