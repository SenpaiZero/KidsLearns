package com.example.kidslearn;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import Helper.BaseActivity;
import Helper.userInterfaceHelper;

public class worksheet extends BaseActivity {

    ImageButton backBtn, downloadWorksheet, downloadColor;
    userInterfaceHelper UIHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worksheet);

        UIHelper = new userInterfaceHelper(this);
        UIHelper.removeActionbar();
        UIHelper.transparentStatusBar();

        downloadColor = findViewById(R.id.dowloadColoring);
        downloadWorksheet = findViewById(R.id.dowloadWorksheet);
        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(worksheet.this, parentalControl.class));
            }
        });

        downloadColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputStream inputStream = getResources().openRawResource(R.raw.coloring_book); // Replace 'your_pdf_file' with your PDF file name
                FileOutputStream outputStream = null;
                try {
                    File file = new File(getExternalFilesDir(null), "coloring_book.pdf"); // Change the file name if needed
                    outputStream = new FileOutputStream(file);

                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }

                    // Optionally, you might want to close the stream here
                    successSave(file);
                    inputStream.close();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        if (outputStream != null) {
                            outputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        downloadWorksheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputStream inputStream = getResources().openRawResource(R.raw.worksheet); // Replace 'your_pdf_file' with your PDF file name
                FileOutputStream outputStream = null;
                try {
                    File file = new File(getExternalFilesDir(null), "worksheet.pdf"); // Change the file name if needed
                    outputStream = new FileOutputStream(file);

                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }

                    // Optionally, you might want to close the stream here
                    successSave(file);
                    inputStream.close();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        if (outputStream != null) {
                            outputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    void successSave(final File file) {
        AlertDialog.Builder builder = new AlertDialog.Builder(worksheet.this);
        builder.setTitle("Saved Success");
        builder.setMessage("You saved the worksheet successfully");
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                openPDF(file);
            }
        });
        builder.show();
    }

    void openPDF(File file) {
        Uri fileUri = FileProvider.getUriForFile(
                worksheet.this,
                worksheet.this.getPackageName() + ".fileprovider",
                file
        );

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(fileUri, "application/pdf");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // PDF viewer not installed, handle this exception
        }
    }
}