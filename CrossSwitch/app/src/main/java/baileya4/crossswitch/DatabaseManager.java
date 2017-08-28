package baileya4.crossswitch;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
/**
 * Based on the Database Manager class from CandyStore
 */

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "playerDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PLAYER = "player";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String SCORE_3 = "score3";
    private static final String SCORE_4 = "score4";
    private static final String SCORE_5 = "score5";
    private static final String SCORE_6 = "score6";
    private static final String SCORE_D = "scoreD";
    private static final String TIME = "time";

    public DatabaseManager( Context context ) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    public void clearDB(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL( "drop table if exists " + TABLE_PLAYER );
        onCreate(db);
        db.close();
    }

    public void onCreate( SQLiteDatabase db ) {
        // build sql create statement
        String sqlCreate = "create table " + TABLE_PLAYER + "( " + ID;
        sqlCreate += " integer primary key autoincrement, " + NAME;
        sqlCreate += " text, " + SCORE_3 + " real, " ;
        sqlCreate += SCORE_4 + " real, " ;
        sqlCreate += SCORE_5 + " real, " ;
        sqlCreate += SCORE_6 + " real, " ;
        sqlCreate += SCORE_D + " real, " ;
        sqlCreate += TIME + " real )" ;

        db.execSQL( sqlCreate );
    }

    public void onUpgrade( SQLiteDatabase db,
                           int oldVersion, int newVersion ) {
        // Drop old table if it exists
        db.execSQL( "drop table if exists " + TABLE_PLAYER );
        // Re-create tables
        onCreate( db );
    }

    public void insert( Player player ) {
        SQLiteDatabase db = this.getWritableDatabase( );
        String sqlInsert = "insert into " + TABLE_PLAYER;
        sqlInsert += " values( null, '" + player.getName( );
        sqlInsert += "', '" + player.get_three( );
        sqlInsert += "', '" + player.get_four( );
        sqlInsert += "', '" + player.get_five( );
        sqlInsert += "', '" + player.get_six( );
        sqlInsert += "', '" + player.get_diamond( );
        sqlInsert += "', '" + player.getTime( ) + "' )";

        db.execSQL( sqlInsert );
        db.close( );
    }

    public void deleteById( int id ) {
        SQLiteDatabase db = this.getWritableDatabase( );
        String sqlDelete = "delete from " + TABLE_PLAYER;
        sqlDelete += " where " + ID + " = " + id;

        db.execSQL( sqlDelete );
        db.close( );
    }

    public void updateById( int id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sqlUpdate = "update " + TABLE_PLAYER;
        sqlUpdate += " set " + NAME + " = '" + name + "'";
        sqlUpdate += " where " + ID + " = " + id;

        db.execSQL( sqlUpdate );
        db.close( );
    }

    public void updateScore( int id, int type, int score) {
        SQLiteDatabase db = this.getWritableDatabase();

        String game = new String();
        switch(type){
            case 3: game = SCORE_3; break;
            case 4: game = SCORE_4; break;
            case 5: game = SCORE_5; break;
            case 6: game = SCORE_6; break;
            case 7: game = SCORE_D; break;
        }

        String sqlUpdate = "update " + TABLE_PLAYER;
        sqlUpdate += " set " + game + " = '" + score + "'";
        sqlUpdate += " where " + ID + " = " + id;

        db.execSQL( sqlUpdate );
        db.close( );
    }

    public void updateScore( int id, long time) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sqlUpdate = "update " + TABLE_PLAYER;
        sqlUpdate += " set " + TIME + " = '" + time + "'";
        sqlUpdate += " where " + ID + " = " + id;

        db.execSQL( sqlUpdate );
        db.close( );
    }

    public ArrayList<Player> selectAll( ) {
        String sqlQuery = "select * from " + TABLE_PLAYER;

        SQLiteDatabase db = this.getWritableDatabase( );
        Cursor cursor = db.rawQuery( sqlQuery, null );

        ArrayList<Player> players = new ArrayList<Player>();
        while( cursor.moveToNext( ) ) {
            Player curr
                    = new Player( Integer.parseInt( cursor.getString( 0 ) ),
                    cursor.getString( 1 ));
            curr.setScores(Integer.parseInt(cursor.getString(2)),
                    Integer.parseInt(cursor.getString(3)),
                    Integer.parseInt(cursor.getString(4)),
                    Integer.parseInt(cursor.getString(5)),
                    Integer.parseInt(cursor.getString(6)),
                    Long.parseLong(cursor.getString(7)));
            players.add( curr );
        }
        db.close( );
        return players;
    }

    public Player selectById( int id ) {
        String sqlQuery = "select * from " + TABLE_PLAYER;
        sqlQuery += " where " + ID + " = " + id;

        SQLiteDatabase db = this.getWritableDatabase( );
        Cursor cursor = db.rawQuery( sqlQuery, null );

        Player player = null;
        if( cursor.moveToFirst( ) )
            player = new Player( Integer.parseInt( cursor.getString( 0 ) ),
                    cursor.getString( 1 ));
        return player;
    }

    public void selectPlayer(int id){
        String sqlQuery = "select * from " + TABLE_PLAYER;
        sqlQuery += " where " + ID + " = " + id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery,null);

        Player player = null;
        if (cursor.moveToFirst()) {
            player = new Player(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1));
            player.setScores(Integer.parseInt(cursor.getString(2)),
                    Integer.parseInt(cursor.getString(3)),
                    Integer.parseInt(cursor.getString(4)),
                    Integer.parseInt(cursor.getString(5)),
                    Integer.parseInt(cursor.getString(6)),
                    Long.parseLong(cursor.getString(7)));
        }

        Player.current = player;
    }
}
