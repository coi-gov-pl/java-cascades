package pl.gov.coi.cascades.server.domain.loadtemplate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.wavesoftware.eid.exceptions.EidIllegalStateException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static pl.wavesoftware.eid.utils.EidPreconditions.tryToExecute;

/**
 * @author <a href="agnieszka.celuch@coi.gov.pl">Agnieszka Celuch</a>
 * @since 27.06.17.
 */
@AllArgsConstructor
public class ZipArchive implements AutoCloseable {
    private static final String PREFIX = "LoadTemplateZipArchive";
    private static final Logger DEFAULT_LOGGER = LoggerFactory.getLogger(ZipArchive.class);
    private static final int BUFFER_SIZE = 2048;
    @Getter(AccessLevel.PACKAGE)
    private final Upload upload;
    private Logger logger;
    private final Path tempPath;

    ZipArchive(Upload upload) {
        this(
            upload,
            DEFAULT_LOGGER,
            tryToExecute(() -> Files.createTempDirectory(PREFIX), "20170627:141443")
        );
    }

    ZipArchive(Upload upload, Logger logger) {
        this(
            upload,
            logger,
            tryToExecute(() -> Files.createTempDirectory(PREFIX), "20170703:125502")
        );
    }

    @Override
    public void close() {
        tryToExecute(() -> Files.walkFileTree(tempPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        }), "20170629:160642");
    }

    public Path getPath() {
        return tempPath;
    }

    ZipArchive ensureUnzipped() {
        Path path = tempPath;
        try (InputStream is = this.getUpload().getInputStream();
             BufferedInputStream bis = new BufferedInputStream(is);
             ZipInputStream zis = new ZipInputStream(bis)) {

            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                    unzipEntry(zis, entry);
                } else {
                    path = path.resolve(entry.getName());
                    File dir = path.toFile();
                    dir.mkdirs();
                }
            }
        } catch (IOException e) {
            throw new EidIllegalStateException("20170605:113002", e);
        }
        return new ZipArchive(upload, logger, path);
    }

    private void unzipEntry(ZipInputStream zis, ZipEntry entry) throws IOException {
        Path path = tempPath.resolve(entry.getName());
        byte[] buffer = new byte[BUFFER_SIZE];
        try (FileOutputStream fos = new FileOutputStream(path.toFile())) {
            unzip(zis, buffer, fos);
        }
    }

    private static void unzip(ZipInputStream zis, byte[] buffer, FileOutputStream fos) {
        try (BufferedOutputStream bos = new BufferedOutputStream(fos, buffer.length)) {
            int size;
            while ((size = zis.read(buffer, 0, buffer.length)) != -1) {
                bos.write(buffer, 0, size);
            }
        } catch (IOException e) {
            throw new EidIllegalStateException("20170628:115221", e);
        }
    }

}
