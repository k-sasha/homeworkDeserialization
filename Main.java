package workWithFiles.deserialization;

import workWithFiles.serialization.GameProgress;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    public static void main(String[] args) {
        // распаковка архива в папке savegames.
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(
                "/Users/sasha/IdeaProjects/netologyCourse/Games/savegames/zip1.zip"))) {

            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("done!");

        // Считывание и десериализация одного из разархивированных файлов save.dat
        GameProgress gameProgress = null;

        try (FileInputStream fis = new FileInputStream(
                "/Users/sasha/IdeaProjects/netologyCourse/Games/savegames/save1.dat");
             ObjectInputStream object = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) object.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // Вывод в консоль состояние сохранненой игры
        System.out.println(gameProgress);

    }
}
