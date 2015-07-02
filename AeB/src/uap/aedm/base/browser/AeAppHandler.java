package uap.aedm.base.browser;

import org.cef.CefApp;
import org.cef.browser.CefBrowser;
import org.cef.callback.CefSchemeHandlerFactory;
import org.cef.callback.CefSchemeRegistrar;
import org.cef.handler.CefAppHandlerAdapter;
import org.cef.handler.CefResourceHandler;
import org.cef.network.CefRequest;

/**
 * 创建时间：Jun 23, 2015 1:25:10 PM
 * 项目名称：org.knime.core
 * 作者：chenkund
 **/
public class AeAppHandler extends CefAppHandlerAdapter{

	private String htmlCnt;
	
	public AeAppHandler() {
		super(null);
	}
	
	/* 
	 * 注册scheme名称
	 * @see org.cef.handler.CefAppHandlerAdapter#onRegisterCustomSchemes(org.cef.callback.CefSchemeRegistrar)
	 */
	@Override
	public void onRegisterCustomSchemes(CefSchemeRegistrar registrar) {
		registrar.addCustomScheme(AedmSchemeHandler.SCHEME_NAME, true, false, false);
	}
	
	/* 注册SchemeHandlerFactory，使用户可以调用注册的scheme名称
	 * @see org.cef.handler.CefAppHandlerAdapter#onContextInitialized()
	 */
	@Override
	public void onContextInitialized() {
		CefApp cefApp = CefApp.getInstance();
		cefApp.registerSchemeHandlerFactory(AedmSchemeHandler.SCHEME_NAME, AedmSchemeHandler.DOMAIN_NAME, new SchemeHandlerFactory());
	}
	
	/**
	 * 创建资源处理器的工厂
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
