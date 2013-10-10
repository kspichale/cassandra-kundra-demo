package com.kspichale.kundera.entity;

import static org.fest.assertions.Assertions.assertThat;

import java.sql.Connection;
import java.sql.DriverManager;
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

import com.kspichale.kundera.entity.TweetEntity;
import com.kspichale.kundera.entity.TweetEntityPK;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/repository-context.xml" })
public class CreateColumnFamiliesIT {

	@BeforeClass
	public static void createTablespace() throws SQLException, ClassNotFoundException {

		Class.forName("org.apache.cassandra.cql.jdbc.CassandraDriver");

		{
			Connection con = DriverManager.getConnection("jdbc:cassandra://127.0.0.1:9160/system?version=3.0.0");
			String createKeyspace = "DROP KEYSPACE demo;";
			PreparedStatement statement = con.prepareStatement(createKeyspace);
			statement.execute();
			statement.close();
		}
		{
			Connection con = DriverManager.getConnection("jdbc:cassandra://127.0.0.1:9160/system?version=3.0.0");
			String createKeyspace = "CREATE KEYSPACE demo WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};";
			PreparedStatement statement = con.prepareStatement(createKeyspace);
			statement.execute();
			statement.close();
		}

		Connection con = DriverManager.getConnection("jdbc:cassandra://127.0.0.1:9160/demo?version=3.0.0");
		{
			String query = "CREATE TABLE user (user_id varchar, user_firstname varchar, PRIMARY KEY(user_id)) WITH COMPACT STORAGE;";
			PreparedStatement statement = con.prepareStatement(query);
			statement.execute();
			statement.close();
		}

		{
			String query = "CREATE TABLE tweet (user_id varchar, tweet_id varchar, author varchar, message varchar, PRIMARY KEY(user_id,tweet_id));";
			PreparedStatement statement = con.prepareStatement(query);
			statement.execute();
			statement.close();
		}
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
