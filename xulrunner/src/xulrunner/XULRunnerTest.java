package xulrunner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * create time: 2015年6月25日 上午10:25:12
 * author: chenkund
 *
 **/

public class XULRunnerTest {

	/**
	* @param args
	*/
	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		FillLayout layout = new FillLayout();
		shell.setLayout(layout);

		Browser browser = new Browser(shell, SWT.MOZILLA); // 1
//		browser.addTitleListener(new TitleListener() { // 2
//			public void changed(TitleEvent event) {
//				shell.setText(event.title);
//			}
//		});
//		browser.setUrl("file:///C:/Users/Administrator/Desktop/test/index_400k.html"); // 3
//		browser.setText("<html><body>Hello World</body></html>");
//		browser.setUrl("http://syntagmatic.github.io/parallel-coordinates/");
		browser.setUrl("http://echarts.baidu.com/doc/example/radar1.html");
//		browser.setUrl("file:///E:/test/index.html");
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

}
