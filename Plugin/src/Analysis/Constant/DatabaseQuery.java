package Analysis.Constant;

/**
 * Created by woong on 2016-01-20.
 */
public class DatabaseQuery {
    public static final String dropTable = "DROP TABLE IF EXISTS ";

    public static final String createManifestTable = "CREATE TABLE Manifest (" +
            "num INTEGER PRIMARY KEY AUTOINCREMENT," +
            "package TEXT, theme TEXT)";
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
            "path TEXT NOT NULL," +
            "extends TEXT," +
            "implements TEXT," +
            "nextActivity TEXT," +
            "intentName TEXT," +
            "intentFuncName TEXT" + ")";
    public static final String createXmlTable = "CREATE TABLE Xml (" +
            "num INTEGER PRIMARY KEY AUTOINCREMENT," +
            "javaId INTEGER NOT NULL," +
            "name TEXT," +
            "xmlName TEXT NOT NULL" + ")";
    public static final String createComponentTable = "CREATE TABLE Component (" +
            "num INTEGER PRIMARY KEY AUTOINCREMENT," +
            "xmlId INTEGER NOT NULL," +
            "name TEXT NOT NULL," +
            "type TEXT NOT NULL," +
            "methodName TEXT NOT NULL," +
            "xmlName TEXT NOT NULL," +
            "totalLine INTEGER NOT NULL," +
            "startLine INTEGER NOT NULL" +")";
    public static final String createEventTable = "CREATE TABLE Event (" +
            "num INTEGER PRIMARY KEY AUTOINCREMENT," +
            "componentId INTEGER NOT NULL," +
            "methodName TEXT NOT NULL," +
            "type INTEGER NOT NULL," +
            "totalLine INTEGER NOT NULL," +
            "startLine INTEGER NOT NULL" +")";

    public static final String insertManifest = "INSERT INTO Manifest(package, theme) VALUES(?, ?)";
    public static final String insertActivity = "INSERT INTO Activity(manifestId, name, action, totalLine, startLine) VALUES(?, ?, ?, ?, ?)";
    public static final String insertJava = "INSERT INTO Java(name, extends, implements, nextActivity) VALUES(?, ?, ?, ?)";
    public static final String insertXml = "INSERT INTO Xml(javaId, name, xmlName) VALUES(?, ?, ?)";
    public static final String insertComponent = "INSERT INTO Component(xmlId, name, xmlName, totalLine, startLine) VALUES(?, ?, ?, ?, ?)";
    public static final String insertEvent = "INSERT INTO Event(componentId, name, type, totalLine, startLine) VALUES(?, ?, ?, ?, ?)";
}
