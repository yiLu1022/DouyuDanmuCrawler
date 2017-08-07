import java.io.IOException;
import java.util.List;


public class MainActivity {
	private static final String url = "https://tieba.baidu.com/f?kw=%E6%8A%97%E5%8E%8B&ie=utf-8";

    public static void main(String args[]){
    	String log = Connecter.Connect(url);
    	FileWriter fw;
    	try {
    		fw = new FileWriter();
    		fw.createTableHeader(ColumnSet.getAll());
    		fw.writeAll(log);
			List<String> strs = Parser.search(log,FilterSet.ListItem.toString());
			for(final String str: strs){
				//fw.writeLine(str);
				fw.writeLine(Parser.searchFirst(Parser.searchFirst(str, FilterSet.AuthorItem.toString()),FilterSet.AuthorBody.toString()));
				fw.writeLine(Parser.searchFirst(Parser.searchFirst(str, FilterSet.ReplyItem.toString()),FilterSet.ReplyBody.toString()));
				fw.writeLine(Parser.searchFirst(Parser.searchFirst(str, FilterSet.TimeItem.toString()),FilterSet.TimeBody.toString()));
				fw.writeLine(Parser.searchFirst(Parser.searchFirst(str, FilterSet.TitleItem.toString()),FilterSet.TitleBody.toString()));
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
    }
}
