package mobile.resitcicek.mychain;
import java.util.List;
import java.util.Random;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "mychains4.db";
    public Cursor randChain;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE user(ID INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, email TEXT, bio TEXT, tw TEXT, insta TEXT)");
        db.execSQL("CREATE TABLE chain(ID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, description TEXT, reminder INT, private INT, category TEXT, duration TEXT)");
        db.execSQL("CREATE TABLE chainRelation(ID INTEGER PRIMARY KEY AUTOINCREMENT,chainID INTEGER, userID INTEGER)");
        //db.execSQL("CREATE TABLE isDone(ID INTEGER PRIMARY KEY AUTOINCREMENT, relationID TEXT, date TEXT)");
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
    //public boolean InsertDone(int chain)


    public boolean Insert(String username, String password, String email){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("email", email);
        contentValues.put("bio","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
        contentValues.put("tw","omercanfanclup");
        contentValues.put("insta", "boraktn");
        long result = sqLiteDatabase.insert("user", null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    public void Update(int ID,String bio, String tw, String insta) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("UPDATE user SET bio=?, tw=?, insta=? WHERE ID=?", new String[] {bio,tw,insta,Integer.toString(ID)});
    }
    public ArrayList<User> Search(String search){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ArrayList<User> userList = new ArrayList<User>();
        Cursor users = sqLiteDatabase.rawQuery("SELECT * FROM user WHERE username LIKE '%'+?+'%'", new String[] {search});
        User user = new User();
        while(users.moveToNext()){
            //username TEXT, password TEXT, email TEXT, bio TEXT, tw TEXT, insta TEXT
            user.setUsername(users.getString(1));
            user.setInsta(users.getString(6));
            user.setBio(users.getString(4));
            user.setTwitter(users.getString(5));
            userList.add(user);
        }
        return userList;
    }

    public long InsertChain(String name, String description, int reminder, int priv, String category, String duration){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("description", description);
        contentValues.put("reminder", reminder);
        contentValues.put("private", priv);
        contentValues.put("category", category);
        contentValues.put("duration", duration);
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
        contentValues.put("userID", userid);
        contentValues.put("chainID", chainid);

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

        while (listChain.stream().count() < 5) {

            randChain = sqLiteDatabase.rawQuery("SELECT * FROM chain ORDER BY RANDOM() LIMIT 1;", null);
            if(randChain.getCount() > 0) {
                randChain.moveToFirst();
                Cursor get = sqLiteDatabase.rawQuery("SELECT * FROM chainRelation WHERE userID = ? AND chainID=?", new String[]{Integer.toString(userID), Integer.toString(randChain.getInt(0))});
                if (get.getCount() < 1) {
                    //while(getUserChains.moveToNext()){


                    Chain chain = new Chain();
                    chain.setName(randChain.getString(1));
                    chain.setDescription(randChain.getString(2));
                    chain.setReminder(randChain.getInt(3));
                    chain.setPriv(randChain.getInt(4));
                    chain.setCategory(randChain.getString(5));
                    chain.setDuration(randChain.getString(6));
                    listChain.add(chain);

                    i++;
                }
            }
            else{
                break;
            }




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
