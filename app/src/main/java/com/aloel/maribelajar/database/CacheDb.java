package com.aloel.maribelajar.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.aloel.maribelajar.model.Quiz;
import com.aloel.maribelajar.util.Debug;

import java.util.ArrayList;

public class CacheDb extends Database {

	public CacheDb(SQLiteDatabase sqlite) {
		super(sqlite);
	}

	public void update(ArrayList<Quiz> list) {
		if (mSqLite == null || !mSqLite.isOpen()) {
			return;
		}

		if (list == null) {
			return;
		}

		Debug.i("Updating cache ");

		mSqLite.delete("cache", null, null);

		int size = list.size();

		for (int i = 0; i < size; i++){
			Quiz quiz		= list.get(i);
			ContentValues values 	= new ContentValues();

			values.put("id",    	quiz.id);
			values.put("subject",	quiz.subject);
			values.put("class",		quiz.classStudent);
			values.put("type",		quiz.type);
			values.put("question",	quiz.question);
			values.put("image",		quiz.image);
			values.put("option1",	quiz.option1);
			values.put("option2",	quiz.option2);
			values.put("option3",	quiz.option3);
			values.put("option4",	quiz.option4);
			values.put("answer",	quiz.answer);
			values.put("penjelasan",	quiz.penjelasan);
			values.put("penjelasan_image",	quiz.penjelasan_image);

			mSqLite.insert("cache", null, values);
		}
	}

	public Quiz getQuiz(String id, String subject, String classStudent) {
		Quiz quiz = null;

		Log.e("TTT", mSqLite + "");
		
		if (mSqLite == null || !mSqLite.isOpen()) {
			return quiz;
		}
		
		String sql	= "SELECT * FROM cache WHERE id = " + id +" AND subject = '" +
				subject + "' AND class = '" + classStudent + "'";
	
		Cursor c 	= mSqLite.rawQuery(sql, null);
	
		Debug.i(sql);
		
		if (c != null) {

			if (c.moveToFirst()) {
				quiz = new Quiz();

				quiz.id 			= c.getString(c.getColumnIndex("id"));
				quiz.subject 		= c.getString(c.getColumnIndex("subject"));
				quiz.classStudent 	= c.getString(c.getColumnIndex("class"));
				quiz.type 			= c.getString(c.getColumnIndex("type"));
				quiz.question 		= c.getString(c.getColumnIndex("question"));
				quiz.image 			= c.getString(c.getColumnIndex("image"));
				quiz.option1 		= c.getString(c.getColumnIndex("option1"));
				quiz.option2 		= c.getString(c.getColumnIndex("option2"));
				quiz.option3 		= c.getString(c.getColumnIndex("option3"));
				quiz.option4 		= c.getString(c.getColumnIndex("option4"));
				quiz.answer 		= c.getString(c.getColumnIndex("answer"));
				quiz.penjelasan 		= c.getString(c.getColumnIndex("penjelasan"));
				quiz.penjelasan_image 		= c.getString(c.getColumnIndex("penjelasan_image"));

			}
			c.close();
		}
	
		return quiz;
	}

	public ArrayList<Quiz> getQuizAll() {
		ArrayList<Quiz> mQuiz = null;

		Log.e("TTT", mSqLite + "");

		if (mSqLite == null || !mSqLite.isOpen()) {
			return mQuiz;
		}

		String sql	= "SELECT * FROM cache";

		Cursor c 	= mSqLite.rawQuery(sql, null);

		Debug.i(sql);

		if (c != null) {

			if (c.moveToFirst()) {
				mQuiz = new ArrayList<Quiz>();

				while (c.isAfterLast()  == false) {
					Quiz quiz = new Quiz();

					quiz.id 			= c.getString(c.getColumnIndex("id"));
					quiz.subject 		= c.getString(c.getColumnIndex("subject"));
					quiz.classStudent 	= c.getString(c.getColumnIndex("class"));
					quiz.type 			= c.getString(c.getColumnIndex("type"));
					quiz.question 		= c.getString(c.getColumnIndex("question"));
					quiz.image 			= c.getString(c.getColumnIndex("image"));
					quiz.option1 		= c.getString(c.getColumnIndex("option1"));
					quiz.option2 		= c.getString(c.getColumnIndex("option2"));
					quiz.option3 		= c.getString(c.getColumnIndex("option3"));
					quiz.option4 		= c.getString(c.getColumnIndex("option4"));
					quiz.answer 		= c.getString(c.getColumnIndex("answer"));
					quiz.penjelasan 		= c.getString(c.getColumnIndex("penjelasan"));
					quiz.penjelasan_image 		= c.getString(c.getColumnIndex("penjelasan_image"));

					mQuiz.add(quiz);

					c.moveToNext();
				}

			}
			c.close();
		}

		return mQuiz;
	}

	public ArrayList<Quiz> getQuizAll(String subject, String classStudent) {
		ArrayList<Quiz> mQuiz = null;

		Log.e("TTT", mSqLite + "");

		if (mSqLite == null || !mSqLite.isOpen()) {
			return mQuiz;
		}

		String sql	= "SELECT * FROM cache WHERE subject = '" + subject + "' AND class = '"  + classStudent + "'";

		Cursor c 	= mSqLite.rawQuery(sql, null);

		Debug.i(sql);

		if (c != null) {

			if (c.moveToFirst()) {
				mQuiz = new ArrayList<Quiz>();

				while (c.isAfterLast()  == false) {
					Quiz quiz = new Quiz();

					quiz.id 			= c.getString(c.getColumnIndex("id"));
					quiz.subject 		= c.getString(c.getColumnIndex("subject"));
					quiz.classStudent 	= c.getString(c.getColumnIndex("class"));
					quiz.type 			= c.getString(c.getColumnIndex("type"));
					quiz.question 		= c.getString(c.getColumnIndex("question"));
					quiz.image 			= c.getString(c.getColumnIndex("image"));
					quiz.option1 		= c.getString(c.getColumnIndex("option1"));
					quiz.option2 		= c.getString(c.getColumnIndex("option2"));
					quiz.option3 		= c.getString(c.getColumnIndex("option3"));
					quiz.option4 		= c.getString(c.getColumnIndex("option4"));
					quiz.answer 		= c.getString(c.getColumnIndex("answer"));
					quiz.penjelasan 		= c.getString(c.getColumnIndex("penjelasan"));
					quiz.penjelasan_image 		= c.getString(c.getColumnIndex("penjelasan_image"));

					mQuiz.add(quiz);

					c.moveToNext();
				}

			}
			c.close();
		}

		return mQuiz;
	}
}
