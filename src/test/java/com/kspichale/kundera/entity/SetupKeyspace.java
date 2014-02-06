package com.kspichale.kundera.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SetupKeyspace {

	public static void createNewKeyspace() throws SQLException, ClassNotFoundException {

		Class.forName("org.apache.cassandra.cql.jdbc.CassandraDriver");

		Connection systemCon = DriverManager.getConnection("jdbc:cassandra://127.0.0.1:9160/system?version=3.0.0");
		executePreparedStatement(systemCon, "DROP KEYSPACE IF EXISTS demo;");
		executePreparedStatement(systemCon, "CREATE KEYSPACE demo WITH replication = {'class':'SimpleStrategy', 'replication_factor':1};");
		
		Connection demoCon = DriverManager.getConnection("jdbc:cassandra://127.0.0.1:9160/demo?version=3.0.0");
		executePreparedStatement(demoCon, "CREATE TABLE user (user_id varchar, user_firstname varchar, PRIMARY KEY(user_id)) WITH COMPACT STORAGE;");
		executePreparedStatement(demoCon, "CREATE TABLE tweet (user_id varchar, tweet_id varchar, author varchar, message varchar, PRIMARY KEY(user_id,tweet_id));");
	}
	
	private static void executePreparedStatement(Connection con, String query) throws SQLException, ClassNotFoundException {
		PreparedStatement statement = con.prepareStatement(query);
		statement.execute();
		statement.close();
	}

}
