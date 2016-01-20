package Analysis.Database;

/**
 * Created by woong on 2016-01-20.
 */
public class DatabaseQuery {
    public static final String dropTable = "DROP TABLE IF EXISTS ";
    public static final String createManifestTable = "CREATE TABLE Manifest ( num INTEGER PRIMARY KEY AUTOINCREMENT," +
            "package TEXT, theme TEXT, activity integer )";
    public static final String createActivityTable = "CREATE TABLE Activity (" +
            "num INTEGER PRIMARY KEY AUTOINCREMENT," +
            "manifestId INTEGER NOT NULL," +
            "name TEXT NOT NULL," +
            "action INTEGER DEFAULT 0," +
            "totalLine INTEGER NOT NULL," +
            "startLine INTEGER NOT NULL" + ")" ;
    public static final String createJavaTable = "CREATE TABLE Java (" +
            "num INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT NOT NULL," +
            "extends TEXT," +
            "implements TEXT," +
            "nextActivity TEXT" + ")";
    public static final String createXmlTable = "CREATE TABLE Xml (" +
            "num INTEGER PRIMARY KEY AUTOINCREMENT," +
            "javaId INTEGER NOT NULL," +
            "xmlName TEXT NOT NULL," +
            "name TEXT" + ")";
    public static final String createComponentTable = "CREATE TABLE Component (" +
            "num INTEGER PRIMARY KEY AUTOINCREMENT," +
            "xmlId INTEGER NOT NULL," +
            "name TEXT NOT NULL," +
            "xmlName TEXT NOT NULL," +
            "startLine INTEGER NOT NULL," +
            "totalLine INTEGER NOT NULL" + ")";
    public static final String createEventTable = "CREATE TABLE Event (" +
            "num INTEGER PRIMARY KEY AUTOINCREMENT," +
            "componentId INTEGER NOT NULL," +
            "name TEXT NOT NULL," +
            "type INTEGER NOT NULL," +
            "startLine INTEGER NOT NULL," +
            "totalLine INTEGER NOT NULL" + ")";
}
