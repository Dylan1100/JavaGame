import java.sql.*;

public class score {
    private int score;
    private String name;

    public score(){
        score = 0;
        score = getScore();

        name = "No Name";
        name = getName();
    }

    public void setScore (int temp) {score = temp;}
    public int getScore () {return score; }

    public void setName(String temp) {name = temp;}
    public String getName () { return name; }

    public void recordScore() {
        score = getScore();
        name = getName();

        Connection conn = null;
        Statement stmt = null;

        try {
            //load the DB driver
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:recordedScores.db";
            conn = DriverManager.getConnection(dbURL);
            if (conn != null) {
                System.out.println("Connection established");

                conn.setAutoCommit(false);
                DatabaseMetaData dm = (DatabaseMetaData)conn.getMetaData();
                System.out.println("Driver Name:" + dm.getDriverName());
                System.out.println("Driver version:" + dm.getDriverVersion());
                System.out.println("Product Name:" + dm.getDatabaseProductName());
                System.out.println("Product verison:" + dm.getDatabaseProductVersion());

                stmt = conn.createStatement();
                String sql = "";
                ResultSet rs = null;

                //CREATE TABLE IF NOT EXIST
                sql = "CREATE TABLE IF NOT EXISTS scores ("+
                        "id INTEGER PRIMARY KEY, " +
                        "name TEXT NOT NULL, " +
                        "score INT NOT NULL" + ")";
                stmt.executeUpdate(sql);
                conn.commit();

                //Insert data
                sql = "INSERT INTO scores (name, score) VALUES ('" + name + "', " + score +  ")";
                stmt.executeUpdate(sql);
                conn.commit();


                //Retrieve
                sql = "SELECT * FROM scores";
                rs = stmt.executeQuery(sql);
                System.out.println("The current data is => ");
                DisplayRecords(rs);
                rs.close();

                conn.close();
            } else {
                System.out.println("Cannot establish connection");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //cleanup
        }
    }

    public static void DisplayRecords(ResultSet rs) throws SQLException {
        while ( rs.next() ) {
            int id = rs.getInt("id");
            int score = rs.getInt("score");

            /*
            System.out.println("id: " + id);
            System.out.println("score: " + score);
            */
        }
    }
}
