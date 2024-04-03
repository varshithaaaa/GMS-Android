package com.example.growwise;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "Login.db";
    public DBHelper(Context context) {
        super(context, "Login.db", null, 1);
    }
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_IMAGE_PATH = "image_path";
    private static final String COLUMN_DOB = "dob";
    private static final String COLUMN_ROLE = "role";
    private static final String COLUMN_MOBILE_NUMBER = "mobile_number";
    private static final String COLUMN_RATING = "rating";
    private static final String COLUMN_IS_ACTIVE = "active";

    private static final String TABLE_LOCATIONS = "locations";
    private static final String COLUMN_LOCATION_ID = "locationID";
    private static final String COLUMN_LOCATION_NAME = "locationName";
    private static final String COLUMN_MANAGER_INCHARGE = "managerInchargeId";
    private static final String COLUMN_SUPERVISOR_INCHARGE = "supervisorInchargeId";
    private static final String COLUMN_NO_OF_WORKERS = "noOfWorkers";
    private static final String COLUMN_PHONE_NUMBER = "phoneNumber";
    private static final String COLUMN_WORKERS = "workers";

    private static final String TABLE_TOOLS = "Tools";
    private static final String COLUMN_TOOL_ID = "toolId";
    private static final String COLUMN_TOOL_NAME = "toolName";
    private static final String COLUMN_TOOL_QUANTITY = "quantity";

    private static final String TABLE_TOOLS_ALLOCATED = "ToolsAllocated";
    private static final String COLUMN_TOOL_ALLOCATION_ID = "toolAllocationId";
    private static final String COLUMN_TOOL_ALLOCATION_TOOL_ID = "toolId";
    private static final String COLUMN_TOOL_ALLOCATION_SUPERVISOR_ID = "supervisorId";
    private static final String COLUMN_TOOL_ALLOCATION_QUANTITY = "quantity";

    private static final String CREATE_TABLE_TOOLS_ALLOCATED = "CREATE TABLE " + TABLE_TOOLS_ALLOCATED + "(" +
            COLUMN_TOOL_ALLOCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_TOOL_ALLOCATION_TOOL_ID + " INTEGER," +
            COLUMN_TOOL_ALLOCATION_SUPERVISOR_ID + " INTEGER," +
            COLUMN_TOOL_ALLOCATION_QUANTITY + " INTEGER," +
            "FOREIGN KEY(" + COLUMN_TOOL_ALLOCATION_TOOL_ID + ") REFERENCES " + TABLE_TOOLS + "(" + COLUMN_TOOL_ID + ")," +
            "FOREIGN KEY(" + COLUMN_TOOL_ALLOCATION_SUPERVISOR_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "))";

    public static final String TABLE_TASKS = "tasks";
    public static final String COLUMN_TASK_ID = "taskId";
    public static final String COLUMN_TASK_NAME = "taskName";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_START_TIME = "startTime";
    public static final String COLUMN_STATUS = "status";
    private static final String STATUS_UPCOMING = "Upcoming";
    private static final String STATUS_COMPLETED = "Completed";
    private static final String SQL_CREATE_TASKS =
            "CREATE TABLE " + TABLE_TASKS + " (" +
                    COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_TASK_NAME + " TEXT," +
                    COLUMN_DATE + " TEXT," +
                    COLUMN_START_TIME + " TEXT," +
                    COLUMN_WORKERS + " TEXT," +
                    COLUMN_STATUS + " TEXT," +
                    COLUMN_MANAGER_INCHARGE + " INTEGER REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ")," +
                    COLUMN_SUPERVISOR_INCHARGE + " INTEGER REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ")" +
                    ")";
    private static final String TABLE_SUPERVISOR_REQUESTS = "supervisorRequests";
    private static final String COLUMN_SUPERVISOR_REQUEST_ID = "supervisorRequestId";
    private static final String COLUMN_REQUEST_TO = "requestTo";
    private static final String COLUMN_MESSAGE = "message";
    private static final String CREATE_TABLE_SUPERVISOR_REQUESTS =
            "CREATE TABLE " + TABLE_SUPERVISOR_REQUESTS + "(" +
                    COLUMN_SUPERVISOR_REQUEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_REQUEST_TO + " INTEGER," +
                    COLUMN_MESSAGE + " TEXT," +
                    "FOREIGN KEY (" + COLUMN_REQUEST_TO + ") REFERENCES " +
                    TABLE_USERS + "(" + COLUMN_USER_ID + "))";

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        String createTableQuery = "CREATE TABLE " + TABLE_USERS + "(" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_NAME +" TEXT, "+
                COLUMN_PASSWORD +" TEXT, "+
                COLUMN_IMAGE_PATH + " TEXT, " +
                COLUMN_DOB + " TEXT, " +
                COLUMN_ROLE + " TEXT, " +
                COLUMN_RATING+ " INTEGER,"+
                COLUMN_IS_ACTIVE+ " BOOLEAN,"+
                COLUMN_MOBILE_NUMBER + " TEXT)";
        MyDB.execSQL(createTableQuery);

        String CREATE_LOCATIONS_TABLE = "CREATE TABLE " + TABLE_LOCATIONS + "("
                + COLUMN_LOCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_LOCATION_NAME + " TEXT,"
                + COLUMN_MANAGER_INCHARGE + " INTEGER,"
                + COLUMN_SUPERVISOR_INCHARGE + " INTEGER,"
                + COLUMN_NO_OF_WORKERS + " INTEGER,"
                + COLUMN_PHONE_NUMBER + " TEXT,"
                + COLUMN_WORKERS + " TEXT,"
                + "FOREIGN KEY (" + COLUMN_MANAGER_INCHARGE + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "),"
                + "FOREIGN KEY (" + COLUMN_SUPERVISOR_INCHARGE + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ")"
                + ")";
        MyDB.execSQL(CREATE_LOCATIONS_TABLE);

        String CREATE_TOOLS_TABLE =
                "CREATE TABLE Tools (toolId INTEGER PRIMARY KEY AUTOINCREMENT, toolName TEXT, quantity INTEGER)";
        MyDB.execSQL(CREATE_TOOLS_TABLE);
        MyDB.execSQL(SQL_CREATE_TASKS);

        addPredefinedLocations(MyDB);
        addPredefinedUsers(MyDB);
        MyDB.execSQL(CREATE_TABLE_TOOLS_ALLOCATED);
        MyDB.execSQL("CREATE TABLE toolsRequested (\n" +
                "    requestId INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    toolId INTEGER,\n" +
                "    requestorId INTEGER REFERENCES users(userId),\n" +
                "    quantity INTEGER,\n" +
                "    accepted INTEGER\n" +
                ");");

        MyDB.execSQL(CREATE_TABLE_SUPERVISOR_REQUESTS);

    }

    public void insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getUserName());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_ROLE, user.getDesignation());
        values.put(COLUMN_DOB, user.getDob());
        values.put(COLUMN_MOBILE_NUMBER, user.getPhoneNumber());
        values.put(COLUMN_RATING, 0);
        values.put(COLUMN_IS_ACTIVE, true);
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    @SuppressLint("Range")
    public int checkUserCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_USER_ID, COLUMN_USER_NAME};
        String selection = COLUMN_USER_NAME + "=? AND " + COLUMN_PASSWORD + "=?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        int userId = -1;

        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID));
        }

        cursor.close();
        db.close();

        return userId;
    }

    @SuppressLint("Range")
    public User getUserDetails(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {
                COLUMN_USER_NAME,
                COLUMN_PASSWORD,
                COLUMN_ROLE,
                COLUMN_DOB,
                COLUMN_MOBILE_NUMBER
        };
        String selection = COLUMN_USER_ID + "=?";
        String[] selectionArgs = {String.valueOf(userId)};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        User user = null;
        if (cursor.moveToFirst()) {
            String fetchedUsername = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME));
            String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
            String designation = cursor.getString(cursor.getColumnIndex(COLUMN_ROLE));
            String dob = cursor.getString(cursor.getColumnIndex(COLUMN_DOB));
            String phoneNumber = cursor.getString(cursor.getColumnIndex(COLUMN_MOBILE_NUMBER));

            user = new User();
            user.setUserID(userId);
            user.setUserName(fetchedUsername);
            user.setDesignation(designation);
            user.setDob(dob);
            user.setPhoneNumber(phoneNumber);
        }

        cursor.close();
        db.close();

        return user;
    }

    private void addPredefinedUsers(SQLiteDatabase db) {
        try {
            String[][] predefinedUsersData = {
                    {"Admin", "Admin", "Admin", "", "02-03-1998", "9876543210"},
                    {"Manager", "Manager", "Manager", "", "02-03-1997", "9876543210"},
                    {"Supervisor", "Supervisor", "Supervisor", "", "02-03-1997", "1234567890"},
                    {"Worker", "Worker", "Worker", "", "03-05-1996", "8754691230"},
                    {"Pavan", "123", "Supervisor", "", "02-03-1997", "1234567890"},
                    {"Meghana", "123", "Supervisor", "", "02-03-1997", "1234567890"}
            };

            for (String[] userData : predefinedUsersData) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_USER_NAME, userData[0]);
                values.put(COLUMN_PASSWORD, userData[1]);
                values.put(COLUMN_ROLE, userData[2]);
                values.put(COLUMN_IMAGE_PATH, userData[3]);
                values.put(COLUMN_DOB, userData[4]);
                values.put(COLUMN_MOBILE_NUMBER, userData[5]);
                values.put(COLUMN_IS_ACTIVE, true);


                db.insert(TABLE_USERS, null, values);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        private void addPredefinedLocations(SQLiteDatabase db) {
        try {
            String[][] predefinedLocationsData = {
                    {"Location1", "2", "3", "10", "1234567890", "4"},
                    {"Location2", "2", "3", "15", "9876543210", "4"}
            };

            for (String[] locationData : predefinedLocationsData) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_LOCATION_NAME, locationData[0]);
                values.put(COLUMN_MANAGER_INCHARGE, Integer.parseInt(locationData[1]));
                values.put(COLUMN_SUPERVISOR_INCHARGE, Integer.parseInt(locationData[2]));
                values.put(COLUMN_NO_OF_WORKERS, Integer.parseInt(locationData[3]));
                values.put(COLUMN_PHONE_NUMBER, locationData[4]);
                values.put(COLUMN_WORKERS, locationData[5]);
                db.insert(TABLE_LOCATIONS, null, values);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        MyDB.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);
        MyDB.execSQL("DROP TABLE IF EXISTS " + TABLE_TOOLS);
        MyDB.execSQL("DROP TABLE IF EXISTS " + TABLE_TOOLS_ALLOCATED);
        MyDB.execSQL("DROP TABLE IF EXISTS "+ TABLE_TASKS);
        MyDB.execSQL("DROP TABLE IF EXISTS toolsRequested");
        MyDB.execSQL("DROP TABLE IF EXISTS " + TABLE_SUPERVISOR_REQUESTS);
    }

    public boolean addSupervisorRequest(int requestToUserId, String message) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_REQUEST_TO, requestToUserId);
        values.put(COLUMN_MESSAGE, message);

        long result = db.insert(TABLE_SUPERVISOR_REQUESTS, null, values);
        db.close();

        return result != -1;
    }

    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getUserName());
        values.put(COLUMN_IMAGE_PATH, user.getImagePath());
        values.put(COLUMN_DOB, user.getDob());
        values.put(COLUMN_ROLE, user.getDesignation());
        values.put(COLUMN_MOBILE_NUMBER, user.getPhoneNumber());
        values.put(COLUMN_RATING, 0);
        values.put(COLUMN_IS_ACTIVE, true);
        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result;
    }

    @SuppressLint("Range")
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String[] columns = {"user_id", "username", "image_path", "dob", "role", "mobile_number","active"};
            String selection = "role != ?";
            String[] selectionArgs = {"Admin"};
            cursor = db.query("users", columns, selection, selectionArgs, null, null, null);

            while (cursor.moveToNext()) {
                User user = new User();
                user.setUserID(cursor.getInt(cursor.getColumnIndex("user_id")));
                user.setUserName(cursor.getString(cursor.getColumnIndex("username")));
                user.setImagePath(cursor.getString(cursor.getColumnIndex("image_path")));
                user.setDob(cursor.getString(cursor.getColumnIndex("dob")));
                user.setDesignation(cursor.getString(cursor.getColumnIndex("role")));
                user.setPhoneNumber(cursor.getString(cursor.getColumnIndex("mobile_number")));
                boolean isActive = cursor.getInt(cursor.getColumnIndex("active")) == 1;
                user.setActive(isActive);
                userList.add(user);
            }
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return userList;
    }

    @SuppressLint("Range")
    public String getLocationNameByUserId(int userId, String userDesignation) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String locationName = "";

        try {
            String[] columns = {"locationName"};
            String selection = null;
            if(userDesignation.equalsIgnoreCase("Manager")) {
                selection = "managerInchargeId=?";
            } else {
                selection = "supervisorInchargeId=?";
            }
            String[] selectionArgs = {String.valueOf(userId)};

            cursor = db.query("locations", columns, selection, selectionArgs, null, null, null);

            if (cursor.moveToFirst()) {
                locationName = cursor.getString(cursor.getColumnIndex("locationName"));
            }
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            db.close();
        }

        return locationName;
    }


    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public boolean deleteUser(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean isDeleted = false;
        try {
            String selection = COLUMN_USER_ID + " = ?";
            String[] selectionArgs = {String.valueOf(userId)};
            int rowsAffected = db.delete(TABLE_USERS, selection, selectionArgs);
            isDeleted = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return isDeleted;
    }


    public boolean toggleUserActiveStatus(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean isActiveToggled = false;

        try {
            boolean currentActiveStatus = getUserActiveStatus(userId);
            Log.d("activeStatus", currentActiveStatus+"");

            ContentValues values = new ContentValues();
            values.put(COLUMN_IS_ACTIVE, !currentActiveStatus);

            String selection = COLUMN_USER_ID + " = ?";
            String[] selectionArgs = {String.valueOf(userId)};
            int rowsAffected = db.update(TABLE_USERS, values, selection, selectionArgs);
            isActiveToggled = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isActiveToggled;
    }

    @SuppressLint("Range")
     boolean getUserActiveStatus(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean isActive = false;

        try {
            String[] columns = {COLUMN_IS_ACTIVE};
            String selection = COLUMN_USER_ID + " = ?";
            String[] selectionArgs = {String.valueOf(userId)};

            Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                isActive = cursor.getInt(cursor.getColumnIndex(COLUMN_IS_ACTIVE)) == 1;
                cursor.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isActive;
    }

    public boolean addLocation(String name, int managerId, int supervisorId, int noOfWorkers, String phNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean isAdded = false;

        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_LOCATION_NAME, name);
            values.put(COLUMN_MANAGER_INCHARGE, managerId);
            values.put(COLUMN_SUPERVISOR_INCHARGE, supervisorId);
            values.put(COLUMN_NO_OF_WORKERS, noOfWorkers);
            values.put(COLUMN_PHONE_NUMBER, phNo);

            // Insert the values into the 'locations' table
            long newRowId = db.insert(TABLE_LOCATIONS, null, values);

            // If the insertion was successful, set isAdded to true
            isAdded = newRowId != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return isAdded;
    }

    @SuppressLint("Range")
    public List<LocationsCardModel> getAllLocations() {
        List<LocationsCardModel> locationsList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        try {
            String selectQuery = "SELECT * FROM " + TABLE_LOCATIONS;

            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    int managerId = cursor.getInt(cursor.getColumnIndex(COLUMN_MANAGER_INCHARGE));
                    int supervisorId = cursor.getInt(cursor.getColumnIndex(COLUMN_SUPERVISOR_INCHARGE));
                    String managerUsername = "";
                    String supervisorUsername = "";

                    User managerUser = getUserDetails(managerId);
                    if (managerUser != null) {
                        managerUsername = managerUser.getUserName();
                    }

                    User supervisorUser = getUserDetails(supervisorId);
                    if (supervisorUser != null) {
                        supervisorUsername = supervisorUser.getUserName();
                    }
                    LocationsCardModel location = new LocationsCardModel();
                    location.setLocationID(cursor.getInt(cursor.getColumnIndex(COLUMN_LOCATION_ID)));
                    location.setLocationName(cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION_NAME)));
                    location.setManagerIncharge(managerUsername);
                    location.setSupervisorIncharge(supervisorUsername);

                    String workerIds = cursor.getString(cursor.getColumnIndex(COLUMN_WORKERS));
                    if (workerIds != null && !workerIds.isEmpty()) {
                        String[] workerIdArray = workerIds.split(",");
                        location.setNoOfWorkers(workerIdArray.length);
                    } else {
                        location.setNoOfWorkers(0);
                    }
                    location.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER)));

                    locationsList.add(location);
                } while (cursor.moveToNext());
            }

            cursor.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return locationsList;
    }

    @SuppressLint("Range")
    public List<User> getUsersByDesignation(String designation) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<User> userList = new ArrayList<>();

        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_NAME,
                COLUMN_MOBILE_NUMBER,
                COLUMN_DOB
        };

        String selection = COLUMN_ROLE + "=?";
        String[] selectionArgs = {designation};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int userID = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID));
                String fetchedUsername = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME));
                String dob = cursor.getString(cursor.getColumnIndex(COLUMN_DOB));
                String phoneNumber = cursor.getString(cursor.getColumnIndex(COLUMN_MOBILE_NUMBER));
                User user = new User();
                user.setUserID(userID);
                user.setUserName(fetchedUsername);
                user.setDob(dob);
                user.setPhoneNumber(phoneNumber);

                userList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return userList;
    }

