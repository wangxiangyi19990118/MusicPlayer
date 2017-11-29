package service;

import data.Music;
import data.MusicPlayer;

import java.util.List;



public class MusicPlayerService {
	
	/*
	 * 功能:添加歌曲到播放器
	 */
	public static void addMusicToPlayer(List<Music> nMusics) {
		MusicPlayer musicPlayer=MusicPlayer.getInstance();
		musicPlayer.removeAllMusics();
		for (Music music : nMusics) {
			musicPlayer.addMusic(music);
		}
	}
	
	

}
