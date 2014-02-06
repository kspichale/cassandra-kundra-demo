package com.kspichale.kundera.entity;

import static org.fest.assertions.Assertions.assertThat;

import java.sql.SQLException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.kspichale.kundera.entity.TweetEntity;
import com.kspichale.kundera.service.TweetService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/repository-context.xml" })
public class KunderaIT {

	@Autowired
	private TweetService service;
	
	@BeforeClass
	public static void createTablespace() throws SQLException, ClassNotFoundException {
		SetupKeyspace.createNewKeyspace();
	}

	@Test
	public void loadTweetsForUser() {
		List<TweetEntity> tweets = service.getTweetsForUser("kai");
		assertThat(tweets).isNotNull();
	}

	@Test
	public void createTweet() {
		service.addTweet("kspichale", "hello world");
	}
}
