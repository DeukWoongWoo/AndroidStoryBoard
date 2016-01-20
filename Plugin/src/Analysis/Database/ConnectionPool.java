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
    // Singleton
    private static ConnectionPool instance;

    // Print debug information to System.err.
    private static final boolean debug = true;

    // Storage for the unused connections.
    private Vector free;

    // Storage for the allocated connections.
    private Vector used;

    // Connection information.
    private String driver;
    private String url;
    private Properties info;

    // Initial Connections
    private int initialCons = 0;

    // Maximum number of concurrent connections allowed.
    private int maxCons = 0;

    // The number of connection that have been created.
    private int numCons = 0;

    // Whether to block until a connection is free when maxCons are in use.
    private boolean block;

    // Timeout waiting for a connection to be released when blocking.
    private long timeout;

    // Whether we should re-use connections or not
    private boolean reuseCons = true;

    private ConnectionPool() throws SQLException {
        loadConf();
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // maxCons has precedence over initialCons
        if (maxCons > 0 && maxCons < initialCons)
            initialCons = maxCons;

        // Create vectors large enough to store all the connections we make now.
        free = new Vector(initialCons);
        used = new Vector(initialCons);

        // Create some connections.
        while (numCons < initialCons) {
            addConnection();
        }
        //System.out.println("Connection Pool Constructor");
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
        this.url = "jdbc:sqlite:C:/Users/woong/IdeaProjects/PluginTest/storyboard.db";
        this.initialCons = 2;
        this.maxCons = 3;
        this.block = true;
        this.timeout = 10000;
		/*
	 	ClassLoader cl = getClass().getClassLoader();
		InputStream in=cl.getResourceAsStream("db.properties");
		Properties p = new Properties();
		try {
			p.load(in);
		} catch(IOException e) {
			e.printStackTrace();
		}
		this.driver=(String)p.get("driver");
		//System.out.println(driver);
		this.url = (String)p.get("url");
		//System.out.println(url);
		this.user = (String)p.get("user");
		//System.out.println(user);
		this.password = (String)p.get("passwd");
		//System.out.println(password);
		this.initialCons = Integer.parseInt((String)p.get("initialCons"));
		//System.out.println(initialCons); this.maxCons =
		Integer.parseInt((String)p.get("maxCons"));
		//System.out.println(maxCons);
		this.block = new Boolean((String)p.get("block")).booleanValue();
		//System.out.println(block);
		this.timeout = Long.parseLong((String)p.get("timeout"));
		//System.out.println(timeout);
		*/

    }

    public synchronized void closeAll() {

        // Close unallocated connections
        Enumeration cons = ((Vector) free.clone()).elements();
        while (cons.hasMoreElements()) {
            Connection con = (Connection) cons.nextElement();

            free.removeElement(con);
            numCons--;

            try {
                con.close();
            } catch (SQLException e) {
                // The Connection appears to be broken anyway, so we will ignore it
                e.printStackTrace();
            }
        }

        // Move allocated connections to a list of connections that are closed
        // when they are released.
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

            // None left, do we create more?
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
                // Did anyone release a connection while we were waiting?
                if (free.isEmpty()) {
					/*
					 * OK, nothing on the free list, but someone may have
					 * released a connection that they closed, so the free list
					 * is empty but numCons is now < maxCons and we can create a
					 * new connection.
					 */
                    if (maxCons <= 0 || numCons < maxCons) {
                        addConnection();
                    } else {
                        throw new SQLException("Timeout waiting for a connection to be released");
                    }
                }
            } else {
                // No connections left and we don't wait for more.
                throw new SQLException("Maximum number of allowed connections reached");
            }
        }
        // If we get this far at least one connection is available.
        Connection con;

        synchronized (used) {

            con = (Connection) free.lastElement();
            // Move this connection off the free list
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
            // Move this connection from the used list to the free list
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

            // Wake up a thread waiting for a connection
            notify();
        } catch (SQLException e) {
			/*
			 * The Connection seems to be broken, but it's off the used list and
			 * the connection count has been decremented.
			 */
            // Just to be sure
            try {
                con.close();
            } catch (Exception e2) {
                // we're expecting an SQLException here
            }
            notify();
        }
    }

    /**
     * Sets the <code>block</code> property for the pool. The block values
     * specifies whether a <code>getConnection()</code> request should wait for
     * a <code>Connection</code> to be release if the maximum allowed are all in
     * use.
     * <p>
     * Setting <code>block</code> to false will have no effect on any connection
     * requests that have already begin to wait for a connection.
     *
     * @param block
     *            The block property.
     * @see #getBlock
     **/
    public void setBlock(boolean block) {
        this.block = block;
    }

    /**
     * Sets the reuseConnections property on the pool. If this property is false
     * then whenever a <code>Connection</code> is released it will be closed.
     *
     * @see #getReuseConnections
     * @see #releaseConnection
     * @see java.sql.Connection
     * @see java.sql.Connection#close
     **/
    public synchronized void setReuseConnections(boolean reuseCons) {
        this.reuseCons = reuseCons;
    }

    /**
     * Sets the <code>timeout</code> property for the pool. The timeout values
     * specifies how long to wait for a <code>Connection</code> to be release if
     * the maximum allowed are all in use when <code>getConnection()</code> is
     * called and <code>block</code> is true.
     * <p>
     * Setting <code>timeout</code> will have no effect on any
     * <code>Connection</code> requests that have already begin to wait for a
     * <code>Connection</code>.
     *
     * @return The timeout property.
     * @see #getTimeout
     **/
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    /**
     * Adds a new <code>Connection</code> to the pool.
     *
     * @exception SQLException
     *                if a connection could not be established.
     **/
    private void addConnection() throws SQLException {
        free.addElement(getNewConnection());
    }

    /**
     * Gets a new <code>Connection</code>.
     *
     * @exception SQLException
     *                if a connection could not be established.
     **/
    private Connection getNewConnection() throws SQLException {

        Connection con = null;

        //System.out.println("About to connect to " + url);
        try {
            con = DriverManager.getConnection(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ++numCons;
        return con;
    }
}
