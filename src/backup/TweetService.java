package com.kspichale.kundera;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

@Service
public class TweetService {

	@PersistenceContext
	public EntityManager em;

	public void test() {
		em.setProperty("cql.version", "3.0.0");

		User user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setFirstname("FirebrandUser");

		em.persist(user);
	}

}
