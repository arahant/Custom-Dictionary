package dictionary.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dictionary.word.Word;


public final class Database {

	private static final String DRIVER_O = "oracle.jdbc.driver.OracleDriver";
	private static final String URL_O = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String USER_O = "dictionary";
	private static final String PASS_O = "panthara";

	private static Connection connect;
	private static Statement stmt;

	private Database() {
		/*cannot instantiate this class outside (not required) as all the methods are static*/
	}

	private static void connect() {
		try {
			Class.forName(DRIVER_O);
			//DriverManager.registerDriver( new oracle.jdbc.driver.OracleDriver());
			connect = DriverManager.getConnection(URL_O, USER_O, PASS_O);
			System.out.println("Connected to the database...");
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void addWord(Word word) {
		if(connect==null)
			connect();
		try {
			if(connect!=null) {
				String query = "insert into dictionary values ('"+word.getWord()+"','"+word.getPostype()+"','"+word.getDefinition()+"')";
				stmt = connect.createStatement();
				stmt.executeUpdate(query);
				System.out.println(query);
				addSynonyms(word);
				addAntonyms(word);
				connect.commit();
				//stmt.close();connect.close();
			}
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void addSynonyms(Word word) {
		if(connect==null)
			connect();
		try {
			if(connect!=null) {
				for(String s:word.getSynonyms()) {
					String query = "insert into synonyms values ('"+word.getWord()+"','"+s+"')";
					stmt = connect.createStatement();
					stmt.executeUpdate(query);
					System.out.println(query);
				}
				connect.commit();
				//stmt.close();connect.close();
			}
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		} catch(NullPointerException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void addAntonyms(Word word) {
		if(connect==null)
			connect();
		try {
			if(connect!=null) {
				for(String a:word.getAntonyms()) {
					String query = "insert into antonym values ('"+word.getWord()+"','"+a+"')";
					stmt = connect.createStatement();
					stmt.executeUpdate(query);
					System.out.println(query);
				}
				connect.commit();
				//stmt.close();connect.close();
			}
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		} catch(NullPointerException e) {
			System.err.println(e.getMessage());
		}
	}

	public static Word findWord(String text) {
		Word word=null;
		if(connect==null)
			connect();
		try {
			if(connect!=null) {
				String query = "select * from dictionary where word ='"+text+"'";
				stmt = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet words = stmt.executeQuery(query);
				if(words.next()) {
					word = new Word(text);
					word.setPostype(words.getString(2));
					word.setDefinition(words.getString(3));
					findSynonyms(word);
					findAntonyms(word);
				}
			}
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		return word;
	}

	public static void findSynonyms(Word word) {
		try {
			if(connect!=null) {
				String query = "select synonyms from synonyms where word ='"+word.getWord()+"'";
				stmt = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet synonyms = stmt.executeQuery(query);
				while(synonyms.next())
					word.addSynonym(synonyms.getString(1));
			}
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void findAntonyms(Word word) {
		try {
			if(connect!=null) {
				String query = "select antonym from antonym where word ='"+word.getWord()+"'";
				stmt = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet antonyms = stmt.executeQuery(query);
				while(antonyms.next())
					word.addAntonym(antonyms.getString(1));
			}
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
	}

}
