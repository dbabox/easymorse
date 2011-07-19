import bookproto.Book

class BootStrap {

    def init = { servletContext ->
		environments{
			development{
				/*
				def testBook=new Book(name:"中国史学史")
				
				testBook.relativeBooks.add(new Book(name:"中国通史"))
				testBook.relativeBooks.add(new Book(name:"中国农业史"))
				testBook.save()
				
				println """test book: ${testBook}"""
				println """test book relative books:${testBook.relativeBooks}"""
				
				new Book(name:"中国手工业史").save()
				new Book(name:"中国战争史").save()
				new Book(name:"中国科学技术史").save()
				new Book(name:"中国对外交流史").save()
				*/
			}
		}
    }
    def destroy = {
    }
}
