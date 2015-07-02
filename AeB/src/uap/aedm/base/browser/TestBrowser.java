package uap.aedm.base.browser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * 创建时间：Jun 23, 2015 2:41:36 PM
 * 项目名称：org.knime.core
 * 作者：chenkund
 **/
public class TestBrowser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final AeBrowser browser = AeBrowser.getInstance();
		
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setBounds(0,0,800,600);
		shell.setLayout(new FormLayout());
		FormData fd = new FormData();
		fd.top = new FormAttachment(0, 17);
		fd.left = new FormAttachment(0, 20);
		Button button = new Button(shell, SWT.NONE);
		button.setLayoutData(fd);
		button.setText("Click me");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent selectionevent){
				browser.setText("<html><body>123</body></html>");
			}
		});
		
		FormData fd1 = new FormData();
		fd1.top = new FormAttachment(0, 57);
		fd1.left = new FormAttachment(0, 20);
		Button button1 = new Button(shell, SWT.NONE);
		button1.setLayoutData(fd1);
		button1.setText("open");
		button1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent selectionevent){
				browser.setVisible(true);
			}
		});
		
		shell.open();
		while(!shell.isDisposed()){
			if(!display.readAndDispatch()){
				display.sleep();
			}
		}
		display.close();
	}

}
