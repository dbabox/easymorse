package bookproto

class Book {
	static mapping = {
		//id generator:'uuid.hex', params:[separator:'-']
		//relativeBooks cascade:"delete"
    }
	
	static hasMany=[relativeBooks:Book]
	
	//String id
	String name
	List<Book> relativeBooks=[]
}
