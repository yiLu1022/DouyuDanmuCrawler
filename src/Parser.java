import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Parser {

	
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
    
    public static String searchFirst(String str, String content){
    	Pattern pattern = Pattern.compile(content);
    	Matcher match = pattern.matcher(str);
    	if(match.find()){
    		return match.group(0);
    	}else{
    		return "";
    	}
    }
}
