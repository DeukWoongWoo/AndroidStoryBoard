package Analysis.Database;

/**
 * Created by woong on 2016-01-20.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

public final class ConnectionPool {
    private static ConnectionPool instance;

    private static final boolean debug = true;

    private static final String DBName = "storyboard.db";
    public static String ProjectDir = null;

    private Vector free;
    private Vector used;

    private String driver;
    private String url;

    private int initialCons = 0;
    private int maxCons = 0;
    private int numCons = 0;

    private boolean block;
    private boolean reuseCons = true;

    private long timeout;

    private ConnectionPool() throws SQLException {
        loadConf();
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (maxCons > 0 && maxCons < initialCons)
            initialCons = maxCons;

        free = new Vector(initialCons);
        used = new Vector(initialCons);

        while (numCons < initialCons) {
            addConnection();
        }
    }

    public static ConnectionPool getInstance() throws SQLException {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }

    private void loadConf() {
        this.driver = "org.sqlite.JDBC";
        this.url = "jdbc:sqlite:"+ ProjectDir +"/" + DBName;
        this.initialCons = 3;
        this.maxCons = 10;
        this.block = true;
        this.timeout = 10000;
    }

    public synchronized void closeAll() {
        Enumeration cons = ((Vector) free.clone()).elements();
        while (cons.hasMoreElements()) {
            Connection con = (Connection) cons.nextElement();

            free.removeElement(con);
            numCons--;

            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        cons = ((Vector) used.clone()).elements();
        while (cons.hasMoreElements()) {
            Connection con = (Connection) cons.nextElement();

            used.removeElement(con);
        }
    }

    public boolean getBlock() {
        return block;
    }

    public Connection getConnection() throws SQLException {
        return getConnection(this.block, timeout);
    }

    public synchronized Connection getConnection(boolean block, long timeout) throws SQLException {

        if (free.isEmpty()) {
            if (maxCons <= 0 || numCons < maxCons) {
                addConnection();
            } else if (block) {
                try {
                    long start = System.currentTimeMillis();
                    do {
                        wait(timeout);
                        if (timeout > 0) {
                            timeout -= System.currentTimeMillis() - start;
                            if (timeout == 0) {
                                timeout -= 1;
                            }
                        }
                    } while (timeout >= 0 && free.isEmpty() && maxCons > 0 && numCons >= maxCons);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (free.isEmpty()) {
                    if (maxCons <= 0 || numCons < maxCons) {
                        addConnection();
                    } else {
                        throw new SQLException("Timeout waiting for a connection to be released");
                    }
                }
            } else {
                throw new SQLException("Maximum number of allowed connections reached");
            }
        }
        Connection con;
        synchronized (used) {
            con = (Connection) free.lastElement();
            free.removeElement(con);
            used.addElement(con);
        }
        return con;
    }

    public int getMaxCons() {
        return maxCons;
    }

    public boolean getReuseConnections() {
        return reuseCons;
    }

    public long getTimeout() {
        return timeout;
    }

    public String getUrl() {
        return url;
    }

    public synchronized void releaseConnection(Connection con) throws SQLException {
        boolean reuseThisCon = reuseCons;
        if (used.contains(con)) {
            used.removeElement(con);
            numCons--;
        } else {
            throw new SQLException("Connection " + con + " did not come from this ConnectionPool");
        }

        try {
            if (reuseThisCon) {
                free.addElement(con);
                numCons++;
            } else {
                con.close();
            }

            notify();
        } catch (SQLException e) {
            try {
                con.close();
            } catch (Exception e2) {
            }
            notify();
        }
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public synchronized void setReuseConnections(boolean reuseCons) {
        this.reuseCons = reuseCons;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    private void addConnection() throws SQLException {
        free.addElement(getNewConnection());
    }

    private Connection getNewConnection() throws SQLException {

        Connection con = null;
        try {
            con = DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ++numCons;
        return con;
    }
}
