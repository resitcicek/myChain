package mobile.resitcicek.mychain;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "mychains6.db";
    public Cursor randChain;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE user(ID INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, email TEXT, bio TEXT, tw TEXT, insta TEXT)");
        db.execSQL("CREATE TABLE chain(ID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, description TEXT, reminder INT, private INT, category TEXT, duration TEXT)");
        db.execSQL("CREATE TABLE chainRelation(ID INTEGER PRIMARY KEY AUTOINCREMENT,chainID INTEGER, userID INTEGER, startDate TEXT)");
        //db.execSQL("CREATE TABLE isDone(TEXT)",);
        db.execSQL("CREATE TABLE isDone(ID INTEGER PRIMARY KEY AUTOINCREMENT, relationID TEXT, date TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS chain");
        db.execSQL("DROP TABLE IF EXISTS id");
        //db.execSQL("DROP TABLE IF EXISTS isDone");
    }

    public boolean reDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("user", null, null);
        db.delete("chain", null, null);
        return true;
    }
    public boolean done(int chainID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT ID FROM chainRelation WHERE chainID=? AND userID=?",new String[] {Integer.toString(chainID),Integer.toString(MainActivity.loggedUser.getID())});
        cursor.moveToFirst();
        contentValues.put("relationID",cursor.getInt(0));
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        String date = df.format(cal.getTime());//Integer.toString(day)+"-"+Integer.toString(month)+"-"+Integer.toString(year);
        contentValues.put("date",date);
        long result = sqLiteDatabase.insert("isDone", null, contentValues);
        if(result == -1){
            return false;
        }
        return true;
    }
    public boolean isInChain(int chainID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor rID = sqLiteDatabase.rawQuery("SELECT ID FROM chainRelation WHERE userID=? AND chainID=?",new String[] {Integer.toString(MainActivity.loggedUser.getID()),Integer.toString(chainID)});
        if(rID.getCount() > 0)return true;
        return false;

    }
    public boolean uncheck(int chainID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor rID = sqLiteDatabase.rawQuery("SELECT ID FROM chainRelation WHERE userID=? AND chainID=?",new String[] {Integer.toString(MainActivity.loggedUser.getID()),Integer.toString(chainID)});
        rID.moveToFirst();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        String date = df.format(cal.getTime());
        long delete = sqLiteDatabase.delete("isDone","relationID=? and date=?",new String[]{Integer.toString(rID.getInt(0)),date});
        if(delete == -1){
            return false;
        }
        return true;
    }
    public boolean isDone(int chainID, String Date) throws ParseException {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor rID = sqLiteDatabase.rawQuery("SELECT ID FROM chainRelation WHERE userID=? AND chainID=?",new String[] {Integer.toString(MainActivity.loggedUser.getID()),Integer.toString(chainID)});
        rID.moveToFirst();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM isDone WHERE relationID=? AND date=?", new String[] {rID.getString(0), Date});
        if(cursor.getCount()>0) {
            return true;
        }
        return false;
    }
    public boolean InsertID(int chainID, int userID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("chainID", chainID);
        contentValues.put("userID", userID);
        long result = sqLiteDatabase.insert("id", null, contentValues);
        if(result == -1){
            return false;
        }
        return true;
    }

    public String getDate(int chainID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM chainRelation WHERE chainID=? AND userID=?", new String[] {Integer.toString(chainID),Integer.toString(MainActivity.loggedUser.getID())});
        cursor.moveToFirst();
        return cursor.getString(3);
    }
    public String getUserCount(int chainID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT COUNT(*) FROM chainRelation WHERE chainID=?", new String[] {Integer.toString(chainID)});
        cursor.moveToFirst();
        return Integer.toString(cursor.getInt(0));
    }


    public boolean Insert(String username, String password, String email){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("email", email);
        contentValues.put("bio","");
        contentValues.put("tw","");
        contentValues.put("insta", "");
        long result = sqLiteDatabase.insert("user", null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean Update(int ID,String bio, String tw, String insta) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues itemNewValues = new ContentValues();
        itemNewValues.put("bio",bio);
        itemNewValues.put("tw",tw);
        itemNewValues.put("insta",insta);
        long updateUser = sqLiteDatabase.update("user",itemNewValues,"ID=?",new String[]{Integer.toString(ID)});
        if(updateUser == -1){
            return false;
        }
        else{
            return true;
        }
    }
    public ArrayList<User> Search(String search){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ArrayList<User> userList = new ArrayList<User>();
        Cursor users = sqLiteDatabase.rawQuery("SELECT ID FROM user WHERE username LIKE ?", new String[] {"%"+search+"%"});

        while(users.moveToNext()){
            User user = new User();
            //username TEXT, password TEXT, email TEXT, bio TEXT, tw TEXT, insta TEXT
            //user.setChainNum();
            user = getUser(users.getInt(0));
            user.setChainNum(getChainNumber(user.getID()));
            //user.setUsername(users.getString(1));
            /*user.setInsta(users.getString(6));
            user.setBio(users.getString(4));
            user.setTwitter(users.getString(5));*/
            userList.add(user);
        }
        return userList;
    }

    public long InsertChain(String name, String description, int reminder, String category){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("description", description);
        contentValues.put("reminder", reminder);
        contentValues.put("category", category);

        long result = sqLiteDatabase.insert("chain", null, contentValues);
        if(result == -1){
            return -1;
        }else{
            return result;
        }
    }

    public long InsertRelation(int userid, int chainid){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        String date = format.format(cal.getTime());
        contentValues.put("userID", userid);
        contentValues.put("chainID", chainid);
        contentValues.put("startDate",date);

        long result = sqLiteDatabase.insert("chainRelation", null, contentValues);
        if(result == -1){
            return -1;
        }else{
            return result;
        }
    }

    public ArrayList<Chain> getChains(int userID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ArrayList<Chain> listChain = new ArrayList<Chain>();
        //For see all chains
        //Cursor getUserChains = sqLiteDatabase.rawQuery("SELECT * FROM chainRelation ORDER BY ID DESC", null);
        Cursor getUserChains = sqLiteDatabase.rawQuery("SELECT * FROM chainRelation WHERE userID = ? ORDER BY ID DESC", new String[]{Integer.toString(userID)});
        while(getUserChains.moveToNext()){
            Cursor chains = sqLiteDatabase.rawQuery("SELECT * FROM chain WHERE ID = ? ORDER BY ID DESC", new String[]{Integer.toString(getUserChains.getInt(1))});

            while(chains.moveToNext()){
                Chain chain = new Chain();
                chain.setID(chains.getInt(0));
                chain.setName(chains.getString(1));
                chain.setDescription(chains.getString(2));
                chain.setReminder(chains.getInt(3));
                chain.setPriv(chains.getInt(4));
                chain.setCategory(chains.getString(5));
                chain.setDuration(chains.getString(6));
                listChain.add(chain);
            }
        }

        return listChain;
    }
    public int getChainNumber(int userID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ArrayList<Chain> listChain = new ArrayList<Chain>();
        //For see all chains
        //Cursor getUserChains = sqLiteDatabase.rawQuery("SELECT * FROM chainRelation ORDER BY ID DESC", null);
        Cursor getUserChains = sqLiteDatabase.rawQuery("SELECT COUNT(ID) FROM chainRelation WHERE userID = ?", new String[]{Integer.toString(userID)});
        getUserChains.moveToFirst();

        return(getUserChains.getInt(0));
    }

    public ArrayList<Chain> getRandom(int userID) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int i = 0;
        ArrayList<Chain> listChain = new ArrayList<Chain>();
        ArrayList<Integer> listID = new ArrayList<Integer>();
        while (listChain.stream().count() < 5 && i < 20) {
            randChain = sqLiteDatabase.rawQuery("SELECT * FROM chain ORDER BY RANDOM() LIMIT 1;", null);

            if(randChain.getCount() > 0) {
                randChain.moveToFirst();
                Cursor get = sqLiteDatabase.rawQuery("SELECT * FROM chainRelation WHERE userID = ? AND chainID=?", new String[]{Integer.toString(userID), Integer.toString(randChain.getInt(0))});
                if (get.getCount() < 1 && !listID.contains(randChain.getInt(0))) {
                    //while(getUserChains.moveToNext()){

                    listID.add(randChain.getInt(0));
                    Chain chain = new Chain();
                    chain.setID(randChain.getInt(0));
                    chain.setName(randChain.getString(1));
                    chain.setDescription(randChain.getString(2));
                    chain.setReminder(randChain.getInt(3));
                    chain.setPriv(randChain.getInt(4));
                    chain.setCategory(randChain.getString(5));
                    chain.setDuration(randChain.getString(6));
                    listChain.add(chain);



                }
            }

            else{
                break;
            }
            i++;



        }
        return listChain;
    }


    public Boolean CheckUsername(String username){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM user WHERE username=?", new String[]{username});
        if(cursor.getCount() > 0){
            return false;
        }else{
            return true;
        }
    }

    public User getUser(int ID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM user WHERE ID=?", new String[]{Integer.toString(ID)});
        User user = new User();
        cursor.moveToFirst();
        user.setID(cursor.getInt(0));
        user.setUsername(cursor.getString(1));
        user.setEmail(cursor.getString(2));
        user.setPassword(cursor.getString(3));
        user.setBio(cursor.getString(4));
        user.setTwitter(cursor.getString(5));
        user.setInsta(cursor.getString(6));
        return user;
    }

    public int CheckLogin(String username, String password){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM user WHERE username=? AND password=?", new String[]{username, password});
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            return cursor.getInt(0);
        }else{
            return -1;
        }
    }
}
