import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.EnumSet;


public class FileWriter {
	public final static String WHOLE_WEB_SITE = "whole.cat";
	public final static String FILE_NAME = "log.cat";
	private File file;
	OutputStream os;
	OutputStreamWriter pw;
	
	FileWriter() throws IOException{
		file = new File(FILE_NAME);
		file.createNewFile();
		os = new FileOutputStream(file);
		pw = new OutputStreamWriter(os);
	}
	
	public void writeAll(String str) throws IOException{
		File wholeHTML = new File(WHOLE_WEB_SITE);
		wholeHTML.createNewFile();
		OutputStream wos = new FileOutputStream(wholeHTML);
		OutputStreamWriter wpw = new OutputStreamWriter(wos);
		wpw.write(str);
		wpw.flush();
		wpw.close();
		wos.close();
		
	}
	
	public void writeLine(String line) throws IOException{	
		pw.write(line);
		pw.write("\r\n");
	}
	
	public void close() throws IOException{
		pw.flush();
		pw.close();
		os.close();
	}
	
	public void createTableHeader(EnumSet<ColumnSet> set) throws IOException{
		StringBuilder sb = new StringBuilder();
		for(ColumnSet arg : set){
			sb.append(arg.toString());
			sb.append("\t");
		}
		sb.append("\r\n");
		writeLine( sb.toString());
	}
	
	
}
