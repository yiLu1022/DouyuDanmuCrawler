import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConnectionUtil {

    public static String Connect(String address){
        HttpURLConnection conn = null;
        URL url = null;
        InputStream in = null;
        BufferedReader reader = null;
        StringBuffer stringBuffer = null;
        try {
            url = new URL(address);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setDoInput(true);
            conn.connect();
            in = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));
            stringBuffer = new StringBuffer();
            String line = null;
            while((line = reader.readLine()) != null){
                stringBuffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            conn.disconnect();
            try {
                in.close();
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return stringBuffer.toString();
    }
    
    public static List<String> reformat(String log){
    	List<String> lines = new ArrayList<String>();
    	Pattern pattern = Pattern.compile("<+.*?>");
    	Matcher match = pattern.matcher(log);
    	while(match.find()){
    		lines.add(match.group());
    	}
    	
    	return lines;
    }
    
    public static List<String> search(String log, String content){
    	List<String> lines = new ArrayList<String>();
    	Pattern pattern = Pattern.compile(content);
    	Matcher match = pattern.matcher(log);
    	while(match.find()){
    		lines.add(match.group());
    	}
    	return lines;
    	
    }
    
    public static void main(String args[]){
    	String log = ConnectionUtil.Connect("https://tieba.baidu.com/f?kw=%E6%8A%97%E5%8E%8B&ie=utf-8");
    	FileOutputStream os;
    	OutputStreamWriter pw = null;
    	try {
    		File file = new File("cat.log");
    		file.createNewFile();
			os = new FileOutputStream(file);
			pw = new OutputStreamWriter(os);
			List<String> strs = search(log,"回复\">\\d*<");
			for(String str: strs){
				pw.write(str);
				pw.write("\r\n");
			}
/*			pw.write(log);
			pw.flush();*/
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
}