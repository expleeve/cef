package uap.aedm.base.browser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.cef.callback.CefCallback;
import org.cef.handler.CefResourceHandlerAdapter;
import org.cef.misc.IntRef;
import org.cef.misc.StringRef;
import org.cef.network.CefRequest;
import org.cef.network.CefResponse;

/**
 * 创建时间：Jun 23, 2015 1:38:00 PM
 * 项目名称：org.knime.core
 * 作者：chenkund
 **/
public class AedmSchemeHandler extends CefResourceHandlerAdapter{

	//注册aedm为自定义的scheme名称，使用aedm://访问scheme
	public static final String SCHEME_NAME = "aedm";
	//注册resources为自定义的domain名称，使用aedm://resources/访问资源
	public static final String DOMAIN_NAME = "resources";
	//伪装的index.html
	public static final String INDEX_HTML = "index.html";
	public static final String SCHEME_DOMAIN = SCHEME_NAME + "://" + DOMAIN_NAME + "/";
	public static final String SCHEME_URL = SCHEME_DOMAIN + INDEX_HTML;
	
	private String htmlContent = "";
	private byte[] htmlbytes;
	private String mime_type;
	private int offset = 0;
	
	public AedmSchemeHandler(String htmlCnt){
		super();
		htmlContent = htmlCnt;
	}
	
	@Override
	public synchronized boolean processRequest(CefRequest request,
	                                CefCallback callback) {
		boolean handled = false;
		String url = request.getURL();
		if(url.equalsIgnoreCase(SCHEME_URL) && htmlContent != null && !htmlContent.isEmpty()){
			htmlbytes = htmlContent.getBytes();
			handled = true;
			mime_type = "text/html";
		}else{
			handled = loadContent(url.substring(SCHEME_DOMAIN.length()));
			mime_type = "*/*";
		}
		
		if(handled){
			callback.Continue();
		}
		return handled;
	}
	
	/**
	 * 加载html页面中的元素
	 * @param resName
	 * @return
	 */
	private boolean loadContent(String resName){
		InputStream in = getClass().getResourceAsStream(resName); //TODO 这个路径需要确定
		if(in != null){
			try{
				ByteArrayOutputStream ops = new ByteArrayOutputStream();
				int readByte = -1;
				while((readByte = in.read()) >= 0){
					ops.write(readByte);
				}
				htmlbytes = ops.toByteArray();
				return true;
			}catch(IOException e){
				//TODO
				e.printStackTrace();
			}finally{
				try {
					if(in != null){
						in.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean readResponse(byte[] data_out,
                              int bytes_to_read,
                              IntRef bytes_read,
                              CefCallback callback) {
		boolean has_data = false;
		if(offset < htmlbytes.length){
			int transfter_size = Math.min(bytes_to_read, (htmlbytes.length - offset));
			System.arraycopy(htmlbytes, offset, data_out, 0, transfter_size);
			offset += transfter_size;
			bytes_read.set(transfter_size);
			
			has_data = true;
		}else{
			offset = 0;
			bytes_read.set(0);
		}
		return has_data;
	}
	
	@Override
	public void getResponseHeaders(CefResponse response,
            IntRef response_length,
            StringRef redirectUrl) {
		response.setMimeType(mime_type);
		response.setStatus(200);
		response_length.set(htmlbytes.length);
	}
	
}
