import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

class Song {
    private String title;
    private double duration;

    public Song(String title, double duration) {
        this.title = title;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public double getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return this.title + ": " + this.duration + " min";
    }
}

class Album {
    public String name;
    public String artist;
    private List<Song> songs;

    public Album(String name, String artist) {
        this.name = name;
        this.artist = artist;
        this.songs = new ArrayList<>();
    }

    public boolean addSong(String title, double duration) {
        if (findSong(title) == null) {
            this.songs.add(new Song(title, duration));
            return true;
        }
        return false;
    }

    private Song findSong(String title) {
        for (Song checkedSong : this.songs) {
            if (checkedSong.getTitle().equals(title)) {
                return checkedSong;
            }
        }
        return null;
    }

    public boolean addToPlaylist(String title, List<Song> playlist) {
        Song checkedSong = findSong(title);
        if (checkedSong != null) {
            playlist.add(checkedSong);
            return true;
        }
        System.out.println("The song " + title + " is not in this album");
        return false;
    }

    public boolean addToPlaylist(int trackNumber, List<Song> playlist) {
        int index = trackNumber - 1;
        if ((index >= 0) && (index < this.songs.size())) {
            playlist.add(this.songs.get(index));
            return true;
        }
        System.out.println("This album does not have a track " + trackNumber);
        return false;
    }
}

class Playlist {
    public String name;
    public List<Song> songs;

    public Playlist(String name) {
        this.name = name;
        this.songs = new LinkedList<>();
    }

    public boolean addSong(Song song) {
        if (!this.songs.contains(song)) {
            this.songs.add(song);
            return true;
        }
        return false;
    }

    public void play() {
        ListIterator<Song> iterator = songs.listIterator();
        if (songs.isEmpty()) {
            System.out.println("No songs in playlist");
            return;
        }

        try (Scanner scanner = new Scanner(System.in)) {
            boolean quit = false;
            boolean forward = true;

            System.out.println("Now playing " + iterator.next().toString());
            printMenu();

            while (!quit) {
                int action = scanner.nextInt();
                scanner.nextLine();

                switch (action) {
                    case 0:
                        System.out.println("Playlist complete");
                        quit = true;
                        break;
                    case 1:
                        if (!forward) {
                            if (iterator.hasNext()) {
                                iterator.next();
                            }
                            forward = true;
                        }
                        if (iterator.hasNext()) {
                            System.out.println("Now playing " + iterator.next().toString());
                        } else {
                            System.out.println("End of playlist");
                            forward = false;
                        }
                        break;
                    case 2:
                        if (forward) {
                            if (iterator.hasPrevious()) {
                                iterator.previous();
                            }
                            forward = false;
                        }
                        if (iterator.hasPrevious()) {
                            System.out.println("Now playing " + iterator.previous().toString());
                        } else {
                            System.out.println("Start of playlist");
                            forward = true;
                        }
                        break;
                    case 3:
                        if (forward) {
                            if (iterator.hasPrevious()) {
                                System.out.println("Replaying " + iterator.previous().toString());
                                forward = false;
                            } else {
                                System.out.println("Start of playlist");
                            }
                        } else {
                            if (iterator.hasNext()) {
                                System.out.println("Replaying " + iterator.next().toString());
                                forward = true;
                            } else {
                                System.out.println("End of playlist");
                            }
                        }
                        break;
                    case 4:
                        if (iterator.hasNext()) {
                            iterator.next();
                            iterator.remove();
                            System.out.println("Song removed from playlist");
                            if (iterator.hasNext()) {
                                System.out.println("Now playing " + iterator.next().toString());
                            } else if (iterator.hasPrevious()) {
                                System.out.println("Now playing " + iterator.previous().toString());
                            }
                        } else {
                            System.out.println("No song to remove");
                        }
                        break;
                    case 5:
                        printList();
                        break;
                    case 6:
                        printMenu();
                        break;
                }
            }
        }
    }

    private void printMenu() {
        System.out.println("Available actions:\npress");
        System.out.println("0 - to quit\n" +
                           "1 - to play next song\n" +
                           "2 - to play previous song\n" +
                           "3 - to replay the current song\n" +
                           "4 - to remove the current song\n" +
                           "5 - list songs in the playlist\n" +
                           "6 - print available actions.");
    }

    private void printList() {
        System.out.println("Playlist:");
        for (Song song : songs) {
            System.out.println(song.toString());
        }
    }
}

public class SongPlaylistApp {
    public static void main(String[] args) {
        Album album = new Album("FavSongs", "Naveetha");
        album.addSong("Song1- Kaise Hua", 4.5);
        album.addSong("Song2- Lover", 3.5);
        album.addSong("Song3- Nenu Nuvvantu", 5.0);

        Playlist playlist = new Playlist("My Playlist");
        album.addToPlaylist("Song1- Kaise Hua", playlist.songs);
        album.addToPlaylist("Song2- Lover", playlist.songs);
        album.addToPlaylist("Song3- Nenu Nuvvantu", playlist.songs);

        playlist.play();
    }
}
