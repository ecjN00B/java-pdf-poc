import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

public class Main {
	
	private static final int HEIGHT = 300;
	private static final int WIDTH = 200;

	private static final int DPI = 35;

	public static void main(String[] args) {
		resizePdf("src/resources/teste.pdf", "src/resources/teste-compressed-resized.pdf");
	}

	public static void resizePdf(String fileSrc, String destinationSrc) {
	  try {
		var pdDocument = new PDDocument();
		var oDocument = Loader.loadPDF(new File(fileSrc));
	    var pdfRenderer = new PDFRenderer(oDocument);
		int numberOfPages = oDocument.getNumberOfPages();
		PDPage page = null;

		for (var i = 0; i < numberOfPages; i++) {
		  page = new PDPage(PDRectangle.LETTER);
		  var bim = pdfRenderer.renderImageWithDPI(i, DPI, ImageType.RGB);
		  var pdImage = JPEGFactory.createFromImage(pdDocument, bim);
		  var contentStream = new PDPageContentStream(pdDocument, page);
		  float newHeight = HEIGHT;
		  float newWidth = WIDTH;
		  contentStream.drawImage(pdImage, 0, 0, newWidth, newHeight);
		  contentStream.close();
		  var rectangle = new PDRectangle(WIDTH, HEIGHT);
		  page.setCropBox(rectangle);
		  pdDocument.addPage(page);
		}

		  pdDocument.save(destinationSrc);
		  pdDocument.close();

		} catch (IOException e) {
		  e.printStackTrace();
		}
	}

}
