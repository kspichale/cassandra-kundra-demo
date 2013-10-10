package com.kspichale.kundera;

import static org.fest.assertions.Assertions.assertThat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/repository-context.xml"})
public class FirebrandIT {
	
	@Autowired
	private TweetService service;

	@BeforeClass
	public static void createTablespace() throws SQLException, ClassNotFoundException {
		
//		Cluster cluster = HFactory.getOrCreateCluster("Test Cluster", "127.0.0.1:9160");
//
//		BasicColumnFamilyDefinition userColFamDef = new BasicColumnFamilyDefinition();
//		userColFamDef.setKeyspaceName("demo");
//		userColFamDef.setName("user");
//		userColFamDef.setKeyValidationClass(ComparatorType.UTF8TYPE.getClassName());
//		userColFamDef.setComparatorType(ComparatorType.UTF8TYPE);
//		ColumnFamilyDefinition userColFamDefThrift = new ThriftCfDef(userColFamDef);
//
//		KeyspaceDefinition keyspaceDefinition = HFactory.createKeyspaceDefinition("demo",
//				"org.apache.cassandra.locator.SimpleStrategy", 1,
//				Arrays.asList(userColFamDefThrift));
//
//		cluster.addKeyspace(keyspaceDefinition);
		
		Class.forName("org.apache.cassandra.cql.jdbc.CassandraDriver");
		
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
	    String query = "CREATE TABLE tweet (tweet_id varchar, user_id varchar, PRIMARY KEY(tweet_id,user_id));";
	    PreparedStatement statement = con.prepareStatement(query);
	    statement.execute();
	    statement.close();
		}
	}

	@Test
	public void createContext() {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("cassandra_pu");
		EntityManager em = emf.createEntityManager();
		em.setProperty("cql.version", "3.0.0");

		User user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setFirstname("FirebrandUser");

		em.persist(user);

		User loadedUser = em.find(User.class, user.getId());

		assertThat(user.getId()).isEqualTo(loadedUser.getId());
		assertThat(user.getFirstname()).isEqualTo(loadedUser.getFirstname());
	}

	@Test
	public void createTweet() {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("cassandra_pu");
		EntityManager em = emf.createEntityManager();
		em.setProperty("cql.version", "3.0.0");

		Tweet tweet = new Tweet();
		TweetPK pk = new TweetPK();
		pk.setTweetId(UUID.randomUUID().toString());
		pk.setUserId(UUID.randomUUID().toString());
		tweet.setPk(pk);

		em.persist(tweet);

		Tweet loadedTweet = em.find(Tweet.class, pk);
		assertThat(loadedTweet).isNotNull();

	}

}
