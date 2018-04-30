package export;

import javax.script.*;

import com.eclipsesource.v8.NodeJS;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.TimerTask;

public class CronJob extends TimerTask {

	public CronJob() {
	}

	@Override
	public void run() {
		System.out.println("Hi see you after 10 seconds");

		// run script to export data from mongo to sybase

		Runtime rt = Runtime.getRuntime();
		Process pr;
		try {
			pr = rt.exec(
					"C:\\Program Files\\MongoDB\\Server\\3.6\\bin\\mongoexport --db sensores --collection info --type=csv --out " + System.getProperty("user.dir") + "\\exported.csv --fields sensor,datapassagem,horapassagem,valormedicaotemperatura,valormedicaohumidade");
			pr.waitFor();
			
		/*	rt.exec("INPUT INTO HumidadeTemperatura\r\n" + 
					"FROM " + System.getProperty("user.dir") + "\\exported.csv\r\n" + 
					"FORMAT TEXT; SELECT * FROM HumidadeTemperatura;");
			
			pr.waitFor();*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*
	 * 
11
    public static void main(String[] args) throws SQLException {
12
        // uid - user id
13
        // pwd - password
14
        // eng - Sybase database server name
15
        // database - sybase database name
16
        // host - database host machine ip
17
        String dburl = "jdbc:sqlanywhere:uid=DBA;pwd=DBA;eng=devdb;database=devdb;links=tcpip(host=172.20.20.20)";
18
         
19
        // Connect to Sybase Database
20
        Connection con = DriverManager.getConnection(dburl);
21
        Statement statement = con.createStatement();
22
 
23
        // We use Sybase specific select getdate() query to return date
24
        ResultSet rs = statement.executeQuery("SELECT GETDATE()");
25
         
26
         
27
        if (rs.next()) {
28
            Date currentDate = rs.getDate(1); // get first column returned
29
            System.out.println("Current Date from Sybase is : "+currentDate);
30
        }
31
        rs.close();
32
        statement.close();
33
        con.close();
34
    }

	 */

}
