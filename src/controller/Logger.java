package controller;

import model.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Logger {
    User user;
    Date lastChangeDate;
    File userFile;
    FileWriter userWriter;
    static File mainFile = new File("Logger.txt");

    static FileWriter mainWriter;

    public void log(String s, LogType logType) {
        try {
            lastChangeDate = new Date();
            if (user != null) {
                userWriter.append("[").append(logType.name()).append("], [").append(String.valueOf(lastChangeDate)).append("], ").append(s).append("\n");
                mainWriter.append("[").append(logType.name()).append("], [").append(String.valueOf(lastChangeDate)).append("], ").append(s).append("      user : ").append(user.username).append("\n");
                userWriter.flush();
            } else
                mainWriter.append("[").append(logType.name()).append("], [").append(String.valueOf(lastChangeDate)).append("], ").append(s).append("\n");
            mainWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean equal(String username) {
        return user.username.equalsIgnoreCase(username);
    }


    public enum LogType {
        Error, Info, Alarm,Command,Replay;
    }

    static private final ArrayList<Logger> loggers = new ArrayList<>();
    static private Logger logger;

    private Logger(User user) {
        userFile = new File("user_log_"+user.username + ".txt");
        if (!userFile.exists()) {
            try {
                userFile.createNewFile();
                userWriter = new FileWriter(userFile);
                lastChangeDate = new Date();
                userWriter.write("creatDate : " + lastChangeDate + "\nusername : " + user.username + "\n\n");
                userWriter.flush();
                log("creat new logger file", LogType.Info);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else try {
            Scanner scanner = new Scanner(userFile);
            String s = "";
            while (scanner.hasNextLine())
                s += scanner.nextLine() + "\n";
            userWriter = new FileWriter(userFile);
            userWriter.append(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.user = user;
    }

    private Logger() {
        if (!mainFile.exists()) {
            try {
                mainFile.createNewFile();
                mainWriter = new FileWriter(mainFile);
                lastChangeDate = new Date();
                mainWriter.write("creatDate : " + lastChangeDate + "\n\n");
                mainWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            try {
                Scanner scanner = new Scanner(mainFile);
                String s = "";
                while (scanner.hasNextLine())
                    s += scanner.nextLine() + "\n";
                mainWriter = new FileWriter(mainFile);
                mainWriter.append(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public static Logger getLogger() {
        if (logger == null)
            logger = new Logger();
        return logger;
    }

    public static Logger getLogger(User user) {
        if (user == null)
            return getLogger();
        for (Logger logger : loggers) {
            if (logger.equal(user.username)) {
                return logger;
            }
        }
        loggers.add(new Logger(user));
        return loggers.get(loggers.size() - 1);
    }
}
