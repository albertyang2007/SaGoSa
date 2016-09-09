package alben.sgs.android.io;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionTraceHelper {
	public static String getTrace(Throwable t) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		t.printStackTrace(writer);
		StringBuffer buffer = stringWriter.getBuffer();
		return buffer.toString();
	}
}
