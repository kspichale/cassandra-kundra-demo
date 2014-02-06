package com.kspichale.kundera.entity;

import static org.fest.assertions.Assertions.assertThat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/repository-context.xml" })
public class CreateColumnFamiliesIT {

	@BeforeClass
	public static void createTablespace() throws SQLException, ClassNotFoundException {
		SetupKeyspace.createNewKeyspace();
	}

	@Test
	public void createTweet() {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("cassandra_pu");
		EntityManager em = emf.createEntityManager();
		em.setProperty("cql.version", "3.0.0");

		TweetEntity tweet = new TweetEntity();
		TweetEntityPK pk = new TweetEntityPK();
		pk.setUserId("kai");
		pk.setTweetId(UUID.randomUUID().toString());
		tweet.setPk(pk);
		tweet.setAuthor("kai");
		tweet.setMessage("This tweet was created by a test.");

		em.persist(tweet);

		TweetEntity loadedTweet = em.find(TweetEntity.class, pk);
		assertThat(loadedTweet).isNotNull();

		Query q = em.createNativeQuery("select * from tweet where user_id = 'kai' limit 10;", TweetEntity.class);
		List<TweetEntity> list = q.getResultList();
		assertThat(list).isNotEmpty();
	}

	public void saveUserName(String userId, String userName, DataSource dataSource) {

		String query = "UPDATE User SET name=? WHERE KEY=?";
		try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(query);) {
			ps.setString(1, userName);
			ps.setString(2, userId);
			ps.executeUpdate();
		} catch (SQLException e) {
			// handle exception
		}
	}
}
