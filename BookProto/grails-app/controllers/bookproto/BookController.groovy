package bookproto

class BookController {

	def book
	
	def image={
		def imageFile=new File(request.session.getServletContext().getRealPath("/WEB-INF/images/grails_logo.png"))
		def range=request.getHeader('Range')
		int rangeValue=0
		
		if(range){
			rangeValue=Integer.parseInt(range.substring (6, range.length()-1))
			response.setStatus(206)
		}
		
		def imageData=null
		
		if(range){
			def inputStream=new FileInputStream(imageFile)
			imageData=new byte[imageFile.length()-rangeValue]
			inputStream.skip(rangeValue)
			inputStream.read(imageData, 0, imageData.length)
		}else{
			imageData=imageFile.readBytes()
		}
		
		//response.setHeader("Content-disposition", "attachment; filename=grails.png")
		response.contentType ='image/jpeg'
		response.addHeader "Content-Length", ""+(imageData.length)
		response.outputStream<<imageData
		response.outputStream.flush()
	}

	//编辑，用于新建和修改
	def edit={
		book=Book.get(params.id)
	}
	
	//保存，新建和修改的保存
	def save={
		book=new Book(params)
		book.save()
		
		flash.message="""图书（${book.name}）已保存。"""
		redirect(action:'edit',id:book.id)
	}

	//对话框获取符合条件的相关图书
	def getNotRelatedBooks={
		def bookId=params.id
		def relatedIds=request.getParameterValues('related_id')
		def notRelatedBooks=[]

		def hql='from Book b '
		
		/*
		if(params.id){
			def relatedIds=request.getParameterValues('related_id')
			hql+=' where b.id not in ('

			if(relatedIds && relatedIds.size()>0){
				relatedIds.each { relatedId-> hql<<="""'${relatedId}',""" }
			}

			hql<<="""'${bookId}')"""
		}
		*/
		
		if(bookId || relatedIds){
			def idList=[]
			
			if(bookId){
				idList.add(bookId)
			}
			
			if(relatedIds){
				idList.addAll(relatedIds)
			}
			
			def notInIdsString=""
			for(int i=0;i<idList.size();i++){
				notInIdsString+="""'${idList[i]}'"""
				if(i<idList.size()-1){
					notInIdsString+=","
				}
			}
			
			hql+=""" where b.id not in ( ${notInIdsString} )"""
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
