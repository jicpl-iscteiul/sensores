package export;

import javax.script.*;

import java.io.FileNotFoundException;
import java.util.TimerTask;

public class CronJob extends TimerTask {

	public CronJob() {
	}

	@Override
	public void run() {
		System.out.println("Hi see you after 10 seconds");

		// run script to export data from mongo to sybase

		// create a script engine manager
		ScriptEngineManager factory = new ScriptEngineManager();

		// create JavaScript engine
		ScriptEngine engine = factory.getEngineByName("JavaScript");
		 
		String path = "export_db.js";
				
		// evaluate JavaScript code from given file
		try {
			engine.eval(new java.io.FileReader(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
