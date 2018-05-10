package export;

import javax.script.*;

import com.eclipsesource.v8.NodeJS;
import com.mongodb.util.Hash;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import Objects.*;

public class CronJob extends TimerTask {

	private boolean loaded;
	//private HashMap<Integer,HashMap<Integer,List<Double>>> variaveisMedidas;
	private List<Cultura> listCultura;

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

/*	public void updateAlerts(ResultSet rs, Statement stmt, Integer idCultura) throws SQLException {
		loadLimits(stmt);


		while (rs.next()) {
			int value = rs.getInt(1);
			String string1 = rs.getString(2);
			String string2 = rs.getString(3);

			//Iteração sobre culturas
			Map<Integer, HashMap<Integer,List<Double>>> mapCultura = variaveisMedidas;
			Iterator<Map.Entry<Integer, HashMap<Integer,List<Double>>>> entriesCultura = mapCultura.entrySet().iterator();
			while (entriesCultura.hasNext()) {
				Map.Entry<Integer, HashMap<Integer, List<Double>>> entryCulturaMap = entriesCultura.next();

				//Iteração sobre Variaveis
				Map<Integer,List<Double>> mapLimits = entryCulturaMap.getValue();
				Iterator<Map.Entry<Integer, List<Double>>> entriesLimits = mapLimits.entrySet().iterator();
				while (entriesLimits.hasNext()) {
					Map.Entry<Integer, List<Double>> entryVariavlesMap = entriesLimits.next();
					List<Double> limitsHash = entryVariavlesMap.getValue();
					if(value > limitsHash.get(0)){
						//Gerar alerta

						//Cultura:
						entryCulturaMap.getKey();

						//Variaveis:
						entryVariavlesMap.getKey();

						//Limite
						limitsHash.get(0);

						//Criar o Alerta
						String Alert = "";

						//Registar o Alerta
						ResultSet limits = stmt.executeQuery(
								"INSERT descricao, a, b, c TABELA VALUES (alert , 'a', 'b')");


					}
				}
			}
		}
	}*/


	public void updateAlerts(ResultSet rs, Statement stmt, Integer idCultura) throws SQLException {
		while (rs.next()) {
			int value = rs.getInt(1);
			String string1 = rs.getString(2);
			String string2 = rs.getString(3);

			//Iteração sobre culturas
			for(Cultura c :listCultura){
				if(value > c.getLimiteinferiorhumidade()){
					//Gerar alerta

					//Cultura:
					c.getNomeCultura();

					//Limite
					c.getLimiteinferiorhumidade();

					//Criar o Alerta
					String Alert = "";

					//Registar o Alerta
					ResultSet limits = stmt.executeQuery(
							"INSERT descricao, a, b, c TABELA VALUES (alert , 'a', 'b')");


				}
			}
		}

	}



	private void loadLimits(Statement stmt) throws SQLException {
		this.listCultura = new ArrayList<Cultura>();
		//Confirmar campos na tabela variaveis medidas
		//verificar : -ordem -nomes -sensitiveCase
		if(!loaded){
			//variaveisMedidas= new HashMap<Integer, HashMap<Integer, List<Double>>>();
			ResultSet limits = stmt.executeQuery(
					//"SELECT limitinferior, limitesuperior, idvariavel, idcultura FROM variaveismedidas");
					"SELECT idcultura, " +
								"nomecultura, " +
								"limiteinferiortemperatura, " +
								"limitesuperiortemperatura " +
								"limiteinferiorhumidade"+
								"limitesuperiorhumidade"+
								"FROM cultura");
			while(limits.next()) {
				Integer idCultura 		= limits.getInt("idcultura");
				String nomeCultura 		= limits.getString("nomecultura");
				Double limiteinferiortemperatura 	= limits.getDouble("limiteinferiortemperatura");
				Double limitesuperiortemperatura 	= limits.getDouble("limitesuperiortemperatura");
				Double limiteinferiorhumidade 	= limits.getDouble("limiteinferiorhumidade");
				Double limitesuperiorhumidade 	= limits.getDouble("limitesuperiorhumidade");

				listCultura.add(new Cultura(idCultura,
											nomeCultura,
											limiteinferiortemperatura,
											limitesuperiortemperatura,
											limiteinferiorhumidade,
											limitesuperiorhumidade));

				//Lista de Limites
//				List<Double> listLimits= new ArrayList<Double>();
//				listLimits.add(limiteInferior);
//				listLimits.add(limiteSuperior);
//
//				//Hash de Variaveis
//				HashMap<Integer,List<Double>> hashVariavelLimites = new HashMap<Integer,List<Double>>();
//				hashVariavelLimites.put(idVariavel,listLimits);

				//Recriação da tabela
				//variaveisMedidas.put(idCultura,hashVariavelLimites);

			}
			loaded = true;
		}
	}
}
