package org.eclipse.swt.custom;
/**
 * Use this test class to validate an implementation of the StyledTextContent
 * interface.  To perform the validation, copy this class to the package where 
 * your StyledTextContent implementation lives.  Then specify the fully qualified
 * name of your StyledTextContent class as an argument to the main method of this 
 * class. 
 * 
 * NOTE:  This test class assumes that your StyledTextContent implementation 
 * handles the following delimiters:
 * 
 *      /r
 *      /n
 *      /r/n
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class StyledTextContentSpec implements TextChangeListener {
    static String contentClassName;
    static int failCount = 0;
    static int errorCount = 0;
    Class contentClass = null;
    StyledTextContent contentInstance = null;
    int verify = 0;
    Method currentMethod = null;
    boolean failed = false;
    StyledText widget = null;
    Shell shell = null;
    
public StyledTextContentSpec() {
}
public void assert2(String message, boolean condition) {
    System.out.print("\t" + currentMethod.getName() + " " + message);
    if (!condition) 
        fail(message);
    else 
        System.out.println(" passed");
        
}
public void fail(String message) {
    failed = true;
    System.out.println(" FAILED");
    failCount++;
}
public StyledTextContent getContentInstance() {
    contentInstance.setText("");
    widget.setContent(contentInstance);
    return contentInstance;
}
public static String getTestText() {
    return 
        "This is the first line.\r\n" +
        "This is the second line.\r\n" +
        "This is the third line.\r\n" +
        "This is the fourth line.\r\n" +
        "This is the fifth line.\r\n" +
        "\r\n" +
        "This is the first line again.\r\n" +
        "This is the second line again.\r\n" +
        "This is the third line again.\r\n" +
        "This is the fourth line again.\r\n" +
        "This is the fifth line again.\r\n" +
        "\r\n" +
        "This is the first line once again.\r\n" +
        "This is the second line once again.\r\n" +
        "This is the third line once again.\r\n" +
        "This is the fourth line once again.\r\n" +
        "This is the fifth line once again.";
}
public static void main(String[] args) {
    StyledTextContentSpec spec = new StyledTextContentSpec();
    if (args.length > 0) {
        contentClassName = args[0];
    } else {
        MessageBox box = new MessageBox(Display.getDefault().getActiveShell(), SWT.ICON_ERROR);
        box.setMessage("Content class must be specified as an execution argument."); //$NON-NLS-1$
        box.open();
        return;
    }
    spec.run();
    System.out.println();
    System.out.println(failCount + " TEST FAILURES.");
    System.out.println(errorCount + " UNEXPECTED ERRORS.");
}
public void run() {
    if (contentClassName.equals("")) {
        MessageBox box = new MessageBox(Display.getDefault().getActiveShell(), SWT.ICON_ERROR);
        box.setMessage("Content class must be specified as an execution argument."); //$NON-NLS-1$
        box.open();
        return;
    }
    if (contentClass == null) {
        try {
            contentClass = Class.forName(contentClassName);
        } catch (ClassNotFoundException e) {
            MessageBox box = new MessageBox(Display.getDefault().getActiveShell(), SWT.ICON_ERROR);
            box.setMessage("Content class:\n" + contentClassName + "\nnot found"); //$NON-NLS-1$
            box.open();
            return;
        }
    }
    try {
        contentInstance = (StyledTextContent)contentClass.newInstance();
    } catch (IllegalAccessException e) {
        MessageBox box = new MessageBox(Display.getDefault().getActiveShell(), SWT.ICON_ERROR);
        box.setMessage("Unable to access content class:\n" + contentClassName); //$NON-NLS-1$
        box.open();
        return;
    } catch (InstantiationException e) {
        MessageBox box = new MessageBox(Display.getDefault().getActiveShell(), SWT.ICON_ERROR);
        box.setMessage("Unable to instantiate content class:\n" + contentClassName); //$NON-NLS-1$
        box.open();
        return;
    }
    Class clazz;
    clazz = this.getClass();
    Method[] methods = clazz.getDeclaredMethods();
    for (int i=0; i<methods.length; i++) {
        setUp();
        currentMethod = methods[i];
        failed = false;
        try {
            if (currentMethod.getName().startsWith("test_")) {
                System.out.println();
                System.out.println(currentMethod.getName() + "...");
                currentMethod.invoke(this, new Object[0]);
                if (!failed) {
                    System.out.println("PASSED.");
                } else {
                    System.out.println("FAILED");
                }
            } 
        } catch (InvocationTargetException ex) {
            System.out.println("\t" + currentMethod.getName() + " ERROR ==> " + ex.getTargetException().toString());
            System.out.println("FAILED");
            errorCount++;
        } catch (Exception ex) {
            System.out.println("\t" + currentMethod.getName() + " ERROR ==> " + ex.toString());
            System.out.println("FAILED");
            errorCount++;
        }
        if (verify != 0) {
            verify = 0;
            contentInstance.removeTextChangeListener(this);
        }
        tearDown();
    }
}
public void textSet(TextChangedEvent event) {
}
public void textChanged(TextChangedEvent event) {
}
public void textChanging(TextChangingEvent event) {
    switch (verify) {
        case 1 : {
            assert2(":1a:", event.replaceLineCount == 0);
            assert2(":1b:", event.newLineCount == 1);
            break;
        }
        case 2 : {
            assert2(":2a:", event.replaceLineCount == 2);
            assert2(":2b:", event.newLineCount == 0);
            break;
        }
        case 3 : {
            assert2(":3a:", event.replaceLineCount == 0);
            assert2(":3b:", event.newLineCount == 2);
            break;
        }
        case 4: {
            assert2(":4:", false);
            break;
        }
        case 5 : {
            assert2(":5a:", event.replaceLineCount == 0);
            assert2(":5b:", event.newLineCount == 1);
            break;
        }
        case 6 : {
            assert2(":6a:", event.replaceLineCount == 1);
            assert2(":6b:", event.newLineCount == 0);
            break;
        }
        case 8 : {
            assert2(":8a:", event.replaceLineCount == 1);
            assert2(":8b:", event.newLineCount == 0);
            break;
        }
        case 9 : {
            assert2(":9a:", event.replaceLineCount == 1);
            assert2(":9b:", event.newLineCount == 0);
            break;
        }
        case 10:{
            assert2(":10:", false);
            break;
        }
        case 11: {
            assert2(":11:", false);
            break;
        }
        case 12: {
            assert2(":12a:", event.replaceLineCount == 0);
            assert2(":12b:", event.newLineCount == 1);
            break;
        }
        case 13: {
            assert2(":13a:", event.replaceLineCount == 0);
            assert2(":13b:", event.newLineCount == 1);
            break;
        }
        case 14: {
            assert2(":14:", false);
            break;
        }
        case 15: {
            assert2(":15a:", event.replaceLineCount == 1);
            assert2(":15b:", event.newLineCount == 2);
            break;
        }
        case 16:{
            assert2(":16:", false);
            break;
        }
        case 17: {
            assert2(":17:", false);
            break;
        }
        case 18: {
            assert2(":18a:", event.replaceLineCount == 0);
            assert2(":18b:", event.newLineCount == 2);
            break;
        }
        case 19: {
            assert2(":19a:", event.replaceLineCount == 0);
            assert2(":19b:", event.newLineCount == 3);
            break;
        }
        case 20: {
            assert2(":20:", false);
            break;
        }
    }
}
public void test_Insert() {
    StyledTextContent content = getContentInstance();
    String newText;
    
    content.setText("This\nis a test\r");
    content.replaceTextRange(0, 0, "test\n ");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":1a:", newText.equals("test\n This\nis a test\r"));
    assert2(":1b:", content.getLineCount() == 4);
    assert2(":1c:", content.getLine(0).equals("test"));
    assert2(":1d:", content.getLine(1).equals(" This"));
    assert2(":1e:", content.getLine(2).equals("is a test"));
    assert2(":1f:", content.getLine(3).equals(""));

    content.setText("This\nis a test\r");
    content.replaceTextRange(5, 0, "*** ");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":2a:", newText.equals("This\n*** is a test\r"));
    assert2(":2b:", content.getLineCount() == 3);
    assert2(":2c:", content.getLine(0).equals("This"));
    assert2(":2d:", content.getLine(1).equals("*** is a test"));
    assert2(":2e:", content.getLine(2).equals(""));

    content.setText("Line 1\r\nLine 2");
    content.replaceTextRange(0, 0, "\r");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":3a:", newText.equals("\rLine 1\r\nLine 2"));
    assert2(":3b:", content.getLineCount() == 3);
    assert2(":3c:", content.getLine(0).equals(""));
    assert2(":3d:", content.getLine(1).equals("Line 1"));
    assert2(":3e:", content.getLine(2).equals("Line 2"));
    content.replaceTextRange(9, 0, "\r");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":3f:", newText.equals("\rLine 1\r\n\rLine 2"));
    assert2(":3g:", content.getLineCount() == 4);
    assert2(":3h:", content.getLine(0).equals(""));
    assert2(":3i:", content.getLine(1).equals("Line 1"));
    assert2(":3j:", content.getLine(2).equals(""));
    assert2(":3k:", content.getLine(3).equals("Line 2"));

    content.setText("This\nis a test\r");
    content.replaceTextRange(0, 0, "\n");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":4a:", newText.equals("\nThis\nis a test\r"));
    assert2(":4b:", content.getLineCount() == 4);
    assert2(":4c:", content.getLine(0).equals(""));
    assert2(":4d:", content.getLine(1).equals("This"));
    assert2(":4e:", content.getLine(2).equals("is a test"));
    assert2(":4f:", content.getLine(3).equals(""));
    
    content.setText("This\nis a test\r");
    content.replaceTextRange(7, 0, "\r\nnewLine");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":5a:", newText.equals("This\nis\r\nnewLine a test\r"));
    assert2(":5b:", content.getLineCount() == 4);
    assert2(":5c:", content.getLine(0).equals("This"));
    assert2(":5d:", content.getLine(1).equals("is"));
    assert2(":5e:", content.getLine(2).equals("newLine a test"));
    assert2(":5f:", content.getLine(3).equals(""));

    content.setText("");
    content.replaceTextRange(0, 0, "This\nis\r\nnewLine a test\r");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":6a:", newText.equals("This\nis\r\nnewLine a test\r"));
    assert2(":6b:", content.getLineCount() == 4);
    assert2(":6c:", content.getLine(0).equals("This"));
    assert2(":6d:", content.getLine(1).equals("is"));
    assert2(":6e:", content.getLine(2).equals("newLine a test"));
    assert2(":6f:", content.getLine(3).equals(""));

    // insert at end
    content.setText("This");
    content.replaceTextRange(4, 0, "\n ");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":7a:", newText.equals("This\n "));
    assert2(":7b:", content.getLineCount() == 2);
    assert2(":7c:", content.getLine(0).equals("This"));
    assert2(":7d:", content.getLine(1).equals(" "));
    content.setText("This\n");
    content.replaceTextRange(5, 0, "\n");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":7e:", newText.equals("This\n\n"));
    assert2(":7f:", content.getLineCount() == 3);
    assert2(":7g:", content.getLine(0).equals("This"));
    assert2(":7h:", content.getLine(1).equals(""));
    assert2(":7i:", content.getLine(2).equals(""));

    // insert at beginning
    content.setText("This");
    content.replaceTextRange(0, 0, "\n");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":8a:", newText.equals("\nThis"));
    assert2(":8b:", content.getLineCount() == 2);
    assert2(":8c:", content.getLine(0).equals(""));
    assert2(":8d:", content.getLine(1).equals("This"));

    // insert text
    content.setText("This\nis a test\r");
    content.replaceTextRange(5, 0, "*** ");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":9a:", newText.equals("This\n*** is a test\r"));
    assert2(":9b:", content.getLineCount() == 3);
    assert2(":9c:", content.getLine(0).equals("This"));
    assert2(":9d:", content.getLine(1).equals("*** is a test"));
    assert2(":9e:", content.getLine(2).equals(""));
    
    content.setText("This\n");
    content.replaceTextRange(5, 0, "line");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":10a:", newText.equals("This\nline"));
    assert2(":10b:", content.getLineCount() == 2);
    assert2(":10c:", content.getLine(0).equals("This"));
    assert2(":10d:", content.getLine(1).equals("line"));
    assert2(":10e:", content.getLineAtOffset(8) == 1);
    assert2(":10f:", content.getLineAtOffset(9) == 1);

    // insert at beginning 
    content.setText("This\n");
    content.replaceTextRange(0, 0, "line\n");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":11a:", newText.equals("line\nThis\n"));
    assert2(":11b:", content.getLineCount() == 3);
    assert2(":11c:", content.getLine(0).equals("line"));
    assert2(":11d:", content.getLine(1).equals("This"));
    assert2(":11e:", content.getLineAtOffset(5) == 1);

    content.setText("Line 1\r\nLine 2\r\nLine 3");
    content.replaceTextRange(0, 0, "\r");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":12a:", newText.equals("\rLine 1\r\nLine 2\r\nLine 3"));
    assert2(":12b:", content.getLineCount() == 4);
    assert2(":12c:", content.getLine(0).equals(""));
    assert2(":12d:", content.getLine(1).equals("Line 1"));
    assert2(":12e:", content.getLine(2).equals("Line 2"));
    assert2(":12f:", content.getLine(3).equals("Line 3"));

    content.setText("Line 1\nLine 2\nLine 3");
    content.replaceTextRange(7, 0, "Line1a\nLine1b\n");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":13a:", newText.equals("Line 1\nLine1a\nLine1b\nLine 2\nLine 3"));
    assert2(":13b:", content.getLineCount() == 5);
    assert2(":13c:", content.getLine(0).equals("Line 1"));
    assert2(":13d:", content.getLine(1).equals("Line1a"));
    assert2(":13e:", content.getLine(2).equals("Line1b"));
    assert2(":13f:", content.getLine(3).equals("Line 2"));
    assert2(":13g:", content.getLine(4).equals("Line 3"));

    content.setText("Line 1\nLine 2\nLine 3");
    content.replaceTextRange(11, 0, "l1a");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":14a:", newText.equals("Line 1\nLinel1a 2\nLine 3"));
    assert2(":14b:", content.getLineCount() == 3);
    assert2(":14c:", content.getLine(0).equals("Line 1"));
    assert2(":14d:", content.getLine(1).equals("Linel1a 2"));
    assert2(":14e:", content.getLine(2).equals("Line 3"));

    content.setText("Line 1\nLine 2 is a very long line that spans many words\nLine 3");
    content.replaceTextRange(19, 0, "very, very, ");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":15a:", newText.equals("Line 1\nLine 2 is a very, very, very long line that spans many words\nLine 3"));
    assert2(":15b:", content.getLineCount() == 3);
    assert2(":15c:", content.getLine(0).equals("Line 1"));
    assert2(":15d:", content.getLine(1).equals("Line 2 is a very, very, very long line that spans many words"));
    assert2(":15e:", content.getLine(2).equals("Line 3"));
}

public void test_Empty() {
    StyledTextContent content = getContentInstance();
    assert2(":1a:", content.getLineCount() == 1);
    assert2(":1b:", content.getLine(0).equals(""));

    content.setText("test");
    content.replaceTextRange(0,4,"");
    assert2(":2a:", content.getLineCount() == 1);
    assert2(":2b:", content.getLine(0).equals(""));
}
public void test_Line_Conversion() {
    StyledTextContent content = getContentInstance();
    
    content.setText("This\nis a test\rrepeat\nend\r");
    assert2(":1a:", content.getLineCount() == 5);    
    assert2(":1b:", content.getLine(0).equals("This"));
    assert2(":1c:", content.getOffsetAtLine(0) == 0);    
    assert2(":1d:", content.getLine(1).equals("is a test"));
    assert2(":1e:", content.getLineAtOffset(4) == 0);
    assert2(":1f:", content.getOffsetAtLine(1) == 5);    
    assert2(":1g:", content.getLine(2).equals("repeat"));
    assert2(":1h:", content.getOffsetAtLine(2) == 15);   
    assert2(":1i:", content.getLine(3).equals("end"));
    assert2(":1j:", content.getOffsetAtLine(3) == 22);
    assert2(":1k:", content.getLine(4).equals(""));
    assert2(":1l:", content.getOffsetAtLine(4) == 26);
    
    content.setText("This\r\nis a test");
    assert2(":2a:", content.getLineCount() == 2);
    assert2(":2b:", content.getLine(1).equals("is a test"));
    assert2(":2c:", content.getLineAtOffset(4) == 0);
    assert2(":2d:", content.getLineAtOffset(5) == 0);

    content.setText("This\r\nis a test\r");
    assert2(":3a:", content.getLineCount() == 3);
    assert2(":3b:", content.getLine(1).equals("is a test"));
    assert2(":3c:", content.getLineAtOffset(15) == 1);
    
    content.setText("\r\n");
    assert2(":4a:", content.getLineCount() == 2);
    assert2(":4b:", content.getLine(0).equals(""));
    assert2(":4c:", content.getLine(1).equals(""));
    assert2(":4d:", content.getLineAtOffset(0) == 0);
    assert2(":4e:", content.getLineAtOffset(1) == 0);
    assert2(":4f:", content.getLineAtOffset(2) == 1);

    content.setText("\r\n\n\r\r\n");
    assert2(":5a:", content.getLineCount() == 5);
    assert2(":5b:", content.getLine(0).equals(""));
    assert2(":5c:", content.getOffsetAtLine(0) == 0);    
    assert2(":5d:", content.getLine(1).equals(""));
    assert2(":5e:", content.getOffsetAtLine(1) == 2);    
    assert2(":5f:", content.getLine(2).equals(""));
    assert2(":5g:", content.getOffsetAtLine(2) == 3);    
    assert2(":5h:", content.getLine(3).equals(""));
    assert2(":5i:", content.getOffsetAtLine(3) == 4);
    assert2(":5j:", content.getLine(4).equals(""));
    assert2(":5k:", content.getOffsetAtLine(4) == 6);
    
    content.setText("test\r\rtest2\r\r");
    assert2(":6a:", content.getLineCount() == 5);
    assert2(":6b:", content.getLine(0).equals("test"));
    assert2(":6c:", content.getOffsetAtLine(0) == 0);
    assert2(":6d:", content.getLine(1).equals(""));
    assert2(":6e:", content.getOffsetAtLine(1) == 5);    
    assert2(":6f:", content.getLine(2).equals("test2"));
    assert2(":6g:", content.getOffsetAtLine(2) == 6);    
    assert2(":6h:", content.getLine(3).equals(""));
    assert2(":6i:", content.getOffsetAtLine(3) == 12);
    assert2(":6j:", content.getLine(4).equals(""));
    assert2(":6k:", content.getOffsetAtLine(4) == 13);
}
public void test_Offset_To_Line() {
    StyledTextContent content = getContentInstance();
    
    content.setText("This\nis a test\rrepeat\nend\r");
    assert2(":1a:", content.getLineAtOffset(0) == 0);
    assert2(":1b:", content.getLineAtOffset(3) == 0);
    assert2(":1c:", content.getLineAtOffset(4) == 0);
    assert2(":1d:", content.getLineAtOffset(25) == 3);
    assert2(":1e:", content.getLineAtOffset(26) == 4);
    
    content.setText("This\r\nis a test");
    assert2(":2a:", content.getLineAtOffset(5) == 0);
    assert2(":2b:", content.getLineAtOffset(6) == 1);
    assert2(":2c:", content.getLineAtOffset(10) == 1);
    
    content.setText("\r\n");
    assert2(":3a:", content.getLineAtOffset(0) == 0);
    assert2(":3b:", content.getLineAtOffset(1) == 0);
    assert2(":3c:", content.getLineAtOffset(2) == 1);

    content.setText("\r\n\n\r\r\n");
    assert2(":4a:", content.getLineAtOffset(0) == 0);
    assert2(":4b:", content.getLineAtOffset(1) == 0);
    assert2(":4c:", content.getLineAtOffset(2) == 1);
    assert2(":4d:", content.getLineAtOffset(3) == 2);
    assert2(":4e:", content.getLineAtOffset(4) == 3);
    assert2(":4f:", content.getLineAtOffset(5) == 3);
    assert2(":4g:", content.getLineAtOffset(6) == 4);

    content.setText("\r\n\r\n");
    assert2(":5a:", content.getLineAtOffset(0) == 0);
    assert2(":5b:", content.getLineAtOffset(1) == 0);
    assert2(":5c:", content.getLineAtOffset(2) == 1);
    assert2(":5d:", content.getLineAtOffset(3) == 1);
    assert2(":5e:", content.getLineAtOffset(4) == 2);

    content.setText("\r\r\r\n\r\n");
    assert2(":6a:", content.getLineAtOffset(0) == 0);
    assert2(":6b:", content.getLineAtOffset(1) == 1);
    assert2(":6c:", content.getLineAtOffset(2) == 2);
    assert2(":6d:", content.getLineAtOffset(4) == 3);
    
    content.setText("");
    assert2(":7a:", content.getLineAtOffset(0) == 0);
    
    content = getContentInstance();
    assert2(":8a:", content.getLineAtOffset(0) == 0);    
}

public void test_Line_To_Offset() {
    StyledTextContent content = getContentInstance();
    
    content.setText("This\nis a test\rrepeat\nend\r");
    assert2(":1a:", content.getOffsetAtLine(0) == 0);
    assert2(":1b:", content.getOffsetAtLine(1) == 5);
    assert2(":1c:", content.getOffsetAtLine(2) == 15);
    assert2(":1d:", content.getOffsetAtLine(3) == 22);
    assert2(":1e:", content.getOffsetAtLine(4) == 26);
    
    content.setText("This\r\nis a test");
    assert2(":2a:", content.getOffsetAtLine(0) == 0);
    assert2(":2b:", content.getOffsetAtLine(1) == 6);
    
    content.setText("\r\n");
    assert2(":3a:", content.getOffsetAtLine(0) == 0);
    assert2(":3b:", content.getOffsetAtLine(1) == 2);

    content.setText("\r\n\n\r\r\n");
    assert2(":4a:", content.getOffsetAtLine(0) == 0);
    assert2(":4b:", content.getOffsetAtLine(1) == 2);
    assert2(":4c:", content.getOffsetAtLine(2) == 3);
    assert2(":4d:", content.getOffsetAtLine(3) == 4);
    assert2(":4e:", content.getOffsetAtLine(4) == 6);

    content.setText("\r\ntest\r\n");
    assert2(":5a:", content.getOffsetAtLine(0) == 0);
    assert2(":5b:", content.getOffsetAtLine(1) == 2);
    assert2(":5c:", content.getOffsetAtLine(2) == 8);
}

public void test_Delete() {
    StyledTextContent content = getContentInstance();
    String newText;
    
    content.setText("This\nis a test\r");
    content.replaceTextRange(6, 2, "");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":1a:", newText.equals("This\nia test\r"));
    assert2(":1b:", content.getLine(0).equals("This"));
    assert2(":1c:", content.getLine(1).equals("ia test"));
    
    content.setText("This\nis a test\r");
    content.replaceTextRange(5, 9, "");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":2a:", newText.equals("This\n\r"));
    assert2(":2b:",content.getLineCount() == 3);
    assert2(":2c:", content.getLine(0).equals("This"));
    assert2(":2d:", content.getLine(1).equals(""));
    assert2(":2e:", content.getLine(2).equals(""));
    
    content.setText("This\nis a test\nline 3\nline 4");
    content.replaceTextRange(21, 7, "");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":3a:", newText.equals("This\nis a test\nline 3"));
    assert2(":3b:", content.getLineCount() == 3);
    assert2(":3c:", content.getLine(0).equals("This"));
    assert2(":3d:", content.getLine(1).equals("is a test"));
    assert2(":3e:", content.getLine(2).equals("line 3"));
    
    content.setText("This\nis a test\nline 3\nline 4");
    content.replaceTextRange(0, 5, "");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":4a:", newText.equals("is a test\nline 3\nline 4"));
    assert2(":4b:", content.getLineCount() == 3);
    assert2(":4c:", content.getLine(0).equals("is a test"));
    assert2(":4d:", content.getLine(1).equals("line 3"));
    assert2(":4e:", content.getLine(2).equals("line 4"));
    content.replaceTextRange(16, 7, "");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":4f:", newText.equals("is a test\nline 3"));
    assert2(":4g:", content.getLine(0).equals("is a test"));
    assert2(":4h:", content.getLine(1).equals("line 3"));
    content.replaceTextRange(9, 7, "");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":4i:", newText.equals("is a test"));
    assert2(":4j:", content.getLine(0).equals("is a test"));
    content.replaceTextRange(1, 8, "");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":4k:", newText.equals("i"));
    assert2(":4l:", content.getLine(0).equals("i"));
    content.replaceTextRange(0, 1, "");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":4m:", newText.equals(""));
    assert2(":4n:", content.getLine(0).equals(""));

    content.setText("This\nis a test\r");
    content.replaceTextRange(5, 9, "");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":5a:", newText.equals("This\n\r"));
    assert2(":5b:",content.getLineCount() == 3);
    assert2(":5c:", content.getLine(0).equals("This"));
    assert2(":5d:", content.getLine(1).equals(""));
    assert2(":5e:", content.getLine(2).equals(""));

    content.setText("L1\r\nL2\r\nL3\r\nL4\r\n");
    content.replaceTextRange(4, 8, "");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":6a:", newText.equals("L1\r\nL4\r\n"));
    assert2(":6b:",content.getLineCount() == 3);
    assert2(":6c:", content.getLine(0).equals("L1"));
    assert2(":6d:", content.getLine(1).equals("L4"));
    assert2(":6e:", content.getLine(2).equals(""));

    content.setText("\nL1\r\nL2");
    content.replaceTextRange(0, 1, "");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":7a:", newText.equals("L1\r\nL2"));
    assert2(":7b:",content.getLineCount() == 2);
    assert2(":7c:", content.getLine(0).equals("L1"));
    assert2(":7d:", content.getLine(1).equals("L2"));

    content.setText("\nL1\r\nL2\r\n");
    content.replaceTextRange(7, 2, "");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":8a:", newText.equals("\nL1\r\nL2"));
    assert2(":8b:",content.getLineCount() == 3);
    assert2(":8c:", content.getLine(0).equals(""));
    assert2(":8d:", content.getLine(1).equals("L1"));
    assert2(":8e:", content.getLine(2).equals("L2"));

    content.setText("\nLine 1\nLine 2\n");
    content.replaceTextRange(0, 7, "");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":9a:", newText.equals("\nLine 2\n"));
    assert2(":9b:", content.getLineCount() == 3);
    assert2(":9c:", content.getLine(0).equals(""));
    assert2(":9d:", content.getLine(1).equals("Line 2"));
    assert2(":9e:", content.getLine(2).equals(""));

    content.setText("Line 1\nLine 2\n");
    content.replaceTextRange(6, 8, "");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":10a:", newText.equals("Line 1"));
    assert2(":10b:", content.getLineCount() == 1);
    assert2(":10c:", content.getLine(0).equals("Line 1"));

    content.setText("Line one is short\r\nLine 2 is a longer line\r\nLine 3\n");
    content.replaceTextRange(12, 17, "");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":11a:", newText.equals("Line one is a longer line\r\nLine 3\n"));
    assert2(":11b:", content.getLineCount() == 3);
    assert2(":11c:", content.getLine(0).equals("Line one is a longer line"));
    assert2(":11d:", content.getLine(1).equals("Line 3"));
    assert2(":11e:", content.getLine(2).equals(""));

}
public void test_Replace() {
    StyledTextContent content = getContentInstance();
    String newText;
    
    content.setText("This\nis a test\r");
    content.replaceTextRange(5, 4, "a");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":1a:", newText.equals("This\na test\r"));
    assert2(":1b:",content.getLineCount() == 3);
    assert2(":1c:", content.getLine(0).equals("This"));
    assert2(":1d:", content.getLine(1).equals("a test"));
    assert2(":1e:", content.getLine(2).equals(""));

    content.setText("This\nis a test\r");
    content.replaceTextRange(5, 2, "was");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":2a:", newText.equals("This\nwas a test\r"));
    assert2(":2b:",content.getLineCount() == 3);
    assert2(":2c:", content.getLine(0).equals("This"));
    assert2(":2d:", content.getLine(1).equals("was a test"));
    assert2(":2e:", content.getLine(2).equals(""));

    content.setText("This is a test\r");
    content.replaceTextRange(5, 2, "was");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":3a:", newText.equals("This was a test\r"));
    assert2(":3b:",content.getLineCount() == 2);
    assert2(":3c:", content.getLine(0).equals("This was a test"));
    assert2(":3d:", content.getLineAtOffset(15) == 0);
    
    content.setText("Line 1\nLine 2\nLine 3");
    content.replaceTextRange(0, 7, "La\nLb\n");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":4a:", newText.equals("La\nLb\nLine 2\nLine 3"));
    assert2(":4b:", content.getLine(0).equals("La"));
    assert2(":4c:", content.getLine(1).equals("Lb"));
    assert2(":4d:", content.getLine(2).equals("Line 2"));
    assert2(":4e:", content.getLine(3).equals("Line 3"));

    content.setText(getTestText());
    newText = content.getTextRange(0, content.getCharCount());
    int start = content.getOffsetAtLine(6);
    int end = content.getOffsetAtLine(11);
    content.replaceTextRange(start, end - start, "");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":5a:", content.getLineCount() == 12);
    assert2(":5a:", content.getLine(5).equals(""));
    assert2(":5a:", content.getLine(6).equals(""));
    start = content.getOffsetAtLine(7);
    content.replaceTextRange(start, content.getCharCount() - start, "");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":5a:", content.getLineCount() == 8);
    assert2(":5a:", content.getLine(5).equals(""));
    assert2(":5a:", content.getLine(6).equals(""));
    assert2(":5a:", content.getLine(7).equals(""));

}
public void test_Special_Cases() {
    String newText;
    StyledTextContent content = getContentInstance();
    assert2(":0a:", content.getLineCount() == 1);
    assert2(":0b:", content.getOffsetAtLine(0) == 0);
    
    content.setText("This is the input/output text component.");
    content.replaceTextRange(0, 0, "\n");
    assert2(":1a:", content.getLine(0).equals(""));
    content.replaceTextRange(1, 0, "\n");
    assert2(":1b:",content.getLine(0).equals(""));
    content.replaceTextRange(2, 0, "\n");
    assert2(":1c:",content.getLine(0).equals(""));
    content.replaceTextRange(3, 0, "\n");
    assert2(":1d:",content.getLine(0).equals(""));
    content.replaceTextRange(4, 0, "\n");
    assert2(":1e:",content.getLine(0).equals(""));
    content.replaceTextRange(5, 0, "\n");
    assert2(":1f:",content.getLine(0).equals(""));
    content.replaceTextRange(6, 0, "\n");
    assert2(":1g:",content.getLine(0).equals(""));
    content.replaceTextRange(7, 0, "\n");
    assert2(":1h:",content.getLine(0).equals(""));

    content.setText("This is the input/output text component.");
    content.replaceTextRange(0, 0, "\n");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":2a:", newText.equals("\nThis is the input/output text component."));
    assert2(":2b:", content.getLine(0).equals(""));
    assert2(":2c:", content.getLine(1).equals("This is the input/output text component."));
    content.replaceTextRange(1, 0, "\n");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":2d:", newText.equals("\n\nThis is the input/output text component."));
    assert2(":2e:", content.getLine(0).equals(""));
    assert2(":2f:", content.getLine(1).equals(""));
    assert2(":2g:", content.getLine(2).equals("This is the input/output text component."));

    content.replaceTextRange(2, 0, "\n");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":3a:", newText.equals("\n\n\nThis is the input/output text component."));
    assert2(":3b:", content.getLine(0).equals(""));
    assert2(":3c:", content.getLine(1).equals(""));
    assert2(":3d:", content.getLine(2).equals(""));
    assert2(":3e:", content.getLine(3).equals("This is the input/output text component."));
    content.replaceTextRange(3, 0, "\n");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":3f:", newText.equals("\n\n\n\nThis is the input/output text component."));
    assert2(":3g:", content.getLine(0).equals(""));
    assert2(":3h:", content.getLine(1).equals(""));
    assert2(":3i:", content.getLine(2).equals(""));
    assert2(":3j:", content.getLine(3).equals(""));
    assert2(":3k:", content.getLine(4).equals("This is the input/output text component."));

    content.replaceTextRange(3, 1, "");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":4a:", newText.equals("\n\n\nThis is the input/output text component."));
    assert2(":4b:", content.getLine(0).equals(""));
    assert2(":4c:", content.getLine(1).equals(""));
    assert2(":4d:", content.getLine(2).equals(""));
    assert2(":4e:", content.getLine(3).equals("This is the input/output text component."));
    content.replaceTextRange(2, 1, "");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":4f:", newText.equals("\n\nThis is the input/output text component."));
    assert2(":4g:", content.getLine(0).equals(""));
    assert2(":4h:", content.getLine(1).equals(""));
    assert2(":4i:", content.getLine(2).equals("This is the input/output text component."));

    content.replaceTextRange(2, 0, "a");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":5a:", newText.equals("\n\naThis is the input/output text component."));
    assert2(":5b:", content.getLine(0).equals(""));
    assert2(":5c:", content.getLine(1).equals(""));
    assert2(":5d:", content.getLine(2).equals("aThis is the input/output text component."));

    content.setText("abc\r\ndef");
    content.replaceTextRange(1, 1, "");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":6a:", newText.equals("ac\r\ndef"));
    assert2(":6b:", content.getLineCount() == 2);
    assert2(":6c:", content.getLine(0).equals("ac"));
    assert2(":6d:", content.getLine(1).equals("def"));
    content.replaceTextRange(1, 1, "");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":6e:", newText.equals("a\r\ndef"));
    assert2(":6f:", content.getLineCount() == 2);
    assert2(":6g:", content.getLine(0).equals("a"));
    assert2(":6h:", content.getLine(1).equals("def"));
    content.replaceTextRange(1, 2, "");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":6i:", newText.equals("adef"));
    assert2(":6j:", content.getLineCount() == 1);
    assert2(":6k:", content.getLine(0).equals("adef"));
    content.replaceTextRange(1, 1, "");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":6l:", newText.equals("aef"));
    assert2(":6m:", content.getLineCount() == 1);
    assert2(":6n:", content.getLine(0).equals("aef"));
    content.replaceTextRange(1, 1, "");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":6o:", newText.equals("af"));
    assert2(":6p:", content.getLineCount() == 1);
    assert2(":6q:", content.getLine(0).equals("af"));

    content.setText("abc");
    content.replaceTextRange(0, 1, "1");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":7a:", content.getLineCount() == 1);
    assert2(":7b:", newText.equals("1bc"));
    assert2(":7c:", content.getLine(0).equals("1bc"));
    content.replaceTextRange(0, 0, "\n");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":7d:", newText.equals("\n1bc"));
    assert2(":7e:", content.getLineCount() == 2);
    assert2(":7f:", content.getLine(0).equals(""));
    assert2(":7g:", content.getLine(1).equals("1bc"));
    
    content = getContentInstance();
    content.replaceTextRange(0,0,"a");
    
    content.setText("package test;\n/* Line 1\n * Line 2\n */\npublic class SimpleClass {\n}");
    content.replaceTextRange(14, 23, "\t/*Line 1\n\t * Line 2\n\t */");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":8a:", newText.equals("package test;\n\t/*Line 1\n\t * Line 2\n\t */\npublic class SimpleClass {\n}"));
    assert2(":8b:", content.getLineCount() == 6);
    assert2(":8c:", content.getLine(0).equals("package test;"));
    assert2(":8d:", content.getLine(1).equals("\t/*Line 1"));
    assert2(":8e:", content.getLine(2).equals("\t * Line 2"));
    assert2(":8f:", content.getLine(3).equals("\t */"));
    assert2(":8g:", content.getLine(4).equals("public class SimpleClass {"));
    assert2(":8h:", content.getLine(5).equals("}"));
}
public void test_Text_Changed_Event() {
    StyledTextContent content = getContentInstance();
    content.addTextChangeListener(this);
    verify = 1;
    content.setText("testing");
    content.replaceTextRange(0, 0, "\n");

    verify = 2;
    content.setText("\n\n");
    content.replaceTextRange(0, 2, "a");

    verify = 3;
    content.setText("a");
    content.replaceTextRange(0, 1, "\n\n");

    verify = 5;
    content.setText("Line 1\r\nLine 2");
    content.replaceTextRange(0, 0, "\r");

    verify = 6;
    content.setText("This\nis a test\nline 3\nline 4");
    content.replaceTextRange(21, 7, "");

    verify = 7;
    content.setText("This\nis a test\r");
    content.replaceTextRange(5, 9, "");

    verify = 8;
    content.setText("\nL1\r\nL2\r\n");
    content.replaceTextRange(7, 2, "");

    verify = 9;
    content.setText("L1\r\n");
    content.replaceTextRange(2, 2, "test");

    verify = 12;
    content.setText("L1\r");
    content.replaceTextRange(3, 0, "\n");

    verify = 13;
    content.setText("L1\n");
    content.replaceTextRange(2, 0, "\r");

    verify = 15;
    content.setText("L1\r\n");
    content.replaceTextRange(2, 2, "test\n\n");

    verify = 18;
    content.setText("L1\r");
    content.replaceTextRange(3, 0, "\ntest\r\n");

    verify = 19;
    content.setText("L1\n");
    content.replaceTextRange(2, 0, "test\r\r\r");

    verify = 0;
    content.removeTextChangeListener(this);
}
public void test_Delimiter_Special_Cases() {
    StyledTextContent content = getContentInstance();
    String newText;
    
    content.setText("\nL1\r\nL2\r\n");
    content.replaceTextRange(7, 2, "");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":1:", newText.equals("\nL1\r\nL2"));
    
    content.setText("L1\r\n");
    content.replaceTextRange(2, 2, "test\n\n");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":2:", newText.equals("L1test\n\n"));

    content.setText("L1\n");
    content.replaceTextRange(2, 0, "test\r\r\r");
    newText = content.getTextRange(0, content.getCharCount());
    assert2(":3:", newText.equals("L1test\r\r\r\n"));

}
protected void setUp()  {
    // create shell
    shell = new Shell ();
    GridLayout layout = new GridLayout();
    layout.numColumns = 1;
    shell.setSize(500, 300);
    shell.setLayout(layout);
    // create widget
    widget = new StyledText (shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
    GridData spec = new GridData();
    spec.horizontalAlignment = spec.FILL;
    spec.grabExcessHorizontalSpace = true;
    spec.verticalAlignment = spec.FILL;
    spec.grabExcessVerticalSpace = true;
    widget.setLayoutData(spec);
    shell.open ();
}
protected void tearDown()  {
    if (shell != null && !shell.isDisposed ()) 
        shell.dispose ();
    shell = null;

}


}
