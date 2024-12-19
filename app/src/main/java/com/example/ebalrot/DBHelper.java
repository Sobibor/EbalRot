package com.example.ebalrot;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DBHelper {
    private static final String DB_NAME = "itperson.db"; // Имя файла БД
    private final String DB_PATH; // Путь к файлу БД в директории приложения
    private final Context context;

    public DBHelper(Context context) {
        this.context = context;
        this.DB_PATH = context.getFilesDir().getPath() + "/" + DB_NAME;
        copyDatabase();
    }

    private boolean checkDatabase() {
        File dbFile = new File(DB_PATH);
        return dbFile.exists();
    }

    private void copyDatabase() {
        if (!checkDatabase()) {
            try (InputStream inputStream = context.getAssets().open(DB_NAME);
                 FileOutputStream outputStream = new FileOutputStream(DB_PATH)) {

                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
            } catch (IOException e) {
                throw new SQLiteException("Ошибка при копировании базы данных: " + e.getMessage());
            }
        }
    }

    public SQLiteDatabase openDatabase() {
        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }
}
