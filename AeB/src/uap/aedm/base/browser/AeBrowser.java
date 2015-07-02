package uap.aedm.base.browser;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.handler.CefLoadHandlerAdapter;

/**
 * 创建时间：Jun 23, 2015 1:14:35 PM
 * 项目名称：org.knime.core
 * 作者：chenkund
 **/
public class AeBrowser extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2662964071263830659L;
	private static AeBrowser instance;
	private static final int BROWSER_WIDTH = 800;
	private static final int BROWSER_HEIGHT = 600;
	private static final boolean enableOSR = false;
	private final CefClient cefClient;
	private final CefBrowser cefBrowser;
	private boolean hasError = false;
	private static final String ABOUT_BLANK = "about:blank";
	private static final String ERROR_MSG = "<html><body>显示失败</body></html>";
	private final AeAppHandler appHandler;
	
	private AeBrowser(){
		CefSettings settings = new CefSettings();
		settings.windowless_rendering_enabled = enableOSR;
		CefApp app = CefApp.getInstance(null, settings);
		appHandler = new AeAppHandler();
		CefApp.addAppHandler(appHandler);
		cefClient = app.createClient();
		cefClient.addLoadHandler(new CefLoadHandlerAdapter() {
			
			@Override
			public void onLoadingStateChange(CefBrowser browser,
                    boolean isLoading,
                    boolean canGoBack,
                    boolean canGoForward) {
				if(!isLoading && hasError){
					browser.loadString(ERROR_MSG, ABOUT_BLANK);
					hasError = false;
				}
			}
			
			@Override
			public void onLoadError(CefBrowser browser,
					int frameIdentifer,
					ErrorCode errorCode,
					String errorText,
					String failedUrl) {
				if(errorCode != ErrorCode.ERR_NONE && errorCode != ErrorCode.ERR_ABORTED){
					hasError = true;
					browser.stopLoad();
				}
			}
		});
//		appHandler.setCnt("<html><body>Hello AE Browser</body></html>");
		cefBrowser = cefClient.createBrowser(AedmSchemeHandler.SCHEME_URL, enableOSR, false);
		
		getContentPane().add(cefBrowser.getUIComponent(), BorderLayout.CENTER);
		setSize(BROWSER_WIDTH, BROWSER_HEIGHT);
		setVisible(true);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e){
				AeBrowser.this.setVisible(false);
			}
		});
	}
	
	/**
	 * 设置html内容
	 * @param htmlContent
	 */
	public void setText(String htmlContent){
		appHandler.setCnt(htmlContent);
		cefBrowser.loadURL(AedmSchemeHandler.SCHEME_URL);
		setVisible(true);
	}
	
	public static synchronized AeBrowser getInstance(){
		if(instance == null){
			instance = new AeBrowser();
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable(){

				@Override
				public void run() {
					CefApp.getInstance().dispose();
					instance.dispose();
				}
				
			}));
		}
		return instance;
	}
}
