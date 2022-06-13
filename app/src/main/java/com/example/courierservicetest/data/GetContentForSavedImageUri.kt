package com.example.courierservicetest.data

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts

class GetContentForSavedImageUri : ActivityResultContracts.GetContent() {
    override fun createIntent(context: Context, input: String): Intent {
        super.createIntent(context, input)
        return Intent(Intent.ACTION_OPEN_DOCUMENT)
            .addCategory(Intent.CATEGORY_OPENABLE)
            .setType(input)
    }
}