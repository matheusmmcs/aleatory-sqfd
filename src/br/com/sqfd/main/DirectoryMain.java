package br.com.sqfd.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;
import br.com.sqfd.enums.EnumJhmType;
import br.com.sqfd.utils.FileAnalyzer;
import br.com.sqfd.utils.MyDirectoryWalker;

public class DirectoryMain {

	private static final String MYPATH = "C:/Eclipse/junojee/infoway/desenvolvimento_MAA/src/";
	private static final String MYEXTENSION = ".jhm.xml";
	private static final int MAXDEPTH = 100;
	private static final String FILEADDRESS = "C:/RequisitosMAA.csv";
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		File dir = new File(MYPATH);
		MyDirectoryWalker walker = new MyDirectoryWalker(MYEXTENSION, MAXDEPTH);
		List<File> files = new ArrayList<File>();
		
		//collect all files with specified extension inside dir
		if(dir.isDirectory()){
			files = walker.getExtensionFiles(dir);
		}else{
			System.out.println("Isn't a folder!");
		}
		
		
		List<String[]> data = new ArrayList<String[]>();
		CSVWriter writer = new CSVWriter(new FileWriter(FILEADDRESS), ';');
		
		/* Header */
		data.add(new String[] {"Requeriment", "Description", "Type"});
		
		//collect information from files
		String description = "", content = "", type = "";
		for (int i = 0; i < files.size(); i++) {
			File file = files.get(i);
			
			content = FileAnalyzer.getFileContent(file);
			
			//get description
			description = FileAnalyzer.getPatternFirst(content, "(<description.*?>)(.+?)(</description>)", 2);
			if(description.equals("")){
				description = FileAnalyzer.getPatternFirst(content, "display-name=\"(.+?)\"", 1);
			}
			
			//get jhm type
			if(content.contains("<class-mapping")){
				type = EnumJhmType.CLASS_MAPPING.getDescription();
			}else if(content.contains("<flow")){
				type = EnumJhmType.FLOW.getDescription();
			}else if(content.contains("<report")){
				type = EnumJhmType.REPORT.getDescription();
			}

			/* Body */
			data.add(new String[] {file.getName() , description, type});
		}
		
		writer.writeAll(data);
		writer.close();
	}

}
