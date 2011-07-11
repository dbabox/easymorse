package bookproto

class Book {
	static mapping = {
		//id generator:'uuid.hex', params:[separator:'-']
    }
	
	static hasMany=[relativeBooks:Book]
	
	//String id
	String name
	List<Book> relativeBooks=[]
}