// DBHelper.java

    @SuppressLint("Range")
    public List<User> getWorkers() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<User> workerList = new ArrayList<>();

        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_NAME,
        };

        String selection = COLUMN_ROLE + "=?";
        String[] selectionArgs = {"Worker"};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int userID = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID));
                String fetchedUsername = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME));

                User user = new User();
                user.setUserID(userID);
                user.setUserName(fetchedUsername);

                workerList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return workerList;
    }

    public boolean updateLocation(int locationId, int supervisorId, String workerIds, int managerId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SUPERVISOR_INCHARGE, supervisorId);
        values.put(COLUMN_WORKERS, workerIds);
        values.put(COLUMN_MANAGER_INCHARGE, managerId);

        int rowsAffected = db.update(TABLE_LOCATIONS, values, COLUMN_LOCATION_ID + " = ?", new String[]{String.valueOf(locationId)});
        db.close();

        return rowsAffected > 0;
    }

    public boolean insertTool(String toolName, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("toolName", toolName);
        values.put("quantity", quantity);

        long result = db.insert("Tools", null, values);
        db.close();
        return result != -1;
    }


    public boolean insertToolsAllocated(int toolId, int supervisorId, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TOOL_ALLOCATION_TOOL_ID, toolId);
        values.put(COLUMN_TOOL_ALLOCATION_SUPERVISOR_ID, supervisorId);
        values.put(COLUMN_TOOL_ALLOCATION_QUANTITY, quantity);

        long id = db.insert(TABLE_TOOLS_ALLOCATED, null, values);
        db.close();

        return id != -1; // Returns true if insertion was successful, false otherwise
    }


    public boolean updateToolsQuantity(int toolId, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TOOL_QUANTITY, quantity);

        int rowsAffected = db.update(TABLE_TOOLS, values, COLUMN_TOOL_ID + "=?", new String[]{String.valueOf(toolId)});
        db.close();

        return rowsAffected > 0;
    }
    @SuppressLint("Range")
    public List<Tool> getAllTools() {
        List<Tool> tools = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TOOLS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Tool tool = new Tool();
                tool.setToolId(cursor.getInt(cursor.getColumnIndex(COLUMN_TOOL_ID)));
                tool.setToolName(cursor.getString(cursor.getColumnIndex(COLUMN_TOOL_NAME)));
                tool.setQuantity(cursor.getInt(cursor.getColumnIndex(COLUMN_TOOL_QUANTITY)));
                tools.add(tool);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tools;
    }


    @SuppressLint("Range")
    public int getToolQuantity(int toolId) {
        int quantity = 0;
        String selectQuery = "SELECT " + COLUMN_TOOL_QUANTITY + " FROM " + TABLE_TOOLS + " WHERE " + COLUMN_TOOL_ID + "=?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{toolId+""});

        if (cursor.moveToFirst()) {
            quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_TOOL_QUANTITY));
        }
        cursor.close();
        db.close();
        return quantity;
    }

    public boolean insertTask(String taskName, int supervisorId, String date, String startTime, String workers, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_NAME, taskName);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_START_TIME, startTime);
        values.put(COLUMN_WORKERS, workers);
        values.put(COLUMN_SUPERVISOR_INCHARGE, supervisorId);

        if (isDateTimeBeforeNow(date, startTime)) {
            values.put(COLUMN_STATUS, STATUS_COMPLETED);
        } else {
            values.put(COLUMN_STATUS, STATUS_UPCOMING);
        }

        int managerInchargeId = getManagerInchargeId(supervisorId);
        values.put(COLUMN_MANAGER_INCHARGE, managerInchargeId);

        long result = db.insert(TABLE_TASKS, null, values);
        return result != -1;
    }

    private boolean isDateTimeBeforeNow(String date, String time) {
        String dateTimeStr = date + " " + time;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);

        try {
            Date dateTime = sdf.parse(dateTimeStr);
            Date now = Calendar.getInstance().getTime();
            return dateTime != null && dateTime.before(now);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    @SuppressLint("Range")
    private int getManagerInchargeId(int supervisorId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_MANAGER_INCHARGE +
                " FROM " + TABLE_LOCATIONS +
                " WHERE " + COLUMN_SUPERVISOR_INCHARGE + " = '" + supervisorId + "'" +
                " LIMIT 1";  // Take only the first occurrence

        Cursor cursor = db.rawQuery(query, null);
        int managerInchargeId = -1;

        if (cursor.moveToFirst()) {
            managerInchargeId = cursor.getInt(cursor.getColumnIndex(COLUMN_MANAGER_INCHARGE));
        }

        cursor.close();
        return managerInchargeId;
    }

    public boolean insertRequest(int toolId, int requestorId, int quantity, int accepted) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("toolId", toolId);
        values.put("requestorId", requestorId);
        values.put("quantity", quantity);
        values.put("accepted", accepted);

        long result = db.insert("toolsRequested", null, values);
        return result != -1;
    }

    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String[] projection = {
                    COLUMN_TASK_ID,
                    COLUMN_TASK_NAME,
                    COLUMN_DATE,
                    COLUMN_START_TIME,
                    COLUMN_WORKERS,
                    COLUMN_STATUS,
                    COLUMN_MANAGER_INCHARGE,
                    COLUMN_SUPERVISOR_INCHARGE
            };
            cursor = db.query(
                    TABLE_TASKS,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Task task = new Task();
                    task.setJobId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TASK_ID)));
                    task.setJobType(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK_NAME)));

                    int managerId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MANAGER_INCHARGE));
                    int supervisorId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SUPERVISOR_INCHARGE));

                    task.setManagerIncharge(getUserNameById(managerId));
                    task.setSupervisorIncharge(getUserNameById(supervisorId));
                    task.setStartTime(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_START_TIME)));
                    task.setDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)));
                    taskList.add(task);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        db.close();

        return taskList;
    }

    private String getUserNameById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        String userName = "";

        try {
            String[] projection = {COLUMN_USER_NAME};
            String selection = COLUMN_USER_ID + " = ?";
            String[] selectionArgs = {String.valueOf(userId)};

            cursor = db.query(
                    TABLE_USERS,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                userName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NAME));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        db.close();

        return userName;
    }


    @SuppressLint("Range")
    public List<Task> getTasksByStatus(String status) {
        List<Task> taskList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COLUMN_TASK_ID,
                COLUMN_TASK_NAME,
                COLUMN_DATE,
                COLUMN_START_TIME,
                COLUMN_WORKERS,
                COLUMN_STATUS,
                COLUMN_MANAGER_INCHARGE,
                COLUMN_SUPERVISOR_INCHARGE
        };

        String selection = COLUMN_STATUS + "=?";
        String[] selectionArgs = {status};

        Cursor cursor = db.query(
                TABLE_TASKS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setJobId(cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_ID)));
                task.setJobType(cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME)));
                task.setManagerIncharge(getUserNameById(cursor.getInt(cursor.getColumnIndex(COLUMN_MANAGER_INCHARGE))));
                task.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                task.setStartTime(cursor.getString(cursor.getColumnIndex(COLUMN_START_TIME)));
                task.setSupervisorIncharge(getUserNameById(cursor.getInt(cursor.getColumnIndex(COLUMN_SUPERVISOR_INCHARGE))));
                task.setStatus(cursor.getString(cursor.getColumnIndex(COLUMN_STATUS)));
                taskList.add(task);
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return taskList;
    }

    @SuppressLint("Range")
    public int getUserIdBySupervisorName(String supervisorName) {
        SQLiteDatabase db = this.getReadableDatabase();
        int userId = -1;

        Cursor cursor = db.query(
                TABLE_USERS,
                new String[]{COLUMN_USER_ID},
                COLUMN_USER_NAME + " = ?",
                new String[]{supervisorName},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID));
            cursor.close();
        }

        return userId;
    }

    // Method to update user rating by user ID
    public boolean updateUserRating(int userId, float rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RATING, rating);

        int rowsAffected = db.update(
                TABLE_USERS,
                values,
                COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(userId)}
        );

        return rowsAffected > 0;
    }
    @SuppressLint("Range")
    public List<Supervisor> getSupervisors() {
        List<Supervisor> supervisorList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_USER_NAME + ", " + COLUMN_RATING +
                " FROM " + TABLE_USERS +
                " WHERE " + COLUMN_ROLE + " = 'Supervisor'" +
                " ORDER BY " + COLUMN_RATING + " DESC";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String supervisorName = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME));
                float rating = cursor.getFloat(cursor.getColumnIndex(COLUMN_RATING));

                Supervisor supervisor = new Supervisor(supervisorName, rating);
                supervisorList.add(supervisor);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();

        supervisorList.sort((supervisor1, supervisor2) ->
                Float.compare(supervisor2.getRating(), supervisor1.getRating()));

        return supervisorList;
    }

    @SuppressLint("Range")
    public List<Request> getNotificationsList() {
        List<Request> notificationsList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM toolsRequested";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int requestId = cursor.getInt(cursor.getColumnIndex("requestId"));
                int toolId = cursor.getInt(cursor.getColumnIndex("toolId"));
                int requestorId = cursor.getInt(cursor.getColumnIndex("requestorId"));
                int quantity = cursor.getInt(cursor.getColumnIndex("quantity"));
                int accepted = cursor.getInt(cursor.getColumnIndex("accepted"));

                String toolName = getToolNameById(toolId);
                String supervisorName = getUserNameById(requestorId);

                Request request = new Request(requestId, toolId, requestorId, quantity, accepted, toolName, supervisorName);
                notificationsList.add(request);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return notificationsList;
    }

    @SuppressLint("Range")
    public String getToolNameById(int toolId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String toolName = null;

        String[] columns = {COLUMN_TOOL_NAME};
        String selection = COLUMN_TOOL_ID + "=?";
        String[] selectionArgs = {String.valueOf(toolId)};

        Cursor cursor = db.query(TABLE_TOOLS, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            toolName = cursor.getString(cursor.getColumnIndex(COLUMN_TOOL_NAME));
        }

        cursor.close();
        db.close();
        return toolName;
    }

    public boolean updateRequest(int requestId, int accepted) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("accepted", accepted);

        String whereClause = "requestId=?";
        String[] whereArgs = {String.valueOf(requestId)};

        int rowsAffected = db.update("toolsRequested", values, whereClause, whereArgs);
        db.close();

        return rowsAffected > 0;
    }
    @SuppressLint("Range")
    public List<SupervisorNotificationModel> getSupervisorNotifications(int userId) {
        List<SupervisorNotificationModel> notificationList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(
                    TABLE_SUPERVISOR_REQUESTS,
                    new String[]{COLUMN_SUPERVISOR_REQUEST_ID, COLUMN_REQUEST_TO, COLUMN_MESSAGE},
                    COLUMN_REQUEST_TO + " = ?",
                    new String[]{String.valueOf(userId)},
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                     int requestId = cursor.getInt(cursor.getColumnIndex(COLUMN_SUPERVISOR_REQUEST_ID));
                    int requestToUserId = cursor.getInt(cursor.getColumnIndex(COLUMN_REQUEST_TO));
                    String message = cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE));

                    SupervisorNotificationModel notification = new SupervisorNotificationModel(requestId, requestToUserId, message);
                    notificationList.add(notification);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return notificationList;
    }

    @SuppressLint("Range")
    public List<AssignedLocationsModel> getAssignedLocations() {
        List<AssignedLocationsModel> assignedLocationsList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(
                    TABLE_LOCATIONS,
                    new String[]{COLUMN_LOCATION_ID, COLUMN_LOCATION_NAME, COLUMN_MANAGER_INCHARGE, COLUMN_SUPERVISOR_INCHARGE, COLUMN_NO_OF_WORKERS, COLUMN_PHONE_NUMBER, COLUMN_WORKERS},
                    null,
                    null,
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int locationId = cursor.getInt(cursor.getColumnIndex(COLUMN_LOCATION_ID));
                    String locationName = cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION_NAME));
                    int managerInchargeId = cursor.getInt(cursor.getColumnIndex(COLUMN_MANAGER_INCHARGE));
                    int supervisorInchargeId = cursor.getInt(cursor.getColumnIndex(COLUMN_SUPERVISOR_INCHARGE));
                    int noOfWorkers = cursor.getInt(cursor.getColumnIndex(COLUMN_NO_OF_WORKERS));
                    String phoneNumber = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER));
                    String workerIds = cursor.getString(cursor.getColumnIndex(COLUMN_WORKERS));

                    // Get supervisor name from supervisorInchargeId
                    String supervisorName = getUserNameById(supervisorInchargeId);

                    // Get worker names from workerIds
                    ArrayList<String> workers = getWorkerNames(workerIds);

                    AssignedLocationsModel assignedLocation = new AssignedLocationsModel(locationId, locationName, managerInchargeId, supervisorInchargeId, noOfWorkers, phoneNumber, workers, supervisorName);
                    assignedLocationsList.add(assignedLocation);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return assignedLocationsList;
    }

    // Helper method to get worker names from comma-separated worker IDs
    private ArrayList<String> getWorkerNames(String workerIds) {
        ArrayList<String> workerNames = new ArrayList<>();

        if (workerIds != null && !workerIds.isEmpty()) {
            String[] workerIdArray = workerIds.split(",");
            for (String workerId : workerIdArray) {
                // Get worker name by worker ID from the users table
                String workerName = getUserNameById(Integer.parseInt(workerId));
                if (!workerName.isEmpty()) {
                    workerNames.add(workerName);
                }
            }
        }

        return workerNames;
    }

}