package uap.aedm.base.browser;

import org.cef.CefApp;
import org.cef.browser.CefBrowser;
import org.cef.callback.CefSchemeHandlerFactory;
import org.cef.callback.CefSchemeRegistrar;
import org.cef.handler.CefAppHandlerAdapter;
import org.cef.handler.CefResourceHandler;
import org.cef.network.CefRequest;

/**
 * ����ʱ�䣺Jun 23, 2015 1:25:10 PM
 * ��Ŀ���ƣ�org.knime.core
 * ���ߣ�chenkund
 **/
public class AeAppHandler extends CefAppHandlerAdapter{

	private String htmlCnt;
	
	public AeAppHandler() {
		super(null);
	}
	
	/* 
	 * ע��scheme����
	 * @see org.cef.handler.CefAppHandlerAdapter#onRegisterCustomSchemes(org.cef.callback.CefSchemeRegistrar)
	 */
	@Override
	public void onRegisterCustomSchemes(CefSchemeRegistrar registrar) {
		registrar.addCustomScheme(AedmSchemeHandler.SCHEME_NAME, true, false, false);
	}
	
	/* ע��SchemeHandlerFactory��ʹ�û����Ե���ע���scheme����
	 * @see org.cef.handler.CefAppHandlerAdapter#onContextInitialized()
	 */
	@Override
	public void onContextInitialized() {
		CefApp cefApp = CefApp.getInstance();
		cefApp.registerSchemeHandlerFactory(AedmSchemeHandler.SCHEME_NAME, AedmSchemeHandler.DOMAIN_NAME, new SchemeHandlerFactory());
	}
	
	/**
	 * ������Դ�������Ĺ���
	 * @author chenkund
	 * @date Jun 23, 2015 1:36:42 PM
	 */
	private class SchemeHandlerFactory implements CefSchemeHandlerFactory{

		@Override
		public CefResourceHandler create(CefBrowser browser, String schemeName,
				CefRequest request) {
			if(schemeName.equals(AedmSchemeHandler.SCHEME_NAME)){
				return new AedmSchemeHandler(getCnt());
			}
			return null;
		}
	}
	
	public synchronized void setCnt(String cnt){
		htmlCnt = cnt;
	}
	
	public synchronized String getCnt(){
		return this.htmlCnt;
	}
}
