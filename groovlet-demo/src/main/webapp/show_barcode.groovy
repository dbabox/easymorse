import com.easymorse.utils.BarcodeWriter

def barcodeWriter=new BarcodeWriter()

barcodeWriter.height=128
barcodeWriter.width=128
barcodeWriter.content=params.content

if(barcodeWriter.content){
	response.contentType='image/png'
	barcodeWriter.generate(sout)
}