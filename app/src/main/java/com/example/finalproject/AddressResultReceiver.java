package com.example.finalproject;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class AddressResultReceiver extends ResultReceiver {

    public String addressOutput;

    public AddressResultReceiver(Handler handler) {
        super(handler);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {

        if (resultData == null) {
            return;
        }

        // Display the address string
        // or an error message sent from the intent service.
        addressOutput = resultData.getString(Constants.RESULT_DATA_KEY);
        if (addressOutput == null) {
            addressOutput = "";
        }
        //displayAddressOutput();

        // Show a toast message if an address was found.
        /*if (resultCode == Constants.SUCCESS_RESULT) {
            showToast(getString(R.string.address_found));
        }


    }*/
}
}

