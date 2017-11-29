/**
 * 
 */
package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dao.MusicDaoImpl;
import dao.MusicSheetDaoImpl;
import dao.MusicSteetToMusicDaoImpl;
import data.Music;
import data.MusicPlayer;
import data.MusicSheet;
import data.MusicSheetToMusic;


/**
 * @author sky
 *
 */
public class MusicSheetService {
	private MusicSheetDaoImpl musicSheetDaoImpl;
	private MusicSteetToMusicDaoImpl musicSteetToMusicDaoImpl;
	private MusicDaoImpl musicDaoImpl;

	public MusicSheetService() {
		musicSheetDaoImpl = new MusicSheetDaoImpl();
		musicSteetToMusicDaoImpl = new MusicSteetToMusicDaoImpl();
		musicDaoImpl = new MusicDaoImpl();
	}

	/**
	 * 功能:获取歌单信息
	 * 
	 * @param 
	 */
	public MusicSheet getMusicSheet(int msid) {
		return musicSheetDaoImpl.getById(msid);
	}

	/**
	 * 功能: 获取某人的所有歌单
	 * 
	 * @return：返回所有歌单
	 */
	public List<MusicSheet> getMusicSheetsByCreatorId(String creatorId) {
		return musicSheetDaoImpl.getByCreatorId(creatorId);
	}

	/**
	 * 功能:获取歌单的歌曲列表
	 * 
	 */
	public List<Music> getMusicByMusicSheetId(int msid) {
		List<Music> musics = new ArrayList<Music>();
		List<MusicSheetToMusic> musicSheetToMusics = musicSteetToMusicDaoImpl.getByMusicSheetId(msid);
		for (MusicSheetToMusic musicSheetToMusic : musicSheetToMusics) {
			musics.add(musicDaoImpl.getById(musicSheetToMusic.getMusicId()));
		}
		return musics;
	}

	/**
	 * 功能:删除歌单
	 * 
	 * @param: 歌单的ID
	 */
	public boolean deleteMusicSheet(int msid) {
		return musicSheetDaoImpl.delete(msid);
	}

	/**
	 * 功能: 移除歌单中歌曲
	 * 
	 * @since 目前沒有此功能
	 * @param: 歌单歌曲中的关联ID
	 */
	public boolean removeMusicFromMusicSheet(int cid) {
		return false;
	}

	/**
	 * 功能: 创建歌单
	 * 
	 * @param: name:歌单名字
	 *             creator:歌单创建者ID creatorName：歌单创建者名字 pictureUrl: 歌单封面ID
	 * @return: 创建成功返回歌单的ID，否则返回 -1
	 */
	public int createMusicSheet(String name, String creatorId, String creatorName, String pictureUrl) {
		MusicSheet musicSheet = new MusicSheet();
		musicSheet.setName(name);
		musicSheet.setCreator(creatorName);
		musicSheet.setCreatorId(creatorId);
		//如果没有选择封面。新new的歌单对象已经有封面了，则不需要替换。
		if(!pictureUrl.isEmpty())
			musicSheet.setPicture(pictureUrl);
		// 插入歌单
		musicSheetDaoImpl.insert(musicSheet);
		// 获取插入歌单的ID
		return musicSheetDaoImpl.getByUuid(musicSheet.getUuid()).getId();
	}

	/**
	 * 功能：添加歌曲到歌单
	 * 
	 * @param msid
	 *            歌单ID
	 * @param musicLis
	 *            歌曲列表,可变参数个数，自动识别为数组
	 * @return
	 *
	 * 		以下调用方法皆可以 addMusicsToMusicSheet(msid,music);
	 *         addMusicsToMusicSheet(msid,music1,music2,music3);
	 *         addMusicsToMusicSheet(msid,music[]);
	 * 
	 */
	public void addMusicsToMusicSheet(int msid, Music... musicLis) {
		for (Music music : musicLis) {
			// 判断歌单中是否已经有本首歌曲
			if (!musicSteetToMusicDaoImpl.musicIsExist(msid, music.getId())) {
				MusicSheetToMusic musicSheetToMusic = new MusicSheetToMusic();
				musicSheetToMusic.setMusicId(music.getId());
				musicSheetToMusic.setMusicSheetId(msid);
				musicSteetToMusicDaoImpl.insert(musicSheetToMusic);
			}
		}
	}

	/**
	 * 功能: 播放歌单全部歌曲
	 * 
	 * @param: 歌单ID
	 * @throws Exception
	 *             播放器歌曲出错信息
	 */
	public void playAllSongs(int msid) {
		List<Music> musicsList = getMusicByMusicSheetId(msid);
		MusicPlayerService.addMusicToPlayer(musicsList);
		// 开线程自己播放全部歌曲
		MusicPlayer musicPlayer = MusicPlayer.getInstance();
		Thread tPlay = new Thread() {
			public void run() {
				do {
					try {
						musicPlayer.autoNext();

					} catch (Exception e) {
						e.printStackTrace();
					}
				} while (musicPlayer.getNowMusic() != null);
			}
		};
		tPlay.start();
	}

	/**
	 * 功能：用于全部播放别人的歌单,下载一首就可以播放一首，下载后自动添加到播放器
	 * 
	 * @param musicItems
	 * @throws Exception
	 */
	public void playAllSongs(Map<String, String> musicItems) {
		MusicPlayer musicPlayer = MusicPlayer.getInstance();
		Thread tDownload = new Thread() {
			public void run() {
				Music music = null;
				System.out.println("歌单歌曲：" + musicItems.size());
				for (String md5 : musicItems.keySet()) {
					music = SokectService.downloadMusic(md5, musicItems.get(md5));
					System.out.println("当前歌曲：" + music.getName());
					if (music != null) {
						musicPlayer.addMusic(music);
						try {
							musicPlayer.autoNext();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		};
		tDownload.start();
	}

	/**
	 * 播放选中歌曲，如果本地下载好了，直接返回歌曲MUSIC，调用方调用播放器的setNowMusic,play即可， 否则返回null，提示先下载
	 * 
	 * @param md5Value
	 */
	public Music playThisSong(String md5Value) {
		// 查看数据库有无本首歌曲
		return musicDaoImpl.getByMd5Value(md5Value);
	}

	/**
	 * 功能: 下载歌单全部歌曲 新开后台线程调用
	 * 
	 * @param <md5,name>
	 */
	public void downloadAllMusic(Map<String, String> musicItems) {
		// 开启守护进程下载
		Thread tBackDownload = new Thread(new Runnable() {
			@Override
			public void run() {
				SokectService.downloadMusic(musicItems);
			}
		});
		tBackDownload.setDaemon(true);
		tBackDownload.start();
	}
}
