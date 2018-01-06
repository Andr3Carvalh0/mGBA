package io.mgba.Data.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.File;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "Cheats", foreignKeys = @ForeignKey(entity = Game.class, parentColumns = "id", childColumns = "idFK", onDelete = CASCADE))
public class Cheat {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @PrimaryKey
    @ColumnInfo(name = "idFK")
    @NonNull
    private File id_FK;

    @ColumnInfo(name = "cheatName")
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public File getId_FK() {
        return id_FK;
    }

    public void setId_FK(@NonNull File id_FK) {
        this.id_FK = id_FK;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
