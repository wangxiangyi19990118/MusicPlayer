package dao;

import data.MusicSheetToMusic;

import java.sql.SQLException;
import java.util.List;



public interface MusicSheetToMusicDao {

	public int insert(MusicSheetToMusic mstm) throws SQLException;

	public void delete(int id) throws SQLException;

	public List<Integer> findByMusicSheetId(int id) throws SQLException;
}
