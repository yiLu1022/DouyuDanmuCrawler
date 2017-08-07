import java.util.EnumSet;


public enum ColumnSet {
	Number("No."),
	Author("Author"),
	Title("Title"),
	TimeStamp("Time"),
	Comment("Comment"),
	Link("Link");
	
	private String value;
	private ColumnSet(String value){
		this.value = value;
	}
	
	public String toString(){
		return value;
	}
	
	public static EnumSet<ColumnSet> getAll(){
		return EnumSet.of(Number,Author,Title,TimeStamp,Comment,Link);
	}
}
