package com.csc.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.csc.bean.User;

/**
 *
 */

public class UserDatabaseBuilder extends DataBaseBuilder<User>{

    @Override
    public User build(Cursor query) {
        int columnUid = query.getColumnIndex("uid");
        int columnToken = query.getColumnIndex("token");
        int columnHeadpicpath = query.getColumnIndex("headpicpath");
        int columnTruename = query.getColumnIndex("truename");
        int columnHospital = query.getColumnIndex("hospital");
        int columnSubject = query.getColumnIndex("subject");
        int columnDoctortitle = query.getColumnIndex("doctortitle");
        int columnDoctorlicense = query.getColumnIndex("doctorlicense");
        int columnFeature = query.getColumnIndex("feature");
        int columnRemark = query.getColumnIndex("remark");
        int columnIscertificated = query.getColumnIndex("iscertificated");
        int columnscore = query.getColumnIndex("score");
        int columnfee = query.getColumnIndex("fee");
        int columnservice = query.getColumnIndex("service");
        int columnbalance = query.getColumnIndex("balance");

//
        User user = new User();
//        user.setUid(query.getString(columnUid));
//        user.setToken(query.getString(columnToken));
//        user.setHeadpicpath(query.getString(columnHeadpicpath));
//        user.setTruename(query.getString(columnTruename));
//        user.setHospital(query.getString(columnHospital));
//        user.setSubject(query.getString(columnSubject));
//        user.setDoctortitle(query.getString(columnDoctortitle));
//        user.setDoctorlicense(query.getString(columnDoctorlicense));
//        user.setFeature(query.getString(columnFeature));
//        user.setRemark(query.getString(columnRemark));
//        user.setIscertificated(query.getString(columnIscertificated));
//        user.setScore(query.getString(columnscore));
//        user.setFee(query.getString(columnfee));
//        user.setService(query.getString(columnservice));
//        user.setBalance(query.getString(columnbalance));
        return user;
    }

    @Override
    public ContentValues deconstruct(User user) {
        ContentValues values = new ContentValues();
//        values.put("id", 1);
//        values.put("uid", user.getUid());
//        values.put("token", user.getToken());
//        values.put("headpicpath", user.getHeadpicpath());
//        values.put("truename", user.getTruename());
//        values.put("hospital", user.getHospital());
//        values.put("subject", user.getSubject());
//        values.put("doctortitle", user.getDoctortitle());
//        values.put("doctorlicense", user.getDoctorlicense());
//        values.put("feature", user.getFeature());
//        values.put("remark", user.getRemark());
//        values.put("iscertificated", user.getIscertificated());
//        values.put("score", user.getScore());
//        values.put("fee", user.getFee());
//        values.put("service", user.getService());
//        values.put("balance", user.getBalance());

        return values;
    }
}
