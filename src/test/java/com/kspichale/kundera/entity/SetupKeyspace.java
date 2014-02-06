package com.kspichale.kundera.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SetupKeyspace {

	public static void createNewKeyspace() throws SQLException, ClassNotFoundException {

		Class.forName("org.apache.cassandra.cql.jdbc.CassandraDriver");

		{
			Connection con = DriverManager.getConnection("jdbc:cassandra://127.0.0.1:9160/system?version=3.0.0");
			String createKeyspace = "DROP KEYSPACE IF EXISTS demo;";
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

}
