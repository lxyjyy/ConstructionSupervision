package com.csc.db;

import android.content.ContentValues;
import android.database.Cursor;

/**
 *
 */

public abstract class DataBaseBuilder<T> {

    public abstract T build(Cursor c);

    public abstract ContentValues deconstruct(T t);
}
