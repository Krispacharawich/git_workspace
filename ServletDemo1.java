package net.codejava;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.HTMLServerImageHandler;
import org.eclipse.birt.report.engine.api.IImage;
import org.eclipse.birt.report.engine.api.IPDFRenderOption;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.eclipse.birt.report.engine.api.RenderOption;
import org.eclipse.core.internal.registry.RegistryProviderFactory;

/**
 * Servlet implementation class ServletDemo1
 */
@WebServlet("/ServletDemo1")
public class ServletDemo1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IReportEngine engine = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletDemo1() {
        super();
        // TODO Auto-generated constructor stub
    }
    private void generatePDF(String filePath, OutputStream outputStream, int orderNumber, String format) {
		try {
			IReportRunnable design = engine.openReportDesign(filePath);
		      IRunAndRenderTask task = engine.createRunAndRenderTask(design);
		      task.setParameterValue("OrderNumber", (new Integer(orderNumber)));

		      task.getAppContext().put(EngineConstants.APPCONTEXT_CLASSLOADER_KEY,
		    		  ServletDemo1.class.getClassLoader());
			
		      IRenderOption options = new RenderOption();
		      options.setOutputStream(outputStream);
		      
		      options.setOutputFormat(format);
		      
		      if (options.getOutputFormat().equalsIgnoreCase("html")) {
		    	  HTMLRenderOption htmlOptions = new HTMLRenderOption(options);
				  htmlOptions.setSupportedImageFormats("PNG");
				  htmlOptions.setEmbeddable(true);
				  htmlOptions.setImageHandler(new HTMLServerImageHandler() {
					@Override
					protected String handleImage(IImage image, Object context, String prefix, boolean needMap) {
						String embeddedImage = Base64.encodeBase64String(image.getImageData());					
						return "data:image/png;base64," + embeddedImage;
					}
				  });		    	  
				  task.setRenderOption(htmlOptions);
		      } else if (options.getOutputFormat().equalsIgnoreCase("pdf")) {
			      PDFRenderOption pdfOptions = new PDFRenderOption( options );
			      pdfOptions.setOption(IPDFRenderOption.PAGE_OVERFLOW, IPDFRenderOption.FIT_TO_PAGE_SIZE);
			      pdfOptions.setOption(IPDFRenderOption.PAGE_OVERFLOW, IPDFRenderOption.OUTPUT_TO_MULTIPLE_PAGES);
			      task.setRenderOption(pdfOptions);
		      }
		      		      
		      task.run();
		      
		} catch (Exception e) {
			e.printStackTrace();
		}    	
    }
    public void init(ServletConfig config) throws ServletException {
		try {			
		final EngineConfig engineConfig = new EngineConfig();
		engineConfig.setLogConfig("/tmp/", Level.OFF);
	      Platform.startup(engineConfig);
	      IReportEngineFactory factory = (IReportEngineFactory) Platform
	          .createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
	      engine = factory.createReportEngine(engineConfig);
	      System.out.println("engine init");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
		      generatePDF("C:/Users/Aspire V15/workspace/birt/WebContent/tmp/SalesInvoice.rptdesign", baos, 10101, "pdf");
		      generatePDF("C:/Users/Aspire V15/workspace/birt/WebContent/tmp/SalesInvoice.rptdesign", baos, 10103, "pdf");
		      baos.close();		      
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {			
			generatePDF("C:/Users/Aspire V15/workspace/birt/WebContent/tmp/SalesInvoice.rptdesign", response.getOutputStream(), 10102, "pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	public void destroy() {
		try {
		      if (engine != null) {
		    	  System.out.println("engine destroy");
		        engine.destroy();
		      }
		      Platform.shutdown();
		      RegistryProviderFactory.releaseDefault();
		    } catch (Exception e) {
		      e.printStackTrace();
		    }		
	}	

}
