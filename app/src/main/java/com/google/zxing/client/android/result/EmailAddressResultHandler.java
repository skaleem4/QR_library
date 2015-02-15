/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.zxing.client.android.result;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.R;
import com.google.zxing.client.result.EmailAddressParsedResult;
import com.google.zxing.client.result.ParsedResult;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;

/**
 * Handles email addresses.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
@SuppressWarnings("unused")
@SuppressLint("DefaultLocale")
public final class EmailAddressResultHandler extends ResultHandler {
	
	Bitmap thumbnail;
	File pic;
	public String directoryPath;
	public String filePath;
	public static String pre_email_address;
	public static int count = 0;
	
  private static final int[] buttons = {
      R.string.button_email,
      R.string.button_add_contact
  };
private static final int CAMERA_PIC_REQUEST = 0;

  public EmailAddressResultHandler(Activity activity, ParsedResult result) {
    super(activity, result);
  }

  @Override
  public int getButtonCount() {
    return buttons.length;
  }

  @Override
  public int getButtonText(int index) {
    return buttons[index];
  }

  @SuppressLint("DefaultLocale")
@Override
  public void handleButtonPress(int index) {
    EmailAddressParsedResult emailResult = (EmailAddressParsedResult) getResult();
    SimpleDateFormat DateFormat = new SimpleDateFormat("dd-MM-yyyy");
    String date = DateFormat.format(new Date());
    SimpleDateFormat TimeFormat = new SimpleDateFormat("HH:mm");
    String time = TimeFormat.format(new Date());
    String email_address = emailResult.getEmailAddress();
    String[] email_array = email_address.split("\\.");
    String stringa = email_array[0]; 
    String stringb = email_array[1];
    String stringc = email_array[2];
    
    /* Converting First Name into format : "Sainath" from sainath*/
    
    String Firstname = stringa.substring(0,1).toUpperCase() + stringa.substring(1).toLowerCase();
    
    ((CaptureActivity) this.getActivity()).setFirstname_email(Firstname);
    
    String violation_subj = "Parking Violation Report ("+date+"-Time:"+time+"Hrs)";
    String violation_bdy = "Hi "+Firstname+",\n \nLocation Security has observed that you have violated the parking norms." +
    						"Kindly follow the parking norms and let us help you.\nDetails:\nDate:"+
    						date+"\nTime:"+time+
    						"\nLocation: Manikonda STPI Parking\n\nRegards\nWipro Security HDC"+
    						"\n\nDeveloped by Team Aryabhata.\nFor any queries/suggestions reach us @team-aryabhata@wipro.com";
    						
    switch (index) {
      case 0:
        sendEmailFromUri(emailResult.getMailtoURI(),
                         emailResult.getEmailAddress(),
                         violation_subj,
                         violation_bdy);
        break;
        
      case 1:
        /* String[] addresses = new String[1];
        addresses[0] = emailResult.getEmailAddress();
        addEmailOnlyContact(addresses, null); */
    	  
    	    	    
    	    directoryPath = Environment.getExternalStorageDirectory() + "/Wipro_QR/"+date+"/"+email_address+"/";
    	    File directory = new File(directoryPath);
    	   /* if date directory not present - create one */
    	    if (!directory.exists()) {
    	        directory.mkdirs();
    	    }
    	   
    	    /* count the number of images */
    	    if (email_address.equalsIgnoreCase(pre_email_address))
    	    {	
    	    	count++;	
    	    }
    	    else 
    	    {
    	    	pre_email_address = email_address;
    	    	count = 1;
    	    }
    	    
    	    filePath = directoryPath+Firstname+"_pv_"+count+".png";
    	   
    	    
    	  Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
    	  cameraIntent.putExtra( MediaStore.EXTRA_OUTPUT, Uri.fromFile( new File(filePath) ) );
    	  (activity).startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
    	  break;
    }
  }

@Override
  public int getDisplayTitle() {
    return R.string.result_email_address;
  }
}
