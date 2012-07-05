package com.result.receiver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.widget.TextView;

public class ResultReceiverDemoActivity extends Activity{

//	http://stackoverflow.com/questions/3197335/restful-api-service/3197456#3197456
	Intent intent;
	TextView txtview;
	MyResultReceiver resultReceiver;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		resultReceiver = new MyResultReceiver(null);

		txtview = (TextView) findViewById(R.id.txtview);

		intent = new Intent(this, MyService.class);
		intent.putExtra("receiver", resultReceiver);
		startService(intent);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopService(intent);
	}

	class UpdateUI implements Runnable
	{
		String updateString;
		
		public UpdateUI(String updateString) {
			this.updateString = updateString;
		}
		public void run() {
			txtview.setText(updateString);
		}
	}
	
	class MyResultReceiver extends ResultReceiver
	{
		public MyResultReceiver(Handler handler) {
			super(handler);
		}

		@Override
		protected void onReceiveResult(int resultCode, Bundle resultData) {

			if(resultCode == 100){
				runOnUiThread(new UpdateUI(resultData.getString("start")));
			}
			else if(resultCode == 200){
				runOnUiThread(new UpdateUI(resultData.getString("end")));
			}
			else{
				runOnUiThread(new UpdateUI("Result Received "+resultCode));
			}
		}
	}
}