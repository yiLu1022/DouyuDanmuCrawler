import java.util.EnumSet;


public enum FilterSet {

	ListItem("<li class=\" j_thread_list clearfix.*?\">.*?<\\/li>"),
	ArticleItem("<div class=\"t_con cleafix\">"),
	ReplyItem("<span class=\"threadlist_rep_num.*?<\\/span>"),
	TitleItem("<a href=\"\\/p\\/\\d+\" title.*?<\\/a>"),
	AuthorItem("title=\"主题作者: .*?\""),
	TimeItem("<span class=\"threadlist_reply_date.*?<\\/span>"),
	
	ReplyBody(">\\d+<"),
	TitleBody(">.*?<"),
	TimeBody(">.*?<"),
	AuthorBody("主题作者: .*?\"");
	
	
	;
	private String value;
	private FilterSet(String value){
		this.value = value;
	}
	
	public String toString(){
		return value;
	}
}
