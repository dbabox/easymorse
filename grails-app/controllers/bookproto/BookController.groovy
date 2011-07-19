package bookproto

import groovy.xml.MarkupBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipEntry;

class BookController {

	ExecutorService executorService=Executors.newFixedThreadPool(2)

	def book

	def getResults(def bookId){
		def writer=new StringWriter()
		def xmlResults=new MarkupBuilder(writer)

		xmlResults.plist(version:1.0){
			dict{
				key 'key'
				string '12'
			}
		}

		def content="""<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
${writer}
		"""

		return content
	}

	def upload={
	}

	def uploadFile={
		def id=params.id
		def file=request.getFile("myFile")


		if(!file.empty){
			def path=request.session.servletContext.getRealPath("/WEB-INF")

			def sourceFile=new File("""${path}/${id}.zip""")
			def distFile=new File("""${path}/${id}-dist.zip""")

			file.transferTo(sourceFile)

			executorService.submit(new Runnable(){
						public void run() {
							ZipFile zipFile=new ZipFile(sourceFile)
							ZipOutputStream outputStream=new ZipOutputStream(new FileOutputStream(distFile))
							Enumeration entries=zipFile.entries()
							while(entries.hasMoreElements()){
								ZipEntry entry=entries.nextElement()
								entry=new ZipEntry(entry.name)
								println entry
								outputStream.putNextEntry(entry)

								if(!entry.isDirectory()){
									InputStream inputStream=zipFile.getInputStream(entry)
									byte[] buffer=new byte[1024*1024]
									for(int i=inputStream.read(buffer);i!=-1;i=inputStream.read(buffer)){
										outputStream.write(buffer,0,i)
									}
									inputStream.close()
								}

								outputStream.closeEntry()
							}
							zipFile.close()

							ZipEntry attachEntry=new ZipEntry("book.plist")
							outputStream.putNextEntry(attachEntry)

							InputStream inputStream=new FileInputStream(attachedFile)

							outputStream.write(getResults(id).getBytes())

							inputStream.close()

							outputStream.closeEntry()

							outputStream.close()
							println ">>>> zip create ok."
						}
					})
		}
		flash.message="""上传完毕."""
		redirect(action:'upload')
	}

	def test={
		def b1=new Book(name:"b1")
		def b2=new Book(name:"b2")
		def b3=new Book(name:"b3")
		b1.relativeBooks.add(b2)
		b1.save()
		b3.relativeBooks.add(b2)
		b3.save(flush:true)


		println """b2.id: ${b2.id}"""

		def hql="""select b from Book b left join b.relativeBooks r where r.id='${b2.id}'"""
		println hql
		def results=Book.executeQuery(hql)

		println """results: ${results}"""
	}

	def image={
		def imageFile=new File(request.session.getServletContext().getRealPath("/WEB-INF/images/0028.jpg"))
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
