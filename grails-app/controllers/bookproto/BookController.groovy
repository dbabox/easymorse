package bookproto

class BookController {

	def book

	//编辑，用于新建和修改
	def edit={
		book=Book.get(params.id)
	}
	
	//保存，新建和修改的保存
	def save={
		book=new Book(params)
		book.save()
		
		redirect(action:'edit',id:book.id)
	}

	//对话框获取符合条件的相关图书
	def getNotRelatedBooks={
		def bookId=params.id
		def notRelatedBooks=[]

		def hql='from Book b'
		
		if(params.id){
			def relatedIds=request.getParameterValues('related_id')
			hql+=' where b.id not in ('

			if(relatedIds && relatedIds.size()>0){
				relatedIds.each { relatedId-> hql<<="""'${relatedId}',""" }
			}

			hql<<="""'${bookId}')"""
		}
		
		notRelatedBooks=Book.findAll(hql)

		render(contentType:"text/json"){
			array{
				for(b in notRelatedBooks){
					book id:b.id, name:b.name
				}
			}
		}
	}
}
