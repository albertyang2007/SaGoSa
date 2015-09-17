package alben.sgs.android.thread;

import android.os.AsyncTask;

public class LogTask extends AsyncTask<String, Integer, String> {

	public int delayMillionSeconds = 1000;

	public LogTask(int d) {
		this.delayMillionSeconds = d;
	}

	protected void onCancelled() {
		super.onCancelled();
	}

	protected void onPostExecute(String result) {
		throw new RuntimeException();
		// super.onPostExecute(result);
	}

	protected void onPreExecute() {
		// super.onPreExecute();
	}

	protected void onProgressUpdate(Integer... values) {
		// super.onProgressUpdate(values);
	}

	protected String doInBackground(String... params) {
		try {
			Thread.sleep(delayMillionSeconds);
		} catch (Exception e) {
		}
		return null;
	}
}
