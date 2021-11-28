package com.booksaw.betterTeams.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.booksaw.betterTeams.database.api.Database;

public class BetterTeamsDatabase extends Database {

	public void setupTables() {
		createTableIfNotExists(TableName.TEAM.toString(),
				"teamID VARCHAR(50) NOT NULL PRIMARY KEY, name VARCHAR(50) NOT NULL, description VARCHAR(200), open BOOLEAN NOT NULL, score INT DEFAULT 0, money DOUBLE DEFAULT 0, home VARCHAR(50), color CHAR(1) DEFAULT '6', echest VARCHAR(50), level INT DEFAULT 1, tag VARCHAR(50)");

		createTableIfNotExists(TableName.PLAYERS.toString(),
				"playerUUID VARCHAR(50) NOT NULL PRIMARY KEY, teamID VARCHAR(50) NOT NULL, playerRank INT NOT NULL, FOREIGN KEY (teamID) REFERENCES "
						+ TableName.TEAM.toString() + "(teamID) ON DELETE CASCADE");

		createTableIfNotExists(TableName.ALLYREQUESTS.toString(),
				"requestingTeamID VARCHAR(50) NOT NULL, receivingTeamID VARCHAR(50) NOT NULL, PRIMARY KEY(requestingTeamID, receivingTeamID), FOREIGN KEY (requestingTeamID) REFERENCES "
						+ TableName.TEAM.toString()
						+ "(teamID) ON DELETE CASCADE, FOREIGN KEY (receivingTeamID) REFERENCES "
						+ TableName.TEAM.toString() + "(teamID) ON DELETE CASCADE");

		createTableIfNotExists(TableName.WARPS.toString(),
				"TeamID VARCHAR(50) NOT NULL, warpInfo VARCHAR(50) NOT NULL, PRIMARY KEY(TeamID, warpInfo), FOREIGN KEY (TeamID) REFERENCES "
						+ TableName.TEAM.toString() + "(teamID) ON DELETE CASCADE");

		createTableIfNotExists(TableName.CHESTCLAIMS.toString(),
				"TeamID VARCHAR(50) NOT NULL, chestLoc VARCHAR(50) NOT NULL, PRIMARY KEY(TeamID, chestLoc), FOREIGN KEY (TeamID) REFERENCES "
						+ TableName.TEAM.toString() + "(teamID) ON DELETE CASCADE");

		createTableIfNotExists(TableName.BANS.toString(),
				"PlayerUUID VARCHAR(50) NOT NULL, TeamID VARCHAR(50) NOT NULL, PRIMARY KEY(PlayerUUID, TeamID), FOREIGN KEY (teamID) REFERENCES "
						+ TableName.TEAM.toString() + "(teamID) ON DELETE CASCADE");

		createTableIfNotExists(TableName.ALLIES.toString(),
				"team1ID VARCHAR(50) NOT NULL, team2ID VARCHAR(50) NOT NULL, PRIMARY KEY(team1ID, team2ID), FOREIGN KEY (team1ID) REFERENCES "
						+ TableName.TEAM.toString() + "(teamID) ON DELETE CASCADE, FOREIGN KEY (team2ID) REFERENCES "
						+ TableName.TEAM.toString() + "(teamID) ON DELETE CASCADE");

	}

	/**
	 * 
	 * @param select the element to select
	 * @param from   the table which the data is from
	 * @param where  the condition required for the data to be included
	 * @return the resultant resultSet
	 */
	public ResultSet selectWhere(String select, TableName from, String where) {
		return executeQuery("SELECT ? FROM ? WHERE ?;", select, from.toString(), where);
	}

	/**
	 * Used to check if an SQL query has a result
	 * 
	 * @param from  the table which the data is from
	 * @param where the condition required for the data to be included
	 * @return if the query has a result
	 */
	public boolean hasResult(TableName from, String where) {

		try {
			return selectWhere("*", from, where).first();
		} catch (SQLException e) {
			return false;
		}

	}

	/**
	 * Used to a specific column value from the
	 * 
	 * @param column The column name
	 * @param from   the table
	 * @param where  the condition
	 * @return the first returned result, the specified column. Will return "" if an
	 *         error occurs
	 */
	public String getResult(String column, TableName from, String where) {

		ResultSet results = selectWhere(column, from, where);
		try {
			return results.getString(column);
		} catch (SQLException e) {
			return "";
		}

	}

}