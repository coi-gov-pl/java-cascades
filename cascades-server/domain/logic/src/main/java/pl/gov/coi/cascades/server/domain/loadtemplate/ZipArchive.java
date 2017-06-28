package pl.gov.coi.cascades.server.domain.loadtemplate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.wavesoftware.eid.exceptions.Eid;
import pl.wavesoftware.eid.exceptions.EidIllegalStateException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static java.lang.String.join;
import static pl.wavesoftware.eid.utils.EidPreconditions.checkNotNull;
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
    private final Path tempPath = tryToExecute(() -> Files.createTempDirectory(PREFIX), "20170627:141443");

    ZipArchive(Upload upload) {
        this(
            upload,
            DEFAULT_LOGGER
        );
    }

    @Override
    public void close() {
        File directory = new File(tempPath.toString());
        File[] fList = directory.listFiles();
        checkNotNull(fList, "20170628:091348");
        for (File file : fList){
            checkFile(file);
        }
        tryToExecute(() -> Files.delete(tempPath), "20170628:095723");
    }

    private void checkFile(File file) {
        if (!file.delete()) {
            if (logger.isErrorEnabled()) {
                logger.error(new Eid("20170628:095307").makeLogMessage(
                    "File is NOT successfully deleted."
                ));
            }
        } else {
            if (logger.isInfoEnabled()) {
                logger.info(new Eid("20170628:095310").makeLogMessage(
                    "File is successfully deleted."
                ));
            }
        }
    }

    public Path getPath() {
        return tempPath;
    }

    ZipArchive ensureUnzipped() {
        try (ZipInputStream zis = new ZipInputStream(new BufferedInputStream(this.getUpload().getInputStream()))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                File file = new File(join(File.separator, tempPath.toString(), entry.getName()));
                byte[] buffer = new byte[BUFFER_SIZE];
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    unzip(zis, buffer, fos);
                }
            }
        } catch (IOException e) {
            throw new EidIllegalStateException("20170605:113002", e);
        }
        return this;
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
